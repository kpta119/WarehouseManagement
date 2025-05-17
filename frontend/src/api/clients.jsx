import apiClient from "../utils/apiClient";
import { dummyClients, dummyClientById } from "../utils/dummyData";

export const listClients = () =>
  import.meta.env.DEV
    ? Promise.resolve({ data: dummyClients })
    : apiClient.get("/api/clients");

export const getClientById = (clientId) =>
  import.meta.env.DEV
    ? Promise.resolve({ data: dummyClientById })
    : apiClient.get(`/api/clients/${clientId}`);

export const createClient = (data) => apiClient.post("/api/clients", data);
