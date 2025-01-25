SELECT
    u.id,
    u.first_name,
    u.last_name,
    u.email,
    u.password,
    u.role
FROM
    users AS u
WHERE
    u.email = :email;

