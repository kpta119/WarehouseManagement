import apiClient from "../../utils/apiClient";

export const login = (credentials) =>
  apiClient.post("/api/auth/login", credentials);

export const logout = () => apiClient.post("/api/auth/logout");

export const verifyToken = () => apiClient.get("/api/auth/verify");
