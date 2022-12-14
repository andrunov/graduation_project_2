DELETE FROM users;
DELETE FROM roles;
DELETE FROM restaurants;
DELETE FROM dish_descriptions;
DELETE FROM dishes;
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


INSERT INTO menu_lists( date) VALUES
  ('2022-01-14'),
  ('2022-01-14'),
  ('2022-01-14'),
  ('2022-01-14');

INSERT INTO votes(date_time, user_id, restaurant_id) VALUES
  ('2022-01-14 16:30:00', 100002, 100006),
  ('2022-01-14 15:45:00', 100003, 100007),
  ('2022-01-14 15:40:00', 100004, 100008),
  ('2022-01-14 15:32:00', 100005, 100009);

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
  (100010,100018,1.25),
  (100010,100019,3.45),
  (100010,100020,2.48),
  (100010,100021,5.57),
  (100010,100022,6.87),
  (100011,100023,12.47),
  (100011,100024,7.96),
  (100011,100025,14.58),
  (100011,100026,0.55),
  (100011,100027,0.55),
  (100012,100028,0.75),
  (100012,100029,0.95),
  (100012,100030,12.54),
  (100012,100031,24.58),
  (100012,100032,4.62),
  (100013,100033,4.12),
  (100013,100034,1.95),
  (100013,100035,17.58),
  (100013,100036,12.75),
  (100013,100037,8.12);

INSERT INTO restaurant_menu(restaurant_id, menu_list_id) VALUES
  (100006, 100010),
  (100007, 100011),
  (100008, 100012),
  (100009, 100013);