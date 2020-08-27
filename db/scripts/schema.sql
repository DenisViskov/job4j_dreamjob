DROP TABLE IF EXISTS post;
DROP TABLE IF EXISTS candidates;
DROP TABLE IF EXISTS user;

CREATE TABLE post (
   id SERIAL PRIMARY KEY,
   name TEXT
);

CREATE TABLE candidates (
   id SERIAL PRIMARY KEY,
   name TEXT,
   photo TEXT
);

CREATE TABLE user (
   id SERIAL PRIMARY KEY,
   name TEXT,
   email TEXT,
   password TEXT
);