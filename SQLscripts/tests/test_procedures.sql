CALL add_transaction(
    'SUPPLIER_TO_WAREHOUSE',
    '2025-05-17',
    'Dostawa od dostawcy',
    1,      -- pracownik
    NULL,   -- źródło
    1,      -- cel
    NULL,   -- klient
    1,      -- supplier
    '[{"ProductID": 2, "Quantity": 10}, {"ProductID": 3, "Quantity": 5}]'
);

CALL add_transaction(
    'SUPPLIER_TO_WAREHOUSE',
    '2025-05-17',
    'Dostawa od dostawcy',
    2,      -- pracownik
    NULL,   -- źródło
    1,      -- cel
    NULL,   -- klient
    2,      -- supplier
    '[{"ProductID": 3, "Quantity": 10}]'
);

CALL add_transaction(
    'WAREHOUSE_TO_WAREHOUSE',
    '2025-05-17',
    'Dostawa z magazynu do magazynu',
    1,      -- pracownik
    1,      -- źródło
    2,      -- cel
    NULL,   -- klient
    NULL,   -- supplier
    '[{"ProductID": 2, "Quantity": 10}]'
);

CALL add_transaction(
    'WAREHOUSE_TO_WAREHOUSE',
    '2025-05-17',
    'Dostawa z magazynu do magazynu',
    1,      -- pracownik
    2,      -- źródło
    1,      -- cel
    NULL,   -- klient
    NULL,   -- supplier
    '[{"ProductID": 2, "Quantity": 2}]'
);

CALL add_transaction(
    'WAREHOUSE_TO_CUSTOMER',
    '2025-05-17',
    'Dostawa z magazynu do magazynu',
    3,      -- pracownik
    2,      -- źródło
    NULL,   -- cel
    1,      -- klient
    NULL,   -- supplier
    '[{"ProductID": 2, "Quantity": 1}]'
);

CALL receive_delivery(
    '2025-05-17',
    'Nowa dostawa',
    5,      -- pracownik
    1,      -- supplier
    1,      -- cel
    '[{"ProductID": 5, "Quantity": 2}]'
);

CALL sell_to_client(
    '2025-05-17',
    'Sprzedaż do klienta',
    7,      -- pracownik
    2,      -- źródło
    1,      -- klient
    '[{"ProductID": 2, "Quantity": 1}]'
);

CALL exchange_between_warehouses(
    '2025-05-17',
    'Wymiana między magazynami',
    8,      -- pracownik
    1,      -- źródło
    2,      -- cel
    '[{"ProductID": 3, "Quantity": 15}]'
);

CALL receive_delivery(
    '2025-05-17',
    'Nowa dostawa produktu 3',
    5,      -- pracownik
    1,      -- supplier
    1,      -- cel
    '[{"ProductID": 3, "Quantity": 5}]'
);
