drop table if exists order_items;
drop table if exists phones;
drop table if exists orders;

create table phones (
  id BIGINT primary key,
  model varchar(254) not null,
  price INT not null
);


create table orders (
  id BIGINT primary key,
  delivery_price INT not null,

  first_name varchar(100) not null,
  last_name varchar(100) not null,
  delivery_address VARCHAR(250) not null,
  contact_phone_no VARCHAR(250) not null

);

create table order_items (
  id BIGINT identity primary key,
  phone_id BIGINT not null,
  order_id BIGINT not null,
  quantity SMALLINT not null,
  foreign key (phone_id) references phones(id),
  foreign key (order_id) references orders(id)
);





