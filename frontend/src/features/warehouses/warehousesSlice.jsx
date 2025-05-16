import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import * as warehousesAPI from "../../api/warehouses";

export const fetchWarehouses = createAsyncThunk(
  "warehouses/fetchAll",
  async () => {
    const response = await warehousesAPI.listWarehouses();
    return response.data;
  }
);
export const fetchWarehouseById = createAsyncThunk(
  "warehouses/fetchById",
  async (id) => {
    const response = await warehousesAPI.getWarehouseById(id);
    return response.data;
  }
);
export const createWarehouse = createAsyncThunk(
  "warehouses/create",
  async (data) => {
    const response = await warehousesAPI.createWarehouse(data);
    return response.data;
  }
);
export const updateWarehouse = createAsyncThunk(
  "warehouses/update",
  async ({ id, data }) => {
    const response = await warehousesAPI.updateWarehouse(id, data);
    return response.data;
  }
);
export const deleteWarehouse = createAsyncThunk(
  "warehouses/delete",
  async (id) => {
    await warehousesAPI.deleteWarehouse(id);
    return id;
  }
);

const warehousesSlice = createSlice({
  name: "warehouses",
  initialState: { list: [], current: null, status: "idle", error: null },
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchWarehouses.pending, (state) => {
        state.status = "loading";
      })
      .addCase(fetchWarehouses.fulfilled, (state, action) => {
        state.status = "succeeded";
        state.list = action.payload;
      })
      .addCase(fetchWarehouses.rejected, (state, action) => {
        state.status = "failed";
        state.error = action.error.message;
      })
      .addCase(fetchWarehouseById.fulfilled, (state, action) => {
        state.current = action.payload;
      })
      .addCase(createWarehouse.fulfilled, (state, action) => {
        state.list.push(action.payload);
      })
      .addCase(updateWarehouse.fulfilled, (state, action) => {
        const idx = state.list.findIndex(
          (w) => w.warehouseId === action.payload.warehouseId
        );
        if (idx !== -1) state.list[idx] = action.payload;
      })
      .addCase(deleteWarehouse.fulfilled, (state, action) => {
        state.list = state.list.filter((w) => w.warehouseId !== action.payload);
      });
  },
});

export default warehousesSlice.reducer;
