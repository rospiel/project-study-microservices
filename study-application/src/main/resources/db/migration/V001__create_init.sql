create table city (
    id bigint not null auto_increment comment 'Unique ID',
    name varchar(60) comment 'Name of the city',
    state_id bigint comment 'Unique ID from table State',
    primary key (id)
) engine=InnoDB default charset=UTF8MB4 comment 'Table to keep info about the city from any address';

create table `group` (
    id bigint not null auto_increment comment 'Unique ID',
    name varchar(60) not null comment 'Name of the group',
    primary key (id)
) engine=InnoDB default charset=UTF8MB4 comment 'Table to keep info about the group of people with some privileges';

create table group_permission (
    group_id bigint not null comment 'Unique ID from table group',
    permission_id bigint not null comment 'Unique ID from table permission',
    primary key (group_id, permission_id)
) engine=InnoDB default charset=UTF8MB4 comment 'Table to associate group and permission ';

create table kitchen (
    id bigint not null auto_increment comment 'Unique ID',
    name varchar(60) comment 'Name of the kitchen',
    primary key (id)
) engine=InnoDB default charset=UTF8MB4 comment 'Table to keep info about the kitchen';

create table payment_method (
    id bigint not null auto_increment comment 'Unique ID',
    description varchar(60) comment 'Description of the payment method',
    primary key (id)
) engine=InnoDB default charset=UTF8MB4 comment 'Table to keep info about the payment method, for example credit card';

create table permission (
    id bigint not null auto_increment comment 'Unique ID',
    description varchar(60) comment 'Description of the permission',
    name varchar(60) comment 'Name of the permission',
    primary key (id)
) engine=InnoDB default charset=UTF8MB4 comment 'Table to keep info about the permission, to access some feature for example';

create table product (
    id bigint not null auto_increment comment 'Unique ID',
    description varchar(60) not null comment 'Description of the product',
    enable bit not null comment 'To signal if the product is or no active',
    name varchar(60) not null comment 'Name of the product',
    price decimal(19,2) not null comment 'Price of the product',
    restaurant_id bigint,
    primary key (id)
) engine=InnoDB default charset=UTF8MB4 comment 'Table to keep info about the product';

create table restaurant (
    id bigint not null auto_increment comment 'Unique ID',
    freight_rate decimal(19,2) comment 'The cost to ship something',
    name varchar(100) not null comment 'Name of the restaurant',
    address_complement varchar(100) comment 'Address complement of the restaurant',
    address_neighborhood varchar(60) comment 'Address neighborhood of the restaurant',
    address_number varchar(7) comment 'Address number of the restaurant',
    address_street varchar(100) comment 'Address street of the restaurant',
    address_zip_code varchar(10) comment 'Address zip code of the restaurant',
    city bigint comment 'Unique ID from table city',
    kitchen_id bigint not null comment 'Unique ID from table kitchen',
    date_create datetime not null comment 'Creation date of the restaurant',
    date_update datetime not null comment 'Update date of the restaurant',
    primary key (id)
) engine=InnoDB default charset=UTF8MB4 comment 'Table to keep info about the restaurant';

create table restaurant_payment_method (
    restaurant_id bigint not null comment 'Unique ID from table restaurant',
    payment_method_id bigint not null comment 'Unique ID from table payment_method',
    primary key (restaurant_id, payment_method_id)
) engine=InnoDB default charset=UTF8MB4 comment 'Table to associate restaurant and payment_method ';

create table state (
    id bigint not null auto_increment comment 'Unique ID',
    name varchar(60) comment 'Name of the state',
    primary key (id)
) engine=InnoDB default charset=UTF8MB4 comment 'Table to keep info about the state';

create table user (
    id bigint not null auto_increment comment 'Unique ID',
    date_create datetime not null comment 'Date create of the user',
    email varchar(60) not null comment 'Email of the user',
    `name` varchar(80) not null comment 'Name of the user',
    `password` varchar(30) not null comment 'Password of the user',
    primary key (id)
) engine=InnoDB default charset=UTF8MB4 comment 'Table to keep info about the user, to access for example the application';

create table user_group (
    user_id bigint not null comment 'Unique ID from table user',
    group_id bigint not null comment 'Unique ID from table group',
    primary key (user_id, group_id)
) engine=InnoDB default charset=UTF8MB4 comment 'Table to associate user and group ';

alter table city add constraint FK_city_state foreign key (state_id) references state (id);

alter table group_permission add constraint FK_group_permission_permission foreign key (permission_id) references permission (id);

alter table group_permission add constraint FK_group_permission_group foreign key (group_id) references `group` (id);

alter table product add constraint FK_product_restaurant foreign key (restaurant_id) references restaurant (id);

alter table restaurant add constraint FK_restaurant_city foreign key (city) references city (id);

alter table restaurant add constraint FK_restaurant_kitchen foreign key (kitchen_id) references kitchen (id);

alter table restaurant_payment_method add constraint FK_restaurant_payment_method_payment_method foreign key (payment_method_id) references payment_method (id);

alter table restaurant_payment_method add constraint FK_restaurant_payment_method_restaurant foreign key (restaurant_id) references restaurant (id);

alter table user_group add constraint FK_user_group_group foreign key (group_id) references `group` (id);

alter table user_group add constraint FK_user_group_user foreign key (user_id) references user (id);