package com.example.runapp.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

/**
 * Service for creating run groups.
 */
@Service
public class CreateGroupService {

    @Autowired
    private DataSource dataSource;

    /**
     * Creates a new run group given group name, date, time, and member max.
     * 
     * @param groupName Name of the group
     * @param date Date of the group run
     * @param time Time of the group run
     * @param memberMax Maximum number of members allowed
     * @param isPrivate Whether the run group is private
     * @return true if the group was created successfully, false otherwise
     */
    public boolean createGroup(String userId, String groupName, LocalDate date, LocalTime time, int memberMax, boolean isPrivate) throws SQLException{
        // this adds the group to the group_table
        String sql = "insert into group_table (group_name, created_by_user_id, is_private, "
                + "member_count, next_run_date, next_run_time, member_max) values (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, groupName);
            pstmt.setInt(2, Integer.parseInt(userId));
            pstmt.setBoolean(3, isPrivate);
            pstmt.setInt(4, 1); // initial member count is 1
            pstmt.setDate(5, java.sql.Date.valueOf(date));
            pstmt.setTime(6, java.sql.Time.valueOf(time));
            pstmt.setInt(7, memberMax);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return  false;
        }

        // this adds the creater as a member of the group in group_membership table
        String memberSql = "insert into group_membership (user_id, group_id, joined_at, role) "
                + "values (?, LAST_INSERT_ID(), NOW(), ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(memberSql)) {

            pstmt.setInt(1, Integer.parseInt(userId));
            pstmt.setString(2, "creator");
            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return  false;
        }
    }
    
}
