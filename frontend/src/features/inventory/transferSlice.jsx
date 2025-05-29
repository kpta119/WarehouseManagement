import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import { transferBetweenWarehouses } from "../../api/inventory";

export const transferInventory = createAsyncThunk(
  "inventory/transfer",
  async (data) => {
    try {
      const response = await transferBetweenWarehouses(data);
      return response.data;
    } catch (err) {
      throw new Error(err.response?.data?.description || err.message);
    }
  }
);

const transferSlice = createSlice({
  name: "inventoryTransfer",
  initialState: { status: "idle", error: null, transaction: null },
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(transferInventory.pending, (state) => {
        state.status = "loading";
      })
      .addCase(transferInventory.fulfilled, (state, action) => {
        state.status = "succeeded";
        state.transaction = action.payload;
      })
      .addCase(transferInventory.rejected, (state, action) => {
        state.status = "failed";
        state.error = action.error.message;
      });
  },
});

export default transferSlice.reducer;
