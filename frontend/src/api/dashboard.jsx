import apiClient from "../utils/apiClient";

const dashboardAPI = {
  get: (warehouseId) =>
    apiClient.get("/api/dashboard/summary", { params: { warehouseId } }),
};

export default dashboardAPI;
