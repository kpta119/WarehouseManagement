import apiClient from "../utils/apiClient";
import createCRUD from "./crud";

const productsAPI = {
  ...createCRUD("products", {
    getId: true,
    create: true,
    update: true,
    delete: true,
  }),
  get: (params) => apiClient.get("/api/products/search", { params }),
};

export default productsAPI;
