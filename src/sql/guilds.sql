create table guilds(
    id                    bigserial
                          constraint guilds_pk
                          primary key,
    guild_id              varchar(20),
    command_prefix        varchar(1),
    verification_role_id  varchar(20),
    verification_input_id varchar(20),
    audit_log_id          varchar(20),
    mod_audit_log_id      varchar(20),
    mod_mail_in_id        varchar(20),
    mod_mail_out_id       varchar(20),
    message_log_id        varchar(20),
    last_incident_id      bigint,
    last_ticket_id        bigint,
    administrators        jsonb,
    moderators            jsonb
);

alter table guilds
    owner to friendlybot;
