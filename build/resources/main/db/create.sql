SET MODE PostgreSQL;


CREATE TABLE IF NOT EXISTS reviews (
    id int PRIMARY KEY auto_increment,
    rating INTEGER, --possibly int
    content VARCHAR,
    restaurantId INTEGER
);

CREATE TABLE IF NOT EXISTS restaurants (
    id int PRIMARY KEY auto_increment,
    name VARCHAR,
    location VARCHAR
);



CREATE TABLE IF NOT EXISTS users (
  id int PRIMARY KEY auto_increment,
  name VARCHAR
);