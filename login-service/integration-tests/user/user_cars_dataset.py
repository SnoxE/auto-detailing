from typing import Any, Dict, List

from dataclasses import dataclass
import random
import bcrypt


@dataclass
class UsersCars:
    users: List[Dict[str, Any]]
    cars: List[Dict[str, Any]]

    def find_cars_by_user_id(self, user_id):
        return [c for c in self.cars if c['user_id'] == user_id]


def gen_users_with_cars(users_number: int):
    users = []
    cars = []
    for index in range(users_number):
        users.append(gen_user(index))

    counter = 1
    for user in users:
        for i in range(random.randint(1, 10)):
            cars.append(gen_car(counter, user['id']))
            counter += 1

    return UsersCars(
        users,
        cars
    )


def gen_user(index):
    role_list = ['EMPLOYEE', 'USER', 'ADMIN']
    phone_no = []

    for i in range(9):
        phone_no.append(random.randint(0, 9))

    password_string = f'password{index:02}'
    bytes = password_string.encode('utf-8')
    salt = bcrypt.gensalt()
    password = bcrypt.hashpw(bytes, salt)
    password = str(password)[2:-1]

    return {
        'id': index + 1,
        'first_name': f'first_name{index:02}',
        'last_name': f'last_name{index:02}',
        'email': f'email{index:02}@email.com',
        'password': password,
        'phone_number': ''.join(map(str, phone_no)),
        'role': role_list[random.randint(0, 2)]
    }


def gen_car(index, user_id):
    sizes = ['MAŁE', 'ŚREDNIE', 'DUŻE']

    return {
        'id': index,
        'user_id': user_id,
        'make': f'make{index:02}',
        'model': f'model{index:02}',
        'production_year': f'make{index:02}',
        'size': sizes[random.randint(0, 2)],
        'colour': f'colour{index:02}'
    }
