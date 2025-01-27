from typing import Dict, Any, List, Optional, Tuple

import psycopg2


class PostgresClient:
    def __init__(self, host: str, port: str, database_name: str, schema_name: str, user: str, password: str):
        self.__database = psycopg2.connect(host=host,
                                           port=port,
                                           dbname=database_name,
                                           options=f'-c search_path={schema_name}',
                                           user=user,
                                           password=password)
        self.__database.autocommit = True

    def connection(self):
        return self.__database

    def insert(self, table_name: str, insert_data: List[Dict[str, Any]]):
        with self.__database.cursor() as cursor:
            for prepared_insert, prepared_data in build_insert(table_name, insert_data):
                cursor.execute(prepared_insert, prepared_data)

    def override_insert(self, table_name: str, insert_data: List[Dict[str, Any]]):
        with self.__database.cursor() as cursor:
            for prepared_insert, prepared_data in build_overriding_insert(table_name, insert_data):
                cursor.execute(prepared_insert, prepared_data)

    def count(self, table_name: str) -> int:
        with self.__database.cursor() as cursor:
            cursor.execute(f"SELECT COUNT(*) FROM {table_name};")
            count = cursor.fetchone()[0]
        return count

    def truncate(self, table_name: str, cascade: bool = False):
        with self.__database.cursor() as cursor:
            if not cascade:
                cursor.execute(f'TRUNCATE {table_name};')
            else:
                cursor.execute(f'TRUNCATE {table_name} CASCADE;')

    def disable_trigger(self, table_name: str, trigger_name: str):
        with self.__database.cursor() as cursor:
            cursor.execute(f'ALTER TABLE {table_name} DISABLE TRIGGER {trigger_name}')

    def enable_trigger(self, table_name: str, trigger_name: str):
        with self.__database.cursor() as cursor:
            cursor.execute(f'ALTER TABLE {table_name} ENABLE TRIGGER {trigger_name}')

    def close(self):
        self.__database.close()


def build_insert(table_name, inserts_data: List[Dict[str, Any]], ignored_columns: Optional[List[str]] = None) -> List[Tuple[str, List[Any]]]:
    ignored_columns = ([] if ignored_columns is None else ignored_columns) + ['id']
    inserts: List[Tuple[str, List[Any]]] = []
    for insert_data in inserts_data:
        keys = [k for k in insert_data.keys() if k not in ignored_columns]
        values = [insert_data[k] for k in keys]
        sql_command = 'INSERT INTO {table} ({keys}) VALUES ({placeholders});'.format(
            table=table_name,
            keys=', '.join(keys),
            placeholders=', '.join(["%s" for _ in values])
        )
        inserts.append((sql_command, values))
    return inserts


def build_overriding_insert(table_name, inserts_data: List[Dict[str, Any]], ignored_columns: Optional[List[str]] = None) -> List[Tuple[str, List[Any]]]:
    ignored_columns = ([] if ignored_columns is None else ignored_columns)
    inserts: List[Tuple[str, List[Any]]] = []
    for insert_data in inserts_data:
        keys = [k for k in insert_data.keys() if k not in ignored_columns]
        values = [insert_data[k] for k in keys]
        sql_command = 'INSERT INTO {table} ({keys}) OVERRIDING SYSTEM VALUE VALUES ({placeholders});'.format(
            table=table_name,
            keys=', '.join(keys),
            placeholders=', '.join(["%s" for _ in values])
        )
        inserts.append((sql_command, values))
    return inserts
