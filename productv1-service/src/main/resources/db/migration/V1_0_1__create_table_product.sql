create table if not exists product_sc.product
(
    id serial primary key,
    name varchar not null,
    description text not null,
    created_dt timestamp not null,
    updated_dt timestamp not null,
    price decimal not null
);