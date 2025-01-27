from postgres_client import PostgresClient
from user.user_cars_dataset import gen_users_with_cars
from user.user_dataset import gen_users


def insert_users(postgres: PostgresClient, users_number: int):
    users = gen_users(users_number)

    postgres.truncate('USERS', cascade=True)
    postgres.insert('USERS', [u for u in users])

    return users


def insert_users_with_cars(postgres: PostgresClient, users_number: int):
    users = gen_users_with_cars(users_number)

    postgres.truncate('CARS', cascade=True)
    postgres.truncate('USERS', cascade=True)
    postgres.override_insert('USERS', [u for u in users.users])
    postgres.override_insert('CARS', [c for c in users.cars])

    return users
