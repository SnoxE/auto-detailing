SELECT
    s.id,
    s.name,
    s.price,
    s.length,
    s.car_size
FROM
    services AS s
WHERE
    s.name = ?
    AND s.car_size = ?::car_size;

