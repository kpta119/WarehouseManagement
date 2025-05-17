import apiClient from "../utils/apiClient";
import { dummyEmployees, dummyEmployeeById } from "../utils/dummyData";

export const listEmployees = (params) =>
  import.meta.env.DEV
    ? Promise.resolve({ data: dummyEmployees })
    : apiClient.get("/api/employees", { params });

export const getEmployeeById = (employeeId) =>
  import.meta.env.DEV
    ? Promise.resolve({ data: dummyEmployeeById })
    : apiClient.get(`/api/employees/${employeeId}`);

export const createEmployee = (data) => apiClient.post("/api/employees", data);
