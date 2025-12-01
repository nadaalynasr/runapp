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
        Double bpm) 
        throws SQLException {
        final String sql = "INSERT INTO run (user_id, run_date, start_time, end_time, elapsed_time, distance_meters, bpm) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setDate(2, runDate);
            stmt.setTimestamp(3, startTime);
            stmt.setTimestamp(4, endTime);
            stmt.setInt(5, elapsedTime);
            stmt.setDouble(6, distanceMeters);
            stmt.setDouble(7, bpm);


            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            }
    }

}





