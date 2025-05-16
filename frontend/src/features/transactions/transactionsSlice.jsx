import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import * as transactionsAPI from "../../api/transactions";

export const fetchTransactions = createAsyncThunk(
  "transactions/fetchAll",
  async (params) => {
    const response = await transactionsAPI.listTransactions(params);
    return response.data;
  }
);
export const fetchTransactionById = createAsyncThunk(
  "transactions/fetchById",
  async (id) => {
    const response = await transactionsAPI.getTransactionById(id);
    return response.data;
  }
);

const transactionsSlice = createSlice({
  name: "transactions",
  initialState: { list: [], current: null, status: "idle", error: null },
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchTransactions.pending, (state) => {
        state.status = "loading";
      })
      .addCase(fetchTransactions.fulfilled, (state, action) => {
        state.status = "succeeded";
        state.list = action.payload;
      })
      .addCase(fetchTransactions.rejected, (state, action) => {
        state.status = "failed";
        state.error = action.error.message;
      })
      .addCase(fetchTransactionById.fulfilled, (state, action) => {
        state.current = action.payload;
      });
  },
});

export default transactionsSlice.reducer;
