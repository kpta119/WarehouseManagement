import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import dashboardAPI from "../../api/dashboard";

export const fetchDashboardSummary = createAsyncThunk(
  "dashboard/fetchSummary",
  async (warehouseId) => {
    const response = await dashboardAPI.get(warehouseId);
    return response.data;
  }
);

const summarySlice = createSlice({
  name: "dashboard",
  initialState: { data: null, status: "idle", error: null },
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchDashboardSummary.pending, (state) => {
        state.status = "loading";
        state.data = null;
        state.error = null;
      })
      .addCase(fetchDashboardSummary.fulfilled, (state, action) => {
        state.status = "succeeded";
        state.data = action.payload;
      })
      .addCase(fetchDashboardSummary.rejected, (state, action) => {
        state.status = "failed";
        state.error = action.error.message;
      });
  },
});

export default summarySlice.reducer;
