CREATE TABLE links(
   link_id serial PRIMARY KEY,
   link_key VARCHAR (10) NOT NULL,
   link VARCHAR (500) NOT NULL,
   click_count INT NOT NULL
);

