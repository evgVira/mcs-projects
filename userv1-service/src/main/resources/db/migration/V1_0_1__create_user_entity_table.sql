create table if not exists user_entity_sc.user_entity
(
    id serial not null primary key,
    username varchar not null,
    email varchar not null,
    password varchar not null,
    created_dt timestamp not null,
    updated_dt timestamp not null
);