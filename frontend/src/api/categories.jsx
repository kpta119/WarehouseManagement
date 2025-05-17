import apiClient from "../utils/apiClient";
import { dummyCategories } from "../utils/dummyData";

export const listCategories = () =>
  import.meta.env.DEV
    ? Promise.resolve({ data: dummyCategories })
    : apiClient.get("/api/categories");

export const createCategory = (data) => apiClient.post("/api/categories", data);

export const updateCategory = (categoryId, data) =>
  apiClient.put(`/api/categories/${categoryId}`, data);

export const deleteCategory = (categoryId) =>
  apiClient.delete(`/api/categories/${categoryId}`);
