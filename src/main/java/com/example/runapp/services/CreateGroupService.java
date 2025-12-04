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
     * @param date Date of the run
     * @param time Time of the run
     * @param memberMax Maximum number of members allowed
     * @return true if the group was created successfully, false otherwise
     */
    public boolean createGroup(String userId, String groupName, LocalDate date, LocalTime time, int memberMax) throws SQLException{
        String sql = "insert into group_table (group_name, description, created_by_user_id, is_private, "
                + "member_count) values (?, ?, ?, ?, ?)" ; // ", next_run_date, next_run_time";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, groupName);
            pstmt.setString(2, ""); // description can be empty for now
            pstmt.setInt(3, Integer.parseInt(userId));
            pstmt.setBoolean(4, false);
            pstmt.setInt(5, 1); // initial member count is 1
            // pstmt.setDate(6, java.sql.Date.valueOf(date));
            // pstmt.setTime(7, java.sql.Time.valueOf(time));
            // pstmt.setInt(8, memberMax);
            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return  false;
        }
    }
    
}
