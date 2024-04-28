create table IF NOT EXISTS user_profile
(
    id                  uuid primary key not null default gen_random_uuid(),
    app_hud_id          varchar,
    gender              varchar(20),
    birthDate           date,
    birthTime           time without time zone,
    tz                  varchar(50),
    city                varchar(100),
    language            varchar(100)
 );

create unique index IF NOT EXISTS user_profile_app_hud_id on user_profile (app_hud_id);
