

set foreign_key_checks = 0;

delete from client;
delete from service_provided;

set foreign_key_checks = 1;

alter table client auto_increment = 1;
alter table service_provided auto_increment = 1;

insert into client (id, name, cpf, creation_date, update_date) values (1, 'Joel Maciel', '81451095074', utc_timestamp, utc_timestamp);
insert into client (id, name, cpf, creation_date, update_date) values (2, 'Maria Abreu', '66287125080', utc_timestamp, utc_timestamp);
insert into client (id, name, cpf, creation_date, update_date) values (3, 'Paulo de Tarso', '41719379068', utc_timestamp, utc_timestamp);
insert into client (id, name, cpf, creation_date, update_date) values (4, 'Angela Maria', '42664776002', utc_timestamp, utc_timestamp);
