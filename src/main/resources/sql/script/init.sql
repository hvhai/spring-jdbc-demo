-- check connection
SELECT now();
-- create database
SHOW DATABASES;
CREATE DATABASE forum;
USE forum;

CREATE TABLE post (
id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
content VARCHAR(255),
reg_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
-- add data for post table
INSERT INTO post(content) VALUES ('Hello mysql!');
SELECT * FROM post p ;
UPDATE post SET content='Hello MySQL!!!' WHERE id=1;
SELECT * FROM post p ;
INSERT INTO post(content) VALUES ('My second post');
SELECT * FROM post p ;

CREATE TABLE comment (
id INT(6) UNSIGNED AUTO_INCREMENT,
post_id INT(6) UNSIGNED,
value VARCHAR(255),
reg_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
PRIMARY KEY (id),
CONSTRAINT fk_comment_post FOREIGN KEY (post_id) REFERENCES post(id)
);
SELECT * FROM comment c ;
desc comment ;
-- change column to not null
ALTER TABLE comment MODIFY post_id INT(6) UNSIGNED NOT NULL;
ALTER TABLE comment MODIFY value VARCHAR(255) NOT NULL;
desc comment;
-- DROP TABLE comment;
-- add data for comment
INSERT INTO comment (post_id, value) VALUES (1, 'You\'re welcome !!');
SELECT * from comment c ;
SELECT * FROM post p ;