SELECT
    s.id,
    s.name,
    s.price,
    s.length,
    s.size
FROM
    services AS s
WHERE
    s.name = ?
    AND s.size = ?::car_size;

