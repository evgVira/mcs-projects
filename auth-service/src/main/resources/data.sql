insert into public.user(name, email, password) values('sam', 'sam@gmail.com', '$2a$12$ijryzgxt40JD/16zMcW1lug4lrTc5aNmKgoe5xKM2kWML1dFlbeNa'),
                                                     ('max', 'max@gmail.com', '$2a$12$E85OJys9tKDPWeCZmakVmOyhRCLJAGFdJ5/49/l72jxk3.2TCWl4m');

insert into public.role(name) values ('ROLE_USER'), ('ROLE_ADMIN');

insert into user_role(user_id, role_id) values (1, 1), (2, 2);