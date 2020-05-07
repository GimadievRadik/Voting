DELETE FROM USER_ROLE;
DELETE FROM VOTE;
DELETE FROM USER;
DELETE FROM DISH;
DELETE FROM RESTAURANT;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO USER (name, email, password) VALUES
    ('User1', 'user1@yandex.ru', 'password1'),
    ('User2', 'user2@yandex.ru', 'password2'),
    ('User3', 'user3@yandex.ru', 'password3'),
    ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO USER_ROLE (role, user_id) VALUES
    ('ROLE_USER', 100000),
    ('ROLE_USER', 100001),
    ('ROLE_USER', 100002),
    ('ROLE_ADMIN', 100003);

INSERT INTO RESTAURANT (NAME) VALUES
    ('Прага'),
    ('Москва'),
    ('Казань');
INSERT INTO DISH (description, price, rest_id, DATE) VALUES
    ('Салат', 15000, 100004, TODAY),
    ('Чай', 5000, 100004, TODAY),
    ('Суп', 20003, 100005, TODAY),
    ('Цыпленок табака', 25000, 100005, TODAY),
    ('Капучино', 7000, 100005, TODAY),
    ('Чак-чак', 23001, 100006, TODAY),
    ('Чай с молоком', 6005, 100006, TODAY);
INSERT INTO DISH (description, price, rest_id, DATE) VALUES
    ('Торт', 15000, 100004, '2020-04-06'),
    ('Треугольник', 3300, 100006, '2020-04-05');

INSERT INTO VOTE (USER_ID, REST_ID) VALUES
    (100000, 100004),
    (100001, 100004),
    (100002, 100006),
    (100003, 100006);
INSERT INTO VOTE (DATE_TIME, USER_ID, REST_ID) VALUES
    ('2020-04-06 10:00:00', 100000, 100004),
    ('2020-04-05 11:01:00', 100000, 100005),
    ('2020-04-04 11:00:00', 100001, 100006);
