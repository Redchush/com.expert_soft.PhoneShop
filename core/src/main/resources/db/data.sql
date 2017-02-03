TRUNCATE TABLE ORDER_ITEMS AND COMMIT ;
TRUNCATE TABLE PHONES AND COMMIT ;
TRUNCATE TABLE ORDERS AND COMMIT ;

insert into phones(id, model, color, displaySize, width, length, camera, price) VALUES
  ('1', 'fly', 'white', 11, NULL ,NULL ,NULL, 11111),
  ('2', 'samsung', 'white', 22,NULL ,NULL , NULL, 22222),
  ('3', 'hts', 'white', 33, NULL ,NULL , NULL, 33333),
  ('4', 'globo', 'white', 44, NULL ,NULL , NULL, 44444),
  ('5', 'iphone', 'white', 55, NULL ,NULL , NULL, 99999);
;


insert into orders(id, delivery_price, subtotal, total,
                   first_name, last_name, delivery_address,
                   contact_phone_no, additional_info) VALUES
  (1, 500, 11111, 11611,
   'klara', 'karlovna', 'karlovy vary', '+375 29 111 77 77', 'klara info'),
  (2, 500, 33333, 33833,
   'kenny', 'poor', 'south park', '+375 29 222 77 77', NULL),
  (3, 500, 66666, 67166,
   'winny', 'winz', 'south park', '+375 29 333 77 77', NULL),
  (4, 500, 111110, 111610,
   'lenny', 'linz', 'south park', '+375 29 444 77 77', NULL),
  (5, 500, 499995, 500495,
   'anny', 'anz', 'south park', '+375 29 555 77 77', NULL),
  (6, 500, 999990, 1000490,
   'wanny', 'anz', 'south park', '+375 29 666 77 77', NULL);
;

insert into order_items(id, phone_id, order_id, quantity, subtotal) VALUES
  (1, 1 , 1 ,1, 11111),

  (2, 1, 2, 1, 11111),
  (3, 2 , 2 ,1, 22222),

  (4, 1 , 3 ,1, 11111),
  (5, 2 , 3 ,1, 22222),
  (6, 3 , 3 ,1, 33333),

  (7, 1 , 4 ,1, 11111),
  (8, 2 , 4 ,1, 22222),
  (9, 3 , 4 ,1, 33333),
  (10, 4 , 4 ,1, 44444),

  (11, 5 , 5 , 5, 499995),
  (12, 5 , 6 , 10, 999990);
;
COMMIT 
