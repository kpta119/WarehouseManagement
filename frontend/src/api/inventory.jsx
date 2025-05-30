import apiClient from "../utils/apiClient";

const inventoryAPI = {
  receive: (data) => apiClient.post("/api/inventory/receive", data),
  transfer: (data) => apiClient.post("/api/inventory/transfer", data),
  deliver: (data) => apiClient.post("/api/inventory/delivery", data),
};

export default inventoryAPI;
