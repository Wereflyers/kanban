insert into users(email, password)
values ('user1@email.ru', '$2a$12$XZi0f66uhLZqHQ01CCdhI.WHqer9VSyOSvXrdbo6I6aesYZpncQyq'),
       ('user2@email.ru', '$2a$12$RbxAnha27VX3gmfKoleWGuoiKaRHvzxavBiclLKUACIglPyfeUrae');

insert into TASK(name, description, executor_id, author_id, status)
values ('task1', 'product1 description', null, 1, 'NEW'),
       ('task2', 'product2 description', 1, 2, 'IN_PROGRESS');

insert into COMMENT(author_id, task_id, text, publication_date)
values ( 1, 2, 'very hard', TIMESTAMP '2023-11-15 12:34:56' )