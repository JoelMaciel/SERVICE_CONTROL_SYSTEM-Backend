

set foreign_key_checks = 0;

delete from client;
delete from service_provided;

set foreign_key_checks = 1;

alter table client auto_increment = 1;
alter table service_provided auto_increment = 1;

insert into client (id, name, cpf, creation_date, update_date) values (1, 'Joel Maciel', '81451095074', utc_timestamp, utc_timestamp);
insert into client (id, name, cpf, creation_date, update_date) values (2, 'Maria Abreu', '66287125080', utc_timestamp, utc_timestamp);
insert into client (id, name, cpf, creation_date, update_date) values (3, 'Paulo de Tarso', '41719379068', utc_timestamp, utc_timestamp);
insert into client (id, name, cpf, creation_date, update_date) values (4, 'Rômulo Figueira', '42664776002', utc_timestamp, utc_timestamp);

insert into service_provided (id, description, price, creation_date, client_id) values (1, 'Swap SSD memory', 250, utc_timestamp ,1 );
insert into service_provided (id, description, price, creation_date, client_id) values (2, 'Air Conditioning Maintenance', 380, utc_timestamp ,2 );
insert into service_provided (id, description, price, creation_date, client_id) values (3, 'Cell phone battery replacement', 120, utc_timestamp ,4 );
insert into service_provided (id, description, price, creation_date, client_id) values (4, 'Side leak coffee maker', 59, utc_timestamp ,3 );
insert into service_provided (id, description, price, creation_date, client_id) values (5, 'Defective keyboard due to water infiltration', 150, utc_timestamp ,2 );
insert into service_provided (id, description, price, creation_date, client_id) values (6, 'Corrupt HD replacement', 525, utc_timestamp ,1 );