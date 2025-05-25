import apiClient from "../utils/apiClient";
import { dummyWarehouses, dummyWarehouseById } from "../utils/dummyData";

export const listWarehouses = () =>
  import.meta.env.DEV
    ? Promise.resolve({ data: dummyWarehouses })
    : apiClient.get("/api/warehouses");

export const getWarehouseById = (warehouseId) =>
  import.meta.env.DEV
    ? Promise.resolve({ data: dummyWarehouseById })
    : apiClient.get(`/api/warehouses/${warehouseId}`);

export const createWarehouse = (data) =>
  apiClient.post("/api/warehouses", data);

export const updateWarehouse = (warehouseId, data) =>
  apiClient.put(`/api/warehouses/${warehouseId}`, data);

export const deleteWarehouse = (warehouseId) =>
  apiClient.delete(`/api/warehouses/${warehouseId}`);
