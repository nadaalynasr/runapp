create database if not exists runapp_platform;

use runapp_platform;

create table if not exists users (
    user_id int auto_increment,
    username varchar(100) not null,
    email varchar(100) not null,
    password_hash varchar(255) not null,
    first_name varchar(50),
    last_name varchar(50),
    last_login datetime,
    created_at datetime,
    primary key (user_id),
    unique (username),
    unique (email),
    constraint username_min_length check (char_length(trim(username)) >= 2),
    constraint first_name_min_len check (char_length(trim(first_name)) >= 2),
    constraint last_name_min_len check (char_length(trim(last_name)) >= 2)
);

create table if not exists run (
    run_id int auto_increment,
    run_title varchar(50),
    user_id int not null,
    run_date date,
    start_time datetime,
    end_time datetime,
    elapsed_time int,
    distance_meters decimal(7,1),
    bpm decimal(4,1),
    primary key (run_id),
    foreign key (user_id) references users(user_id)
);

create table if not exists stats (
    stats_id int auto_increment,
    user_id int,
    total_runs int default 0,
    total_distance_meters decimal(10,1) default 0,
    best_time_seconds int,
    last_updated datetime,
    primary key (stats_id),
    foreign key (user_id) references users(user_id)
);

create table if not exists group_table ( 
    group_id int auto_increment,
    group_name varchar(100),
    created_by_user_id int,
    is_private boolean,
    member_count int not null,
    next_run_date date,
    next_run_time time,
    member_max int,
    primary key (group_id),
    foreign key (created_by_user_id) references users(user_id),
    unique (group_name)
);

create table if not exists group_membership (
    user_id int,
    group_id int,
    joined_at datetime,
    role varchar(50),
    primary key (user_id, group_id),
    foreign key (user_id) references users(user_id),
    foreign key (group_id) references group_table(group_id)
);

create table if not exists personal_best (
    personal_best_id int auto_increment,
    user_id int,
    run_id int,
    distance_meters decimal(7,1),
    elapsed_time int,
    achieved_on datetime,
    primary key (personal_best_id),
    foreign key (user_id) references users(user_id),
    foreign key (run_id) references run(run_id)
);

-- Indexing for group filter optimization
CREATE INDEX idx_group_membership_user_id ON group_membership(user_id);
CREATE INDEX idx_group_table_is_private ON group_table(is_private);
