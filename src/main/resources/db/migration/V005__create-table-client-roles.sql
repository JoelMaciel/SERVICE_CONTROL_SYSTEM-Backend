CREATE TABLE roles (
  role_id bigint NOT NULL,
  role_name varchar(30) NOT NULL,

  primary key (role_id)
) engine=InnoDB default charset=utf8;