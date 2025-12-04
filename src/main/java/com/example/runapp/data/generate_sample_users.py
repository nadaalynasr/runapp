import random
from datetime import datetime, timedelta
from hashlib import sha256

# generate a random datetime within the last year for created_at
def random_datetime_last_year():
    now = datetime.now()
    one_year_ago = now - timedelta(days=365)
    random_seconds = random.randint(0, int((now - one_year_ago).total_seconds()))
    return one_year_ago + timedelta(seconds=random_seconds)

# insert statement array
insert_statements = []

# generate 100 random users with random data
for i in range(1, 101): 
    user_id = i
    username = f"user{i}"
    email = f"user{i}@example.com"
    # hash the password
    password_hash = sha256(f"password{i}".encode()).hexdigest()

    first_name = f"First{i}"
    last_name = f"Last{i}"


    insert = f"INSERT INTO users (username, email, password_hash, first_name, last_name) VALUES ('{username}', '{email}', '{password_hash}', '{first_name}', '{last_name}');"
    insert_statements.append(insert)

# write to sample_users.sql
with open('sample_users.sql', 'w') as f:
    f.write("USE runapp_platform;\n\n")
    for stmt in insert_statements:
        f.write(stmt + "\n")
