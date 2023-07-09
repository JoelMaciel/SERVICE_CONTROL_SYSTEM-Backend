

set foreign_key_checks = 0;

delete from client;
delete from service_provided;
delete from roles;
delete from client_role;

set foreign_key_checks = 1;

alter table client auto_increment = 1;
alter table service_provided auto_increment = 1;
alter table roles auto_increment = 1;
alter table client_role auto_increment = 1;

insert into client (id, username, cpf, email, password, creation_date, update_date) values (1, 'JoelMaciel', '81451095074', 'joel@teste.com', '$2a$10$EeVAODuyBscvNB2ZdifeBeQMyxZamlZSbbL0NBDHXMtXC9eJgR9HC', utc_timestamp, utc_timestamp);
insert into client (id, username, cpf, email, password, creation_date, update_date) values (2, 'MariaAbreu', '66287125080', 'maria@teste.com', '$2a$10$EeVAODuyBscvNB2ZdifeBeQMyxZamlZSbbL0NBDHXMtXC9eJgR9HC', utc_timestamp, utc_timestamp);
insert into client (id, username, cpf, email, password, creation_date, update_date) values (3, 'PaulodeTarso', '41719379068', 'paulo@teste.com', '$2a$10$EeVAODuyBscvNB2ZdifeBeQMyxZamlZSbbL0NBDHXMtXC9eJgR9HC', utc_timestamp, utc_timestamp);
insert into client (id, username, cpf, email, password, creation_date, update_date) values (4, 'RômuloFigueira', '42664776002', 'romulo@teste.com', '$2a$10$EeVAODuyBscvNB2ZdifeBeQMyxZamlZSbbL0NBDHXMtXC9eJgR9HC', utc_timestamp, utc_timestamp);

insert into service_provided (id, description, price, creation_date, client_id) values (1, 'Swap SSD memory', 250, utc_timestamp ,1 );
insert into service_provided (id, description, price, creation_date, client_id) values (2, 'Air Conditioning Maintenance', 380, utc_timestamp ,2 );
insert into service_provided (id, description, price, creation_date, client_id) values (3, 'Cell phone battery replacement', 120, utc_timestamp ,4 );
insert into service_provided (id, description, price, creation_date, client_id) values (4, 'Side leak coffee maker', 59, utc_timestamp ,3 );
insert into service_provided (id, description, price, creation_date, client_id) values (5, 'Defective keyboard due to water infiltration', 150, utc_timestamp ,2 );
insert into service_provided (id, description, price, creation_date, client_id) values (6, 'Corrupt HD replacement', 525, utc_timestamp ,1 );

insert into roles (role_id, role_name) values (1 , 'ROLE_ADMIN');
insert into roles (role_id, role_name) values (2 , 'ROLE_USER');

insert into client_role (client_id, role_id) values (1, 1);
insert into client_role (client_id, role_id) values (2, 2);

