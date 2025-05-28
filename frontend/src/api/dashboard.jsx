import apiClient from "../utils/apiClient";

export const getDashboardSummary = (warehouseId) =>
  apiClient.get("/api/dashboard/summary", { params: { warehouseId } });
