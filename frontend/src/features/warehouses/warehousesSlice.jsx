import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import warehousesAPI from "../../api/warehouses";

export const fetchWarehouses = createAsyncThunk(
  "warehouses/fetchAll",
  async (params) => {
    const response = await warehousesAPI.list(params);
    return response.data;
  }
);

export const fetchWarehouseById = createAsyncThunk(
  "warehouses/fetchById",
  async (id) => {
    const response = await warehousesAPI.getId(id);
    return response.data;
  }
);

export const createWarehouse = createAsyncThunk(
  "warehouses/create",
  async (data) => {
    try {
      const response = await warehousesAPI.create(data);
      return response.data;
    } catch (err) {
      throw new Error(err.response?.data?.description || err.message);
    }
  }
);

export const updateWarehouse = createAsyncThunk(
  "warehouses/update",
  async ({ id, data }) => {
    try {
      const response = await warehousesAPI.update(id, data);
      return response.data;
    } catch (err) {
      throw new Error(err.response?.data?.description || err.message);
    }
  }
);

export const deleteWarehouse = createAsyncThunk(
  "warehouses/delete",
  async (id) => {
    try {
      await warehousesAPI.delete(id);
      return id;
    } catch (err) {
      throw new Error(err.response?.data?.description || err.message);
    }
  }
);

const warehousesSlice = createSlice({
  name: "warehouses",
  initialState: {
    list: { content: [], page: {} },
    current: null,
    status: "idle",
    error: null,
    formStatus: "idle",
  },
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchWarehouses.pending, (state) => {
        state.status = "loading";
        state.formStatus = "idle";
        state.list = { content: [], page: {} };
        state.error = null;
      })
      .addCase(fetchWarehouses.fulfilled, (state, action) => {
        state.status = "succeeded";
        state.list = action.payload;
      })
      .addCase(fetchWarehouses.rejected, (state, action) => {
        state.status = "failed";
        state.error = action.error.message;
      })
      .addCase(fetchWarehouseById.pending, (state) => {
        state.status = "loading";
        state.current = null;
        state.error = null;
      })
      .addCase(fetchWarehouseById.fulfilled, (state, action) => {
        state.status = "succeeded";
        state.current = action.payload;
      })
      .addCase(createWarehouse.pending, (state) => {
        state.formStatus = "loading";
        state.error = null;
      })
      .addCase(createWarehouse.fulfilled, (state, action) => {
        state.formStatus = "succeeded";
        state.list.content.push(action.payload);
      })
      .addCase(createWarehouse.rejected, (state, action) => {
        state.formStatus = "failed";
        state.error = action.error.message;
      })
      .addCase(updateWarehouse.pending, (state) => {
        state.formStatus = "loading";
        state.error = null;
      })
      .addCase(updateWarehouse.fulfilled, (state, action) => {
        state.formStatus = "succeeded";
        const idx = state.list.content.findIndex(
          (w) => w.warehouseId === action.payload.warehouseId
        );
        if (idx !== -1) state.list.content[idx] = action.payload;
      })
      .addCase(updateWarehouse.rejected, (state, action) => {
        state.formStatus = "failed";
        state.error = action.error.message;
      })
      .addCase(deleteWarehouse.pending, (state) => {
        state.formStatus = "loading";
        state.error = null;
      })
      .addCase(deleteWarehouse.fulfilled, (state, action) => {
        state.formStatus = "succeeded";
        state.list.content = state.list.content.filter(
          (w) => w.warehouseId !== action.payload
        );
      })
      .addCase(deleteWarehouse.rejected, (state, action) => {
        state.formStatus = "failed";
        state.error = action.error.message;
      });
  },
});

export default warehousesSlice.reducer;
