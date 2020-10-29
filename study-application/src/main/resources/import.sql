insert into kitchen(name) values ('Indian');
insert into kitchen(name) values ('Japanese');

insert into state(name) values('São Paulo');
insert into state(name) values('Bahia');

insert into city(name, state_id) values('Osasco', (select id from state where name = 'São Paulo'));
insert into city(name, state_id) values('Barueri', (select id from state where name = 'São Paulo'));
insert into city(name, state_id) values('Campinas', (select id from state where name = 'São Paulo'));
insert into city(name, state_id) values('Cotia', (select id from state where name = 'São Paulo'));

insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('São Paulo', 10.0, 1, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp);
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('Barueri', 20.0, 2, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp);
insert into restaurant(name, freight_rate, kitchen_id, address_zip_code, address_street, address_number, address_complement, address_neighborhood, city, date_create, date_update)
values ('Cotia', 0.0, 2, '06045-270', 'Estrela', '6789', ' ', 'Belmonte', 1, utc_timestamp, utc_timestamp);

insert into payment_method(description) values('Automatic Payment');
insert into payment_method(description) values('Bank Draft');
insert into payment_method(description) values('Bank Transfer');
insert into payment_method(description) values('Cash');
insert into payment_method(description) values('Credit Card');

insert into restaurant_payment_method(restaurant_id, payment_method_id) values(1, 1);
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(1, 2);
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(2, 2);
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(3, 4);
insert into restaurant_payment_method(restaurant_id, payment_method_id) values(3, 5);

insert into product(name, description, price, enable, restaurant_id)
values('Arroz', 'Grãos', 5.0, 1, 1);
insert into product(name, description, price, enable, restaurant_id)
values('Feijão', 'Grãos', 4.0, 1, 1);
insert into product(name, description, price, enable, restaurant_id)
values('Ovo', 'Animal', 8.0, 1, 2);
insert into product(name, description, price, enable, restaurant_id)
values('Leite', 'Animal', '3.0', 0, 3);



