import apiClient from "../utils/apiClient";

export const listClients = (params) =>
  apiClient.get("/api/clients", { params });

export const getClientById = (clientId) =>
  apiClient.get(`/api/clients/${clientId}`);

export const createClient = (data) => apiClient.post("/api/clients", data);
