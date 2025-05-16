CALL add_transaction(
    'SUPPLIER_TO_WAREHOUSE',
    '2025-05-16',
    'Dostawa od dostawcy',
    1,
    NULL,
    1,
    NULL,
    1,
    '[{"ProductID": 1, "Quantity": 10}, {"ProductID": 2, "Quantity": 3}]'
);

CALL add_transaction(
    'WAREHOUSE_TO_WAREHOUSE',
    '2025-05-16',
    'Dostawa od magazynu do magazynu',
    1,
    1,
    2,
    NULL,
    NULL,
    '[{"ProductID": 1, "Quantity": 10}, {"ProductID": 2, "Quantity": 3}]'
);

CALL add_transaction(
    'WAREHOUSE_TO_CUSTOMER',
    '2025-05-16',
    'Dostawa do klienta',
    1,
    1,
    NULL,
    1,
    NULL,
    '[{"ProductID": 1, "Quantity": 10}, {"ProductID": 2, "Quantity": 3}]'
);

CALL receive_delivery(
    '2025-05-16',
    'Dostawa nowych monitorów',
    1,                
    2,               
    3,               
    '[
        { "ProductID": 101, "Quantity": 10 },
        { "ProductID": 102, "Quantity": 5 }
    ]'
);

CALL sell_to_client(
    '2025-05-16',
    'Sprzedaż laptopów',
    1,     
    4,      
    3,         
    '[
        { "ProductID": 101, "Quantity": 2 },
        { "ProductID": 102, "Quantity": 1 }
    ]'
);
