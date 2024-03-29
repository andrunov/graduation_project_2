DROP TABLE IF EXISTS users CASCADE ;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS restaurants CASCADE ;
DROP TABLE IF EXISTS dishes CASCADE ;
DROP TABLE IF EXISTS menu_items CASCADE ;
DROP TABLE IF EXISTS votes CASCADE ;
DROP TABLE IF EXISTS menu_lists CASCADE ;
DROP TABLE IF EXISTS restaurant_menu CASCADE ;
DROP SEQUENCE IF EXISTS GLOBAL_SEQ;

CREATE SEQUENCE GLOBAL_SEQ START WITH 100000;

CREATE TABLE users
(
  id                    INTEGER PRIMARY KEY,
  name                  VARCHAR(255),
  email                 VARCHAR(255),
  password              VARCHAR(255)
);

CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TABLE roles
(
    user_id             INTEGER NOT NULL,
    role                VARCHAR(255),
    CONSTRAINT          user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY         (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE restaurants
(
    id                    INTEGER PRIMARY KEY,
    name                  VARCHAR(255),
    address               VARCHAR(255)
);

CREATE TABLE menu_lists(
    id                    INTEGER PRIMARY KEY,
    date                  TIMESTAMP NOT NULL,
    restaurant_id         INTEGER NOT NULL,
    FOREIGN KEY           (restaurant_id) REFERENCES restaurants(id) ON DELETE CASCADE
);

CREATE TABLE dishes
(
    id                    INTEGER  PRIMARY KEY,
    name                  VARCHAR(255)
);

CREATE TABLE menu_items
(
    id                    INTEGER  PRIMARY KEY,
    price                 DOUBLE PRECISION,
    menu_list_id          INTEGER NOT NULL ,
    dish_id   INTEGER NOT NULL ,
    FOREIGN KEY           (menu_list_id) REFERENCES menu_lists (id) ON DELETE CASCADE,
    FOREIGN KEY           (dish_id) REFERENCES dishes (id) ON DELETE CASCADE
);

CREATE TABLE votes
(
    id                  INTEGER  PRIMARY KEY,
    date_time           TIMESTAMP NOT NULL,
    user_id             INTEGER NOT NULL,
    restaurant_id       INTEGER NOT NULL,
    menu_list_id             INTEGER NOT NULL,
    FOREIGN KEY         (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY         (restaurant_id) REFERENCES restaurants(id) ON DELETE CASCADE,
    FOREIGN KEY         (menu_list_id) REFERENCES menu_lists(id) ON DELETE CASCADE
);

