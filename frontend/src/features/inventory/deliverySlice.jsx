import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import { deliverToClient } from "../../api/inventory";

export const deliverInventory = createAsyncThunk(
  "inventory/deliver",
  async (data) => {
    try {
      const response = await deliverToClient(data);
      return response.data;
    } catch (err) {
      throw new Error(err.response?.data?.description || err.message);
    }
  }
);

const deliverySlice = createSlice({
  name: "inventoryDelivery",
  initialState: { status: "idle", error: null, transaction: null },
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(deliverInventory.pending, (state) => {
        state.status = "loading";
      })
      .addCase(deliverInventory.fulfilled, (state, action) => {
        state.status = "succeeded";
        state.transaction = action.payload;
      })
      .addCase(deliverInventory.rejected, (state, action) => {
        state.status = "failed";
        state.error = action.error.message;
      });
  },
});

export default deliverySlice.reducer;
