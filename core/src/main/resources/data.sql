TRUNCATE TABLE ORDER_ITEMS AND COMMIT ;
TRUNCATE TABLE PHONES AND COMMIT ;
TRUNCATE TABLE ORDERS AND COMMIT ;

insert into phones(id, model, price) VALUES
  ('1', 'fly', 111111),
  ('2', 'samsung', 222222),
  ('3', 'hts', 333333),
  ('4', 'globo', 444444),
  ('5', 'iphone', 99999);
;

insert into orders(id, delivery_price, first_name, last_name, delivery_address,
                   contact_phone_no) VALUES
  (1, 10, 'klara', 'karlovna', 'karlovy vary', '+375 29 111 77 77'),
  (2, 20, 'kenny', 'poor', 'south park', '+375 29 222 77 77'),
  (3, 30, 'winny', 'winz', 'south park', '+375 29 333 77 77'),
  (4, 40, 'lenny', 'linz', 'south park', '+375 29 444 77 77'),
  (5, 50, 'anny', 'anz', 'south park', '+375 29 555 77 77'),
  (6, 50, 'wanny', 'anz', 'south park', '+375 29 666 77 77');
;

insert into order_items(id, phone_id, order_id, quantity) VALUES
  (1, 1 , 1 ,1),

  (2, 1, 2, 1),
  (3, 2 , 2 ,1),

  (4, 1 , 3 ,1),
  (5, 2 , 3 ,1),
  (6, 3 , 3 ,1),

  (7, 1 , 4 ,1),
  (8, 2 , 4 ,1),
  (9, 3 , 4 ,1),
  (10, 4 , 4 ,1),

  (11, 5 , 5 , 5),
  (12, 5 , 6 , 10);
;


--
-- create table phones (
--   id BIGINT primary key,
--   model varchar(254) IDENTITY not null ,
--   price INT not null
-- );
--
-- create table order_items (
--   id BIGINT identity primary key,
--   phone_id BIGINT not null,
--   order_id BIGINT not null,
--   quantity SMALLINT not null,
--   foreign key (phone_id) references phones(id),
--   foreign key (order_id) references phones(id)
-- );
--
-- create table orders (
--   id BIGINT primary key,
--   delivery_price INT not null,
--
--   first_name varchar(100) not null,
--   last_name varchar(100) not null,
--   delivery_address VARCHAR(250) not null,
--   contact_phone_no VARCHAR(250) not null
--
-- );