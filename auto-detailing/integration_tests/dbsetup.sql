CREATE TYPE public.car_size AS ENUM ('SMALL', 'MEDIUM', 'LARGE');

create table users(
	id integer NOT NULL primary key generated always as identity,
	first_name varchar(30) NOT NULL,
	last_name varchar(30),
	email varchar(50) NOT NULL,
	password text NOT NULL,
	phone_number varchar(15) NOT NULL,
  role varchar(20) NOT NULL
);

create table cars(
  id integer NOT NULL primary key generated always as identity,
  user_id integer NOT NULL references users(id),
  make varchar(30) NOT NULL,
  model varchar(30) NOT NULL,
  production_year varchar(10) NOT NULL,
  size car_size NOT NULL,
  colour varchar(30) NOT NULL
);

-- SERVICES TABLE --

create table services(
  id integer NOT NULL primary key generated always as identity,
  name varchar(50) NOT NULL,
  price integer NOT NULL,
  length TIME NOT NULL,
  size car_size NOT NULL
);

INSERT INTO services(name, price, length, size) VALUES 
('Mycie Ręczne', 100, '01:00:00', 'SMALL'),
('Mycie Ręczne', 120, '01:15:00', 'MEDIUM'),
('Mycie Ręczne', 140, '01:30:00', 'LARGE'),
('Woskowanie', 400, '03:00:00', 'SMALL'),
('Woskowanie', 500, '03:15:00', 'MEDIUM'),
('Woskowanie', 600, '03:30:00', 'LARGE'),
('One Polish', 1000, '04:00:00', 'SMALL'),
('One Polish', 1100, '04:30:00', 'MEDIUM'),
('One Polish', 1200, '05:00:00', 'LARGE'),
('Powłoka Ceramiczna', 1800, '06:00:00', 'SMALL'),
('Powłoka Ceramiczna', 2100, '06:30:00', 'MEDIUM'),
('Powłoka Ceramiczna', 2400, '07:00:00', 'LARGE'),
('Korekta Lakieru', 1500, '08:00:00', 'SMALL'),
('Korekta Lakieru', 1800, '09:00:00', 'MEDIUM'),
('Korekta Lakieru', 2100, '10:00:00', 'LARGE'),
('Pranie Podstawowe', 300, '03:00:00', 'SMALL'),
('Pranie Podstawowe', 375, '03:30:00', 'MEDIUM'),
('Pranie Podstawowe', 450, '04:00:00', 'LARGE'),
('Detailing Wnętrza', 600, '06:00:00', 'SMALL'),
('Detailing Wnętrza', 675, '06:30:00', 'MEDIUM'),
('Detailing Wnętrza', 750, '07:00:00', 'LARGE'),
('Niewidzialna Wycieraczka', 100, '00:40:00', 'SMALL'),
('Niewidzialna Wycieraczka', 120, '00:50:00', 'MEDIUM'),
('Niewidzialna Wycieraczka', 140, '01:00:00', 'LARGE');

create table reservations(
  id integer NOT NULL primary key generated always as identity,
  user_id integer NOT NULL references users(id) ,
  service_id integer NOT NULL references services(id),
  car_id integer NOT NULL references cars(id),
  start_at timestamp NOT NULL,
  end_at timestamp NOT NULL
);
