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

-- Counts the total number of runs a user has logged
-- http://localhost:8080/stats
"SELECT COUNT(*)
FROM run
WHERE user_id = ?";

-- Sums the total distance of all runs a user has logged
-- http://localhost:8080/stats
"SELECT COALESCE(SUM(distance_meters), 0)
FROM run
WHERE user_id = ?";

-- Finds the run with the best speed for a user and returns its elapsed time, used as the user's best time
-- http://localhost:8080/stats
"SELECT elapsed_time
FROM run
WHERE user_id = ?
  AND elapsed_time > 0
  AND (distance_meters / elapsed_time) = (
      SELECT MAX(distance_meters / elapsed_time)
      FROM run
      WHERE user_id = ?
        AND elapsed_time > 0
  )
LIMIT 1";

-- Inserts or updates the aggregated stats for a user in the stats table each time the Stats page is accessed
-- http://localhost:8080/stats
"INSERT INTO stats (user_id, total_runs, total_distance_meters, best_time_seconds, last_updated)
VALUES (?, ?, ?, ?, NOW())
ON DUPLICATE KEY UPDATE
  total_runs = VALUES(total_runs),
  total_distance_meters = VALUES(total_distance_meters),
  best_time_seconds = VALUES(best_time_seconds),
  last_updated = VALUES(last_updated)";

-- Retrieves the top three runs for a user iredred by speed, if there are ties, the most recent runs are prioritized
-- http://localhost:8080/stats
"SELECT run_id, run_date, elapsed_time, distance_meters
FROM run
WHERE user_id = ?
  AND elapsed_time > 0
ORDER BY (distance_meters / elapsed_time) DESC, run_date DESC
LIMIT 3";