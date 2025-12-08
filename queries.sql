-- Creates a new group run
-- This adds a new group run with necessary details to the database to be displayed on the groups page.
-- http://localhost:8080/create-group
insert into group_table (group_name, created_by_user_id, is_private, member_count, next_run_date, next_run_time, member_max) values (?, ?, ?, ?, ?, ?, ?);

-- Adds creator to group
-- This adds the creator of the group to the group they just created.
-- http://localhost:8080/create-group
insert into group_membership (user_id, group_id, joined_at, role) values (?, LAST_INSERT_ID(), NOW(), ?);

-- Deletes a private group
-- This deletes a private when the creater (only user) leaves the group.
-- http://localhost:8080/groups
delete from group_table where group_id = ? and is_private = true;

-- Used for authentication, displaying data for the logged in user
-- http://localhost:8080/login
SELECT * FROM users WHERE username = ?;

-- Used to register a new user with our app and store proper login data
-- http://localhost:8080/register
INSERT INTO users (username, email, password_hash, first_name, last_name, created_at) VALUES (?, ?, ?, ?, ?, NOW());

-- Displays all logged runs within our app for the home page. 
-- http://localhost:8080
SELECT r.*, u.username FROM run r JOIN users u ON r.user_id = u.user_id ORDER BY r.run_date DESC;

-- Creates a new run in the database
-- http://localhost:8080/log-run
insert into run (user_id, run_date, start_time, end_time, elapsed_time, distance_meters, bpm) VALUES (?, ?, ?, ?, ?, ?, ?);