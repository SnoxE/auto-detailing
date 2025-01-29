CREATE TYPE public.car_size AS ENUM ('MAŁE', 'ŚREDNIE', 'DUŹE');

create table users
(
    id           integer     NOT NULL primary key generated always as identity,
    first_name   varchar(30) NOT NULL,
    last_name    varchar(30),
    email        varchar(50) NOT NULL,
    password     text        NOT NULL,
    phone_number varchar(15) NOT NULL,
    role         varchar(20) NOT NULL
);

create table cars
(
    id              integer     NOT NULL primary key generated always as identity,
    user_id         integer     NOT NULL references users (id),
    make            varchar(30) NOT NULL,
    model           varchar(30) NOT NULL,
    production_year varchar(10) NOT NULL,
    car_size        car_size    NOT NULL,
    colour          varchar(30) NOT NULL
);

-- SERVICES TABLE --

create table services
(
    id       integer     NOT NULL primary key generated always as identity,
    name     varchar(50) NOT NULL,
    price    integer     NOT NULL,
    length   TIME        NOT NULL,
    car_size car_size    NOT NULL
);

INSERT INTO services(name, price, length, car_size)
VALUES ('Mycie Ręczne', 100, '01:00:00', 'MAŁE'),
       ('Mycie Ręczne', 120, '01:15:00', 'ŚREDNIE'),
       ('Mycie Ręczne', 140, '01:30:00', 'DUŻE'),
       ('Woskowanie', 400, '03:00:00', 'MAŁE'),
       ('Woskowanie', 500, '03:15:00', 'ŚREDNIE'),
       ('Woskowanie', 600, '03:30:00', 'DUŻE'),
       ('One Polish', 1000, '04:00:00', 'MAŁE'),
       ('One Polish', 1100, '04:30:00', 'ŚREDNIE'),
       ('One Polish', 1200, '05:00:00', 'DUŻE'),
       ('Powłoka Ceramiczna', 1800, '06:00:00', 'MAŁE'),
       ('Powłoka Ceramiczna', 2100, '06:30:00', 'ŚREDNIE'),
       ('Powłoka Ceramiczna', 2400, '07:00:00', 'DUŻE'),
       ('Korekta Lakieru', 1500, '08:00:00', 'MAŁE'),
       ('Korekta Lakieru', 1800, '09:00:00', 'ŚREDNIE'),
       ('Korekta Lakieru', 2100, '10:00:00', 'DUŻE'),
       ('Pranie Podstawowe', 300, '03:00:00', 'MAŁE'),
       ('Pranie Podstawowe', 375, '03:30:00', 'ŚREDNIE'),
       ('Pranie Podstawowe', 450, '04:00:00', 'DUŻE'),
       ('Detailing Wnętrza', 600, '06:00:00', 'MAŁE'),
       ('Detailing Wnętrza', 675, '06:30:00', 'ŚREDNIE'),
       ('Detailing Wnętrza', 750, '07:00:00', 'DUŻE'),
       ('Niewidzialna Wycieraczka', 100, '00:40:00', 'MAŁE'),
       ('Niewidzialna Wycieraczka', 120, '00:50:00', 'ŚREDNIE'),
       ('Niewidzialna Wycieraczka', 140, '01:00:00', 'DUŻE');

create table reservations
(
    id         integer   NOT NULL primary key generated always as identity,
    user_id    integer   NOT NULL references users (id),
    service_id integer   NOT NULL references services (id),
    car_id     integer   NOT NULL references cars (id),
    start_at   timestamp NOT NULL,
    end_at     timestamp NOT NULL
);
