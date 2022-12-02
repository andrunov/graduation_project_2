DROP TABLE IF EXISTS users CASCADE ;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS restaurants CASCADE ;
DROP TABLE IF EXISTS dishes CASCADE ;
DROP TABLE IF EXISTS votes CASCADE ;
DROP TABLE IF EXISTS user_votes CASCADE ;
DROP TABLE IF EXISTS menu_lists CASCADE ;
DROP TABLE IF EXISTS restaurant_menu CASCADE ;

DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START 100000;

CREATE TABLE users
(
  id                    INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  name                  VARCHAR NOT NULL,
  email                 VARCHAR NOT NULL UNIQUE,
  password              VARCHAR NOT NULL
);

CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TABLE roles
(
    user_id             INTEGER NOT NULL,
    role                VARCHAR,
    CONSTRAINT          user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY         (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE restaurants
(
    id                    INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name                  VARCHAR NOT NULL,
    address               VARCHAR NOT NULL
);

CREATE TABLE user_votes
(
    id                  INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    user_id             INTEGER NOT NULL,
    date_time           TIMESTAMP NOT NULL,
    restaurant_id       INTEGER NOT NULL,
    FOREIGN KEY         (restaurant_id) REFERENCES restaurants (id) ON DELETE CASCADE,
    FOREIGN KEY         (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE menu_lists(
                           id                    INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
                           date             TIMESTAMP NOT NULL
);

CREATE TABLE dishes
(
  id                    INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  name                  VARCHAR NOT NULL,
  price                 DOUBLE PRECISION,
  menu_list_id          INTEGER NOT NULL ,
  FOREIGN KEY           (menu_list_id) REFERENCES menu_lists (id) ON DELETE CASCADE
);


CREATE TABLE restaurant_menu
(
    restaurant_id       INTEGER NOT NULL ,
    menu_list_id        INTEGER NOT NULL ,
    local_date          TIMESTAMP NOT NULL,
    FOREIGN KEY         (menu_list_id) REFERENCES menu_lists (id) ON DELETE CASCADE,
    FOREIGN KEY         (restaurant_id) REFERENCES restaurants(id) ON DELETE CASCADE
);

CREATE TABLE votes
(
    id                  INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    date_time           TIMESTAMP NOT NULL,
    restaurant_id       INTEGER NOT NULL,
    FOREIGN KEY         (restaurant_id) REFERENCES restaurants(id) ON DELETE CASCADE
);

