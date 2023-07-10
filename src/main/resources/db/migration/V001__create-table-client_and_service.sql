create table client (
        id bigint not null auto_increment,
        name varchar(150) not null,
        email varchar(50) not null unique,
        cpf varchar(11) not null unique,
        creation_date timestamp not null,
        update_date timestamp not null,

        primary key (id)
    )   engine=InnoDB default charset=utf8;

    create table service_provided (
        id bigint not null auto_increment,
        description varchar(255) not null,
        price decimal(19,2) not null,
        creation_date timestamp not null ,
        update_date timestamp not null,
        client_id bigint not null,

        primary key (id)
    )   engine=InnoDB default charset=utf8;

alter table service_provided add constraint FK_client_service_provided
foreign key (client_id) references client (id);


