package com.example.runapp.services;

import com.example.runapp.models.GroupsView;
import com.example.runapp.models.TrendingView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class GroupsService {

    @Autowired
    private DataSource dataSource;

    // Build display string
    private String formatNextRun(Date date, Time time) {
        if (date == null || time == null) return "No run scheduled";
        return date.toString() + " " + time.toString();
    }

    // Get groups the user belongs to
    public List<GroupsView> getGroupsForUser(int userId) {
        String sql =
            "SELECT g.group_id, g.group_name, g.member_count, g.member_max, g.next_run_date, g.next_run_time " +
            "FROM group_table g " +
            "JOIN group_membership gm ON g.group_id = gm.group_id " +
            "WHERE gm.user_id = ?";

        List<GroupsView> list = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("group_id");
                String name = rs.getString("group_name");
                int memberCount = rs.getInt("member_count");
                int memberMax = rs.getInt("member_max");
                Date nextDate = rs.getDate("next_run_date");
                Time nextTime = rs.getTime("next_run_time");

                String nextRunDisplay = formatNextRun(nextDate, nextTime);

                list.add(new GroupsView(id, name, nextRunDisplay, memberCount, memberMax, true));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    // Get groups user is NOT in
    public List<GroupsView> getGroupsUserIsNotIn(int userId) {
        String sql =
            "SELECT g.group_id, g.group_name, g.member_count, g.member_max, g.next_run_date, g.next_run_time " +
            "FROM group_table g " +
            "WHERE g.group_id NOT IN (" +
            "    SELECT group_id FROM group_membership WHERE user_id = ?" +
            ")" +
            "   and g.is_private = false";
            

        List<GroupsView> list = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("group_id");
                String name = rs.getString("group_name");
                int memberCount = rs.getInt("member_count");
                int memberMax = rs.getInt("member_max");
                Date nextDate = rs.getDate("next_run_date");
                Time nextTime = rs.getTime("next_run_time");

                String nextRunDisplay = formatNextRun(nextDate, nextTime);

                list.add(new GroupsView(id, name, nextRunDisplay, memberCount, memberMax, false));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    // Join group
    public void joinGroup(int userId, int groupId) {
        String insert =
            "INSERT IGNORE INTO group_membership (user_id, group_id, joined_at) VALUES (?, ?, NOW())";

        String update =
            "UPDATE group_table SET member_count = member_count + 1 WHERE group_id = ?";

        try (Connection conn = dataSource.getConnection()) {

            PreparedStatement stmt1 = conn.prepareStatement(insert);
            stmt1.setInt(1, userId);
            stmt1.setInt(2, groupId);
            stmt1.executeUpdate();

            PreparedStatement stmt2 = conn.prepareStatement(update);
            stmt2.setInt(1, groupId);
            stmt2.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Leave group
    public void leaveGroup(int userId, int groupId) {
        String delete =
            "DELETE FROM group_membership WHERE user_id = ? AND group_id = ?";

        String update =
            "UPDATE group_table SET member_count = member_count - 1 WHERE group_id = ?";

        // deletes the group itself if private group (because only member "creator" is leaving and no one else can join)
        String deleteGroup = 
            "delete from group_table where group_id = ? and is_private = true";

        try (Connection conn = dataSource.getConnection()) {

            PreparedStatement stmt1 = conn.prepareStatement(delete);
            stmt1.setInt(1, userId);
            stmt1.setInt(2, groupId);
            stmt1.executeUpdate();

            PreparedStatement stmt2 = conn.prepareStatement(update);
            stmt2.setInt(1, groupId);
            stmt2.executeUpdate();

            PreparedStatement stmt3 = conn.prepareStatement(deleteGroup);
            stmt3.setInt(1, groupId);
            stmt3.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Pull trending groups
    public List<TrendingView> getTrendingGroups() {
        String sql =
            "SELECT g.group_id, g.group_name, g.member_count, g.next_run_date, g.next_run_time " +
            "FROM group_table g order by g.member_count desc limit 5";
        List<TrendingView> list = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("group_id");
                String name = rs.getString("group_name");
                int memberCount = rs.getInt("member_count");
                int memberMax = rs.getInt("member_max");
                Date nextDate = rs.getDate("next_run_date");
                Time nextTime = rs.getTime("next_run_time");

                String nextRunDisplay = formatNextRun(nextDate, nextTime);

                list.add(new TrendingView(id, name, nextRunDisplay, memberCount, false));
            }

        } catch (SQLException e) { 
            throw new RuntimeException(e);
        }

        return list;
    }
}
