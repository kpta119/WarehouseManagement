import apiClient from "../utils/apiClient";
import { dummyDashboardSummary } from "../utils/dummyData";

export const getDashboardSummary = (warehouseId) =>
  import.meta.env.DEV
    ? Promise.resolve({ data: dummyDashboardSummary })
    : apiClient.get("/api/dashboard/summary", { params: { warehouseId } });
