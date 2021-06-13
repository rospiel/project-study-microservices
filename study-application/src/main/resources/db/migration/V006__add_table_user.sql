create table users (
	id bigint not null auto_increment,
	username varchar(50) not null,
	password varchar(50) not null,
	email varchar(100) not null,
	enabled boolean not null,
	primary key (id)
) engine=InnoDB default charset=UTF8MB4;

create table authorities (
	id bigint not null auto_increment,
	authority varchar(50) not null,
	primary key (id)
) engine=InnoDB default charset=UTF8MB4;

create table users_authorities (
    users_id bigint not null,
    authorities_id bigint not null,
    primary key (users_id, authorities_id)
) engine=InnoDB default charset=UTF8MB4;

alter table users_authorities add constraint FK_users_authorities_users foreign key (users_id) references users (id);

alter table users_authorities add constraint FK_users_authorities_authorities foreign key (authorities_id) references authorities (id);