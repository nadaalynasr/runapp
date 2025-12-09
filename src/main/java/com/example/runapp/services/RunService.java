package com.example.runapp.services;

import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.runapp.models.Run;

@Service
public class RunService {

    @Autowired
    private DataSource dataSource;

    public boolean createRun(Integer userId, 
        Date runDate, 
        Timestamp startTime, 
        Timestamp endTime, 
        Integer elapsedTime, 
        Double distanceMeters, 
        Double bpm,
        String runTitle) 
        throws SQLException {
        final String sql = "insert into run (user_id, run_date, start_time, end_time, elapsed_time, distance_meters, bpm, run_title) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setDate(2, runDate);
            stmt.setTimestamp(3, startTime);
            stmt.setTimestamp(4, endTime);
            stmt.setInt(5, elapsedTime);
            stmt.setDouble(6, distanceMeters);
            stmt.setDouble(7, bpm);
            stmt.setString(8, runTitle);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            }
    }


        public List<Run> getRuns() throws SQLException {
        List<Run> runs = new ArrayList<>();
        String sql = "SELECT r.*, u.username FROM run r JOIN users u ON r.user_id = u.user_id ORDER BY r.run_date DESC";


        try (Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {


            while (rs.next()) {
                Integer runId = rs.getInt("run_id");
                Integer userId = rs.getInt("user_id");
                String username = rs.getString("username");
                Date runDate = rs.getDate("run_date");
                Timestamp startTime = rs.getTimestamp("start_time");
                Timestamp endTime = rs.getTimestamp("end_time");
                Integer elapsedTime = rs.getInt("elapsed_time");
                Double distanceMeters = rs.getDouble("distance_meters");
                Double bpm = rs.getDouble("bpm");
                String runTitle = null;
                try { runTitle = rs.getString("run_title"); } catch (Exception e) { }

                Run run = new Run(runTitle, userId, runDate, startTime, endTime, elapsedTime, distanceMeters, bpm);
                run.setRunId(runId);
                run.setUsername(username);
                runs.add(run);
            }
        }
        return runs;
    }


}





