create table if not exists public.user
(
    id serial primary key not null,
    name varchar not null,
    email varchar unique not null,
    password varchar not null
);

create table if not exists public.role
(
    id serial primary key not null,
    name varchar not null
);

create table if not exists public.user_role
(
    user_id int not null references public.user(id),
    role_id int not null references public.role(id)
);