create table users
(
    id                 serial
                       constraint users_pk
                       primary key,
    guild_id           varchar(20),
    user_id            varchar(20),
    custom_color_role  varchar(7),
    discord_tag        varchar(40),
    last_changed_color timestamp
);

alter table users
    owner to postgres;