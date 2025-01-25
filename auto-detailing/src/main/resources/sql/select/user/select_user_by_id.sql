SELECT
    u.id,
    u.first_name,
    u.last_name,
    u.email,
    u.password
FROM
    users AS u
WHERE
    u.id = ?;

