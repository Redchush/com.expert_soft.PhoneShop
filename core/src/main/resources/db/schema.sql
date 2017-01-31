drop table if exists order_items;
drop table if exists phones;
drop table if exists orders;

-- bigint	-2^63 (-9,223,372,036,854,775,808) to 2^63-1 (9,223,372,036,854,775,807)	8 Bytes
-- int	-2^31 (-2,147,483,648) to 2^31-1 (2,147,483,647)	4 Bytes
-- smallint	-2^15 (-32,768) to 2^15-1 (32,767)	2 Bytes
-- tinyint	0 to 255	1 Byte

create table phones (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1 INCREMENT BY 1)  primary key,
  model varchar(254)  not null,
  color VARCHAR (50) NOT  NULL,
  displaySize SMALLINT NOT NULL,
  width SMALLINT,
  length SMALLINT,
  camera SMALLINT,
  price INT not null
);

create table orders (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1 INCREMENT BY 1) primary key,
  delivery_price INT not null,
  subtotal INT NOT NULL,
  total int NOT  NULL,

  first_name varchar(100) not null,
  last_name varchar(100) not null,
  delivery_address VARCHAR(500) not null,
  contact_phone_no VARCHAR(50) not null,
  additional_info VARCHAR(500)



);

create table order_items (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1 INCREMENT BY 1)  primary key,
  phone_id BIGINT not null,
  order_id BIGINT not null,
  quantity SMALLINT not null,
  foreign key (phone_id) references phones(id),
  foreign key (order_id) references orders(id)
);

CREATE UNIQUE INDEX phones
  ON phones(model);

COMMIT 




