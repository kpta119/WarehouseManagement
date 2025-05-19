export const dummyDashboardSummary = {
  productsCount: 528,
  categoriesCount: 12,
  monthlyReceipts: 45,
  monthlyDeliveries: 38,
  lowStockCount: 7,
  topProduct: "SuperWidget",
  inventoryValue: 120000.0,
  turnoverLastWeek: 15000.0,
  lastReceiptDate: "2025-05-10T12:34:56Z",
  lastDeliveryDate: "2025-05-09T15:20:00Z",
  lastReceiptId: 1,
  lastDeliveryId: 1,
  topProductId: 1,
};

export const dummyCategories = [
  { categoryId: 1, name: "Electronics", description: "Gadgets" },
  { categoryId: 2, name: "Furniture", description: "Home & Office" },
];

export const dummyClients = [
  {
    clientId: 1,
    name: "Acme Corp",
    email: "contact@acme.com",
    phoneNumber: "123-456",
    address: "123 Elm St",
    transactionsCount: 5,
  },
];

export const dummyClientById = {
  clientId: 1,
  name: "Acme Corp",
  email: "contact@acme.com",
  phoneNumber: "123-456",
  address: {
    street: "Elm St",
    streetNumber: "123",
    postalCode: "00-001",
    city: "Metropolis",
    country: "USA",
    region: "North",
  },
  history: [
    {
      transactionId: 1,
      date: "2025-05-01",
      description: "Order 1",
      type: "WAREHOUSE_TO_CLIENT",
      employeeId: 1,
      totalPrice: 1500.0,
    },
    {
      transactionId: 2,
      date: "2025-05-02",
      description: "Order 2",
      type: "WAREHOUSE_TO_CLIENT",
      employeeId: 1,
      totalPrice: 2000.0,
    },
  ],
};

export const dummyEmployees = [
  {
    employeeId: 1,
    name: "John",
    surname: "Doe",
    email: "john@doe.com",
    phoneNumber: "555-1234",
    position: "Manager",
    warehouseName: "Main",
    transactionsCount: 20,
  },
];

export const dummyEmployeeById = {
  employeeId: 1,
  name: "John",
  surname: "Doe",
  email: "john@doe.com",
  phoneNumber: "555-1234",
  position: "Manager",
  warehouseId: 1,
  warehouseName: "Main",
  transactionsCount: 20,
  history: [
    {
      transactionId: 1,
      date: "2025-05-01",
      description: "Order 1",
      type: "SUPPLIER_TO_WAREHOUSE",
      employeeId: 1,
      totalPrice: 1500.0,
    },
    {
      transactionId: 2,
      date: "2025-05-02",
      description: "Order 2",
      type: "SUPPLIER_TO_WAREHOUSE",
      employeeId: 1,
      totalPrice: 2000.0,
    },
  ],
};

export const dummyRegions = [
  { regionId: 1, name: "North" },
  { regionId: 2, name: "South" },
];

export const dummyCountries = [
  { countryId: 1, name: "USA", regionId: 1 },
  { countryId: 2, name: "Canada", regionId: 1 },
];

export const dummyWarehouses = [
  {
    warehouseId: 1,
    name: "Main",
    capacity: 1000,
    occupiedCapacity: 450,
    address: "123 Elm St",
    employeesCount: 5,
    productsCount: 100,
    transactionsCount: 20,
  },
];

export const dummyWarehouseById = {
  warehouseId: 1,
  name: "Main",
  capacity: 1000,
  occupiedCapacity: 450,
  address: "123 Elm St",
  employees: [
    {
      employeeId: 1,
      name: "string",
      surname: "string",
      email: "string",
      phoneNumber: "string",
      position: "string",
    },
  ],
  products: [
    {
      productId: 1,
      name: "string",
      quantity: 100,
      unitPrice: 0.0,
    },
  ],
  transactions: [
    {
      transactionId: 1,
      date: "2025-05-01",
      description: "string",
      type: "SUPPLIER_TO_WAREHOUSE",
      employeeId: 1,
      totalPrice: 1500.0,
    },
  ],
  occupancyHistory: [{ date: "2025-04-01", occupiedCapacity: 400 }],
};

export const dummyProducts = [
  {
    productId: 1,
    name: "Widget",
    description: "A useful widget",
    unitPrice: 9.99,
    unitSize: 1.0,
    categoryName: "Gadgets",
    inventoryCount: 100,
    transactionsCount: 20,
  },
];

export const dummyProductById = {
  productId: 1,
  name: "Widget",
  description: "A useful widget",
  unitPrice: 9.99,
  unitSize: 1.0,
  categoryName: "Gadgets",
  inventory: {
    warehouseId1: 10,
    warehouseId2: 5,
  },
  transactions: [
    {
      transactionId: 1,
      date: "2025-05-01",
      type: "SUPPLIER_TO_WAREHOUSE",
      price: 1500.0,
      quantity: 10,
    },
  ],
};

export const dummyLowStock = [1, 2, 3];
export const dummyBestSelling = [1, 2, 3];

export const dummySuppliers = [
  {
    supplierId: 1,
    name: "SupplyCo",
    email: "trade@supplyco.com",
    phoneNumber: "555-6789",
    address: "456 Oak St",
    transactionsCount: 8,
  },
];

export const dummySupplierById = {
  supplierId: 1,
  name: "SupplyCo",
  email: "trade@supplyco.com",
  phoneNumber: "555-6789",
  address: {
    street: "Oak St",
    streetNumber: "456",
    postalCode: "00-002",
    city: "Gotham",
    country: "USA",
    region: "East",
  },
  history: [
    {
      transactionId: 1,
      date: "2025-05-01",
      description: "Order 1",
      type: "SUPPLIER_TO_WAREHOUSE",
      employeeId: 1,
      totalPrice: 1500.0,
    },
    {
      transactionId: 2,
      date: "2025-05-02",
      description: "Order 2",
      type: "SUPPLIER_TO_WAREHOUSE",
      employeeId: 1,
      totalPrice: 2000.0,
    },
  ],
};

export const dummyTransactions = [
  {
    transactionId: 1,
    date: "2025-05-01",
    description: "Order 1",
    type: "WAREHOUSE_TO_CLIENT",
    employeeId: 1,
    fromWarehouseId: 1,
    toWarehouseId: 1,
    clientId: 1,
    supplierId: 1,
    totalPrice: 1500.0,
  },
];

export const dummyTransactionById = {
  transactionId: 1,
  date: "2025-05-01",
  description: "Order 1",
  type: "WAREHOUSE_TO_CLIENT",
  employeeId: 1,
  fromWarehouseId: 1,
  clientId: 1,
  products: [
    {
      productId: 1,
      name: "Widget",
      categoryName: "Gadgets",
      quantity: 10,
      unitPrice: 9.99,
    },
    {
      productId: 2,
      name: "Gadget",
      categoryName: "Gadgets",
      quantity: 5,
      unitPrice: 19.99,
    },
  ],
};
