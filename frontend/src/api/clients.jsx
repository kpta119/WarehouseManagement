import apiClient from "../utils/apiClient";

export const listClients = () => apiClient.get("/api/clients");

export const getClientById = (clientId) =>
  apiClient.get(`/api/clients/${clientId}`);

export const createClient = (data) => apiClient.post("/api/clients", data);
