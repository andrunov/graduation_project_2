DELETE FROM users;
DELETE FROM roles;
DELETE FROM restaurants;
DELETE FROM menu_items;
DELETE FROM dishes;
DELETE FROM menu_lists;
ALTER SEQUENCE GLOBAL_SEQ RESTART WITH 100000;

INSERT INTO users(name,email,password) VALUES
  ('Алексей Иванов','ivanov.alexey@gmail.com','111222'),
  ('Андрей Горбунов','andrunov@gmail.com','222333'),
  ('Павел Сидоров','sidor@gmail.com','333444'),
  ('Roberto Zanetti','rzanetti@gmail.com','444555'),
  ('John Bon Jovi','jbj@gmail.com','555666'),
  ('Didier Maoruani','dmauruani@gmail.com','666777');

INSERT INTO roles(user_id, role) VALUES
  (100000,'ROLE_USER'),
  (100001,'ROLE_ADMIN'),
  (100002,'ROLE_USER'),
  (100003,'ROLE_USER'),
  (100004,'ROLE_USER'),
  (100005,'ROLE_USER');

INSERT INTO restaurants(name, address) VALUES
  ('Ёлки-палки','ул. Некрасова, 14'),
  ('Пиццерия','пл. Пушкина, 6'),
  ('Закусочная','Привокзальная пл, 3'),
  ('Прага','ул. Арбат, 1');


INSERT INTO menu_lists( date, restaurant_id) VALUES
  (now(), 100006),
  (now(), 100007),
  (now(), 100008),
  (now(), 100009),
  ('2022-12-14 00:00:00', 100006),
  ('2022-12-14 00:00:00', 100007),
  ('2022-12-14 00:00:00', 100008),
  ('2022-12-14 00:00:00', 100009),
  ('2022-12-15 00:00:00', 100006),
  ('2022-12-15 00:00:00', 100007),
  ('2022-12-15 00:00:00', 100008),
  ('2022-12-15 00:00:00', 100009);

INSERT INTO votes(date_time, user_id, restaurant_id) VALUES
  ('2022-12-14 12:00:00' , 100000, 100006),
  ('2022-12-15 15:00:00' , 100001, 100006),
  (now(), 100002, 100006),
  (now(), 100003, 100007),
  (now(), 100004, 100008),
  (now(), 100005, 100009);

INSERT INTO dishes(name) VALUES
  ('Каша овсяная'),
  ('Сырники'),
  ('Блины'),
  ('Суп гороховый'),
  ('Рассольник'),
  ('Жульен с грибами'),
  ('Пельмени'),
  ('Котлета по киевски'),
  ('Чай черный'),
  ('Чай зеленый'),
  ('Кофе черный'),
  ('Кофе белый'),
  ('Котлета по питерски'),
  ('Поросенок под хреном'),
  ('Манты грузинские'),
  ('Жаркое'),
  ('Чай черный с лимоном'),
  ('Борщ'),
  ('Плов узбекский'),
  ('Салат оливье');


INSERT INTO menu_items(menu_list_id, dish_id,price) VALUES
  (100010,100028,1.25),
  (100010,100029,3.45),
  (100010,100030,2.48),
  (100010,100031,5.57),
  (100010,100032,6.87),
  (100011,100033,12.47),
  (100011,100034,7.96),
  (100011,100035,14.58),
  (100011,100036,0.55),
  (100011,100037,0.55),
  (100012,100038,0.75),
  (100012,100039,0.95),
  (100012,100040,12.54),
  (100012,100041,24.58),
  (100012,100042,4.62),
  (100013,100043,4.12),
  (100013,100044,1.95),
  (100013,100045,17.58),
  (100013,100046,12.75),
  (100013,100047,8.12);

