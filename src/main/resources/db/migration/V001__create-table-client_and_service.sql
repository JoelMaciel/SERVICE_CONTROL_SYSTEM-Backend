create table client (
        id bigint not null auto_increment,
        name varchar(150) not null,
        cpf varchar(11) not null unique,
        creation_date timestamp,
        update_date timestamp,

        primary key (id)
    )   engine=InnoDB default charset=utf8;

    create table service (
        id bigint not null auto_increment,
        description varchar(255) not null,
        price decimal(19,2) not null,
        client_id bigint,
        primary key (id)
    )   engine=InnoDB default charset=utf8;

    alter table service
    add constraint FK_client_service
    foreign key (client_id)
    references client (id);


