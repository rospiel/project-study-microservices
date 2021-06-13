alter table restaurant add enable boolean not null comment 'If the restaurant is enabled or not, 0 = false and 1 = true';

update  restaurant
set     enable = true;