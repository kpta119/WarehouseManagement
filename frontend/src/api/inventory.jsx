import apiClient from "../utils/apiClient";

export const receiveFromSupplier = (data) =>
  apiClient.post("/api/inventory/receive", data);

export const transferBetweenWarehouses = (data) =>
  apiClient.post("/api/inventory/transfer", data);

export const deliverToClient = (data) =>
  apiClient.post("/api/inventory/delivery", data);
