import requests


def authenticate(email: str, password: str) -> str:
    try:
        data_to_send = {
            "email": email,
            "password": password
        }

        response = requests.post('http://localhost:8081/api/token', json=data_to_send)
        if response.status_code == 200:
            data = response.json()
            return data['token']
        else:
            print("Failed to retrieve token:", response.status_code)
    except requests.exceptions.RequestException as e:
        print("Error: ", e)


def get_user(email, password):
    token = authenticate(email, password)
    headers = {"Authorization": f"Bearer {token}"}
    url = 'http://localhost:8081/api/users/user'

    return requests.get(url=url, headers=headers)


def get_cars_by_user_id(email, password, user_id):
    token = authenticate(email, password)
    headers = {"Authorization": f"Bearer {token}"}
    url = f'http://localhost:8080/api/users/{user_id}/cars'

    return requests.get(url=url, headers=headers)

