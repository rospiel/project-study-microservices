alter table `order`
add code varchar(36) not null comment 'Unique code that represent a order, is not to expose de primary key' after id;

update  `order`
set     code = uuid();

alter table `order`
add constraint uk_order_code unique (code);