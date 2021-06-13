alter table restaurant
add code varchar(36) not null comment 'Unique code that represent a restaurant, is not to expose de primary key' after id;

update  restaurant
set     code = uuid();

alter table restaurant
add constraint uk_order_code unique (code);