INSERT INTO tb_user (name, email, birth, gender, photo_uri) VALUES ('Malfe', 'malfe@gmail.com', '2024-06-22', 'female', null);
INSERT INTO tb_user (name, email, birth, gender, photo_uri) VALUES ('Xawio', 'xawio@gmail.com', '1992-04-15', 'female', null);
INSERT INTO tb_user (name, email, birth, gender, photo_uri) VALUES ('Isobor', 'isobor@gmail.com', '1996-09-27', 'male', null);
INSERT INTO tb_user (name, email, birth, gender, photo_uri) VALUES ('Hihoauk', 'hihoauk@gmail.com', '1996-03-31', 'female', null);
INSERT INTO tb_user (name, email, birth, gender, photo_uri) VALUES ('Lioarn', 'lioarn@gmail.com', '1990-07-14', 'male', null);
INSERT INTO tb_user (name, email, birth, gender, photo_uri) VALUES ('Noyka', 'noyka@hotmail.com', '2001-07-29', 'male', null);

INSERT INTO tb_post (date, user_id, text) VALUES ('2024-06-22T10:30:00.123Z', 2L, 'Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...');
INSERT INTO tb_post (date, user_id, text) VALUES ('2024-03-22T14:30:00.123Z', 1L, 'Vitae aenean nec condimentum tempor, suspendisse fringilla.');
INSERT INTO tb_post (date, user_id, text) VALUES ('2024-01-22T18:30:00.123Z', 4L, 'Aliquam inceptos orci amet odio, nulla fusce.');
INSERT INTO tb_post (date, user_id, text) VALUES ('2024-02-22T11:30:00.123Z', 2L, 'Pulvinar cras eros dapibus leo, id elementum.');

INSERT INTO tb_album (title, date, user_id) VALUES ('Férias 2024', '2024-02-22T11:30:00.123Z', 1L);

INSERT INTO tb_photo (uri, album_id, post_id, user_id) VALUES ('https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRxjKUkd4O9Y85dBugotgzbWb_iFnVdUY1o-Q&s', null, 4L, 2L);
INSERT INTO tb_photo (uri, album_id, post_id, user_id) VALUES ('https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQU45vXYj1wFd-44TN0hzpsHRxz-bbnUu_MkA&s', null, 1L, 2L);
INSERT INTO tb_photo (uri, album_id, post_id, user_id) VALUES ('https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS4ARDanQuflyFM7-GooEdWDzA6mz-MMgj8oQ&s', null, 2L, 1L);
INSERT INTO tb_photo (uri, album_id, post_id, user_id) VALUES ('https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRsejLIhYr9RG-MQhnCxhgM7iVtEWE1h7VJJw&s', null, 3L, 4L);