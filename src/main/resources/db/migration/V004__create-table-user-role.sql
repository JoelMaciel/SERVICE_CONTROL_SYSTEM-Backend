CREATE TABLE user_role (
  user_id bigint NOT NULL,
  role_id bigint NOT NULL,

  PRIMARY KEY (user_id , role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table user_role add constraint FK_user_role
foreign key (user_id) references user (id);

alter table user_role add constraint FK_role_user
foreign key (role_id) references role (role_id);