CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

create table IF NOT EXISTS user_profile
(
    id                  uuid primary key default uuid_generate_v4(),
    app_hud_id          varchar,
    gender              varchar(20),
    birth_date          date,
    birth_time          time without time zone,
    tz                  varchar(50),
    city                varchar(100),
    language            varchar(100)
 );

create unique index IF NOT EXISTS user_profile_app_hud_id on user_profile (app_hud_id);

create table IF NOT EXISTS app_hud
(
    app_hud_id                  varchar primary key not null,
    user_profile_id             uuid references user_profile not null
);

create table IF NOT EXISTS user_transaction
(
    id                          uuid primary key default uuid_generate_v4(),
    transaction_id              varchar,
    user_profile_id             uuid references user_profile not null,
    created_at                  timestamp default now()
);

