SELECT
    r.id AS res_id,
    s.name AS services_name,
    s.price AS services_price,
    c.make AS cars_make,
    c.model AS cars_model,
    c.production_year AS cars_year,
    c.colour AS cars_colour,
    r.start_at AS res_start_at,
    r.end_at AS res_end_at
FROM
    reservations AS r
    JOIN services AS s ON r.service_id = s.id
    JOIN cars AS c ON r.car_id = c.id
WHERE
    r.user_id = ?
ORDER BY
    r.start_at DESC;

