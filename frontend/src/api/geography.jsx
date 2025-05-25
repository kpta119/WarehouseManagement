import apiClient from "../utils/apiClient";
import { dummyRegions, dummyCountries } from "../utils/dummyData";

export const listRegions = () =>
  import.meta.env.DEV
    ? Promise.resolve({ data: dummyRegions })
    : apiClient.get("/api/regions");

export const listCountries = (regionId) =>
  import.meta.env.DEV
    ? Promise.resolve({ data: dummyCountries })
    : apiClient.get("/api/countries", { params: { regionId } });

export const createAddress = (data) => apiClient.post("/api/addresses", data);
