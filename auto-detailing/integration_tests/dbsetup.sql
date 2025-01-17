create table reservations(
                             id integer NOT NULL primary key generated always as identity,
                             user_id integer NOT NULL references users(id) ,
                             service_id integer NOT NULL references services(id),
                             car_id integer NOT NULL references cars(id),
                             start_at timestamp NOT NULL,
                             end_at timestamp NOT NULL
);