from typing import Any, Dict, List

from dataclasses import dataclass
import random
import bcrypt


@dataclass
class Users:
    users: List[Dict[str, Any]]


def gen_users(users_number: int):
    users = []
    for index in range(users_number):
        users.append(gen_user(index))

    return users


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
        'first_name': f'first_name{index:02}',
        'last_name': f'last_name{index:02}',
        'email': f'email{index:02}@email.com',
        'password': password,
        'phone_number': ''.join(map(str, phone_no)),
        'role': role_list[random.randint(0, 2)]
    }


def find_user_by_email(users, email):
    for user in users:
        if user['email'] == email:
            return user
