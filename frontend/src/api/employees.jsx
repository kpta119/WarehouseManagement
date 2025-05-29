import apiClient from "../utils/apiClient";

export const listEmployees = (params) =>
  apiClient.get("/api/employees", { params });

export const getEmployeeById = (employeeId) =>
  apiClient.get(`/api/employees/${employeeId}`);

export const createEmployee = (data) => apiClient.post("/api/employees", data);
