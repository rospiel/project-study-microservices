create table city (
    id bigint not null auto_increment comment 'Unique ID',
    name varchar(60) comment 'Name of the city',
    state_id bigint comment 'Unique ID from table State',
    primary key (id)
) engine=InnoDB default charset=UTF8MB4 comment 'Table to keep info about the city from any address';

create table group (
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
    name varchar(80) not null comment 'Name of the user',
    password varchar(30) not null 'Password of the user',
    primary key (id)
) engine=InnoDB default charset=UTF8MB4 comment 'Table to keep info about the user, to access for example the application';

create table user_group (
    user_id bigint not null comment 'Unique ID from table user',
    group_id bigint not null comment 'Unique ID from table group',
    primary key (user_id, group_id)
) engine=InnoDB default charset=UTF8MB4 comment 'Table to associate user and group ';

alter table city add constraint FK_city_state foreign key (state_id) references state (id);

alter table group_permission add constraint FK_group_permission_permission foreign key (permission_id) references permission (id);

alter table group_permission add constraint FK_group_permission_group foreign key (group_id) references group (id);

alter table product add constraint FK_product_restaurant foreign key (restaurant_id) references restaurant (id);

alter table restaurant add constraint FK_restaurant_city foreign key (city) references city (id);

alter table restaurant add constraint FK_restaurant_kitchen foreign key (kitchen_id) references kitchen (id);

alter table restaurant_payment_method add constraint FK_restaurant_payment_method_payment_method foreign key (payment_method_id) references payment_method (id);

alter table restaurant_payment_method add constraint FK_restaurant_payment_method_restaurant foreign key (restaurant_id) references restaurant (id);

alter table user_group add constraint FK_user_group_group foreign key (group_id) references group (id);

alter table user_group add constraint FK_user_group_user foreign key (user_id) references user (id);

create table city (id bigint not null auto_increment, name varchar(255), state_id bigint, primary key (id)) engine=InnoDB
create table group (id bigint not null auto_increment, name varchar(255) not null, primary key (id)) engine=InnoDB
create table group_permission (group_id bigint not null, permission_id bigint not null, primary key (group_id, permission_id)) engine=InnoDB
create table kitchen (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB
create table payment_method (id bigint not null auto_increment, description varchar(255), primary key (id)) engine=InnoDB
create table permission (id bigint not null auto_increment, description varchar(255), name varchar(255), primary key (id)) engine=InnoDB
create table product (id bigint not null auto_increment, description varchar(255) not null, enable bit not null, name varchar(255) not null, price decimal(19,2) not null, restaurant_id bigint, primary key (id)) engine=InnoDB
create table restaurant (id bigint not null auto_increment, address_complement varchar(255), address_neighborhood varchar(255), address_number varchar(255), address_street varchar(255), address_zip_code varchar(255), date_create datetime not null, date_update datetime not null, freight_rate decimal(19,2), name varchar(255) not null, city bigint, kitchen_id bigint not null, primary key (id)) engine=InnoDB
create table restaurant_payment_method (restaurant_id bigint not null, payment_method_id bigint not null, primary key (restaurant_id, payment_method_id)) engine=InnoDB
create table state (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB
create table user (id bigint not null auto_increment, date_create datetime not null, email varchar(255) not null, name varchar(255) not null, password varchar(255) not null, primary key (id)) engine=InnoDB
create table user_group (user_id bigint not null, group_id bigint not null, primary key (user_id, group_id)) engine=InnoDB
alter table city add constraint FK6p2u50v8fg2y0js6djc6xanit foreign key (state_id) references state (id)
alter table group_permission add constraint FKss14p30qbokhpkpdov4ha3wro foreign key (permission_id) references permission (id)
alter table group_permission add constraint FK4twq9e99a6jwgl699bqlu5tsc foreign key (group_id) references group (id)
alter table product add constraint FKp4b7e36gck7975p51raud8juk foreign key (restaurant_id) references restaurant (id)
alter table restaurant add constraint FKa49hmlvxq6e64axrmdgmwvkaf foreign key (city) references city (id)
alter table restaurant add constraint FKrur1dqx79jim8s8dvdp16qc3d foreign key (kitchen_id) references kitchen (id)
alter table restaurant_payment_method add constraint FK5dxd5cjhjqf8eai6xugad3e1g foreign key (payment_method_id) references payment_method (id)
alter table restaurant_payment_method add constraint FKbjuwyavt07p2uihdqt6jtmkyj foreign key (restaurant_id) references restaurant (id)
alter table user_group add constraint FKjonf4pqux3h1e687sd18dhcnj foreign key (group_id) references group (id)
alter table user_group add constraint FK1c1dsw3q36679vaiqwvtv36a6 foreign key (user_id) references user (id)
insert into kitchen(name) values ('Indian')
insert into kitchen(name) values ('Japanese')
insert into state(name) values('São Paulo')
insert into state(name) values('Bahia')
insert into city(name, state_id) values('Osasco', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Barueri', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Campinas', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Cotia', (select id from state where name = 'São Paulo'))
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('São Paulo', 10.0, 1, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('Barueri', 20.0, 2, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('Cotia', 0.0, 2, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into payment_method(description) values('Automatic Payment')
insert into payment_method(description) values('Bank Draft')
insert into payment_method(description) values('Bank Transfer')
insert into payment_method(description) values('Cash')
insert into payment_method(description) values('Credit Card')
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(1, 1)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(1, 2)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(2, 2)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(3, 4)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(3, 5)
insert into product(name, description, price, enable, restaurant_id)
values('Arroz', 'Grãos', 5.0, 1, 1)
insert into product(name, description, price, enable, restaurant_id)
values('Feijão', 'Grãos', 4.0, 1, 1)
insert into product(name, description, price, enable, restaurant_id)
values('Ovo', 'Animal', 8.0, 1, 2)
insert into product(name, description, price, enable, restaurant_id)
values('Leite', 'Animal', '3.0', 0, 3)
create table city (id bigint not null auto_increment, name varchar(255), state_id bigint, primary key (id)) engine=InnoDB
create table group (id bigint not null auto_increment, name varchar(255) not null, primary key (id)) engine=InnoDB
create table group_permission (group_id bigint not null, permission_id bigint not null, primary key (group_id, permission_id)) engine=InnoDB
create table kitchen (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB
create table payment_method (id bigint not null auto_increment, description varchar(255), primary key (id)) engine=InnoDB
create table permission (id bigint not null auto_increment, description varchar(255), name varchar(255), primary key (id)) engine=InnoDB
create table product (id bigint not null auto_increment, description varchar(255) not null, enable bit not null, name varchar(255) not null, price decimal(19,2) not null, restaurant_id bigint, primary key (id)) engine=InnoDB
create table restaurant (id bigint not null auto_increment, address_complement varchar(255), address_neighborhood varchar(255), address_number varchar(255), address_street varchar(255), address_zip_code varchar(255), date_create datetime not null, date_update datetime not null, freight_rate decimal(19,2), name varchar(255) not null, city bigint, kitchen_id bigint not null, primary key (id)) engine=InnoDB
create table restaurant_payment_method (restaurant_id bigint not null, payment_method_id bigint not null, primary key (restaurant_id, payment_method_id)) engine=InnoDB
create table state (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB
create table user (id bigint not null auto_increment, date_create datetime not null, email varchar(255) not null, name varchar(255) not null, password varchar(255) not null, primary key (id)) engine=InnoDB
create table user_group (user_id bigint not null, group_id bigint not null, primary key (user_id, group_id)) engine=InnoDB
alter table city add constraint FK6p2u50v8fg2y0js6djc6xanit foreign key (state_id) references state (id)
alter table group_permission add constraint FKss14p30qbokhpkpdov4ha3wro foreign key (permission_id) references permission (id)
alter table group_permission add constraint FK4twq9e99a6jwgl699bqlu5tsc foreign key (group_id) references group (id)
alter table product add constraint FKp4b7e36gck7975p51raud8juk foreign key (restaurant_id) references restaurant (id)
alter table restaurant add constraint FKa49hmlvxq6e64axrmdgmwvkaf foreign key (city) references city (id)
alter table restaurant add constraint FKrur1dqx79jim8s8dvdp16qc3d foreign key (kitchen_id) references kitchen (id)
alter table restaurant_payment_method add constraint FK5dxd5cjhjqf8eai6xugad3e1g foreign key (payment_method_id) references payment_method (id)
alter table restaurant_payment_method add constraint FKbjuwyavt07p2uihdqt6jtmkyj foreign key (restaurant_id) references restaurant (id)
alter table user_group add constraint FKjonf4pqux3h1e687sd18dhcnj foreign key (group_id) references group (id)
alter table user_group add constraint FK1c1dsw3q36679vaiqwvtv36a6 foreign key (user_id) references user (id)
insert into kitchen(name) values ('Indian')
insert into kitchen(name) values ('Japanese')
insert into state(name) values('São Paulo')
insert into state(name) values('Bahia')
insert into city(name, state_id) values('Osasco', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Barueri', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Campinas', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Cotia', (select id from state where name = 'São Paulo'))
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('São Paulo', 10.0, 1, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('Barueri', 20.0, 2, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('Cotia', 0.0, 2, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into payment_method(description) values('Automatic Payment')
insert into payment_method(description) values('Bank Draft')
insert into payment_method(description) values('Bank Transfer')
insert into payment_method(description) values('Cash')
insert into payment_method(description) values('Credit Card')
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(1, 1)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(1, 2)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(2, 2)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(3, 4)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(3, 5)
insert into product(name, description, price, enable, restaurant_id)
values('Arroz', 'Grãos', 5.0, 1, 1)
insert into product(name, description, price, enable, restaurant_id)
values('Feijão', 'Grãos', 4.0, 1, 1)
insert into product(name, description, price, enable, restaurant_id)
values('Ovo', 'Animal', 8.0, 1, 2)
insert into product(name, description, price, enable, restaurant_id)
values('Leite', 'Animal', '3.0', 0, 3)
create table city (id bigint not null auto_increment, name varchar(255), state_id bigint, primary key (id)) engine=InnoDB
create table group (id bigint not null auto_increment, name varchar(255) not null, primary key (id)) engine=InnoDB
create table group_permission (group_id bigint not null, permission_id bigint not null, primary key (group_id, permission_id)) engine=InnoDB
create table kitchen (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB
create table payment_method (id bigint not null auto_increment, description varchar(255), primary key (id)) engine=InnoDB
create table permission (id bigint not null auto_increment, description varchar(255), name varchar(255), primary key (id)) engine=InnoDB
create table product (id bigint not null auto_increment, description varchar(255) not null, enable bit not null, name varchar(255) not null, price decimal(19,2) not null, restaurant_id bigint, primary key (id)) engine=InnoDB
create table restaurant (id bigint not null auto_increment, address_complement varchar(255), address_neighborhood varchar(255), address_number varchar(255), address_street varchar(255), address_zip_code varchar(255), date_create datetime not null, date_update datetime not null, freight_rate decimal(19,2), name varchar(255) not null, city bigint, kitchen_id bigint not null, primary key (id)) engine=InnoDB
create table restaurant_payment_method (restaurant_id bigint not null, payment_method_id bigint not null, primary key (restaurant_id, payment_method_id)) engine=InnoDB
create table state (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB
create table user (id bigint not null auto_increment, date_create datetime not null, email varchar(255) not null, name varchar(255) not null, password varchar(255) not null, primary key (id)) engine=InnoDB
create table user_group (user_id bigint not null, group_id bigint not null, primary key (user_id, group_id)) engine=InnoDB
alter table city add constraint FK6p2u50v8fg2y0js6djc6xanit foreign key (state_id) references state (id)
alter table group_permission add constraint FKss14p30qbokhpkpdov4ha3wro foreign key (permission_id) references permission (id)
alter table group_permission add constraint FK4twq9e99a6jwgl699bqlu5tsc foreign key (group_id) references group (id)
alter table product add constraint FKp4b7e36gck7975p51raud8juk foreign key (restaurant_id) references restaurant (id)
alter table restaurant add constraint FKa49hmlvxq6e64axrmdgmwvkaf foreign key (city) references city (id)
alter table restaurant add constraint FKrur1dqx79jim8s8dvdp16qc3d foreign key (kitchen_id) references kitchen (id)
alter table restaurant_payment_method add constraint FK5dxd5cjhjqf8eai6xugad3e1g foreign key (payment_method_id) references payment_method (id)
alter table restaurant_payment_method add constraint FKbjuwyavt07p2uihdqt6jtmkyj foreign key (restaurant_id) references restaurant (id)
alter table user_group add constraint FKjonf4pqux3h1e687sd18dhcnj foreign key (group_id) references group (id)
alter table user_group add constraint FK1c1dsw3q36679vaiqwvtv36a6 foreign key (user_id) references user (id)
insert into kitchen(name) values ('Indian')
insert into kitchen(name) values ('Japanese')
insert into state(name) values('São Paulo')
insert into state(name) values('Bahia')
insert into city(name, state_id) values('Osasco', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Barueri', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Campinas', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Cotia', (select id from state where name = 'São Paulo'))
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('São Paulo', 10.0, 1, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('Barueri', 20.0, 2, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('Cotia', 0.0, 2, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into payment_method(description) values('Automatic Payment')
insert into payment_method(description) values('Bank Draft')
insert into payment_method(description) values('Bank Transfer')
insert into payment_method(description) values('Cash')
insert into payment_method(description) values('Credit Card')
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(1, 1)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(1, 2)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(2, 2)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(3, 4)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(3, 5)
insert into product(name, description, price, enable, restaurant_id)
values('Arroz', 'Grãos', 5.0, 1, 1)
insert into product(name, description, price, enable, restaurant_id)
values('Feijão', 'Grãos', 4.0, 1, 1)
insert into product(name, description, price, enable, restaurant_id)
values('Ovo', 'Animal', 8.0, 1, 2)
insert into product(name, description, price, enable, restaurant_id)
values('Leite', 'Animal', '3.0', 0, 3)
create table city (id bigint not null auto_increment, name varchar(255), state_id bigint, primary key (id)) engine=InnoDB
create table group (id bigint not null auto_increment, name varchar(255) not null, primary key (id)) engine=InnoDB
create table group_permission (group_id bigint not null, permission_id bigint not null, primary key (group_id, permission_id)) engine=InnoDB
create table item_order (id bigint not null auto_increment, amount decimal(19,2) not null, observation varchar(255) not null, quantity integer not null, unity_value decimal(19,2) not null, order_id bigint not null, product_id bigint not null, primary key (id)) engine=InnoDB
create table kitchen (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB
create table order (id bigint not null auto_increment, address_complement varchar(255), address_neighborhood varchar(255), address_number varchar(255), address_street varchar(255), address_zip_code varchar(255), amount decimal(19,2) not null, date_cancellation datetime not null, date_confirmation datetime not null, date_create datetime not null, date_deliver datetime not null, freight_rate decimal(19,2) not null, status_order integer, subtotal decimal(19,2) not null, city bigint, user_client_id bigint not null, payment_method_id bigint not null, restaurant_id bigint not null, primary key (id)) engine=InnoDB
create table payment_method (id bigint not null auto_increment, description varchar(255), primary key (id)) engine=InnoDB
create table permission (id bigint not null auto_increment, description varchar(255), name varchar(255), primary key (id)) engine=InnoDB
create table product (id bigint not null auto_increment, description varchar(255) not null, enable bit not null, name varchar(255) not null, price decimal(19,2) not null, restaurant_id bigint, primary key (id)) engine=InnoDB
create table restaurant (id bigint not null auto_increment, address_complement varchar(255), address_neighborhood varchar(255), address_number varchar(255), address_street varchar(255), address_zip_code varchar(255), date_create datetime not null, date_update datetime not null, freight_rate decimal(19,2), name varchar(255) not null, city bigint, kitchen_id bigint not null, primary key (id)) engine=InnoDB
create table restaurant_payment_method (restaurant_id bigint not null, payment_method_id bigint not null, primary key (restaurant_id, payment_method_id)) engine=InnoDB
create table state (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB
create table user (id bigint not null auto_increment, date_create datetime not null, email varchar(255) not null, name varchar(255) not null, password varchar(255) not null, primary key (id)) engine=InnoDB
create table user_group (user_id bigint not null, group_id bigint not null, primary key (user_id, group_id)) engine=InnoDB
alter table city add constraint FK6p2u50v8fg2y0js6djc6xanit foreign key (state_id) references state (id)
alter table group_permission add constraint FKss14p30qbokhpkpdov4ha3wro foreign key (permission_id) references permission (id)
alter table group_permission add constraint FK4twq9e99a6jwgl699bqlu5tsc foreign key (group_id) references group (id)
alter table item_order add constraint FKxopx30c18t8o3rjnv07tw604 foreign key (order_id) references order (id)
alter table item_order add constraint FKkybbpxcya7d6pdtyrhpmckdyl foreign key (product_id) references product (id)
alter table order add constraint FKjxyk7ktb1jmu7emdlehvwd4t6 foreign key (city) references city (id)
alter table order add constraint FKbxpmw9isre0rl3cwimb98x6ue foreign key (user_client_id) references user (id)
alter table order add constraint FKlaf31ka37nhamsdy9gvct70q0 foreign key (payment_method_id) references payment_method (id)
alter table order add constraint FK64rch5g46ue4a83ww6cq7r92w foreign key (restaurant_id) references restaurant (id)
alter table product add constraint FKp4b7e36gck7975p51raud8juk foreign key (restaurant_id) references restaurant (id)
alter table restaurant add constraint FKa49hmlvxq6e64axrmdgmwvkaf foreign key (city) references city (id)
alter table restaurant add constraint FKrur1dqx79jim8s8dvdp16qc3d foreign key (kitchen_id) references kitchen (id)
alter table restaurant_payment_method add constraint FK5dxd5cjhjqf8eai6xugad3e1g foreign key (payment_method_id) references payment_method (id)
alter table restaurant_payment_method add constraint FKbjuwyavt07p2uihdqt6jtmkyj foreign key (restaurant_id) references restaurant (id)
alter table user_group add constraint FKjonf4pqux3h1e687sd18dhcnj foreign key (group_id) references group (id)
alter table user_group add constraint FK1c1dsw3q36679vaiqwvtv36a6 foreign key (user_id) references user (id)
insert into kitchen(name) values ('Indian')
insert into kitchen(name) values ('Japanese')
insert into state(name) values('São Paulo')
insert into state(name) values('Bahia')
insert into city(name, state_id) values('Osasco', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Barueri', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Campinas', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Cotia', (select id from state where name = 'São Paulo'))
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('São Paulo', 10.0, 1, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('Barueri', 20.0, 2, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('Cotia', 0.0, 2, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into payment_method(description) values('Automatic Payment')
insert into payment_method(description) values('Bank Draft')
insert into payment_method(description) values('Bank Transfer')
insert into payment_method(description) values('Cash')
insert into payment_method(description) values('Credit Card')
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(1, 1)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(1, 2)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(2, 2)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(3, 4)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(3, 5)
insert into product(name, description, price, enable, restaurant_id)
values('Arroz', 'Grãos', 5.0, 1, 1)
insert into product(name, description, price, enable, restaurant_id)
values('Feijão', 'Grãos', 4.0, 1, 1)
insert into product(name, description, price, enable, restaurant_id)
values('Ovo', 'Animal', 8.0, 1, 2)
insert into product(name, description, price, enable, restaurant_id)
values('Leite', 'Animal', '3.0', 0, 3)
create table city (id bigint not null auto_increment, name varchar(255), state_id bigint, primary key (id)) engine=InnoDB
create table group (id bigint not null auto_increment, name varchar(255) not null, primary key (id)) engine=InnoDB
create table group_permission (group_id bigint not null, permission_id bigint not null, primary key (group_id, permission_id)) engine=InnoDB
create table item_order (id bigint not null auto_increment, amount decimal(19,2) not null, observation varchar(255) not null, quantity integer not null, unity_value decimal(19,2) not null, order_id bigint not null, product_id bigint not null, primary key (id)) engine=InnoDB
create table kitchen (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB
create table order (id bigint not null auto_increment, address_complement varchar(255), address_neighborhood varchar(255), address_number varchar(255), address_street varchar(255), address_zip_code varchar(255), amount decimal(19,2) not null, date_cancellation datetime not null, date_confirmation datetime not null, date_create datetime not null, date_deliver datetime not null, freight_rate decimal(19,2) not null, status_order integer, subtotal decimal(19,2) not null, city bigint, user_client_id bigint not null, payment_method_id bigint not null, restaurant_id bigint not null, primary key (id)) engine=InnoDB
create table payment_method (id bigint not null auto_increment, description varchar(255), primary key (id)) engine=InnoDB
create table permission (id bigint not null auto_increment, description varchar(255), name varchar(255), primary key (id)) engine=InnoDB
create table product (id bigint not null auto_increment, description varchar(255) not null, enable bit not null, name varchar(255) not null, price decimal(19,2) not null, restaurant_id bigint, primary key (id)) engine=InnoDB
create table restaurant (id bigint not null auto_increment, address_complement varchar(255), address_neighborhood varchar(255), address_number varchar(255), address_street varchar(255), address_zip_code varchar(255), date_create datetime not null, date_update datetime not null, freight_rate decimal(19,2), name varchar(255) not null, city bigint, kitchen_id bigint not null, primary key (id)) engine=InnoDB
create table restaurant_payment_method (restaurant_id bigint not null, payment_method_id bigint not null, primary key (restaurant_id, payment_method_id)) engine=InnoDB
create table state (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB
create table user (id bigint not null auto_increment, date_create datetime not null, email varchar(255) not null, name varchar(255) not null, password varchar(255) not null, primary key (id)) engine=InnoDB
create table user_group (user_id bigint not null, group_id bigint not null, primary key (user_id, group_id)) engine=InnoDB
alter table city add constraint FK6p2u50v8fg2y0js6djc6xanit foreign key (state_id) references state (id)
alter table group_permission add constraint FKss14p30qbokhpkpdov4ha3wro foreign key (permission_id) references permission (id)
alter table group_permission add constraint FK4twq9e99a6jwgl699bqlu5tsc foreign key (group_id) references group (id)
alter table item_order add constraint FKxopx30c18t8o3rjnv07tw604 foreign key (order_id) references order (id)
alter table item_order add constraint FKkybbpxcya7d6pdtyrhpmckdyl foreign key (product_id) references product (id)
alter table order add constraint FKjxyk7ktb1jmu7emdlehvwd4t6 foreign key (city) references city (id)
alter table order add constraint FKbxpmw9isre0rl3cwimb98x6ue foreign key (user_client_id) references user (id)
alter table order add constraint FKlaf31ka37nhamsdy9gvct70q0 foreign key (payment_method_id) references payment_method (id)
alter table order add constraint FK64rch5g46ue4a83ww6cq7r92w foreign key (restaurant_id) references restaurant (id)
alter table product add constraint FKp4b7e36gck7975p51raud8juk foreign key (restaurant_id) references restaurant (id)
alter table restaurant add constraint FKa49hmlvxq6e64axrmdgmwvkaf foreign key (city) references city (id)
alter table restaurant add constraint FKrur1dqx79jim8s8dvdp16qc3d foreign key (kitchen_id) references kitchen (id)
alter table restaurant_payment_method add constraint FK5dxd5cjhjqf8eai6xugad3e1g foreign key (payment_method_id) references payment_method (id)
alter table restaurant_payment_method add constraint FKbjuwyavt07p2uihdqt6jtmkyj foreign key (restaurant_id) references restaurant (id)
alter table user_group add constraint FKjonf4pqux3h1e687sd18dhcnj foreign key (group_id) references group (id)
alter table user_group add constraint FK1c1dsw3q36679vaiqwvtv36a6 foreign key (user_id) references user (id)
insert into kitchen(name) values ('Indian')
insert into kitchen(name) values ('Japanese')
insert into state(name) values('São Paulo')
insert into state(name) values('Bahia')
insert into city(name, state_id) values('Osasco', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Barueri', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Campinas', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Cotia', (select id from state where name = 'São Paulo'))
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('São Paulo', 10.0, 1, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('Barueri', 20.0, 2, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('Cotia', 0.0, 2, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into payment_method(description) values('Automatic Payment')
insert into payment_method(description) values('Bank Draft')
insert into payment_method(description) values('Bank Transfer')
insert into payment_method(description) values('Cash')
insert into payment_method(description) values('Credit Card')
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(1, 1)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(1, 2)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(2, 2)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(3, 4)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(3, 5)
insert into product(name, description, price, enable, restaurant_id)
values('Arroz', 'Grãos', 5.0, 1, 1)
insert into product(name, description, price, enable, restaurant_id)
values('Feijão', 'Grãos', 4.0, 1, 1)
insert into product(name, description, price, enable, restaurant_id)
values('Ovo', 'Animal', 8.0, 1, 2)
insert into product(name, description, price, enable, restaurant_id)
values('Leite', 'Animal', '3.0', 0, 3)
create table city (id bigint not null auto_increment, name varchar(255), state_id bigint, primary key (id)) engine=InnoDB
create table group (id bigint not null auto_increment, name varchar(255) not null, primary key (id)) engine=InnoDB
create table group_permission (group_id bigint not null, permission_id bigint not null, primary key (group_id, permission_id)) engine=InnoDB
create table item_order (id bigint not null auto_increment, amount decimal(19,2) not null, observation varchar(255) not null, quantity integer not null, unity_value decimal(19,2) not null, order_id bigint not null, product_id bigint not null, primary key (id)) engine=InnoDB
create table kitchen (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB
create table order (id bigint not null auto_increment, address_complement varchar(255), address_neighborhood varchar(255), address_number varchar(255), address_street varchar(255), address_zip_code varchar(255), amount decimal(19,2) not null, date_cancellation datetime not null, date_confirmation datetime not null, date_create datetime not null, date_deliver datetime not null, freight_rate decimal(19,2) not null, status_order integer, subtotal decimal(19,2) not null, city bigint, user_client_id bigint not null, payment_method_id bigint not null, restaurant_id bigint not null, primary key (id)) engine=InnoDB
create table payment_method (id bigint not null auto_increment, description varchar(255), primary key (id)) engine=InnoDB
create table permission (id bigint not null auto_increment, description varchar(255), name varchar(255), primary key (id)) engine=InnoDB
create table product (id bigint not null auto_increment, description varchar(255) not null, enable bit not null, name varchar(255) not null, price decimal(19,2) not null, restaurant_id bigint, primary key (id)) engine=InnoDB
create table restaurant (id bigint not null auto_increment, address_complement varchar(255), address_neighborhood varchar(255), address_number varchar(255), address_street varchar(255), address_zip_code varchar(255), date_create datetime not null, date_update datetime not null, freight_rate decimal(19,2), name varchar(255) not null, city bigint, kitchen_id bigint not null, primary key (id)) engine=InnoDB
create table restaurant_payment_method (restaurant_id bigint not null, payment_method_id bigint not null, primary key (restaurant_id, payment_method_id)) engine=InnoDB
create table state (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB
create table user (id bigint not null auto_increment, date_create datetime not null, email varchar(255) not null, name varchar(255) not null, password varchar(255) not null, primary key (id)) engine=InnoDB
create table user_group (user_id bigint not null, group_id bigint not null, primary key (user_id, group_id)) engine=InnoDB
alter table city add constraint FK6p2u50v8fg2y0js6djc6xanit foreign key (state_id) references state (id)
alter table group_permission add constraint FKss14p30qbokhpkpdov4ha3wro foreign key (permission_id) references permission (id)
alter table group_permission add constraint FK4twq9e99a6jwgl699bqlu5tsc foreign key (group_id) references group (id)
alter table item_order add constraint FKxopx30c18t8o3rjnv07tw604 foreign key (order_id) references order (id)
alter table item_order add constraint FKkybbpxcya7d6pdtyrhpmckdyl foreign key (product_id) references product (id)
alter table order add constraint FKjxyk7ktb1jmu7emdlehvwd4t6 foreign key (city) references city (id)
alter table order add constraint FKbxpmw9isre0rl3cwimb98x6ue foreign key (user_client_id) references user (id)
alter table order add constraint FKlaf31ka37nhamsdy9gvct70q0 foreign key (payment_method_id) references payment_method (id)
alter table order add constraint FK64rch5g46ue4a83ww6cq7r92w foreign key (restaurant_id) references restaurant (id)
alter table product add constraint FKp4b7e36gck7975p51raud8juk foreign key (restaurant_id) references restaurant (id)
alter table restaurant add constraint FKa49hmlvxq6e64axrmdgmwvkaf foreign key (city) references city (id)
alter table restaurant add constraint FKrur1dqx79jim8s8dvdp16qc3d foreign key (kitchen_id) references kitchen (id)
alter table restaurant_payment_method add constraint FK5dxd5cjhjqf8eai6xugad3e1g foreign key (payment_method_id) references payment_method (id)
alter table restaurant_payment_method add constraint FKbjuwyavt07p2uihdqt6jtmkyj foreign key (restaurant_id) references restaurant (id)
alter table user_group add constraint FKjonf4pqux3h1e687sd18dhcnj foreign key (group_id) references group (id)
alter table user_group add constraint FK1c1dsw3q36679vaiqwvtv36a6 foreign key (user_id) references user (id)
insert into kitchen(name) values ('Indian')
insert into kitchen(name) values ('Japanese')
insert into state(name) values('São Paulo')
insert into state(name) values('Bahia')
insert into city(name, state_id) values('Osasco', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Barueri', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Campinas', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Cotia', (select id from state where name = 'São Paulo'))
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('São Paulo', 10.0, 1, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('Barueri', 20.0, 2, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('Cotia', 0.0, 2, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into payment_method(description) values('Automatic Payment')
insert into payment_method(description) values('Bank Draft')
insert into payment_method(description) values('Bank Transfer')
insert into payment_method(description) values('Cash')
insert into payment_method(description) values('Credit Card')
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(1, 1)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(1, 2)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(2, 2)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(3, 4)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(3, 5)
insert into product(name, description, price, enable, restaurant_id)
values('Arroz', 'Grãos', 5.0, 1, 1)
insert into product(name, description, price, enable, restaurant_id)
values('Feijão', 'Grãos', 4.0, 1, 1)
insert into product(name, description, price, enable, restaurant_id)
values('Ovo', 'Animal', 8.0, 1, 2)
insert into product(name, description, price, enable, restaurant_id)
values('Leite', 'Animal', '3.0', 0, 3)
create table city (id bigint not null auto_increment, name varchar(255), state_id bigint, primary key (id)) engine=InnoDB
create table group (id bigint not null auto_increment, name varchar(255) not null, primary key (id)) engine=InnoDB
create table group_permission (group_id bigint not null, permission_id bigint not null, primary key (group_id, permission_id)) engine=InnoDB
create table item_order (id bigint not null auto_increment, amount decimal(19,2) not null, observation varchar(255) not null, quantity integer not null, unity_value decimal(19,2) not null, order_id bigint not null, product_id bigint not null, primary key (id)) engine=InnoDB
create table kitchen (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB
create table order (id bigint not null auto_increment, address_complement varchar(255), address_neighborhood varchar(255), address_number varchar(255), address_street varchar(255), address_zip_code varchar(255), amount decimal(19,2) not null, date_cancellation datetime not null, date_confirmation datetime not null, date_create datetime not null, date_deliver datetime not null, freight_rate decimal(19,2) not null, status_order integer, subtotal decimal(19,2) not null, city bigint, user_client_id bigint not null, payment_method_id bigint not null, restaurant_id bigint not null, primary key (id)) engine=InnoDB
create table payment_method (id bigint not null auto_increment, description varchar(255), primary key (id)) engine=InnoDB
create table permission (id bigint not null auto_increment, description varchar(255), name varchar(255), primary key (id)) engine=InnoDB
create table product (id bigint not null auto_increment, description varchar(255) not null, enable bit not null, name varchar(255) not null, price decimal(19,2) not null, restaurant_id bigint, primary key (id)) engine=InnoDB
create table restaurant (id bigint not null auto_increment, address_complement varchar(255), address_neighborhood varchar(255), address_number varchar(255), address_street varchar(255), address_zip_code varchar(255), date_create datetime not null, date_update datetime not null, freight_rate decimal(19,2), name varchar(255) not null, city bigint, kitchen_id bigint not null, primary key (id)) engine=InnoDB
create table restaurant_payment_method (restaurant_id bigint not null, payment_method_id bigint not null, primary key (restaurant_id, payment_method_id)) engine=InnoDB
create table state (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB
create table user (id bigint not null auto_increment, date_create datetime not null, email varchar(255) not null, name varchar(255) not null, password varchar(255) not null, primary key (id)) engine=InnoDB
create table user_group (user_id bigint not null, group_id bigint not null, primary key (user_id, group_id)) engine=InnoDB
alter table city add constraint FK6p2u50v8fg2y0js6djc6xanit foreign key (state_id) references state (id)
alter table group_permission add constraint FKss14p30qbokhpkpdov4ha3wro foreign key (permission_id) references permission (id)
alter table group_permission add constraint FK4twq9e99a6jwgl699bqlu5tsc foreign key (group_id) references group (id)
alter table item_order add constraint FKxopx30c18t8o3rjnv07tw604 foreign key (order_id) references order (id)
alter table item_order add constraint FKkybbpxcya7d6pdtyrhpmckdyl foreign key (product_id) references product (id)
alter table order add constraint FKjxyk7ktb1jmu7emdlehvwd4t6 foreign key (city) references city (id)
alter table order add constraint FKbxpmw9isre0rl3cwimb98x6ue foreign key (user_client_id) references user (id)
alter table order add constraint FKlaf31ka37nhamsdy9gvct70q0 foreign key (payment_method_id) references payment_method (id)
alter table order add constraint FK64rch5g46ue4a83ww6cq7r92w foreign key (restaurant_id) references restaurant (id)
alter table product add constraint FKp4b7e36gck7975p51raud8juk foreign key (restaurant_id) references restaurant (id)
alter table restaurant add constraint FKa49hmlvxq6e64axrmdgmwvkaf foreign key (city) references city (id)
alter table restaurant add constraint FKrur1dqx79jim8s8dvdp16qc3d foreign key (kitchen_id) references kitchen (id)
alter table restaurant_payment_method add constraint FK5dxd5cjhjqf8eai6xugad3e1g foreign key (payment_method_id) references payment_method (id)
alter table restaurant_payment_method add constraint FKbjuwyavt07p2uihdqt6jtmkyj foreign key (restaurant_id) references restaurant (id)
alter table user_group add constraint FKjonf4pqux3h1e687sd18dhcnj foreign key (group_id) references group (id)
alter table user_group add constraint FK1c1dsw3q36679vaiqwvtv36a6 foreign key (user_id) references user (id)
insert into kitchen(name) values ('Indian')
insert into kitchen(name) values ('Japanese')
insert into state(name) values('São Paulo')
insert into state(name) values('Bahia')
insert into city(name, state_id) values('Osasco', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Barueri', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Campinas', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Cotia', (select id from state where name = 'São Paulo'))
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('São Paulo', 10.0, 1, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('Barueri', 20.0, 2, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('Cotia', 0.0, 2, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into payment_method(description) values('Automatic Payment')
insert into payment_method(description) values('Bank Draft')
insert into payment_method(description) values('Bank Transfer')
insert into payment_method(description) values('Cash')
insert into payment_method(description) values('Credit Card')
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(1, 1)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(1, 2)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(2, 2)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(3, 4)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(3, 5)
insert into product(name, description, price, enable, restaurant_id)
values('Arroz', 'Grãos', 5.0, 1, 1)
insert into product(name, description, price, enable, restaurant_id)
values('Feijão', 'Grãos', 4.0, 1, 1)
insert into product(name, description, price, enable, restaurant_id)
values('Ovo', 'Animal', 8.0, 1, 2)
insert into product(name, description, price, enable, restaurant_id)
values('Leite', 'Animal', '3.0', 0, 3)
create table city (id bigint not null auto_increment, name varchar(255), state_id bigint, primary key (id)) engine=InnoDB
create table group (id bigint not null auto_increment, name varchar(255) not null, primary key (id)) engine=InnoDB
create table group_permission (group_id bigint not null, permission_id bigint not null, primary key (group_id, permission_id)) engine=InnoDB
create table item_order (id bigint not null auto_increment, amount decimal(19,2) not null, observation varchar(255) not null, quantity integer not null, unity_value decimal(19,2) not null, order_id bigint not null, product_id bigint not null, primary key (id)) engine=InnoDB
create table kitchen (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB
create table order (id bigint not null auto_increment, address_complement varchar(255), address_neighborhood varchar(255), address_number varchar(255), address_street varchar(255), address_zip_code varchar(255), amount decimal(19,2) not null, date_cancellation datetime not null, date_confirmation datetime not null, date_create datetime not null, date_deliver datetime not null, freight_rate decimal(19,2) not null, status_order integer, subtotal decimal(19,2) not null, city bigint, user_client_id bigint not null, payment_method_id bigint not null, restaurant_id bigint not null, primary key (id)) engine=InnoDB
create table payment_method (id bigint not null auto_increment, description varchar(255), primary key (id)) engine=InnoDB
create table permission (id bigint not null auto_increment, description varchar(255), name varchar(255), primary key (id)) engine=InnoDB
create table product (id bigint not null auto_increment, description varchar(255) not null, enable bit not null, name varchar(255) not null, price decimal(19,2) not null, restaurant_id bigint, primary key (id)) engine=InnoDB
create table restaurant (id bigint not null auto_increment, address_complement varchar(255), address_neighborhood varchar(255), address_number varchar(255), address_street varchar(255), address_zip_code varchar(255), date_create datetime not null, date_update datetime not null, freight_rate decimal(19,2), name varchar(255) not null, city bigint, kitchen_id bigint not null, primary key (id)) engine=InnoDB
create table restaurant_payment_method (restaurant_id bigint not null, payment_method_id bigint not null, primary key (restaurant_id, payment_method_id)) engine=InnoDB
create table state (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB
create table user (id bigint not null auto_increment, date_create datetime not null, email varchar(255) not null, name varchar(255) not null, password varchar(255) not null, primary key (id)) engine=InnoDB
create table user_group (user_id bigint not null, group_id bigint not null, primary key (user_id, group_id)) engine=InnoDB
alter table city add constraint FK6p2u50v8fg2y0js6djc6xanit foreign key (state_id) references state (id)
alter table group_permission add constraint FKss14p30qbokhpkpdov4ha3wro foreign key (permission_id) references permission (id)
alter table group_permission add constraint FK4twq9e99a6jwgl699bqlu5tsc foreign key (group_id) references group (id)
alter table item_order add constraint FKxopx30c18t8o3rjnv07tw604 foreign key (order_id) references order (id)
alter table item_order add constraint FKkybbpxcya7d6pdtyrhpmckdyl foreign key (product_id) references product (id)
alter table order add constraint FKjxyk7ktb1jmu7emdlehvwd4t6 foreign key (city) references city (id)
alter table order add constraint FKbxpmw9isre0rl3cwimb98x6ue foreign key (user_client_id) references user (id)
alter table order add constraint FKlaf31ka37nhamsdy9gvct70q0 foreign key (payment_method_id) references payment_method (id)
alter table order add constraint FK64rch5g46ue4a83ww6cq7r92w foreign key (restaurant_id) references restaurant (id)
alter table product add constraint FKp4b7e36gck7975p51raud8juk foreign key (restaurant_id) references restaurant (id)
alter table restaurant add constraint FKa49hmlvxq6e64axrmdgmwvkaf foreign key (city) references city (id)
alter table restaurant add constraint FKrur1dqx79jim8s8dvdp16qc3d foreign key (kitchen_id) references kitchen (id)
alter table restaurant_payment_method add constraint FK5dxd5cjhjqf8eai6xugad3e1g foreign key (payment_method_id) references payment_method (id)
alter table restaurant_payment_method add constraint FKbjuwyavt07p2uihdqt6jtmkyj foreign key (restaurant_id) references restaurant (id)
alter table user_group add constraint FKjonf4pqux3h1e687sd18dhcnj foreign key (group_id) references group (id)
alter table user_group add constraint FK1c1dsw3q36679vaiqwvtv36a6 foreign key (user_id) references user (id)
insert into kitchen(name) values ('Indian')
insert into kitchen(name) values ('Japanese')
insert into state(name) values('São Paulo')
insert into state(name) values('Bahia')
insert into city(name, state_id) values('Osasco', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Barueri', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Campinas', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Cotia', (select id from state where name = 'São Paulo'))
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('São Paulo', 10.0, 1, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('Barueri', 20.0, 2, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('Cotia', 0.0, 2, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into payment_method(description) values('Automatic Payment')
insert into payment_method(description) values('Bank Draft')
insert into payment_method(description) values('Bank Transfer')
insert into payment_method(description) values('Cash')
insert into payment_method(description) values('Credit Card')
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(1, 1)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(1, 2)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(2, 2)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(3, 4)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(3, 5)
insert into product(name, description, price, enable, restaurant_id)
values('Arroz', 'Grãos', 5.0, 1, 1)
insert into product(name, description, price, enable, restaurant_id)
values('Feijão', 'Grãos', 4.0, 1, 1)
insert into product(name, description, price, enable, restaurant_id)
values('Ovo', 'Animal', 8.0, 1, 2)
insert into product(name, description, price, enable, restaurant_id)
values('Leite', 'Animal', '3.0', 0, 3)
create table city (id bigint not null auto_increment, name varchar(255), state_id bigint, primary key (id)) engine=InnoDB
create table group (id bigint not null auto_increment, name varchar(255) not null, primary key (id)) engine=InnoDB
create table group_permission (group_id bigint not null, permission_id bigint not null, primary key (group_id, permission_id)) engine=InnoDB
create table item_order (id bigint not null auto_increment, amount decimal(19,2) not null, observation varchar(255) not null, quantity integer not null, unity_value decimal(19,2) not null, order_id bigint not null, product_id bigint not null, primary key (id)) engine=InnoDB
create table kitchen (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB
create table order (id bigint not null auto_increment, address_complement varchar(255), address_neighborhood varchar(255), address_number varchar(255), address_street varchar(255), address_zip_code varchar(255), amount decimal(19,2) not null, date_cancellation datetime not null, date_confirmation datetime not null, date_create datetime not null, date_deliver datetime not null, freight_rate decimal(19,2) not null, status_order integer, subtotal decimal(19,2) not null, city bigint, user_client_id bigint not null, payment_method_id bigint not null, restaurant_id bigint not null, primary key (id)) engine=InnoDB
create table payment_method (id bigint not null auto_increment, description varchar(255), primary key (id)) engine=InnoDB
create table permission (id bigint not null auto_increment, description varchar(255), name varchar(255), primary key (id)) engine=InnoDB
create table product (id bigint not null auto_increment, description varchar(255) not null, enable bit not null, name varchar(255) not null, price decimal(19,2) not null, restaurant_id bigint, primary key (id)) engine=InnoDB
create table restaurant (id bigint not null auto_increment, address_complement varchar(255), address_neighborhood varchar(255), address_number varchar(255), address_street varchar(255), address_zip_code varchar(255), date_create datetime not null, date_update datetime not null, freight_rate decimal(19,2), name varchar(255) not null, city bigint, kitchen_id bigint not null, primary key (id)) engine=InnoDB
create table restaurant_payment_method (restaurant_id bigint not null, payment_method_id bigint not null, primary key (restaurant_id, payment_method_id)) engine=InnoDB
create table state (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB
create table user (id bigint not null auto_increment, date_create datetime not null, email varchar(255) not null, name varchar(255) not null, password varchar(255) not null, primary key (id)) engine=InnoDB
create table user_group (user_id bigint not null, group_id bigint not null, primary key (user_id, group_id)) engine=InnoDB
alter table city add constraint FK6p2u50v8fg2y0js6djc6xanit foreign key (state_id) references state (id)
alter table group_permission add constraint FKss14p30qbokhpkpdov4ha3wro foreign key (permission_id) references permission (id)
alter table group_permission add constraint FK4twq9e99a6jwgl699bqlu5tsc foreign key (group_id) references group (id)
alter table item_order add constraint FKxopx30c18t8o3rjnv07tw604 foreign key (order_id) references order (id)
alter table item_order add constraint FKkybbpxcya7d6pdtyrhpmckdyl foreign key (product_id) references product (id)
alter table order add constraint FKjxyk7ktb1jmu7emdlehvwd4t6 foreign key (city) references city (id)
alter table order add constraint FKbxpmw9isre0rl3cwimb98x6ue foreign key (user_client_id) references user (id)
alter table order add constraint FKlaf31ka37nhamsdy9gvct70q0 foreign key (payment_method_id) references payment_method (id)
alter table order add constraint FK64rch5g46ue4a83ww6cq7r92w foreign key (restaurant_id) references restaurant (id)
alter table product add constraint FKp4b7e36gck7975p51raud8juk foreign key (restaurant_id) references restaurant (id)
alter table restaurant add constraint FKa49hmlvxq6e64axrmdgmwvkaf foreign key (city) references city (id)
alter table restaurant add constraint FKrur1dqx79jim8s8dvdp16qc3d foreign key (kitchen_id) references kitchen (id)
alter table restaurant_payment_method add constraint FK5dxd5cjhjqf8eai6xugad3e1g foreign key (payment_method_id) references payment_method (id)
alter table restaurant_payment_method add constraint FKbjuwyavt07p2uihdqt6jtmkyj foreign key (restaurant_id) references restaurant (id)
alter table user_group add constraint FKjonf4pqux3h1e687sd18dhcnj foreign key (group_id) references group (id)
alter table user_group add constraint FK1c1dsw3q36679vaiqwvtv36a6 foreign key (user_id) references user (id)
insert into kitchen(name) values ('Indian')
insert into kitchen(name) values ('Japanese')
insert into state(name) values('São Paulo')
insert into state(name) values('Bahia')
insert into city(name, state_id) values('Osasco', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Barueri', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Campinas', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Cotia', (select id from state where name = 'São Paulo'))
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('São Paulo', 10.0, 1, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('Barueri', 20.0, 2, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('Cotia', 0.0, 2, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into payment_method(description) values('Automatic Payment')
insert into payment_method(description) values('Bank Draft')
insert into payment_method(description) values('Bank Transfer')
insert into payment_method(description) values('Cash')
insert into payment_method(description) values('Credit Card')
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(1, 1)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(1, 2)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(2, 2)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(3, 4)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(3, 5)
insert into product(name, description, price, enable, restaurant_id)
values('Arroz', 'Grãos', 5.0, 1, 1)
insert into product(name, description, price, enable, restaurant_id)
values('Feijão', 'Grãos', 4.0, 1, 1)
insert into product(name, description, price, enable, restaurant_id)
values('Ovo', 'Animal', 8.0, 1, 2)
insert into product(name, description, price, enable, restaurant_id)
values('Leite', 'Animal', '3.0', 0, 3)
create table city (id bigint not null auto_increment, name varchar(255), state_id bigint, primary key (id)) engine=InnoDB
create table group (id bigint not null auto_increment, name varchar(255) not null, primary key (id)) engine=InnoDB
create table group_permission (group_id bigint not null, permission_id bigint not null, primary key (group_id, permission_id)) engine=InnoDB
create table item_order (id bigint not null auto_increment, amount decimal(19,2) not null, observation varchar(255) not null, quantity integer not null, unity_value decimal(19,2) not null, order_id bigint not null, product_id bigint not null, primary key (id)) engine=InnoDB
create table kitchen (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB
create table order (id bigint not null auto_increment, address_complement varchar(255), address_neighborhood varchar(255), address_number varchar(255), address_street varchar(255), address_zip_code varchar(255), amount decimal(19,2) not null, date_cancellation datetime not null, date_confirmation datetime not null, date_create datetime not null, date_deliver datetime not null, freight_rate decimal(19,2) not null, status_order integer, subtotal decimal(19,2) not null, city bigint, user_client_id bigint not null, payment_method_id bigint not null, restaurant_id bigint not null, primary key (id)) engine=InnoDB
create table payment_method (id bigint not null auto_increment, description varchar(255), primary key (id)) engine=InnoDB
create table permission (id bigint not null auto_increment, description varchar(255), name varchar(255), primary key (id)) engine=InnoDB
create table product (id bigint not null auto_increment, description varchar(255) not null, enable bit not null, name varchar(255) not null, price decimal(19,2) not null, restaurant_id bigint, primary key (id)) engine=InnoDB
create table restaurant (id bigint not null auto_increment, address_complement varchar(255), address_neighborhood varchar(255), address_number varchar(255), address_street varchar(255), address_zip_code varchar(255), date_create datetime not null, date_update datetime not null, freight_rate decimal(19,2), name varchar(255) not null, city bigint, kitchen_id bigint not null, primary key (id)) engine=InnoDB
create table restaurant_payment_method (restaurant_id bigint not null, payment_method_id bigint not null, primary key (restaurant_id, payment_method_id)) engine=InnoDB
create table state (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB
create table user (id bigint not null auto_increment, date_create datetime not null, email varchar(255) not null, name varchar(255) not null, password varchar(255) not null, primary key (id)) engine=InnoDB
create table user_group (user_id bigint not null, group_id bigint not null, primary key (user_id, group_id)) engine=InnoDB
alter table city add constraint FK6p2u50v8fg2y0js6djc6xanit foreign key (state_id) references state (id)
alter table group_permission add constraint FKss14p30qbokhpkpdov4ha3wro foreign key (permission_id) references permission (id)
alter table group_permission add constraint FK4twq9e99a6jwgl699bqlu5tsc foreign key (group_id) references group (id)
alter table item_order add constraint FKxopx30c18t8o3rjnv07tw604 foreign key (order_id) references order (id)
alter table item_order add constraint FKkybbpxcya7d6pdtyrhpmckdyl foreign key (product_id) references product (id)
alter table order add constraint FKjxyk7ktb1jmu7emdlehvwd4t6 foreign key (city) references city (id)
alter table order add constraint FKbxpmw9isre0rl3cwimb98x6ue foreign key (user_client_id) references user (id)
alter table order add constraint FKlaf31ka37nhamsdy9gvct70q0 foreign key (payment_method_id) references payment_method (id)
alter table order add constraint FK64rch5g46ue4a83ww6cq7r92w foreign key (restaurant_id) references restaurant (id)
alter table product add constraint FKp4b7e36gck7975p51raud8juk foreign key (restaurant_id) references restaurant (id)
alter table restaurant add constraint FKa49hmlvxq6e64axrmdgmwvkaf foreign key (city) references city (id)
alter table restaurant add constraint FKrur1dqx79jim8s8dvdp16qc3d foreign key (kitchen_id) references kitchen (id)
alter table restaurant_payment_method add constraint FK5dxd5cjhjqf8eai6xugad3e1g foreign key (payment_method_id) references payment_method (id)
alter table restaurant_payment_method add constraint FKbjuwyavt07p2uihdqt6jtmkyj foreign key (restaurant_id) references restaurant (id)
alter table user_group add constraint FKjonf4pqux3h1e687sd18dhcnj foreign key (group_id) references group (id)
alter table user_group add constraint FK1c1dsw3q36679vaiqwvtv36a6 foreign key (user_id) references user (id)
insert into kitchen(name) values ('Indian')
insert into kitchen(name) values ('Japanese')
insert into state(name) values('São Paulo')
insert into state(name) values('Bahia')
insert into city(name, state_id) values('Osasco', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Barueri', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Campinas', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Cotia', (select id from state where name = 'São Paulo'))
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('São Paulo', 10.0, 1, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('Barueri', 20.0, 2, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('Cotia', 0.0, 2, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into payment_method(description) values('Automatic Payment')
insert into payment_method(description) values('Bank Draft')
insert into payment_method(description) values('Bank Transfer')
insert into payment_method(description) values('Cash')
insert into payment_method(description) values('Credit Card')
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(1, 1)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(1, 2)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(2, 2)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(3, 4)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(3, 5)
insert into product(name, description, price, enable, restaurant_id)
values('Arroz', 'Grãos', 5.0, 1, 1)
insert into product(name, description, price, enable, restaurant_id)
values('Feijão', 'Grãos', 4.0, 1, 1)
insert into product(name, description, price, enable, restaurant_id)
values('Ovo', 'Animal', 8.0, 1, 2)
insert into product(name, description, price, enable, restaurant_id)
values('Leite', 'Animal', '3.0', 0, 3)
create table city (id bigint not null auto_increment, name varchar(255), state_id bigint, primary key (id)) engine=InnoDB
create table group (id bigint not null auto_increment, name varchar(255) not null, primary key (id)) engine=InnoDB
create table group_permission (group_id bigint not null, permission_id bigint not null, primary key (group_id, permission_id)) engine=InnoDB
create table item_order (id bigint not null auto_increment, amount decimal(19,2) not null, observation varchar(255) not null, quantity integer not null, unity_value decimal(19,2) not null, order_id bigint not null, product_id bigint not null, primary key (id)) engine=InnoDB
create table kitchen (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB
create table order (id bigint not null auto_increment, address_complement varchar(255), address_neighborhood varchar(255), address_number varchar(255), address_street varchar(255), address_zip_code varchar(255), amount decimal(19,2) not null, date_cancellation datetime not null, date_confirmation datetime not null, date_create datetime not null, date_deliver datetime not null, freight_rate decimal(19,2) not null, status_order integer, subtotal decimal(19,2) not null, city bigint, user_client_id bigint not null, payment_method_id bigint not null, restaurant_id bigint not null, primary key (id)) engine=InnoDB
create table payment_method (id bigint not null auto_increment, description varchar(255), primary key (id)) engine=InnoDB
create table permission (id bigint not null auto_increment, description varchar(255), name varchar(255), primary key (id)) engine=InnoDB
create table product (id bigint not null auto_increment, description varchar(255) not null, enable bit not null, name varchar(255) not null, price decimal(19,2) not null, restaurant_id bigint, primary key (id)) engine=InnoDB
create table restaurant (id bigint not null auto_increment, address_complement varchar(255), address_neighborhood varchar(255), address_number varchar(255), address_street varchar(255), address_zip_code varchar(255), date_create datetime not null, date_update datetime not null, freight_rate decimal(19,2), name varchar(255) not null, city bigint, kitchen_id bigint not null, primary key (id)) engine=InnoDB
create table restaurant_payment_method (restaurant_id bigint not null, payment_method_id bigint not null, primary key (restaurant_id, payment_method_id)) engine=InnoDB
create table state (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB
create table user (id bigint not null auto_increment, date_create datetime not null, email varchar(255) not null, name varchar(255) not null, password varchar(255) not null, primary key (id)) engine=InnoDB
create table user_group (user_id bigint not null, group_id bigint not null, primary key (user_id, group_id)) engine=InnoDB
alter table city add constraint FK6p2u50v8fg2y0js6djc6xanit foreign key (state_id) references state (id)
alter table group_permission add constraint FKss14p30qbokhpkpdov4ha3wro foreign key (permission_id) references permission (id)
alter table group_permission add constraint FK4twq9e99a6jwgl699bqlu5tsc foreign key (group_id) references group (id)
alter table item_order add constraint FKxopx30c18t8o3rjnv07tw604 foreign key (order_id) references order (id)
alter table item_order add constraint FKkybbpxcya7d6pdtyrhpmckdyl foreign key (product_id) references product (id)
alter table order add constraint FKjxyk7ktb1jmu7emdlehvwd4t6 foreign key (city) references city (id)
alter table order add constraint FKbxpmw9isre0rl3cwimb98x6ue foreign key (user_client_id) references user (id)
alter table order add constraint FKlaf31ka37nhamsdy9gvct70q0 foreign key (payment_method_id) references payment_method (id)
alter table order add constraint FK64rch5g46ue4a83ww6cq7r92w foreign key (restaurant_id) references restaurant (id)
alter table product add constraint FKp4b7e36gck7975p51raud8juk foreign key (restaurant_id) references restaurant (id)
alter table restaurant add constraint FKa49hmlvxq6e64axrmdgmwvkaf foreign key (city) references city (id)
alter table restaurant add constraint FKrur1dqx79jim8s8dvdp16qc3d foreign key (kitchen_id) references kitchen (id)
alter table restaurant_payment_method add constraint FK5dxd5cjhjqf8eai6xugad3e1g foreign key (payment_method_id) references payment_method (id)
alter table restaurant_payment_method add constraint FKbjuwyavt07p2uihdqt6jtmkyj foreign key (restaurant_id) references restaurant (id)
alter table user_group add constraint FKjonf4pqux3h1e687sd18dhcnj foreign key (group_id) references group (id)
alter table user_group add constraint FK1c1dsw3q36679vaiqwvtv36a6 foreign key (user_id) references user (id)
insert into kitchen(name) values ('Indian')
insert into kitchen(name) values ('Japanese')
insert into state(name) values('São Paulo')
insert into state(name) values('Bahia')
insert into city(name, state_id) values('Osasco', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Barueri', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Campinas', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Cotia', (select id from state where name = 'São Paulo'))
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('São Paulo', 10.0, 1, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('Barueri', 20.0, 2, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('Cotia', 0.0, 2, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into payment_method(description) values('Automatic Payment')
insert into payment_method(description) values('Bank Draft')
insert into payment_method(description) values('Bank Transfer')
insert into payment_method(description) values('Cash')
insert into payment_method(description) values('Credit Card')
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(1, 1)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(1, 2)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(2, 2)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(3, 4)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(3, 5)
insert into product(name, description, price, enable, restaurant_id)
values('Arroz', 'Grãos', 5.0, 1, 1)
insert into product(name, description, price, enable, restaurant_id)
values('Feijão', 'Grãos', 4.0, 1, 1)
insert into product(name, description, price, enable, restaurant_id)
values('Ovo', 'Animal', 8.0, 1, 2)
insert into product(name, description, price, enable, restaurant_id)
values('Leite', 'Animal', '3.0', 0, 3)
create table city (id bigint not null auto_increment, name varchar(255), state_id bigint, primary key (id)) engine=InnoDB
create table group (id bigint not null auto_increment, name varchar(255) not null, primary key (id)) engine=InnoDB
create table group_permission (group_id bigint not null, permission_id bigint not null, primary key (group_id, permission_id)) engine=InnoDB
create table item_order (id bigint not null auto_increment, amount decimal(19,2) not null, observation varchar(255) not null, quantity integer not null, unity_value decimal(19,2) not null, order_id bigint not null, product_id bigint not null, primary key (id)) engine=InnoDB
create table kitchen (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB
create table order (id bigint not null auto_increment, address_complement varchar(255), address_neighborhood varchar(255), address_number varchar(255), address_street varchar(255), address_zip_code varchar(255), amount decimal(19,2) not null, date_cancellation datetime not null, date_confirmation datetime not null, date_create datetime not null, date_deliver datetime not null, freight_rate decimal(19,2) not null, status_order integer, subtotal decimal(19,2) not null, city bigint, user_client_id bigint not null, payment_method_id bigint not null, restaurant_id bigint not null, primary key (id)) engine=InnoDB
create table payment_method (id bigint not null auto_increment, description varchar(255), primary key (id)) engine=InnoDB
create table permission (id bigint not null auto_increment, description varchar(255), name varchar(255), primary key (id)) engine=InnoDB
create table product (id bigint not null auto_increment, description varchar(255) not null, enable bit not null, name varchar(255) not null, price decimal(19,2) not null, restaurant_id bigint, primary key (id)) engine=InnoDB
create table restaurant (id bigint not null auto_increment, address_complement varchar(255), address_neighborhood varchar(255), address_number varchar(255), address_street varchar(255), address_zip_code varchar(255), date_create datetime not null, date_update datetime not null, freight_rate decimal(19,2), name varchar(255) not null, city bigint, kitchen_id bigint not null, primary key (id)) engine=InnoDB
create table restaurant_payment_method (restaurant_id bigint not null, payment_method_id bigint not null, primary key (restaurant_id, payment_method_id)) engine=InnoDB
create table state (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB
create table user (id bigint not null auto_increment, date_create datetime not null, email varchar(255) not null, name varchar(255) not null, password varchar(255) not null, primary key (id)) engine=InnoDB
create table user_group (user_id bigint not null, group_id bigint not null, primary key (user_id, group_id)) engine=InnoDB
alter table city add constraint FK6p2u50v8fg2y0js6djc6xanit foreign key (state_id) references state (id)
alter table group_permission add constraint FKss14p30qbokhpkpdov4ha3wro foreign key (permission_id) references permission (id)
alter table group_permission add constraint FK4twq9e99a6jwgl699bqlu5tsc foreign key (group_id) references group (id)
alter table item_order add constraint FKxopx30c18t8o3rjnv07tw604 foreign key (order_id) references order (id)
alter table item_order add constraint FKkybbpxcya7d6pdtyrhpmckdyl foreign key (product_id) references product (id)
alter table order add constraint FKjxyk7ktb1jmu7emdlehvwd4t6 foreign key (city) references city (id)
alter table order add constraint FKbxpmw9isre0rl3cwimb98x6ue foreign key (user_client_id) references user (id)
alter table order add constraint FKlaf31ka37nhamsdy9gvct70q0 foreign key (payment_method_id) references payment_method (id)
alter table order add constraint FK64rch5g46ue4a83ww6cq7r92w foreign key (restaurant_id) references restaurant (id)
alter table product add constraint FKp4b7e36gck7975p51raud8juk foreign key (restaurant_id) references restaurant (id)
alter table restaurant add constraint FKa49hmlvxq6e64axrmdgmwvkaf foreign key (city) references city (id)
alter table restaurant add constraint FKrur1dqx79jim8s8dvdp16qc3d foreign key (kitchen_id) references kitchen (id)
alter table restaurant_payment_method add constraint FK5dxd5cjhjqf8eai6xugad3e1g foreign key (payment_method_id) references payment_method (id)
alter table restaurant_payment_method add constraint FKbjuwyavt07p2uihdqt6jtmkyj foreign key (restaurant_id) references restaurant (id)
alter table user_group add constraint FKjonf4pqux3h1e687sd18dhcnj foreign key (group_id) references group (id)
alter table user_group add constraint FK1c1dsw3q36679vaiqwvtv36a6 foreign key (user_id) references user (id)
insert into kitchen(name) values ('Indian')
insert into kitchen(name) values ('Japanese')
insert into state(name) values('São Paulo')
insert into state(name) values('Bahia')
insert into city(name, state_id) values('Osasco', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Barueri', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Campinas', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Cotia', (select id from state where name = 'São Paulo'))
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('São Paulo', 10.0, 1, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('Barueri', 20.0, 2, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('Cotia', 0.0, 2, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into payment_method(description) values('Automatic Payment')
insert into payment_method(description) values('Bank Draft')
insert into payment_method(description) values('Bank Transfer')
insert into payment_method(description) values('Cash')
insert into payment_method(description) values('Credit Card')
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(1, 1)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(1, 2)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(2, 2)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(3, 4)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(3, 5)
insert into product(name, description, price, enable, restaurant_id)
values('Arroz', 'Grãos', 5.0, 1, 1)
insert into product(name, description, price, enable, restaurant_id)
values('Feijão', 'Grãos', 4.0, 1, 1)
insert into product(name, description, price, enable, restaurant_id)
values('Ovo', 'Animal', 8.0, 1, 2)
insert into product(name, description, price, enable, restaurant_id)
values('Leite', 'Animal', '3.0', 0, 3)
create table city (id bigint not null auto_increment, name varchar(255), state_id bigint, primary key (id)) engine=InnoDB
create table group (id bigint not null auto_increment, name varchar(255) not null, primary key (id)) engine=InnoDB
create table group_permission (group_id bigint not null, permission_id bigint not null, primary key (group_id, permission_id)) engine=InnoDB
create table item_order (id bigint not null auto_increment, amount decimal(19,2) not null, observation varchar(255) not null, quantity integer not null, unity_value decimal(19,2) not null, order_id bigint not null, product_id bigint not null, primary key (id)) engine=InnoDB
create table kitchen (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB
create table order (id bigint not null auto_increment, address_complement varchar(255), address_neighborhood varchar(255), address_number varchar(255), address_street varchar(255), address_zip_code varchar(255), amount decimal(19,2) not null, date_cancellation datetime not null, date_confirmation datetime not null, date_create datetime not null, date_deliver datetime not null, freight_rate decimal(19,2) not null, status_order integer, subtotal decimal(19,2) not null, city bigint, user_client_id bigint not null, payment_method_id bigint not null, restaurant_id bigint not null, primary key (id)) engine=InnoDB
create table payment_method (id bigint not null auto_increment, description varchar(255), primary key (id)) engine=InnoDB
create table permission (id bigint not null auto_increment, description varchar(255), name varchar(255), primary key (id)) engine=InnoDB
create table product (id bigint not null auto_increment, description varchar(255) not null, enable bit not null, name varchar(255) not null, price decimal(19,2) not null, restaurant_id bigint, primary key (id)) engine=InnoDB
create table restaurant (id bigint not null auto_increment, address_complement varchar(255), address_neighborhood varchar(255), address_number varchar(255), address_street varchar(255), address_zip_code varchar(255), date_create datetime not null, date_update datetime not null, freight_rate decimal(19,2), name varchar(255) not null, city bigint, kitchen_id bigint not null, primary key (id)) engine=InnoDB
create table restaurant_payment_method (restaurant_id bigint not null, payment_method_id bigint not null, primary key (restaurant_id, payment_method_id)) engine=InnoDB
create table state (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB
create table user (id bigint not null auto_increment, date_create datetime not null, email varchar(255) not null, name varchar(255) not null, password varchar(255) not null, primary key (id)) engine=InnoDB
create table user_group (user_id bigint not null, group_id bigint not null, primary key (user_id, group_id)) engine=InnoDB
alter table city add constraint FK6p2u50v8fg2y0js6djc6xanit foreign key (state_id) references state (id)
alter table group_permission add constraint FKss14p30qbokhpkpdov4ha3wro foreign key (permission_id) references permission (id)
alter table group_permission add constraint FK4twq9e99a6jwgl699bqlu5tsc foreign key (group_id) references group (id)
alter table item_order add constraint FKxopx30c18t8o3rjnv07tw604 foreign key (order_id) references order (id)
alter table item_order add constraint FKkybbpxcya7d6pdtyrhpmckdyl foreign key (product_id) references product (id)
alter table order add constraint FKjxyk7ktb1jmu7emdlehvwd4t6 foreign key (city) references city (id)
alter table order add constraint FKbxpmw9isre0rl3cwimb98x6ue foreign key (user_client_id) references user (id)
alter table order add constraint FKlaf31ka37nhamsdy9gvct70q0 foreign key (payment_method_id) references payment_method (id)
alter table order add constraint FK64rch5g46ue4a83ww6cq7r92w foreign key (restaurant_id) references restaurant (id)
alter table product add constraint FKp4b7e36gck7975p51raud8juk foreign key (restaurant_id) references restaurant (id)
alter table restaurant add constraint FKa49hmlvxq6e64axrmdgmwvkaf foreign key (city) references city (id)
alter table restaurant add constraint FKrur1dqx79jim8s8dvdp16qc3d foreign key (kitchen_id) references kitchen (id)
alter table restaurant_payment_method add constraint FK5dxd5cjhjqf8eai6xugad3e1g foreign key (payment_method_id) references payment_method (id)
alter table restaurant_payment_method add constraint FKbjuwyavt07p2uihdqt6jtmkyj foreign key (restaurant_id) references restaurant (id)
alter table user_group add constraint FKjonf4pqux3h1e687sd18dhcnj foreign key (group_id) references group (id)
alter table user_group add constraint FK1c1dsw3q36679vaiqwvtv36a6 foreign key (user_id) references user (id)
insert into kitchen(name) values ('Indian')
insert into kitchen(name) values ('Japanese')
insert into state(name) values('São Paulo')
insert into state(name) values('Bahia')
insert into city(name, state_id) values('Osasco', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Barueri', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Campinas', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Cotia', (select id from state where name = 'São Paulo'))
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('São Paulo', 10.0, 1, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('Barueri', 20.0, 2, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('Cotia', 0.0, 2, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into payment_method(description) values('Automatic Payment')
insert into payment_method(description) values('Bank Draft')
insert into payment_method(description) values('Bank Transfer')
insert into payment_method(description) values('Cash')
insert into payment_method(description) values('Credit Card')
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(1, 1)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(1, 2)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(2, 2)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(3, 4)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(3, 5)
insert into product(name, description, price, enable, restaurant_id)
values('Arroz', 'Grãos', 5.0, 1, 1)
insert into product(name, description, price, enable, restaurant_id)
values('Feijão', 'Grãos', 4.0, 1, 1)
insert into product(name, description, price, enable, restaurant_id)
values('Ovo', 'Animal', 8.0, 1, 2)
insert into product(name, description, price, enable, restaurant_id)
values('Leite', 'Animal', '3.0', 0, 3)
create table city (id bigint not null auto_increment, name varchar(255), state_id bigint, primary key (id)) engine=InnoDB
create table group (id bigint not null auto_increment, name varchar(255) not null, primary key (id)) engine=InnoDB
create table group_permission (group_id bigint not null, permission_id bigint not null, primary key (group_id, permission_id)) engine=InnoDB
create table item_order (id bigint not null auto_increment, amount decimal(19,2) not null, observation varchar(255) not null, quantity integer not null, unity_value decimal(19,2) not null, order_id bigint not null, product_id bigint not null, primary key (id)) engine=InnoDB
create table kitchen (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB
create table order (id bigint not null auto_increment, address_complement varchar(255), address_neighborhood varchar(255), address_number varchar(255), address_street varchar(255), address_zip_code varchar(255), amount decimal(19,2) not null, date_cancellation datetime not null, date_confirmation datetime not null, date_create datetime not null, date_deliver datetime not null, freight_rate decimal(19,2) not null, status_order integer, subtotal decimal(19,2) not null, city bigint, user_client_id bigint not null, payment_method_id bigint not null, restaurant_id bigint not null, primary key (id)) engine=InnoDB
create table payment_method (id bigint not null auto_increment, description varchar(255), primary key (id)) engine=InnoDB
create table permission (id bigint not null auto_increment, description varchar(255), name varchar(255), primary key (id)) engine=InnoDB
create table product (id bigint not null auto_increment, description varchar(255) not null, enable bit not null, name varchar(255) not null, price decimal(19,2) not null, restaurant_id bigint, primary key (id)) engine=InnoDB
create table restaurant (id bigint not null auto_increment, address_complement varchar(255), address_neighborhood varchar(255), address_number varchar(255), address_street varchar(255), address_zip_code varchar(255), date_create datetime not null, date_update datetime not null, freight_rate decimal(19,2), name varchar(255) not null, city bigint, kitchen_id bigint not null, primary key (id)) engine=InnoDB
create table restaurant_payment_method (restaurant_id bigint not null, payment_method_id bigint not null, primary key (restaurant_id, payment_method_id)) engine=InnoDB
create table state (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB
create table user (id bigint not null auto_increment, date_create datetime not null, email varchar(255) not null, name varchar(255) not null, password varchar(255) not null, primary key (id)) engine=InnoDB
create table user_group (user_id bigint not null, group_id bigint not null, primary key (user_id, group_id)) engine=InnoDB
alter table city add constraint FK6p2u50v8fg2y0js6djc6xanit foreign key (state_id) references state (id)
alter table group_permission add constraint FKss14p30qbokhpkpdov4ha3wro foreign key (permission_id) references permission (id)
alter table group_permission add constraint FK4twq9e99a6jwgl699bqlu5tsc foreign key (group_id) references group (id)
alter table item_order add constraint FKxopx30c18t8o3rjnv07tw604 foreign key (order_id) references order (id)
alter table item_order add constraint FKkybbpxcya7d6pdtyrhpmckdyl foreign key (product_id) references product (id)
alter table order add constraint FKjxyk7ktb1jmu7emdlehvwd4t6 foreign key (city) references city (id)
alter table order add constraint FKbxpmw9isre0rl3cwimb98x6ue foreign key (user_client_id) references user (id)
alter table order add constraint FKlaf31ka37nhamsdy9gvct70q0 foreign key (payment_method_id) references payment_method (id)
alter table order add constraint FK64rch5g46ue4a83ww6cq7r92w foreign key (restaurant_id) references restaurant (id)
alter table product add constraint FKp4b7e36gck7975p51raud8juk foreign key (restaurant_id) references restaurant (id)
alter table restaurant add constraint FKa49hmlvxq6e64axrmdgmwvkaf foreign key (city) references city (id)
alter table restaurant add constraint FKrur1dqx79jim8s8dvdp16qc3d foreign key (kitchen_id) references kitchen (id)
alter table restaurant_payment_method add constraint FK5dxd5cjhjqf8eai6xugad3e1g foreign key (payment_method_id) references payment_method (id)
alter table restaurant_payment_method add constraint FKbjuwyavt07p2uihdqt6jtmkyj foreign key (restaurant_id) references restaurant (id)
alter table user_group add constraint FKjonf4pqux3h1e687sd18dhcnj foreign key (group_id) references group (id)
alter table user_group add constraint FK1c1dsw3q36679vaiqwvtv36a6 foreign key (user_id) references user (id)
insert into kitchen(name) values ('Indian')
insert into kitchen(name) values ('Japanese')
insert into state(name) values('São Paulo')
insert into state(name) values('Bahia')
insert into city(name, state_id) values('Osasco', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Barueri', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Campinas', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Cotia', (select id from state where name = 'São Paulo'))
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('São Paulo', 10.0, 1, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('Barueri', 20.0, 2, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('Cotia', 0.0, 2, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into payment_method(description) values('Automatic Payment')
insert into payment_method(description) values('Bank Draft')
insert into payment_method(description) values('Bank Transfer')
insert into payment_method(description) values('Cash')
insert into payment_method(description) values('Credit Card')
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(1, 1)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(1, 2)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(2, 2)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(3, 4)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(3, 5)
insert into product(name, description, price, enable, restaurant_id)
values('Arroz', 'Grãos', 5.0, 1, 1)
insert into product(name, description, price, enable, restaurant_id)
values('Feijão', 'Grãos', 4.0, 1, 1)
insert into product(name, description, price, enable, restaurant_id)
values('Ovo', 'Animal', 8.0, 1, 2)
insert into product(name, description, price, enable, restaurant_id)
values('Leite', 'Animal', '3.0', 0, 3)
create table city (id bigint not null auto_increment, name varchar(255), state_id bigint, primary key (id)) engine=InnoDB
create table group (id bigint not null auto_increment, name varchar(255) not null, primary key (id)) engine=InnoDB
create table group_permission (group_id bigint not null, permission_id bigint not null, primary key (group_id, permission_id)) engine=InnoDB
create table item_order (id bigint not null auto_increment, amount decimal(19,2) not null, observation varchar(255) not null, quantity integer not null, unity_value decimal(19,2) not null, order_id bigint not null, product_id bigint not null, primary key (id)) engine=InnoDB
create table kitchen (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB
create table order (id bigint not null auto_increment, address_complement varchar(255), address_neighborhood varchar(255), address_number varchar(255), address_street varchar(255), address_zip_code varchar(255), amount decimal(19,2) not null, date_cancellation datetime not null, date_confirmation datetime not null, date_create datetime not null, date_deliver datetime not null, freight_rate decimal(19,2) not null, status_order integer, subtotal decimal(19,2) not null, city bigint, user_client_id bigint not null, payment_method_id bigint not null, restaurant_id bigint not null, primary key (id)) engine=InnoDB
create table payment_method (id bigint not null auto_increment, description varchar(255), primary key (id)) engine=InnoDB
create table permission (id bigint not null auto_increment, description varchar(255), name varchar(255), primary key (id)) engine=InnoDB
create table product (id bigint not null auto_increment, description varchar(255) not null, enable bit not null, name varchar(255) not null, price decimal(19,2) not null, restaurant_id bigint, primary key (id)) engine=InnoDB
create table restaurant (id bigint not null auto_increment, address_complement varchar(255), address_neighborhood varchar(255), address_number varchar(255), address_street varchar(255), address_zip_code varchar(255), date_create datetime not null, date_update datetime not null, freight_rate decimal(19,2), name varchar(255) not null, city bigint, kitchen_id bigint not null, primary key (id)) engine=InnoDB
create table restaurant_payment_method (restaurant_id bigint not null, payment_method_id bigint not null, primary key (restaurant_id, payment_method_id)) engine=InnoDB
create table state (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB
create table user (id bigint not null auto_increment, date_create datetime not null, email varchar(255) not null, name varchar(255) not null, password varchar(255) not null, primary key (id)) engine=InnoDB
create table user_group (user_id bigint not null, group_id bigint not null, primary key (user_id, group_id)) engine=InnoDB
alter table city add constraint FK6p2u50v8fg2y0js6djc6xanit foreign key (state_id) references state (id)
alter table group_permission add constraint FKss14p30qbokhpkpdov4ha3wro foreign key (permission_id) references permission (id)
alter table group_permission add constraint FK4twq9e99a6jwgl699bqlu5tsc foreign key (group_id) references group (id)
alter table item_order add constraint FKxopx30c18t8o3rjnv07tw604 foreign key (order_id) references order (id)
alter table item_order add constraint FKkybbpxcya7d6pdtyrhpmckdyl foreign key (product_id) references product (id)
alter table order add constraint FKjxyk7ktb1jmu7emdlehvwd4t6 foreign key (city) references city (id)
alter table order add constraint FKbxpmw9isre0rl3cwimb98x6ue foreign key (user_client_id) references user (id)
alter table order add constraint FKlaf31ka37nhamsdy9gvct70q0 foreign key (payment_method_id) references payment_method (id)
alter table order add constraint FK64rch5g46ue4a83ww6cq7r92w foreign key (restaurant_id) references restaurant (id)
alter table product add constraint FKp4b7e36gck7975p51raud8juk foreign key (restaurant_id) references restaurant (id)
alter table restaurant add constraint FKa49hmlvxq6e64axrmdgmwvkaf foreign key (city) references city (id)
alter table restaurant add constraint FKrur1dqx79jim8s8dvdp16qc3d foreign key (kitchen_id) references kitchen (id)
alter table restaurant_payment_method add constraint FK5dxd5cjhjqf8eai6xugad3e1g foreign key (payment_method_id) references payment_method (id)
alter table restaurant_payment_method add constraint FKbjuwyavt07p2uihdqt6jtmkyj foreign key (restaurant_id) references restaurant (id)
alter table user_group add constraint FKjonf4pqux3h1e687sd18dhcnj foreign key (group_id) references group (id)
alter table user_group add constraint FK1c1dsw3q36679vaiqwvtv36a6 foreign key (user_id) references user (id)
insert into kitchen(name) values ('Indian')
insert into kitchen(name) values ('Japanese')
insert into state(name) values('São Paulo')
insert into state(name) values('Bahia')
insert into city(name, state_id) values('Osasco', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Barueri', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Campinas', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Cotia', (select id from state where name = 'São Paulo'))
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('São Paulo', 10.0, 1, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('Barueri', 20.0, 2, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('Cotia', 0.0, 2, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into payment_method(description) values('Automatic Payment')
insert into payment_method(description) values('Bank Draft')
insert into payment_method(description) values('Bank Transfer')
insert into payment_method(description) values('Cash')
insert into payment_method(description) values('Credit Card')
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(1, 1)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(1, 2)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(2, 2)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(3, 4)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(3, 5)
insert into product(name, description, price, enable, restaurant_id)
values('Arroz', 'Grãos', 5.0, 1, 1)
insert into product(name, description, price, enable, restaurant_id)
values('Feijão', 'Grãos', 4.0, 1, 1)
insert into product(name, description, price, enable, restaurant_id)
values('Ovo', 'Animal', 8.0, 1, 2)
insert into product(name, description, price, enable, restaurant_id)
values('Leite', 'Animal', '3.0', 0, 3)
create table city (id bigint not null auto_increment, name varchar(255), state_id bigint, primary key (id)) engine=InnoDB
create table group (id bigint not null auto_increment, name varchar(255) not null, primary key (id)) engine=InnoDB
create table group_permission (group_id bigint not null, permission_id bigint not null, primary key (group_id, permission_id)) engine=InnoDB
create table item_order (id bigint not null auto_increment, amount decimal(19,2) not null, observation varchar(255) not null, quantity integer not null, unity_value decimal(19,2) not null, order_id bigint not null, product_id bigint not null, primary key (id)) engine=InnoDB
create table kitchen (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB
create table order (id bigint not null auto_increment, address_complement varchar(255), address_neighborhood varchar(255), address_number varchar(255), address_street varchar(255), address_zip_code varchar(255), amount decimal(19,2) not null, date_cancellation datetime not null, date_confirmation datetime not null, date_create datetime not null, date_deliver datetime not null, freight_rate decimal(19,2) not null, status_order integer, subtotal decimal(19,2) not null, city bigint, user_client_id bigint not null, payment_method_id bigint not null, restaurant_id bigint not null, primary key (id)) engine=InnoDB
create table payment_method (id bigint not null auto_increment, description varchar(255), primary key (id)) engine=InnoDB
create table permission (id bigint not null auto_increment, description varchar(255), name varchar(255), primary key (id)) engine=InnoDB
create table product (id bigint not null auto_increment, description varchar(255) not null, enable bit not null, name varchar(255) not null, price decimal(19,2) not null, restaurant_id bigint, primary key (id)) engine=InnoDB
create table restaurant (id bigint not null auto_increment, address_complement varchar(255), address_neighborhood varchar(255), address_number varchar(255), address_street varchar(255), address_zip_code varchar(255), date_create datetime not null, date_update datetime not null, freight_rate decimal(19,2), name varchar(255) not null, city bigint, kitchen_id bigint not null, primary key (id)) engine=InnoDB
create table restaurant_payment_method (restaurant_id bigint not null, payment_method_id bigint not null, primary key (restaurant_id, payment_method_id)) engine=InnoDB
create table state (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB
create table user (id bigint not null auto_increment, date_create datetime not null, email varchar(255) not null, name varchar(255) not null, password varchar(255) not null, primary key (id)) engine=InnoDB
create table user_group (user_id bigint not null, group_id bigint not null, primary key (user_id, group_id)) engine=InnoDB
alter table city add constraint FK6p2u50v8fg2y0js6djc6xanit foreign key (state_id) references state (id)
alter table group_permission add constraint FKss14p30qbokhpkpdov4ha3wro foreign key (permission_id) references permission (id)
alter table group_permission add constraint FK4twq9e99a6jwgl699bqlu5tsc foreign key (group_id) references group (id)
alter table item_order add constraint FKxopx30c18t8o3rjnv07tw604 foreign key (order_id) references order (id)
alter table item_order add constraint FKkybbpxcya7d6pdtyrhpmckdyl foreign key (product_id) references product (id)
alter table order add constraint FKjxyk7ktb1jmu7emdlehvwd4t6 foreign key (city) references city (id)
alter table order add constraint FKbxpmw9isre0rl3cwimb98x6ue foreign key (user_client_id) references user (id)
alter table order add constraint FKlaf31ka37nhamsdy9gvct70q0 foreign key (payment_method_id) references payment_method (id)
alter table order add constraint FK64rch5g46ue4a83ww6cq7r92w foreign key (restaurant_id) references restaurant (id)
alter table product add constraint FKp4b7e36gck7975p51raud8juk foreign key (restaurant_id) references restaurant (id)
alter table restaurant add constraint FKa49hmlvxq6e64axrmdgmwvkaf foreign key (city) references city (id)
alter table restaurant add constraint FKrur1dqx79jim8s8dvdp16qc3d foreign key (kitchen_id) references kitchen (id)
alter table restaurant_payment_method add constraint FK5dxd5cjhjqf8eai6xugad3e1g foreign key (payment_method_id) references payment_method (id)
alter table restaurant_payment_method add constraint FKbjuwyavt07p2uihdqt6jtmkyj foreign key (restaurant_id) references restaurant (id)
alter table user_group add constraint FKjonf4pqux3h1e687sd18dhcnj foreign key (group_id) references group (id)
alter table user_group add constraint FK1c1dsw3q36679vaiqwvtv36a6 foreign key (user_id) references user (id)
insert into kitchen(name) values ('Indian')
insert into kitchen(name) values ('Japanese')
insert into state(name) values('São Paulo')
insert into state(name) values('Bahia')
insert into city(name, state_id) values('Osasco', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Barueri', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Campinas', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Cotia', (select id from state where name = 'São Paulo'))
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('São Paulo', 10.0, 1, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('Barueri', 20.0, 2, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('Cotia', 0.0, 2, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into payment_method(description) values('Automatic Payment')
insert into payment_method(description) values('Bank Draft')
insert into payment_method(description) values('Bank Transfer')
insert into payment_method(description) values('Cash')
insert into payment_method(description) values('Credit Card')
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(1, 1)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(1, 2)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(2, 2)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(3, 4)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(3, 5)
insert into product(name, description, price, enable, restaurant_id)
values('Arroz', 'Grãos', 5.0, 1, 1)
insert into product(name, description, price, enable, restaurant_id)
values('Feijão', 'Grãos', 4.0, 1, 1)
insert into product(name, description, price, enable, restaurant_id)
values('Ovo', 'Animal', 8.0, 1, 2)
insert into product(name, description, price, enable, restaurant_id)
values('Leite', 'Animal', '3.0', 0, 3)
create table city (id bigint not null auto_increment, name varchar(255), state_id bigint, primary key (id)) engine=InnoDB
create table group (id bigint not null auto_increment, name varchar(255) not null, primary key (id)) engine=InnoDB
create table group_permission (group_id bigint not null, permission_id bigint not null, primary key (group_id, permission_id)) engine=InnoDB
create table item_order (id bigint not null auto_increment, amount decimal(19,2) not null, observation varchar(255) not null, quantity integer not null, unity_value decimal(19,2) not null, order_id bigint not null, product_id bigint not null, primary key (id)) engine=InnoDB
create table kitchen (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB
create table order (id bigint not null auto_increment, address_complement varchar(255), address_neighborhood varchar(255), address_number varchar(255), address_street varchar(255), address_zip_code varchar(255), amount decimal(19,2) not null, date_cancellation datetime not null, date_confirmation datetime not null, date_create datetime not null, date_deliver datetime not null, freight_rate decimal(19,2) not null, status_order integer, subtotal decimal(19,2) not null, city bigint, user_client_id bigint not null, payment_method_id bigint not null, restaurant_id bigint not null, primary key (id)) engine=InnoDB
create table payment_method (id bigint not null auto_increment, description varchar(255), primary key (id)) engine=InnoDB
create table permission (id bigint not null auto_increment, description varchar(255), name varchar(255), primary key (id)) engine=InnoDB
create table product (id bigint not null auto_increment, description varchar(255) not null, enable bit not null, name varchar(255) not null, price decimal(19,2) not null, restaurant_id bigint, primary key (id)) engine=InnoDB
create table restaurant (id bigint not null auto_increment, address_complement varchar(255), address_neighborhood varchar(255), address_number varchar(255), address_street varchar(255), address_zip_code varchar(255), date_create datetime not null, date_update datetime not null, freight_rate decimal(19,2), name varchar(255) not null, city bigint, kitchen_id bigint not null, primary key (id)) engine=InnoDB
create table restaurant_payment_method (restaurant_id bigint not null, payment_method_id bigint not null, primary key (restaurant_id, payment_method_id)) engine=InnoDB
create table state (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB
create table user (id bigint not null auto_increment, date_create datetime not null, email varchar(255) not null, name varchar(255) not null, password varchar(255) not null, primary key (id)) engine=InnoDB
create table user_group (user_id bigint not null, group_id bigint not null, primary key (user_id, group_id)) engine=InnoDB
alter table city add constraint FK6p2u50v8fg2y0js6djc6xanit foreign key (state_id) references state (id)
alter table group_permission add constraint FKss14p30qbokhpkpdov4ha3wro foreign key (permission_id) references permission (id)
alter table group_permission add constraint FK4twq9e99a6jwgl699bqlu5tsc foreign key (group_id) references group (id)
alter table item_order add constraint FKxopx30c18t8o3rjnv07tw604 foreign key (order_id) references order (id)
alter table item_order add constraint FKkybbpxcya7d6pdtyrhpmckdyl foreign key (product_id) references product (id)
alter table order add constraint FKjxyk7ktb1jmu7emdlehvwd4t6 foreign key (city) references city (id)
alter table order add constraint FKbxpmw9isre0rl3cwimb98x6ue foreign key (user_client_id) references user (id)
alter table order add constraint FKlaf31ka37nhamsdy9gvct70q0 foreign key (payment_method_id) references payment_method (id)
alter table order add constraint FK64rch5g46ue4a83ww6cq7r92w foreign key (restaurant_id) references restaurant (id)
alter table product add constraint FKp4b7e36gck7975p51raud8juk foreign key (restaurant_id) references restaurant (id)
alter table restaurant add constraint FKa49hmlvxq6e64axrmdgmwvkaf foreign key (city) references city (id)
alter table restaurant add constraint FKrur1dqx79jim8s8dvdp16qc3d foreign key (kitchen_id) references kitchen (id)
alter table restaurant_payment_method add constraint FK5dxd5cjhjqf8eai6xugad3e1g foreign key (payment_method_id) references payment_method (id)
alter table restaurant_payment_method add constraint FKbjuwyavt07p2uihdqt6jtmkyj foreign key (restaurant_id) references restaurant (id)
alter table user_group add constraint FKjonf4pqux3h1e687sd18dhcnj foreign key (group_id) references group (id)
alter table user_group add constraint FK1c1dsw3q36679vaiqwvtv36a6 foreign key (user_id) references user (id)
insert into kitchen(name) values ('Indian')
insert into kitchen(name) values ('Japanese')
insert into state(name) values('São Paulo')
insert into state(name) values('Bahia')
insert into city(name, state_id) values('Osasco', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Barueri', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Campinas', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Cotia', (select id from state where name = 'São Paulo'))
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('São Paulo', 10.0, 1, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('Barueri', 20.0, 2, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('Cotia', 0.0, 2, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into payment_method(description) values('Automatic Payment')
insert into payment_method(description) values('Bank Draft')
insert into payment_method(description) values('Bank Transfer')
insert into payment_method(description) values('Cash')
insert into payment_method(description) values('Credit Card')
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(1, 1)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(1, 2)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(2, 2)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(3, 4)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(3, 5)
insert into product(name, description, price, enable, restaurant_id)
values('Arroz', 'Grãos', 5.0, 1, 1)
insert into product(name, description, price, enable, restaurant_id)
values('Feijão', 'Grãos', 4.0, 1, 1)
insert into product(name, description, price, enable, restaurant_id)
values('Ovo', 'Animal', 8.0, 1, 2)
insert into product(name, description, price, enable, restaurant_id)
values('Leite', 'Animal', '3.0', 0, 3)
create table city (id bigint not null auto_increment, name varchar(255), state_id bigint, primary key (id)) engine=InnoDB
create table group (id bigint not null auto_increment, name varchar(255) not null, primary key (id)) engine=InnoDB
create table group_permission (group_id bigint not null, permission_id bigint not null, primary key (group_id, permission_id)) engine=InnoDB
create table item_order (id bigint not null auto_increment, amount decimal(19,2) not null, observation varchar(255) not null, quantity integer not null, unity_value decimal(19,2) not null, order_id bigint not null, product_id bigint not null, primary key (id)) engine=InnoDB
create table kitchen (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB
create table order (id bigint not null auto_increment, address_complement varchar(255), address_neighborhood varchar(255), address_number varchar(255), address_street varchar(255), address_zip_code varchar(255), amount decimal(19,2) not null, date_cancellation datetime not null, date_confirmation datetime not null, date_create datetime not null, date_deliver datetime not null, freight_rate decimal(19,2) not null, status_order integer, subtotal decimal(19,2) not null, city bigint, user_client_id bigint not null, payment_method_id bigint not null, restaurant_id bigint not null, primary key (id)) engine=InnoDB
create table payment_method (id bigint not null auto_increment, description varchar(255), primary key (id)) engine=InnoDB
create table permission (id bigint not null auto_increment, description varchar(255), name varchar(255), primary key (id)) engine=InnoDB
create table product (id bigint not null auto_increment, description varchar(255) not null, enable bit not null, name varchar(255) not null, price decimal(19,2) not null, restaurant_id bigint, primary key (id)) engine=InnoDB
create table restaurant (id bigint not null auto_increment, address_complement varchar(255), address_neighborhood varchar(255), address_number varchar(255), address_street varchar(255), address_zip_code varchar(255), date_create datetime not null, date_update datetime not null, freight_rate decimal(19,2), name varchar(255) not null, city bigint, kitchen_id bigint not null, primary key (id)) engine=InnoDB
create table restaurant_payment_method (restaurant_id bigint not null, payment_method_id bigint not null, primary key (restaurant_id, payment_method_id)) engine=InnoDB
create table state (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB
create table user (id bigint not null auto_increment, date_create datetime not null, email varchar(255) not null, name varchar(255) not null, password varchar(255) not null, primary key (id)) engine=InnoDB
create table user_group (user_id bigint not null, group_id bigint not null, primary key (user_id, group_id)) engine=InnoDB
alter table city add constraint FK6p2u50v8fg2y0js6djc6xanit foreign key (state_id) references state (id)
alter table group_permission add constraint FKss14p30qbokhpkpdov4ha3wro foreign key (permission_id) references permission (id)
alter table group_permission add constraint FK4twq9e99a6jwgl699bqlu5tsc foreign key (group_id) references group (id)
alter table item_order add constraint FKxopx30c18t8o3rjnv07tw604 foreign key (order_id) references order (id)
alter table item_order add constraint FKkybbpxcya7d6pdtyrhpmckdyl foreign key (product_id) references product (id)
alter table order add constraint FKjxyk7ktb1jmu7emdlehvwd4t6 foreign key (city) references city (id)
alter table order add constraint FKbxpmw9isre0rl3cwimb98x6ue foreign key (user_client_id) references user (id)
alter table order add constraint FKlaf31ka37nhamsdy9gvct70q0 foreign key (payment_method_id) references payment_method (id)
alter table order add constraint FK64rch5g46ue4a83ww6cq7r92w foreign key (restaurant_id) references restaurant (id)
alter table product add constraint FKp4b7e36gck7975p51raud8juk foreign key (restaurant_id) references restaurant (id)
alter table restaurant add constraint FKa49hmlvxq6e64axrmdgmwvkaf foreign key (city) references city (id)
alter table restaurant add constraint FKrur1dqx79jim8s8dvdp16qc3d foreign key (kitchen_id) references kitchen (id)
alter table restaurant_payment_method add constraint FK5dxd5cjhjqf8eai6xugad3e1g foreign key (payment_method_id) references payment_method (id)
alter table restaurant_payment_method add constraint FKbjuwyavt07p2uihdqt6jtmkyj foreign key (restaurant_id) references restaurant (id)
alter table user_group add constraint FKjonf4pqux3h1e687sd18dhcnj foreign key (group_id) references group (id)
alter table user_group add constraint FK1c1dsw3q36679vaiqwvtv36a6 foreign key (user_id) references user (id)
insert into kitchen(name) values ('Indian')
insert into kitchen(name) values ('Japanese')
insert into state(name) values('São Paulo')
insert into state(name) values('Bahia')
insert into city(name, state_id) values('Osasco', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Barueri', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Campinas', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Cotia', (select id from state where name = 'São Paulo'))
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('São Paulo', 10.0, 1, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('Barueri', 20.0, 2, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('Cotia', 0.0, 2, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into payment_method(description) values('Automatic Payment')
insert into payment_method(description) values('Bank Draft')
insert into payment_method(description) values('Bank Transfer')
insert into payment_method(description) values('Cash')
insert into payment_method(description) values('Credit Card')
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(1, 1)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(1, 2)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(2, 2)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(3, 4)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(3, 5)
insert into product(name, description, price, enable, restaurant_id)
values('Arroz', 'Grãos', 5.0, 1, 1)
insert into product(name, description, price, enable, restaurant_id)
values('Feijão', 'Grãos', 4.0, 1, 1)
insert into product(name, description, price, enable, restaurant_id)
values('Ovo', 'Animal', 8.0, 1, 2)
insert into product(name, description, price, enable, restaurant_id)
values('Leite', 'Animal', '3.0', 0, 3)
create table city (id bigint not null auto_increment, name varchar(255), state_id bigint, primary key (id)) engine=InnoDB
create table group (id bigint not null auto_increment, name varchar(255) not null, primary key (id)) engine=InnoDB
create table group_permission (group_id bigint not null, permission_id bigint not null, primary key (group_id, permission_id)) engine=InnoDB
create table item_order (id bigint not null auto_increment, amount decimal(19,2) not null, observation varchar(255) not null, quantity integer not null, unity_value decimal(19,2) not null, order_id bigint not null, product_id bigint not null, primary key (id)) engine=InnoDB
create table kitchen (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB
create table order (id bigint not null auto_increment, address_complement varchar(255), address_neighborhood varchar(255), address_number varchar(255), address_street varchar(255), address_zip_code varchar(255), amount decimal(19,2) not null, date_cancellation datetime not null, date_confirmation datetime not null, date_create datetime not null, date_deliver datetime not null, freight_rate decimal(19,2) not null, status_order integer, subtotal decimal(19,2) not null, city bigint, user_client_id bigint not null, payment_method_id bigint not null, restaurant_id bigint not null, primary key (id)) engine=InnoDB
create table payment_method (id bigint not null auto_increment, description varchar(255), primary key (id)) engine=InnoDB
create table permission (id bigint not null auto_increment, description varchar(255), name varchar(255), primary key (id)) engine=InnoDB
create table product (id bigint not null auto_increment, description varchar(255) not null, enable bit not null, name varchar(255) not null, price decimal(19,2) not null, restaurant_id bigint, primary key (id)) engine=InnoDB
create table restaurant (id bigint not null auto_increment, address_complement varchar(255), address_neighborhood varchar(255), address_number varchar(255), address_street varchar(255), address_zip_code varchar(255), date_create datetime not null, date_update datetime not null, freight_rate decimal(19,2), name varchar(255) not null, city bigint, kitchen_id bigint not null, primary key (id)) engine=InnoDB
create table restaurant_payment_method (restaurant_id bigint not null, payment_method_id bigint not null, primary key (restaurant_id, payment_method_id)) engine=InnoDB
create table state (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB
create table user (id bigint not null auto_increment, date_create datetime not null, email varchar(255) not null, name varchar(255) not null, password varchar(255) not null, primary key (id)) engine=InnoDB
create table user_group (user_id bigint not null, group_id bigint not null, primary key (user_id, group_id)) engine=InnoDB
alter table city add constraint FK6p2u50v8fg2y0js6djc6xanit foreign key (state_id) references state (id)
alter table group_permission add constraint FKss14p30qbokhpkpdov4ha3wro foreign key (permission_id) references permission (id)
alter table group_permission add constraint FK4twq9e99a6jwgl699bqlu5tsc foreign key (group_id) references group (id)
alter table item_order add constraint FKxopx30c18t8o3rjnv07tw604 foreign key (order_id) references order (id)
alter table item_order add constraint FKkybbpxcya7d6pdtyrhpmckdyl foreign key (product_id) references product (id)
alter table order add constraint FKjxyk7ktb1jmu7emdlehvwd4t6 foreign key (city) references city (id)
alter table order add constraint FKbxpmw9isre0rl3cwimb98x6ue foreign key (user_client_id) references user (id)
alter table order add constraint FKlaf31ka37nhamsdy9gvct70q0 foreign key (payment_method_id) references payment_method (id)
alter table order add constraint FK64rch5g46ue4a83ww6cq7r92w foreign key (restaurant_id) references restaurant (id)
alter table product add constraint FKp4b7e36gck7975p51raud8juk foreign key (restaurant_id) references restaurant (id)
alter table restaurant add constraint FKa49hmlvxq6e64axrmdgmwvkaf foreign key (city) references city (id)
alter table restaurant add constraint FKrur1dqx79jim8s8dvdp16qc3d foreign key (kitchen_id) references kitchen (id)
alter table restaurant_payment_method add constraint FK5dxd5cjhjqf8eai6xugad3e1g foreign key (payment_method_id) references payment_method (id)
alter table restaurant_payment_method add constraint FKbjuwyavt07p2uihdqt6jtmkyj foreign key (restaurant_id) references restaurant (id)
alter table user_group add constraint FKjonf4pqux3h1e687sd18dhcnj foreign key (group_id) references group (id)
alter table user_group add constraint FK1c1dsw3q36679vaiqwvtv36a6 foreign key (user_id) references user (id)
insert into kitchen(name) values ('Indian')
insert into kitchen(name) values ('Japanese')
insert into state(name) values('São Paulo')
insert into state(name) values('Bahia')
insert into city(name, state_id) values('Osasco', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Barueri', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Campinas', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Cotia', (select id from state where name = 'São Paulo'))
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('São Paulo', 10.0, 1, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('Barueri', 20.0, 2, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('Cotia', 0.0, 2, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into payment_method(description) values('Automatic Payment')
insert into payment_method(description) values('Bank Draft')
insert into payment_method(description) values('Bank Transfer')
insert into payment_method(description) values('Cash')
insert into payment_method(description) values('Credit Card')
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(1, 1)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(1, 2)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(2, 2)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(3, 4)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(3, 5)
insert into product(name, description, price, enable, restaurant_id)
values('Arroz', 'Grãos', 5.0, 1, 1)
insert into product(name, description, price, enable, restaurant_id)
values('Feijão', 'Grãos', 4.0, 1, 1)
insert into product(name, description, price, enable, restaurant_id)
values('Ovo', 'Animal', 8.0, 1, 2)
insert into product(name, description, price, enable, restaurant_id)
values('Leite', 'Animal', '3.0', 0, 3)
create table city (id bigint not null auto_increment, name varchar(255), state_id bigint, primary key (id)) engine=InnoDB
create table group (id bigint not null auto_increment, name varchar(255) not null, primary key (id)) engine=InnoDB
create table group_permission (group_id bigint not null, permission_id bigint not null, primary key (group_id, permission_id)) engine=InnoDB
create table item_order (id bigint not null auto_increment, amount decimal(19,2) not null, observation varchar(255) not null, quantity integer not null, unity_value decimal(19,2) not null, order_id bigint not null, product_id bigint not null, primary key (id)) engine=InnoDB
create table kitchen (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB
create table order (id bigint not null auto_increment, address_complement varchar(255), address_neighborhood varchar(255), address_number varchar(255), address_street varchar(255), address_zip_code varchar(255), amount decimal(19,2) not null, date_cancellation datetime not null, date_confirmation datetime not null, date_create datetime not null, date_deliver datetime not null, freight_rate decimal(19,2) not null, status_order integer, subtotal decimal(19,2) not null, city bigint, user_client_id bigint not null, payment_method_id bigint not null, restaurant_id bigint not null, primary key (id)) engine=InnoDB
create table payment_method (id bigint not null auto_increment, description varchar(255), primary key (id)) engine=InnoDB
create table permission (id bigint not null auto_increment, description varchar(255), name varchar(255), primary key (id)) engine=InnoDB
create table product (id bigint not null auto_increment, description varchar(255) not null, enable bit not null, name varchar(255) not null, price decimal(19,2) not null, restaurant_id bigint, primary key (id)) engine=InnoDB
create table restaurant (id bigint not null auto_increment, address_complement varchar(255), address_neighborhood varchar(255), address_number varchar(255), address_street varchar(255), address_zip_code varchar(255), date_create datetime not null, date_update datetime not null, freight_rate decimal(19,2), name varchar(255) not null, city bigint, kitchen_id bigint not null, primary key (id)) engine=InnoDB
create table restaurant_payment_method (restaurant_id bigint not null, payment_method_id bigint not null, primary key (restaurant_id, payment_method_id)) engine=InnoDB
create table state (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB
create table user (id bigint not null auto_increment, date_create datetime not null, email varchar(255) not null, name varchar(255) not null, password varchar(255) not null, primary key (id)) engine=InnoDB
create table user_group (user_id bigint not null, group_id bigint not null, primary key (user_id, group_id)) engine=InnoDB
alter table city add constraint FK6p2u50v8fg2y0js6djc6xanit foreign key (state_id) references state (id)
alter table group_permission add constraint FKss14p30qbokhpkpdov4ha3wro foreign key (permission_id) references permission (id)
alter table group_permission add constraint FK4twq9e99a6jwgl699bqlu5tsc foreign key (group_id) references group (id)
alter table item_order add constraint FKxopx30c18t8o3rjnv07tw604 foreign key (order_id) references order (id)
alter table item_order add constraint FKkybbpxcya7d6pdtyrhpmckdyl foreign key (product_id) references product (id)
alter table order add constraint FKjxyk7ktb1jmu7emdlehvwd4t6 foreign key (city) references city (id)
alter table order add constraint FKbxpmw9isre0rl3cwimb98x6ue foreign key (user_client_id) references user (id)
alter table order add constraint FKlaf31ka37nhamsdy9gvct70q0 foreign key (payment_method_id) references payment_method (id)
alter table order add constraint FK64rch5g46ue4a83ww6cq7r92w foreign key (restaurant_id) references restaurant (id)
alter table product add constraint FKp4b7e36gck7975p51raud8juk foreign key (restaurant_id) references restaurant (id)
alter table restaurant add constraint FKa49hmlvxq6e64axrmdgmwvkaf foreign key (city) references city (id)
alter table restaurant add constraint FKrur1dqx79jim8s8dvdp16qc3d foreign key (kitchen_id) references kitchen (id)
alter table restaurant_payment_method add constraint FK5dxd5cjhjqf8eai6xugad3e1g foreign key (payment_method_id) references payment_method (id)
alter table restaurant_payment_method add constraint FKbjuwyavt07p2uihdqt6jtmkyj foreign key (restaurant_id) references restaurant (id)
alter table user_group add constraint FKjonf4pqux3h1e687sd18dhcnj foreign key (group_id) references group (id)
alter table user_group add constraint FK1c1dsw3q36679vaiqwvtv36a6 foreign key (user_id) references user (id)
insert into kitchen(name) values ('Indian')
insert into kitchen(name) values ('Japanese')
insert into state(name) values('São Paulo')
insert into state(name) values('Bahia')
insert into city(name, state_id) values('Osasco', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Barueri', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Campinas', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Cotia', (select id from state where name = 'São Paulo'))
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('São Paulo', 10.0, 1, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('Barueri', 20.0, 2, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('Cotia', 0.0, 2, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into payment_method(description) values('Automatic Payment')
insert into payment_method(description) values('Bank Draft')
insert into payment_method(description) values('Bank Transfer')
insert into payment_method(description) values('Cash')
insert into payment_method(description) values('Credit Card')
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(1, 1)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(1, 2)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(2, 2)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(3, 4)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(3, 5)
insert into product(name, description, price, enable, restaurant_id)
values('Arroz', 'Grãos', 5.0, 1, 1)
insert into product(name, description, price, enable, restaurant_id)
values('Feijão', 'Grãos', 4.0, 1, 1)
insert into product(name, description, price, enable, restaurant_id)
values('Ovo', 'Animal', 8.0, 1, 2)
insert into product(name, description, price, enable, restaurant_id)
values('Leite', 'Animal', '3.0', 0, 3)
create table city (id bigint not null auto_increment, name varchar(255), state_id bigint, primary key (id)) engine=InnoDB
create table group (id bigint not null auto_increment, name varchar(255) not null, primary key (id)) engine=InnoDB
create table group_permission (group_id bigint not null, permission_id bigint not null, primary key (group_id, permission_id)) engine=InnoDB
create table item_order (id bigint not null auto_increment, amount decimal(19,2) not null, observation varchar(255) not null, quantity integer not null, unity_value decimal(19,2) not null, order_id bigint not null, product_id bigint not null, primary key (id)) engine=InnoDB
create table kitchen (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB
create table order (id bigint not null auto_increment, address_complement varchar(255), address_neighborhood varchar(255), address_number varchar(255), address_street varchar(255), address_zip_code varchar(255), amount decimal(19,2) not null, date_cancellation datetime not null, date_confirmation datetime not null, date_create datetime not null, date_deliver datetime not null, freight_rate decimal(19,2) not null, status_order integer, subtotal decimal(19,2) not null, city bigint, user_client_id bigint not null, payment_method_id bigint not null, restaurant_id bigint not null, primary key (id)) engine=InnoDB
create table payment_method (id bigint not null auto_increment, description varchar(255), primary key (id)) engine=InnoDB
create table permission (id bigint not null auto_increment, description varchar(255), name varchar(255), primary key (id)) engine=InnoDB
create table product (id bigint not null auto_increment, description varchar(255) not null, enable bit not null, name varchar(255) not null, price decimal(19,2) not null, restaurant_id bigint, primary key (id)) engine=InnoDB
create table restaurant (id bigint not null auto_increment, address_complement varchar(255), address_neighborhood varchar(255), address_number varchar(255), address_street varchar(255), address_zip_code varchar(255), date_create datetime not null, date_update datetime not null, freight_rate decimal(19,2), name varchar(255) not null, city bigint, kitchen_id bigint not null, primary key (id)) engine=InnoDB
create table restaurant_payment_method (restaurant_id bigint not null, payment_method_id bigint not null, primary key (restaurant_id, payment_method_id)) engine=InnoDB
create table state (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB
create table user (id bigint not null auto_increment, date_create datetime not null, email varchar(255) not null, name varchar(255) not null, password varchar(255) not null, primary key (id)) engine=InnoDB
create table user_group (user_id bigint not null, group_id bigint not null, primary key (user_id, group_id)) engine=InnoDB
alter table city add constraint FK6p2u50v8fg2y0js6djc6xanit foreign key (state_id) references state (id)
alter table group_permission add constraint FKss14p30qbokhpkpdov4ha3wro foreign key (permission_id) references permission (id)
alter table group_permission add constraint FK4twq9e99a6jwgl699bqlu5tsc foreign key (group_id) references group (id)
alter table item_order add constraint FKxopx30c18t8o3rjnv07tw604 foreign key (order_id) references order (id)
alter table item_order add constraint FKkybbpxcya7d6pdtyrhpmckdyl foreign key (product_id) references product (id)
alter table order add constraint FKjxyk7ktb1jmu7emdlehvwd4t6 foreign key (city) references city (id)
alter table order add constraint FKbxpmw9isre0rl3cwimb98x6ue foreign key (user_client_id) references user (id)
alter table order add constraint FKlaf31ka37nhamsdy9gvct70q0 foreign key (payment_method_id) references payment_method (id)
alter table order add constraint FK64rch5g46ue4a83ww6cq7r92w foreign key (restaurant_id) references restaurant (id)
alter table product add constraint FKp4b7e36gck7975p51raud8juk foreign key (restaurant_id) references restaurant (id)
alter table restaurant add constraint FKa49hmlvxq6e64axrmdgmwvkaf foreign key (city) references city (id)
alter table restaurant add constraint FKrur1dqx79jim8s8dvdp16qc3d foreign key (kitchen_id) references kitchen (id)
alter table restaurant_payment_method add constraint FK5dxd5cjhjqf8eai6xugad3e1g foreign key (payment_method_id) references payment_method (id)
alter table restaurant_payment_method add constraint FKbjuwyavt07p2uihdqt6jtmkyj foreign key (restaurant_id) references restaurant (id)
alter table user_group add constraint FKjonf4pqux3h1e687sd18dhcnj foreign key (group_id) references group (id)
alter table user_group add constraint FK1c1dsw3q36679vaiqwvtv36a6 foreign key (user_id) references user (id)
insert into kitchen(name) values ('Indian')
insert into kitchen(name) values ('Japanese')
insert into state(name) values('São Paulo')
insert into state(name) values('Bahia')
insert into city(name, state_id) values('Osasco', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Barueri', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Campinas', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Cotia', (select id from state where name = 'São Paulo'))
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('São Paulo', 10.0, 1, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('Barueri', 20.0, 2, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('Cotia', 0.0, 2, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into payment_method(description) values('Automatic Payment')
insert into payment_method(description) values('Bank Draft')
insert into payment_method(description) values('Bank Transfer')
insert into payment_method(description) values('Cash')
insert into payment_method(description) values('Credit Card')
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(1, 1)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(1, 2)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(2, 2)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(3, 4)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(3, 5)
insert into product(name, description, price, enable, restaurant_id)
values('Arroz', 'Grãos', 5.0, 1, 1)
insert into product(name, description, price, enable, restaurant_id)
values('Feijão', 'Grãos', 4.0, 1, 1)
insert into product(name, description, price, enable, restaurant_id)
values('Ovo', 'Animal', 8.0, 1, 2)
insert into product(name, description, price, enable, restaurant_id)
values('Leite', 'Animal', '3.0', 0, 3)
create table city (id bigint not null auto_increment, name varchar(255), state_id bigint, primary key (id)) engine=InnoDB
create table group (id bigint not null auto_increment, name varchar(255) not null, primary key (id)) engine=InnoDB
create table group_permission (group_id bigint not null, permission_id bigint not null, primary key (group_id, permission_id)) engine=InnoDB
create table item_order (id bigint not null auto_increment, amount decimal(19,2) not null, observation varchar(255) not null, quantity integer not null, unity_value decimal(19,2) not null, order_id bigint not null, product_id bigint not null, primary key (id)) engine=InnoDB
create table kitchen (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB
create table order (id bigint not null auto_increment, address_complement varchar(255), address_neighborhood varchar(255), address_number varchar(255), address_street varchar(255), address_zip_code varchar(255), amount decimal(19,2) not null, date_cancellation datetime not null, date_confirmation datetime not null, date_create datetime not null, date_deliver datetime not null, freight_rate decimal(19,2) not null, status_order integer, subtotal decimal(19,2) not null, city bigint, user_client_id bigint not null, payment_method_id bigint not null, restaurant_id bigint not null, primary key (id)) engine=InnoDB
create table payment_method (id bigint not null auto_increment, description varchar(255), primary key (id)) engine=InnoDB
create table permission (id bigint not null auto_increment, description varchar(255), name varchar(255), primary key (id)) engine=InnoDB
create table product (id bigint not null auto_increment, description varchar(255) not null, enable bit not null, name varchar(255) not null, price decimal(19,2) not null, restaurant_id bigint, primary key (id)) engine=InnoDB
create table restaurant (id bigint not null auto_increment, address_complement varchar(255), address_neighborhood varchar(255), address_number varchar(255), address_street varchar(255), address_zip_code varchar(255), date_create datetime not null, date_update datetime not null, freight_rate decimal(19,2), name varchar(255) not null, city bigint, kitchen_id bigint not null, primary key (id)) engine=InnoDB
create table restaurant_payment_method (restaurant_id bigint not null, payment_method_id bigint not null, primary key (restaurant_id, payment_method_id)) engine=InnoDB
create table state (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB
create table user (id bigint not null auto_increment, date_create datetime not null, email varchar(255) not null, name varchar(255) not null, password varchar(255) not null, primary key (id)) engine=InnoDB
create table user_group (user_id bigint not null, group_id bigint not null, primary key (user_id, group_id)) engine=InnoDB
alter table city add constraint FK6p2u50v8fg2y0js6djc6xanit foreign key (state_id) references state (id)
alter table group_permission add constraint FKss14p30qbokhpkpdov4ha3wro foreign key (permission_id) references permission (id)
alter table group_permission add constraint FK4twq9e99a6jwgl699bqlu5tsc foreign key (group_id) references group (id)
alter table item_order add constraint FKxopx30c18t8o3rjnv07tw604 foreign key (order_id) references order (id)
alter table item_order add constraint FKkybbpxcya7d6pdtyrhpmckdyl foreign key (product_id) references product (id)
alter table order add constraint FKjxyk7ktb1jmu7emdlehvwd4t6 foreign key (city) references city (id)
alter table order add constraint FKbxpmw9isre0rl3cwimb98x6ue foreign key (user_client_id) references user (id)
alter table order add constraint FKlaf31ka37nhamsdy9gvct70q0 foreign key (payment_method_id) references payment_method (id)
alter table order add constraint FK64rch5g46ue4a83ww6cq7r92w foreign key (restaurant_id) references restaurant (id)
alter table product add constraint FKp4b7e36gck7975p51raud8juk foreign key (restaurant_id) references restaurant (id)
alter table restaurant add constraint FKa49hmlvxq6e64axrmdgmwvkaf foreign key (city) references city (id)
alter table restaurant add constraint FKrur1dqx79jim8s8dvdp16qc3d foreign key (kitchen_id) references kitchen (id)
alter table restaurant_payment_method add constraint FK5dxd5cjhjqf8eai6xugad3e1g foreign key (payment_method_id) references payment_method (id)
alter table restaurant_payment_method add constraint FKbjuwyavt07p2uihdqt6jtmkyj foreign key (restaurant_id) references restaurant (id)
alter table user_group add constraint FKjonf4pqux3h1e687sd18dhcnj foreign key (group_id) references group (id)
alter table user_group add constraint FK1c1dsw3q36679vaiqwvtv36a6 foreign key (user_id) references user (id)
insert into kitchen(name) values ('Indian')
insert into kitchen(name) values ('Japanese')
insert into state(name) values('São Paulo')
insert into state(name) values('Bahia')
insert into city(name, state_id) values('Osasco', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Barueri', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Campinas', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Cotia', (select id from state where name = 'São Paulo'))
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('São Paulo', 10.0, 1, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('Barueri', 20.0, 2, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('Cotia', 0.0, 2, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into payment_method(description) values('Automatic Payment')
insert into payment_method(description) values('Bank Draft')
insert into payment_method(description) values('Bank Transfer')
insert into payment_method(description) values('Cash')
insert into payment_method(description) values('Credit Card')
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(1, 1)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(1, 2)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(2, 2)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(3, 4)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(3, 5)
insert into product(name, description, price, enable, restaurant_id)
values('Arroz', 'Grãos', 5.0, 1, 1)
insert into product(name, description, price, enable, restaurant_id)
values('Feijão', 'Grãos', 4.0, 1, 1)
insert into product(name, description, price, enable, restaurant_id)
values('Ovo', 'Animal', 8.0, 1, 2)
insert into product(name, description, price, enable, restaurant_id)
values('Leite', 'Animal', '3.0', 0, 3)
create table city (id bigint not null auto_increment, name varchar(255), state_id bigint, primary key (id)) engine=InnoDB
create table group (id bigint not null auto_increment, name varchar(255) not null, primary key (id)) engine=InnoDB
create table group_permission (group_id bigint not null, permission_id bigint not null, primary key (group_id, permission_id)) engine=InnoDB
create table item_order (id bigint not null auto_increment, amount decimal(19,2) not null, observation varchar(255) not null, quantity integer not null, unity_value decimal(19,2) not null, order_id bigint not null, product_id bigint not null, primary key (id)) engine=InnoDB
create table kitchen (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB
create table order (id bigint not null auto_increment, address_complement varchar(255), address_neighborhood varchar(255), address_number varchar(255), address_street varchar(255), address_zip_code varchar(255), amount decimal(19,2) not null, date_cancellation datetime not null, date_confirmation datetime not null, date_create datetime not null, date_deliver datetime not null, freight_rate decimal(19,2) not null, status_order integer, subtotal decimal(19,2) not null, city bigint, user_client_id bigint not null, payment_method_id bigint not null, restaurant_id bigint not null, primary key (id)) engine=InnoDB
create table payment_method (id bigint not null auto_increment, description varchar(255), primary key (id)) engine=InnoDB
create table permission (id bigint not null auto_increment, description varchar(255), name varchar(255), primary key (id)) engine=InnoDB
create table product (id bigint not null auto_increment, description varchar(255) not null, enable bit not null, name varchar(255) not null, price decimal(19,2) not null, restaurant_id bigint, primary key (id)) engine=InnoDB
create table restaurant (id bigint not null auto_increment, address_complement varchar(255), address_neighborhood varchar(255), address_number varchar(255), address_street varchar(255), address_zip_code varchar(255), date_create datetime not null, date_update datetime not null, freight_rate decimal(19,2), name varchar(255) not null, city bigint, kitchen_id bigint not null, primary key (id)) engine=InnoDB
create table restaurant_payment_method (restaurant_id bigint not null, payment_method_id bigint not null, primary key (restaurant_id, payment_method_id)) engine=InnoDB
create table state (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB
create table user (id bigint not null auto_increment, date_create datetime not null, email varchar(255) not null, name varchar(255) not null, password varchar(255) not null, primary key (id)) engine=InnoDB
create table user_group (user_id bigint not null, group_id bigint not null, primary key (user_id, group_id)) engine=InnoDB
alter table city add constraint FK6p2u50v8fg2y0js6djc6xanit foreign key (state_id) references state (id)
alter table group_permission add constraint FKss14p30qbokhpkpdov4ha3wro foreign key (permission_id) references permission (id)
alter table group_permission add constraint FK4twq9e99a6jwgl699bqlu5tsc foreign key (group_id) references group (id)
alter table item_order add constraint FKxopx30c18t8o3rjnv07tw604 foreign key (order_id) references order (id)
alter table item_order add constraint FKkybbpxcya7d6pdtyrhpmckdyl foreign key (product_id) references product (id)
alter table order add constraint FKjxyk7ktb1jmu7emdlehvwd4t6 foreign key (city) references city (id)
alter table order add constraint FKbxpmw9isre0rl3cwimb98x6ue foreign key (user_client_id) references user (id)
alter table order add constraint FKlaf31ka37nhamsdy9gvct70q0 foreign key (payment_method_id) references payment_method (id)
alter table order add constraint FK64rch5g46ue4a83ww6cq7r92w foreign key (restaurant_id) references restaurant (id)
alter table product add constraint FKp4b7e36gck7975p51raud8juk foreign key (restaurant_id) references restaurant (id)
alter table restaurant add constraint FKa49hmlvxq6e64axrmdgmwvkaf foreign key (city) references city (id)
alter table restaurant add constraint FKrur1dqx79jim8s8dvdp16qc3d foreign key (kitchen_id) references kitchen (id)
alter table restaurant_payment_method add constraint FK5dxd5cjhjqf8eai6xugad3e1g foreign key (payment_method_id) references payment_method (id)
alter table restaurant_payment_method add constraint FKbjuwyavt07p2uihdqt6jtmkyj foreign key (restaurant_id) references restaurant (id)
alter table user_group add constraint FKjonf4pqux3h1e687sd18dhcnj foreign key (group_id) references group (id)
alter table user_group add constraint FK1c1dsw3q36679vaiqwvtv36a6 foreign key (user_id) references user (id)
insert into kitchen(name) values ('Indian')
insert into kitchen(name) values ('Japanese')
insert into state(name) values('São Paulo')
insert into state(name) values('Bahia')
insert into city(name, state_id) values('Osasco', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Barueri', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Campinas', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Cotia', (select id from state where name = 'São Paulo'))
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('São Paulo', 10.0, 1, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('Barueri', 20.0, 2, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('Cotia', 0.0, 2, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into payment_method(description) values('Automatic Payment')
insert into payment_method(description) values('Bank Draft')
insert into payment_method(description) values('Bank Transfer')
insert into payment_method(description) values('Cash')
insert into payment_method(description) values('Credit Card')
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(1, 1)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(1, 2)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(2, 2)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(3, 4)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(3, 5)
insert into product(name, description, price, enable, restaurant_id)
values('Arroz', 'Grãos', 5.0, 1, 1)
insert into product(name, description, price, enable, restaurant_id)
values('Feijão', 'Grãos', 4.0, 1, 1)
insert into product(name, description, price, enable, restaurant_id)
values('Ovo', 'Animal', 8.0, 1, 2)
insert into product(name, description, price, enable, restaurant_id)
values('Leite', 'Animal', '3.0', 0, 3)
create table city (id bigint not null auto_increment, name varchar(255), state_id bigint, primary key (id)) engine=InnoDB
create table group (id bigint not null auto_increment, name varchar(255) not null, primary key (id)) engine=InnoDB
create table group_permission (group_id bigint not null, permission_id bigint not null, primary key (group_id, permission_id)) engine=InnoDB
create table item_order (id bigint not null auto_increment, amount decimal(19,2) not null, observation varchar(255) not null, quantity integer not null, unity_value decimal(19,2) not null, order_id bigint not null, product_id bigint not null, primary key (id)) engine=InnoDB
create table kitchen (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB
create table order (id bigint not null auto_increment, address_complement varchar(255), address_neighborhood varchar(255), address_number varchar(255), address_street varchar(255), address_zip_code varchar(255), amount decimal(19,2) not null, date_cancellation datetime not null, date_confirmation datetime not null, date_create datetime not null, date_deliver datetime not null, freight_rate decimal(19,2) not null, status_order integer, subtotal decimal(19,2) not null, city bigint, user_client_id bigint not null, payment_method_id bigint not null, restaurant_id bigint not null, primary key (id)) engine=InnoDB
create table payment_method (id bigint not null auto_increment, description varchar(255), primary key (id)) engine=InnoDB
create table permission (id bigint not null auto_increment, description varchar(255), name varchar(255), primary key (id)) engine=InnoDB
create table product (id bigint not null auto_increment, description varchar(255) not null, enable bit not null, name varchar(255) not null, price decimal(19,2) not null, restaurant_id bigint, primary key (id)) engine=InnoDB
create table restaurant (id bigint not null auto_increment, address_complement varchar(255), address_neighborhood varchar(255), address_number varchar(255), address_street varchar(255), address_zip_code varchar(255), date_create datetime not null, date_update datetime not null, freight_rate decimal(19,2), name varchar(255) not null, city bigint, kitchen_id bigint not null, primary key (id)) engine=InnoDB
create table restaurant_payment_method (restaurant_id bigint not null, payment_method_id bigint not null, primary key (restaurant_id, payment_method_id)) engine=InnoDB
create table state (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB
create table user (id bigint not null auto_increment, date_create datetime not null, email varchar(255) not null, name varchar(255) not null, password varchar(255) not null, primary key (id)) engine=InnoDB
create table user_group (user_id bigint not null, group_id bigint not null, primary key (user_id, group_id)) engine=InnoDB
alter table city add constraint FK6p2u50v8fg2y0js6djc6xanit foreign key (state_id) references state (id)
alter table group_permission add constraint FKss14p30qbokhpkpdov4ha3wro foreign key (permission_id) references permission (id)
alter table group_permission add constraint FK4twq9e99a6jwgl699bqlu5tsc foreign key (group_id) references group (id)
alter table item_order add constraint FKxopx30c18t8o3rjnv07tw604 foreign key (order_id) references order (id)
alter table item_order add constraint FKkybbpxcya7d6pdtyrhpmckdyl foreign key (product_id) references product (id)
alter table order add constraint FKjxyk7ktb1jmu7emdlehvwd4t6 foreign key (city) references city (id)
alter table order add constraint FKbxpmw9isre0rl3cwimb98x6ue foreign key (user_client_id) references user (id)
alter table order add constraint FKlaf31ka37nhamsdy9gvct70q0 foreign key (payment_method_id) references payment_method (id)
alter table order add constraint FK64rch5g46ue4a83ww6cq7r92w foreign key (restaurant_id) references restaurant (id)
alter table product add constraint FKp4b7e36gck7975p51raud8juk foreign key (restaurant_id) references restaurant (id)
alter table restaurant add constraint FKa49hmlvxq6e64axrmdgmwvkaf foreign key (city) references city (id)
alter table restaurant add constraint FKrur1dqx79jim8s8dvdp16qc3d foreign key (kitchen_id) references kitchen (id)
alter table restaurant_payment_method add constraint FK5dxd5cjhjqf8eai6xugad3e1g foreign key (payment_method_id) references payment_method (id)
alter table restaurant_payment_method add constraint FKbjuwyavt07p2uihdqt6jtmkyj foreign key (restaurant_id) references restaurant (id)
alter table user_group add constraint FKjonf4pqux3h1e687sd18dhcnj foreign key (group_id) references group (id)
alter table user_group add constraint FK1c1dsw3q36679vaiqwvtv36a6 foreign key (user_id) references user (id)
insert into kitchen(name) values ('Indian')
insert into kitchen(name) values ('Japanese')
insert into state(name) values('São Paulo')
insert into state(name) values('Bahia')
insert into city(name, state_id) values('Osasco', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Barueri', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Campinas', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Cotia', (select id from state where name = 'São Paulo'))
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('São Paulo', 10.0, 1, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('Barueri', 20.0, 2, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('Cotia', 0.0, 2, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into payment_method(description) values('Automatic Payment')
insert into payment_method(description) values('Bank Draft')
insert into payment_method(description) values('Bank Transfer')
insert into payment_method(description) values('Cash')
insert into payment_method(description) values('Credit Card')
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(1, 1)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(1, 2)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(2, 2)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(3, 4)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(3, 5)
insert into product(name, description, price, enable, restaurant_id)
values('Arroz', 'Grãos', 5.0, 1, 1)
insert into product(name, description, price, enable, restaurant_id)
values('Feijão', 'Grãos', 4.0, 1, 1)
insert into product(name, description, price, enable, restaurant_id)
values('Ovo', 'Animal', 8.0, 1, 2)
insert into product(name, description, price, enable, restaurant_id)
values('Leite', 'Animal', '3.0', 0, 3)
create table city (id bigint not null auto_increment, name varchar(255), state_id bigint, primary key (id)) engine=InnoDB
create table group (id bigint not null auto_increment, name varchar(255) not null, primary key (id)) engine=InnoDB
create table group_permission (group_id bigint not null, permission_id bigint not null, primary key (group_id, permission_id)) engine=InnoDB
create table item_order (id bigint not null auto_increment, amount decimal(19,2) not null, observation varchar(255) not null, quantity integer not null, unity_value decimal(19,2) not null, order_id bigint not null, product_id bigint not null, primary key (id)) engine=InnoDB
create table kitchen (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB
create table order (id bigint not null auto_increment, address_complement varchar(255), address_neighborhood varchar(255), address_number varchar(255), address_street varchar(255), address_zip_code varchar(255), amount decimal(19,2) not null, date_cancellation datetime not null, date_confirmation datetime not null, date_create datetime not null, date_deliver datetime not null, freight_rate decimal(19,2) not null, status_order integer, subtotal decimal(19,2) not null, city bigint, user_client_id bigint not null, payment_method_id bigint not null, restaurant_id bigint not null, primary key (id)) engine=InnoDB
create table payment_method (id bigint not null auto_increment, description varchar(255), primary key (id)) engine=InnoDB
create table permission (id bigint not null auto_increment, description varchar(255), name varchar(255), primary key (id)) engine=InnoDB
create table product (id bigint not null auto_increment, description varchar(255) not null, enable bit not null, name varchar(255) not null, price decimal(19,2) not null, restaurant_id bigint, primary key (id)) engine=InnoDB
create table restaurant (id bigint not null auto_increment, address_complement varchar(255), address_neighborhood varchar(255), address_number varchar(255), address_street varchar(255), address_zip_code varchar(255), date_create datetime not null, date_update datetime not null, freight_rate decimal(19,2), name varchar(255) not null, city bigint, kitchen_id bigint not null, primary key (id)) engine=InnoDB
create table restaurant_payment_method (restaurant_id bigint not null, payment_method_id bigint not null, primary key (restaurant_id, payment_method_id)) engine=InnoDB
create table state (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB
create table user (id bigint not null auto_increment, date_create datetime not null, email varchar(255) not null, name varchar(255) not null, password varchar(255) not null, primary key (id)) engine=InnoDB
create table user_group (user_id bigint not null, group_id bigint not null, primary key (user_id, group_id)) engine=InnoDB
alter table city add constraint FK6p2u50v8fg2y0js6djc6xanit foreign key (state_id) references state (id)
alter table group_permission add constraint FKss14p30qbokhpkpdov4ha3wro foreign key (permission_id) references permission (id)
alter table group_permission add constraint FK4twq9e99a6jwgl699bqlu5tsc foreign key (group_id) references group (id)
alter table item_order add constraint FKxopx30c18t8o3rjnv07tw604 foreign key (order_id) references order (id)
alter table item_order add constraint FKkybbpxcya7d6pdtyrhpmckdyl foreign key (product_id) references product (id)
alter table order add constraint FKjxyk7ktb1jmu7emdlehvwd4t6 foreign key (city) references city (id)
alter table order add constraint FKbxpmw9isre0rl3cwimb98x6ue foreign key (user_client_id) references user (id)
alter table order add constraint FKlaf31ka37nhamsdy9gvct70q0 foreign key (payment_method_id) references payment_method (id)
alter table order add constraint FK64rch5g46ue4a83ww6cq7r92w foreign key (restaurant_id) references restaurant (id)
alter table product add constraint FKp4b7e36gck7975p51raud8juk foreign key (restaurant_id) references restaurant (id)
alter table restaurant add constraint FKa49hmlvxq6e64axrmdgmwvkaf foreign key (city) references city (id)
alter table restaurant add constraint FKrur1dqx79jim8s8dvdp16qc3d foreign key (kitchen_id) references kitchen (id)
alter table restaurant_payment_method add constraint FK5dxd5cjhjqf8eai6xugad3e1g foreign key (payment_method_id) references payment_method (id)
alter table restaurant_payment_method add constraint FKbjuwyavt07p2uihdqt6jtmkyj foreign key (restaurant_id) references restaurant (id)
alter table user_group add constraint FKjonf4pqux3h1e687sd18dhcnj foreign key (group_id) references group (id)
alter table user_group add constraint FK1c1dsw3q36679vaiqwvtv36a6 foreign key (user_id) references user (id)
insert into kitchen(name) values ('Indian')
insert into kitchen(name) values ('Japanese')
insert into state(name) values('São Paulo')
insert into state(name) values('Bahia')
insert into city(name, state_id) values('Osasco', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Barueri', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Campinas', (select id from state where name = 'São Paulo'))
insert into city(name, state_id) values('Cotia', (select id from state where name = 'São Paulo'))
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('São Paulo', 10.0, 1, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('Barueri', 20.0, 2, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('Cotia', 0.0, 2, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp)
insert into payment_method(description) values('Automatic Payment')
insert into payment_method(description) values('Bank Draft')
insert into payment_method(description) values('Bank Transfer')
insert into payment_method(description) values('Cash')
insert into payment_method(description) values('Credit Card')
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(1, 1)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(1, 2)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(2, 2)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(3, 4)
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(3, 5)
insert into product(name, description, price, enable, restaurant_id)
values('Arroz', 'Grãos', 5.0, 1, 1)
insert into product(name, description, price, enable, restaurant_id)
values('Feijão', 'Grãos', 4.0, 1, 1)
insert into product(name, description, price, enable, restaurant_id)
values('Ovo', 'Animal', 8.0, 1, 2)
insert into product(name, description, price, enable, restaurant_id)
values('Leite', 'Animal', '3.0', 0, 3)
