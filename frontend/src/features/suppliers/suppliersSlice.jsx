import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import * as suppliersAPI from "../../api/suppliers";

export const fetchSuppliers = createAsyncThunk(
  "suppliers/fetchAll",
  async () => {
    const response = await suppliersAPI.listSuppliers();
    return response.data;
  }
);
export const fetchSupplierById = createAsyncThunk(
  "suppliers/fetchById",
  async (id) => {
    const response = await suppliersAPI.getSupplierById(id);
    return response.data;
  }
);
export const createSupplier = createAsyncThunk(
  "suppliers/create",
  async (data) => {
    const response = await suppliersAPI.createSupplier(data);
    return response.data;
  }
);

const suppliersSlice = createSlice({
  name: "suppliers",
  initialState: { list: [], current: null, status: "idle", error: null },
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchSuppliers.pending, (state) => {
        state.status = "loading";
      })
      .addCase(fetchSuppliers.fulfilled, (state, action) => {
        state.status = "succeeded";
        state.list = action.payload;
      })
      .addCase(fetchSuppliers.rejected, (state, action) => {
        state.status = "failed";
        state.error = action.error.message;
      })
      .addCase(fetchSupplierById.fulfilled, (state, action) => {
        state.current = action.payload;
      })
      .addCase(createSupplier.fulfilled, (state, action) => {
        state.list.push(action.payload);
      });
  },
});

export default suppliersSlice.reducer;
