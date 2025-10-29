create database ishoppingproducts;

create table products(
	id serial not null primary key,
	name varchar(100) not null,
	unit_value decimal(16,2) not null
);


create database ishoppingorders;

create table orders (
	id serial not null primary key,
	client_id bigint not null,
	data_order timestamp not null default now(),
	payment_key text,
	observation text,
	status varchar(20) check( status in ('PLACED', 'PAID', 'INVOICED', 'SHIPPED', 'ERROR_PAYMENT', 'PREPARING_SHIPPING')),
	total decimal(16,2) not null,
	tracking_code varchar(255),
	url_invoice text
);

create table order_item(
	id serial not null primary key,
	order_id bigint not null references orders (id),
	product_id bigint not null,
	quantity int not null,
	unit_value decimal(16,2) not null
);

create database ishoppingclients;

create table clients(
	id serial not null primary key,
	name varchar(50) not null,
	cpf varchar(11) not null,
	address varchar(100),
	number_address varchar(10),
	district varchar(100),
	email varchar(50),
	phone_number varchar(20)
);