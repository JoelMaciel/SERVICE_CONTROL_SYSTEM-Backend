CREATE TABLE client_role (
  client_id bigint NOT NULL,
  role_id bigint NOT NULL,

  PRIMARY KEY (client_id , role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table client_role add constraint FK_client_roles
foreign key (client_id) references client (id);

alter table client_role add constraint FK_roles_client
foreign key (role_id) references roles (role_id);