import apiClient from "../utils/apiClient";
import { dummyTransactions, dummyTransactionById } from "../utils/dummyData";

export const listTransactions = (params) =>
  import.meta.env.DEV
    ? Promise.resolve({ data: dummyTransactions })
    : apiClient.get("/api/transactions", { params });

export const getTransactionById = (transactionId) =>
  import.meta.env.DEV
    ? Promise.resolve({ data: dummyTransactionById })
    : apiClient.get(`/api/transactions/${transactionId}`);
