import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import { receiveFromSupplier } from "../../api/inventory";

export const receiveInventory = createAsyncThunk(
  "inventory/receive",
  async (data) => {
    try {
      const response = await receiveFromSupplier(data);
      return response.data;
    } catch (err) {
      throw new Error(err.response?.data?.description || err.message);
    }
  }
);

const receiveSlice = createSlice({
  name: "inventoryReceive",
  initialState: { status: "idle", error: null, transaction: null },
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(receiveInventory.pending, (state) => {
        state.status = "loading";
      })
      .addCase(receiveInventory.fulfilled, (state, action) => {
        state.status = "succeeded";
        state.transaction = action.payload;
      })
      .addCase(receiveInventory.rejected, (state, action) => {
        state.status = "failed";
        state.error = action.error.message;
      });
  },
});

export default receiveSlice.reducer;
