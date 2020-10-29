create table item_order (
    id bigint not null auto_increment comment 'Unique ID',
    amount decimal(19,2) not null comment 'The multiplication of quantity by unity_value',
    observation varchar(255) not null comment 'Any kind of observation of the item',
    quantity integer not null comment 'Quantity of the item',
    unity_value decimal(19,2) not null comment 'Unity price of the item',
    order_id bigint not null comment 'Unique ID from table order',
    product_id bigint not null comment 'Unique ID from table product',
    primary key (id)
) engine=InnoDB default charset=UTF8MB4 comment 'Table to keep info about the item of the order';


create table `order` (
    id bigint not null auto_increment comment 'Unique ID',
    amount decimal(19,2) not null comment 'The amout of the order plus freight rate',
    date_cancellation datetime not null comment 'Date of cancellation of the order',
    date_confirmation datetime not null comment 'Date of confirmation of the order',
    date_create datetime not null comment 'Date of creation of the order',
    date_deliver datetime not null comment 'Date of deliver of the order',
    freight_rate decimal(19,2) not null comment 'Freight rate to deliver the order',
    status_order integer comment 'Status of the order',
    subtotal decimal(19,2) not null comment 'The amout of the order',
    address_complement varchar(100) comment 'Address complement of the order',
    address_neighborhood varchar(60) comment 'Address neighborhood of the order',
    address_number varchar(7) comment 'Address number of the order',
    address_street varchar(100) comment 'Address street of the order',
    address_zip_code varchar(10) comment 'Address zip code of the order',
    city bigint comment 'Unique ID from table city',
    user_client_id bigint not null comment 'Unique ID from table user',
    payment_method_id bigint not null comment 'Unique ID from table payment_method',
    restaurant_id bigint not null comment 'Unique ID from table restaurant',
    primary key (id)
) engine=InnoDB default charset=UTF8MB4 comment 'Table to keep info about the order';


alter table item_order add constraint FK_item_order_order foreign key (order_id) references `order` (id);
alter table item_order add constraint FK_item_order_product foreign key (product_id) references product (id);
alter table `order` add constraint FK_order_city foreign key (city) references city (id);
alter table `order` add constraint FK_order_user foreign key (user_client_id) references `user` (id);
alter table `order` add constraint FK_order_payment_method foreign key (payment_method_id) references payment_method (id);
alter table `order` add constraint FK_order_restaurant foreign key (restaurant_id) references restaurant (id);