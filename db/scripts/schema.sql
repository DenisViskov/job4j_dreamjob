DROP TABLE IF EXISTS post;
DROP TABLE IF EXISTS candidates;
DROP TABLE IF EXISTS client;
DROP TABLE IF EXISTS city;

CREATE TABLE post (
   id SERIAL PRIMARY KEY,
   name TEXT
);

CREATE TABLE city (
   id SERIAL PRIMARY KEY,
   name TEXT UNIQUE
);

INSERT INTO city(name) VALUES ('MSK');
INSERT INTO city(name) VALUES ('SPB');
INSERT INTO city(name) VALUES ('EKB');

CREATE TABLE candidates (
   id SERIAL PRIMARY KEY,
   name TEXT,
   photo TEXT,
   city TEXT
);

CREATE TABLE client (
   id SERIAL PRIMARY KEY,
   name TEXT,
   email TEXT UNIQUE,
   password TEXT
);