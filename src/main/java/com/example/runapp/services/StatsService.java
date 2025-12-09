package com.example.runapp.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class StatsService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static class StatsDTO {
        public int totalRuns;
        public double totalDistanceMeters;
        public Integer bestTimeSeconds;  
        public LocalDateTime lastUpdated;
    }

    public static class RunCard {
        public String title;
        public String length;
        public String time;
        public String date;

        public RunCard(String title, String length, String time, String date) {
            this.title = title;
            this.length = length;
            this.time = time;
            this.date = date;
        }
    }


    /**
     * Recalculate stats from the run table (using 3 separate aggregation
     * queries) and upsert into the stats table.
     * Called whenever the stats page is opened.
     */
    public StatsDTO updateAndGetStatsForUser(String userId) {
        StatsDTO stats = new StatsDTO();

        // 1) TOTAL RUNS (aggregation: COUNT)
        String totalRunsSql =
            "SELECT COUNT(*) " +
            "FROM run " +
            "WHERE user_id = ?";

        Integer totalRuns = jdbcTemplate.queryForObject(
            totalRunsSql,
            Integer.class,
            userId
        );
        stats.totalRuns = (totalRuns != null ? totalRuns : 0);

        // 2) TOTAL DISTANCE (aggregation: SUM)
        String totalDistanceSql =
            "SELECT COALESCE(SUM(distance_meters), 0) " +
            "FROM run " +
            "WHERE user_id = ?";

        Double totalDistance = jdbcTemplate.queryForObject(
            totalDistanceSql,
            Double.class,
            userId
        );
        stats.totalDistanceMeters = (totalDistance != null ? totalDistance : 0.0);

        // 3) BEST TIME based on distance/time (aggregation: MAX in a subquery)
        //
        // max (distance_meters / elapsed_time) and return its elapsed_time.
        // elapsed_time > 0 to avoid division by zero.
        String bestTimeSql =
            "SELECT elapsed_time " +
            "FROM run " +
            "WHERE user_id = ? " +
            "  AND elapsed_time > 0 " +
            "  AND (distance_meters / elapsed_time) = ( " +
            "      SELECT MAX(distance_meters / elapsed_time) " +
            "      FROM run " +
            "      WHERE user_id = ? " +
            "        AND elapsed_time > 0 " +
            "  ) " +
            "LIMIT 1";

        Integer bestTimeSeconds = null;
        try {
            bestTimeSeconds = jdbcTemplate.queryForObject(
                bestTimeSql,
                Integer.class,
                userId,  // outer query
                userId   // inner query
            );
        } catch (EmptyResultDataAccessException ex) {
            // no runs with elapsed_time > 0 or 0 runs
            bestTimeSeconds = null;
        }
        stats.bestTimeSeconds = bestTimeSeconds;

        // Update stats table
        String upsertSql =
            "INSERT INTO stats (user_id, total_runs, total_distance_meters, best_time_seconds, last_updated) " +
            "VALUES (?, ?, ?, ?, NOW()) " +
            "ON DUPLICATE KEY UPDATE " +
            "  total_runs = VALUES(total_runs), " +
            "  total_distance_meters = VALUES(total_distance_meters), " +
            "  best_time_seconds = VALUES(best_time_seconds), " +
            "  last_updated = VALUES(last_updated)";

        jdbcTemplate.update(
            upsertSql,
            userId,
            stats.totalRuns,
            stats.totalDistanceMeters,
            stats.bestTimeSeconds
        );

        stats.lastUpdated = LocalDateTime.now();
        return stats;
    }

    /**
     * Top 3 runs for the cards on the stats page.
     * Ordered by distance/time, then by most recent date.
     */
    public List<RunCard> getTopRunsForUser(String userId) {
        String sql =
            "SELECT COALESCE(run_title, 'Untitled Run') as run_title, run_date, elapsed_time, distance_meters " +
            "FROM run " +
            "WHERE user_id = ? " +
            "  AND elapsed_time > 0 " +
            "ORDER BY (distance_meters / elapsed_time) DESC, run_date DESC " +
            "LIMIT 3";

        return jdbcTemplate.query(
            sql,
            new Object[] { userId },
            (rs, rowNum) -> {
                String title = rs.getString("run_title");
                double miles = rs.getDouble("distance_meters") / 1609.34;
                String length = String.format("%.1f miles", miles);
                int totalSec = rs.getInt("elapsed_time");
                String time = formatSeconds(totalSec);
                String date = rs.getDate("run_date").toString();
                return new RunCard(title, length, time, date);
            }
        );
    }

    private String formatSeconds(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%d:%02d", minutes, seconds);
    }
}
