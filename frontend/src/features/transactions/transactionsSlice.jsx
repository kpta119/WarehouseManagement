import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import transactionsAPI from "../../api/transactions";

export const fetchTransactions = createAsyncThunk(
  "transactions/fetchAll",
  async (params) => {
    const response = await transactionsAPI.list(params);
    return response.data;
  }
);

export const fetchTransactionById = createAsyncThunk(
  "transactions/fetchById",
  async (id) => {
    const response = await transactionsAPI.getId(id);
    return response.data;
  }
);

const transactionsSlice = createSlice({
  name: "transactions",
  initialState: {
    list: { content: [], page: {} },
    current: null,
    status: "idle",
    error: null,
  },
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchTransactions.pending, (state) => {
        state.status = "loading";
        state.list = { content: [], page: {} };
        state.error = null;
      })
      .addCase(fetchTransactions.fulfilled, (state, action) => {
        state.status = "succeeded";
        state.list = action.payload;
      })
      .addCase(fetchTransactions.rejected, (state, action) => {
        state.status = "failed";
        state.error = action.error.message;
      })
      .addCase(fetchTransactionById.pending, (state) => {
        state.status = "loading";
        state.current = null;
        state.error = null;
      })
      .addCase(fetchTransactionById.fulfilled, (state, action) => {
        state.status = "succeeded";
        state.current = action.payload;
      });
  },
});

export default transactionsSlice.reducer;
