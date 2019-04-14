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

create table if not exists ledger (
    uuid varchar(36),
    source varchar(15),
    target varchar(15),
    quantity decimal,
    created datetime,
    primary key (uuid)
) engine = INNODB;

create index ledger_source on ledger (source);
create index ledger_target on ledger (target);

insert into ledger(uuid, source, target, quantity, created)
values ('fd946d15-7f3c-4a81-a96e-1e48044b17f7', '', '456987', 100, sysdate());
insert into ledger(uuid, source, target, quantity, created)
values ('fd946d16-7f3c-4a81-a96e-1e48044b17f7', '456987', '963741', 50, sysdate());
insert into ledger(uuid, source, target, quantity, created)
values ('fd947d16-7f3c-5a81-a96e-1e48044b17f3', '456987', '963741', 25, sysdate());
