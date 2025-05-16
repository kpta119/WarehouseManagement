import apiClient from "../utils/apiClient";

export const listCategories = () => apiClient.get("/api/categories");

export const createCategory = (data) => apiClient.post("/api/categories", data);

export const updateCategory = (categoryId, data) =>
  apiClient.put(`/api/categories/${categoryId}`, data);

export const deleteCategory = (categoryId) =>
  apiClient.delete(`/api/categories/${categoryId}`);
