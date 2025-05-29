import apiClient from "../utils/apiClient";

export const listWarehouses = () => apiClient.get("/api/warehouses");

export const getWarehouseById = (warehouseId) =>
  apiClient.get(`/api/warehouses/${warehouseId}`);

export const createWarehouse = (data) =>
  apiClient.post("/api/warehouses", data);

export const updateWarehouse = (warehouseId, data) =>
  apiClient.put(`/api/warehouses/${warehouseId}`, data);

export const deleteWarehouse = (warehouseId) =>
  apiClient.delete(`/api/warehouses/${warehouseId}`);
