import apiClient from "../utils/apiClient";

const geographyAPI = {
  regions: () => apiClient.get("/api/regions"),
  countries: (regionId) =>
    apiClient.get("/api/countries", { params: { regionId } }),
  create: (data) => apiClient.post("/api/addresses", data),
};

export default geographyAPI;
