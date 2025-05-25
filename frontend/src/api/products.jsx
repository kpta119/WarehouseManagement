import apiClient from "../utils/apiClient";
import {
  dummyProducts,
  dummyProductById,
  dummyLowStock,
  dummyBestSelling,
} from "../utils/dummyData";

export const searchProducts = (params) =>
  import.meta.env.DEV
    ? Promise.resolve({ data: dummyProducts })
    : apiClient.get("/api/products/search", { params });

export const getProductById = (productId) =>
  import.meta.env.DEV
    ? Promise.resolve({ data: dummyProductById })
    : apiClient.get(`/api/products/${productId}`);

export const getLowStockProducts = (warehouseId) =>
  import.meta.env.DEV
    ? Promise.resolve({ data: dummyLowStock })
    : apiClient.get("/api/products/low-stock", { params: { warehouseId } });

export const getBestSelling = ({ warehouseId, period }) =>
  import.meta.env.DEV
    ? Promise.resolve({ data: dummyBestSelling })
    : apiClient.get("/api/products/best-selling", {
        params: { warehouseId, period },
      });

export const createProduct = (data) => apiClient.post("/api/products", data);

export const updateProduct = (productId, data) =>
  apiClient.put(`/api/products/${productId}`, data);

export const deleteProduct = (productId) =>
  apiClient.delete(`/api/products/${productId}`);
