DELETE FROM users;
DELETE FROM roles;
DELETE FROM restaurants;
DELETE FROM dish_descriptions;
DELETE FROM dishes;
DELETE FROM user_votes;
DELETE FROM menu_lists;
DELETE FROM restaurant_menu;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users(name,email,password) VALUES
  ('Алексей Иванов','ivanov.alexey@gmail.com','111222'),
  ('Андрей Горбунов','andrunov@gmail.com','222333'),
  ('Павел Сидоров','sidor@gmail.com','333444'),
  ('Roberto Zanetti','rzanetti@gmail.com','444555'),
  ('John Bon Jovi','jbj@gmail.com','555666'),
  ('Didier Maoruani','dmauruani@gmail.com','666777');

INSERT INTO roles(user_id, role) VALUES
  (100000,'REGULAR'),
  (100001,'REGULAR'),
  (100001,'ADMIN'),
  (100002,'REGULAR'),
  (100003,'REGULAR'),
  (100004,'REGULAR'),
  (100005,'REGULAR');

INSERT INTO restaurants(name, address) VALUES
  ('Ёлки-палки','ул. Некрасова, 14'),
  ('Пиццерия','пл. Пушкина, 6'),
  ('Закусочная','Привокзальная пл, 3'),
  ('Прага','ул. Арбат, 1');

INSERT INTO user_votes(user_id,  restaurant_id) VALUES
  (100002, 100006),
  (100003, 100007),
  (100004, 100008),
  (100005, 100009);

INSERT INTO menu_lists( date) VALUES
  ('2022-01-14'),
  ('2022-01-14'),
  ('2022-01-14'),
  ('2022-01-14');

INSERT INTO votes(date_time, restaurant_id) VALUES
  ('2022-01-14 16:30:00', 100006),
  ('2022-01-14 15:45:00', 100007),
  ('2022-01-14 15:40:00', 100008),
  ('2022-01-14 15:32:00', 100009);

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


INSERT INTO dish_descriptions(menu_list_id, dish_id,price) VALUES
  (100014,100022,1.25),
  (100014,100023,3.45),
  (100014,100024,2.48),
  (100014,100025,5.57),
  (100014,100026,6.87),
  (100015,100027,12.47),
  (100015,100028,7.96),
  (100015,100029,14.58),
  (100015,100030,0.55),
  (100015,100031,0.55),
  (100016,100032,0.75),
  (100016,100033,0.95),
  (100016,100034,12.54),
  (100016,100035,24.58),
  (100016,100036,4.62),
  (100017,100037,4.12),
  (100017,100038,1.95),
  (100017,100039,17.58),
  (100017,100040,12.75),
  (100017,100041,8.12);

INSERT INTO restaurant_menu(restaurant_id, menu_list_id) VALUES
  (100006, 100014),
  (100007, 100015),
  (100008, 100016),
  (100009, 100017);