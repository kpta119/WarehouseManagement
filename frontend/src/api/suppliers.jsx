import apiClient from "../utils/apiClient";

export const listSuppliers = () => apiClient.get("/api/suppliers");

export const getSupplierById = (supplierId) =>
  apiClient.get(`/api/suppliers/${supplierId}`);

export const createSupplier = (data) => apiClient.post("/api/suppliers", data);
