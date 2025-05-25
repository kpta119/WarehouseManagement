import apiClient from "../utils/apiClient";
import { dummySupplierById, dummySuppliers } from "../utils/dummyData";

export const listSuppliers = () =>
  import.meta.env.DEV
    ? Promise.resolve({ data: dummySuppliers })
    : apiClient.get("/api/suppliers");

export const getSupplierById = (supplierId) =>
  import.meta.env.DEV
    ? Promise.resolve({ data: dummySupplierById })
    : apiClient.get(`/api/suppliers/${supplierId}`);

export const createSupplier = (data) => apiClient.post("/api/suppliers", data);
