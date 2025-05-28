import apiClient from "../utils/apiClient";

export const searchProducts = (params) =>
  apiClient.get("/api/products/search", { params });

export const getProductById = (productId) =>
  apiClient.get(`/api/products/${productId}`);

export const getLowStockProducts = (warehouseId) =>
  apiClient.get("/api/products/low-stock", { params: { warehouseId } });

export const getBestSelling = ({ warehouseId, period }) =>
  apiClient.get("/api/products/best-selling", {
    params: { warehouseId, period },
  });

export const createProduct = (data) => apiClient.post("/api/products", data);

export const updateProduct = (productId, data) =>
  apiClient.put(`/api/products/${productId}`, data);

export const deleteProduct = (productId) =>
  apiClient.delete(`/api/products/${productId}`);
