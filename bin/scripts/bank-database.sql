create database bank;

create user 'bank'@'%' identified by 'REVvuuJj';
grant all privileges on bank.* to 'bank'@'%';
flush privileges;

use bank;

create table if not exists client (
    uid varchar(15),
    name varchar(50),
    primary key (uid)
) engine = INNODB;