GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' IDENTIFIED BY 'test1357' WITH GRANT OPTION;
flush privileges;

create schema `users`;
create schema `orders`;
create schema `catalogs`;

create table `users`.users_sample
(
    id         int auto_increment
        primary key,
    user_id    varchar(20)                        not null,
    pwd        varchar(20)                        not null,
    name       varchar(20)                        not null,
    created_at datetime default CURRENT_TIMESTAMP null
);

