DELETE FROM USER_ROLE;
DELETE FROM VOTE;
DELETE FROM USER;
DELETE FROM DISH;
DELETE FROM RESTAURANT;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO USER (name, email, password) VALUES
    ('User1', 'user1@yandex.ru', 'password1'),
    ('User2', 'user2@yandex.ru', 'password2'),
    ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO USER_ROLE (role, user_id) VALUES
    ('ROLE_USER', 100000),
    ('ROLE_USER', 100001),
    ('ROLE_ADMIN', 100002);

INSERT INTO RESTAURANT (NAME) VALUES
    ('Прага'),
    ('Москва'),
    ('Казань');

INSERT INTO DISH (description, price, rest_id) VALUES
    ('Салат', 15000, 100003),
    ('Чай', 5000, 100003),
    ('Суп', 20000, 100004),
    ('Цыпленок табака', 25000, 100004),
    ('Капучино', 7000, 100004),
    ('Чак-чак', 23000, 100005),
    ('Чай с молоком', 6000, 100005);
INSERT INTO DISH (DATE, DESCRIPTION, PRICE, REST_ID) VALUES
    ('2020-04-06', 'Торт', 15000, 100003),
    ('2020-04-05', 'Треугольник', 3300, 100005);

INSERT INTO VOTE (USER_ID, REST_ID) VALUES
    (100000, 100003),
    (100001, 100003),
    (100002, 100005);

