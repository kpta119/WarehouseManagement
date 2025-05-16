import apiClient from "../utils/apiClient";

export const listRegions = () => apiClient.get("/api/regions");

export const listCountries = (regionId) =>
  apiClient.get("/api/countries", { params: { regionId } });

export const createAddress = (data) => apiClient.post("/api/addresses", data);
