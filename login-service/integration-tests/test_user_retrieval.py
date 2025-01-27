from typing import Dict, Any

import os
import requests
import pytest

from postgres_client import PostgresClient
from user.user_cars_dataset import UsersCars
from user.user_dataset import Users, find_user_by_email
from user.users_init import insert_users, insert_users_with_cars
from user.user_client import get_user, get_cars_by_user_id


@pytest.fixture(scope='session')
def postgres():
    client = PostgresClient(
        host=os.getenv("POSTGRES_HOST", "localhost"),
        port=os.getenv("POSTGRES_PORT", "5434"),
        database_name=os.getenv("POSTGRES_DB", "postgres"),
        schema_name=os.getenv("POSTGRES_SCHEMA", "public"),
        user=os.getenv("POSTGRES_USER", "postgres"),
        password=os.getenv("POSTGRES_PASSWORD", "password")
    )

    yield client
    client.close()


@pytest.fixture(scope='session')
def users(postgres: PostgresClient):
    return insert_users(postgres, 3)


@pytest.fixture(scope='session')
def users_cars(postgres: PostgresClient):
    return insert_users_with_cars(postgres, 3)


def test_user_retrieval(users: Users):
    # given
    email = 'email00@email.com'
    expected_user = find_user_by_email(users, email)

    # when
    result = get_user(email, 'password00')

    # then
    assert result.status_code == requests.codes.ok
    body = result.json()
    assert_user(body, expected_user)


# @pytest.mark.parametrize('user_id', [1])
# def test_user_cars_retrieval(users_cars: UsersCars, user_id: int):
#     # given
#     expected_cars = users_cars.find_cars_by_user_id(user_id)
#
#     # when
#     result = get_cars_by_user_id('email00@email.com', 'password00', user_id)
#     assert result.status_code == requests.codes.ok
#     body = result.json()
#     for actual, expected in zip(body['content'], expected_cars):
#         assert_car(actual, expected)


def assert_user(actual_user: Dict[str, Any], expected_user: Dict[str, Any]):
    assert actual_user['first_name'] == expected_user['first_name']
    assert actual_user['last_name'] == expected_user['last_name']
    assert actual_user['email'] == expected_user['email']
    assert actual_user['role'] == expected_user['role']


def assert_car(actual_car: Dict[str, Any], expected_car: Dict[str, Any]):
    assert actual_car['id'] == str(expected_car['id'])
    assert actual_car['make'] == expected_car['make']
    assert actual_car['model'] == expected_car['model']
    assert actual_car['production_year'] == expected_car['production_year']
    assert actual_car['size'] == expected_car['size']
    assert actual_car['colour'] == expected_car['colour']
