SELECT
    s.id,
    s.name,
    s.price,
    s.length,
    s.car_size
FROM
    services AS s
WHERE
    s.name = ?;

