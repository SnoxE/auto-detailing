SELECT
    u.id,
    u.first_name,
    u.last_name,
    u.email,
    u.phone_number,
    u.password,
    u.role
FROM
    users AS u
WHERE
    u.email = ?;

