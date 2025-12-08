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

-- Gets all groups the current user is a member of
-- http://localhost:8080/groups
"SELECT 
    g.group_id, g.group_name, g.member_count, g.member_max,
    g.next_run_date, g.next_run_time
FROM group_table g
JOIN group_membership gm ON g.group_id = gm.group_id
WHERE gm.user_id = ?";


-- Gets all public groups the user is NOT in (Discover Groups)
-- http://localhost:8080/groups
"SELECT 
    g.group_id, g.group_name, g.member_count, g.member_max,
    g.next_run_date, g.next_run_time
FROM group_table g
WHERE g.group_id NOT IN (
    SELECT group_id FROM group_membership WHERE user_id = ?
)
AND g.is_private = false";

-- Adds a user to a group and updates the member count
-- http://localhost:8080/groups
"INSERT IGNORE INTO group_membership (user_id, group_id, joined_at)
VALUES (?, ?, NOW());

UPDATE group_table
SET member_count = member_count + 1
WHERE group_id = ?";

-- Removes a user from a group and updates the member count
-- http://localhost:8080/groups
"DELETE FROM group_membership
WHERE user_id = ? AND group_id = ?;

UPDATE group_table
SET member_count = member_count - 1
WHERE group_id = ?";

-- Deletes the entire group if it is private and the last member leaves
-- http://localhost:8080/groups/{id}/leave
"DELETE FROM group_table
WHERE group_id = ? AND is_private = true";