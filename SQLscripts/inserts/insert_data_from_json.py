import json
import mysql.connector
import os
from dotenv import load_dotenv


def parse_json_data(filepath: str) -> dict:
    """Wczytuje dane JSON z pliku i zwraca jako słownik."""
    try:
        with open(filepath, 'r', encoding='utf-8') as f:
            return json.load(f)
    except FileNotFoundError:
        raise Exception(f"Plik {filepath} nie istnieje.")
    except json.JSONDecodeError as e:
        raise Exception(f"Błąd dekodowania JSON: {e}")

def insert_data_to_database(data: dict, config: dict):
    """Wstawia dane do bazy MySQL w odpowiedniej kolejności."""

    logical_order = [
        "region", "country", "city", "address",
        "client", "supplier", "warehouse", "employee",
        "category", "product", "productinventory",
        "report", "transaction", "transactionproduct"
    ]

    table_name_map = {
        "region": "Region",
        "country": "Country",
        "city": "City",
        "address": "Address",
        "client": "Client",
        "supplier": "Supplier",
        "warehouse": "Warehouse",
        "employee": "Employee",
        "category": "Category",
        "product": "Product",
        "productinventory": "ProductInventory",
        "report": "Report",
        "transaction": "Transaction",
        "transactionproduct": "TransactionProduct"
    }

    def insert_table(cursor, actual_table_name, rows):
        if not rows:
            return
        columns = rows[0].keys()
        placeholders = ", ".join(["%s"] * len(columns))
        column_names = ", ".join(columns)
        query = f"INSERT INTO {actual_table_name} ({column_names}) VALUES ({placeholders})"
        for row in rows:
            values = tuple(row[col] for col in columns)
            cursor.execute(query, values)

    try:
        conn = mysql.connector.connect(**config)
        cursor = conn.cursor()

        for logical_name in reversed(logical_order):
            actual_table = table_name_map[logical_name]
            print(f"Resetowanie tabeli: {actual_table}")
            cursor.execute(f"DELETE FROM {actual_table}")
            cursor.execute(f"ALTER TABLE {actual_table} AUTO_INCREMENT = 1")
        conn.commit()

        for logical_name in logical_order:
            if logical_name in data:
                actual_table = table_name_map[logical_name]
                print(f"Wstawianie danych do tabeli: {actual_table}")
                insert_table(cursor, actual_table, data[logical_name])

        conn.commit()
        print("Wszystkie dane zostały pomyślnie wstawione.")

    except mysql.connector.Error as err:
        print(f"Błąd MySQL: {err}")
    finally:
        if 'cursor' in locals():
            cursor.close()
        if 'conn' in locals() and conn.is_connected():
            conn.close()

if __name__ == "__main__":
    load_dotenv()

    config = {
        'user': os.getenv("DB_USER"),
        'password': os.getenv("DB_PASSWORD"),
        'host': os.getenv("DB_HOST"),
        'port': int(os.getenv("DB_PORT")),
        'database': os.getenv("DB_NAME"),
        'raise_on_warnings': True
    }

    data = parse_json_data("data.json")
    insert_data_to_database(data, config)
