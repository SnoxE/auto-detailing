SELECT
    c.id,
    c.make,
    c.model,
    c.production_year,
    c.size,
    c.colour
FROM
    cars AS c
WHERE
    c.user_id = ?;

