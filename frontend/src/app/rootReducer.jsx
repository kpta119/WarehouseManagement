import { combineReducers } from "redux";
import authReducer from "../features/auth/authSlice";
import productsReducer from "../features/products/productsSlice";
import categoriesReducer from "../features/categories/categoriesSlice";
import warehousesReducer from "../features/warehouses/warehousesSlice";
import receiveReducer from "../features/inventory/receiveSlice";
import transferReducer from "../features/inventory/transferSlice";
import deliveryReducer from "../features/inventory/deliverySlice";
import transactionsReducer from "../features/transactions/transactionsSlice";
import clientsReducer from "../features/clients/clientsSlice";
import suppliersReducer from "../features/suppliers/suppliersSlice";
import employeesReducer from "../features/employees/employeesSlice";
import summaryReducer from "../features/dashboard/summarySlice";
import selectedWarehouseReducer from "../features/selectedWarehouse/selectedWarehouseSlice";

export default combineReducers({
  auth: authReducer,
  products: productsReducer,
  categories: categoriesReducer,
  warehouses: warehousesReducer,
  inventory: combineReducers({
    receive: receiveReducer,
    transfer: transferReducer,
    delivery: deliveryReducer,
  }),
  transactions: transactionsReducer,
  clients: clientsReducer,
  suppliers: suppliersReducer,
  employees: employeesReducer,
  dashboard: summaryReducer,
  selectedWarehouse: selectedWarehouseReducer,
});
