import random
from datetime import datetime, timedelta

# fake user ids 1 - 100
user_ids = list(range(1, 101)) 

# generate a random datetime within the last year
def random_datetime_last_year():
    now = datetime.now()
    one_year_ago = now - timedelta(days=365)
    random_seconds = random.randint(0, int((now - one_year_ago).total_seconds()))
    return one_year_ago + timedelta(seconds=random_seconds)

# insert statement array
insert_statements = []

for i in range(1500):
    user_id = random.choice(user_ids)
    elapsed_time = random.randint(1800, 5400)  
    distance_meters = round(random.uniform(1000, 10000), 1) 
    bpm = round(random.uniform(120, 200), 1) 

    # random start time
    start_time = random_datetime_last_year()
    run_date = start_time.date()

    # calcualte end time from start time & elapsed 
    end_time = start_time + timedelta(seconds=elapsed_time)

    # format time & date 
    start_str = start_time.strftime('%Y-%m-%d %H:%M:%S')
    end_str = end_time.strftime('%Y-%m-%d %H:%M:%S')
    date_str = run_date.strftime('%Y-%m-%d')

    insert = f"INSERT INTO run (user_id, run_date, start_time, end_time, elapsed_time, distance_meters, bpm) VALUES ({user_id}, '{date_str}', '{start_str}', '{end_str}', {elapsed_time}, {distance_meters}, {bpm});"
    insert_statements.append(insert)

# write to sample_runs.sql
with open('sample_runs.sql', 'w') as f:
    f.write("USE runapp_platform;\n\n")
    for stmt in insert_statements:
        f.write(stmt + "\n")
