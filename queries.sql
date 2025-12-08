-- Used for authentication, displaying data for the logged in user
-- http://localhost:8080/login
"SELECT * FROM users WHERE username = ?";

-- Used to register a new user with our app and store proper login data
-- http://localhost:8080/register
"INSERT INTO users (username, email, password_hash, first_name, last_name, created_at) VALUES (?, ?, ?, ?, ?, NOW())";

-- Displays all logged runs within our app for the home page. 
-- http://localhost:8080
"SELECT r.*, u.username FROM run r JOIN users u ON r.user_id = u.user_id ORDER BY r.run_date DESC";

-- Creates a new run in the database
-- http://localhost:8080/log-run
"insert into run (user_id, run_date, start_time, end_time, elapsed_time, distance_meters, bpm) VALUES (?, ?, ?, ?, ?, ?, ?)";