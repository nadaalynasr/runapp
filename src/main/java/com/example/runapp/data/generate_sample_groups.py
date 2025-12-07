import random
from datetime import datetime, timedelta

# generate a random datetime within the next year - keeping hour exact
def random_datetime_next_year():
    now = datetime.now()
    one_year = now + timedelta(days=365)
    random_seconds = random.randint(0, int((one_year - now).total_seconds()))
    random_datetime = now + timedelta(seconds=random_seconds)
    random_on_the_hour = random_datetime.replace(minute=0, second=0)
    return random_on_the_hour



# insert statement array
insert_statements = []

# generate 100 random group runs
for i in range(1, 101):
    group_id = i
    group_name = f"Group Run {i}"
    created_by_user_id = random.randint(0, 100)
    is_private = False
    member_count = random.randint(1, 5)
    next_datetime = random_datetime_next_year()
    next_run_date = next_datetime.date()
    next_run_time = next_datetime.strftime('%H:%M:%S')
    member_max = random.randint(1,4)

    insert = f"INSERT INTO group_table (group_id, group_name, created_by_user_id, is_private, member_count, next_run_date, next_run_time, member_max) VALUES ('{group_id}', '{group_name}', {created_by_user_id}, {int(is_private)}, {member_count}, '{next_run_date}', '{next_run_time}', {member_max});"
    insert_statements.append(insert)

# write to sample_groups.sql
with open('sample_groups.sql', 'w') as f:
    f.write("USE runapp_platform;\n\n")
    for stmt in insert_statements:
        f.write(stmt + "\n")
