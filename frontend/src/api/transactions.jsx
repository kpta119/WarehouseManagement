import apiClient from "../utils/apiClient";

export const listTransactions = (params) =>
  apiClient.get("/api/transactions", { params });

export const getTransactionById = (transactionId) =>
  apiClient.get(`/api/transactions/${transactionId}`);
