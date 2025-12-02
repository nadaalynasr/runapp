/**
Copyright (c) 2024 Sami Menik, PhD. All rights reserved.

This is a project developed by Dr. Menik to give the students an opportunity to apply database concepts learned in the class in a real world project. Permission is granted to host a running version of this software and to use images or videos of this work solely for the purpose of demonstrating the work to potential employers. Any form of reproduction, distribution, or transmission of the software's source code, in part or whole, without the prior written consent of the copyright owner, is strictly prohibited.
*/
package com.example.runapp.services;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import jakarta.servlet.http.HttpSession;
import com.example.runapp.models.User;

/**
 * This is a service class that enables user related functions.
 * The class interacts with the database through a dataSource instance.
 * See authenticate and registerUser functions for examples.
 * This service object is special — its lifetime is limited to a user session.
 */
@Service
@SessionScope
public class UserService {

    // Enables database access
    private final DataSource dataSource;

    // Handles password hashing
    private final BCryptPasswordEncoder passwordEncoder;

    // Keeps a reference to the current logged-in user (for convenience)
    private User loggedInUser = null;

    // Access to HTTP session for authentication persistence
    @Autowired
    private HttpSession session;

    /**
     * Constructor for dependency injection.
     */
    @Autowired
    public UserService(DataSource dataSource) {
        this.dataSource = dataSource;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    /**
     * Checks if a user is currently authenticated.
     */
    public boolean isAuthenticated() {
        return session.getAttribute("user") != null;
    }

    /**
     * Authenticate user given the username and the password and
     * stores user object for the logged in user in session scope.
     * Returns true if authentication is succesful. False otherwise.
     */
    public boolean authenticate(String username, String password) throws SQLException {
        final String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String storedPasswordHash = rs.getString("password_hash");
                    boolean isPassMatch = passwordEncoder.matches(password, storedPasswordHash);

                    if (isPassMatch) {
                        String userId = rs.getString("user_id");
                        String firstName = rs.getString("first_name");
                        String lastName = rs.getString("last_name");

                        // Initialize and store the logged-in user
                        loggedInUser = new User(userId, firstName, lastName);
                        session.setAttribute("user", loggedInUser);
                    }
                    return isPassMatch;
                }
            }
        }
        return false;
    }

    /**
     * Logs out the current user by clearing both the local variable and session.
     */
    public void unAuthenticate() {
        loggedInUser = null;
        session.invalidate(); // clear session completely
    }

    /**
     * Retrieves the currently logged-in user.
     */
    public User getLoggedInUser() {
        return (User) session.getAttribute("user");
    }

    /**
     * Registers a new user with the given details.
     * Returns true if registration is successful.
     */
    public boolean registerUser(String username, String password, String firstName, String lastName, String email)
            throws SQLException {

            System.out.println("IN REGISTER");

        final String registerSql =
                "INSERT INTO users (username, email, password_hash, first_name, last_name, created_at) VALUES (?, ?, ?, ?, ?, NOW())";

        try (Connection conn = dataSource.getConnection();
            PreparedStatement registerStmt = conn.prepareStatement(registerSql)) {

                System.out.println("TRYING TO REGISTER");

            registerStmt.setString(1, username);
            registerStmt.setString(2, email);
            registerStmt.setString(3, passwordEncoder.encode(password));
            registerStmt.setString(4, firstName);
            registerStmt.setString(5, lastName);

            int rowsAffected = registerStmt.executeUpdate();
            System.out.println("ROWS AFFECTED:" + rowsAffected );
            return rowsAffected > 0;
        }
    }

}
