DROP TABLE IF EXISTS post;
DROP TABLE IF EXISTS candidates;

CREATE TABLE post (
   id SERIAL PRIMARY KEY,
   name TEXT
);

CREATE TABLE candidates (
   id SERIAL PRIMARY KEY,
   name TEXT,
   photo TEXT
);