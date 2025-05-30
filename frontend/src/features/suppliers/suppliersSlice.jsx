import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import suppliersAPI from "../../api/suppliers";

export const fetchSuppliers = createAsyncThunk(
  "suppliers/fetchAll",
  async (params) => {
    const response = await suppliersAPI.list(params);
    return response.data;
  }
);

export const fetchSupplierById = createAsyncThunk(
  "suppliers/fetchById",
  async (id) => {
    const response = await suppliersAPI.getId(id);
    return response.data;
  }
);

export const createSupplier = createAsyncThunk(
  "suppliers/create",
  async (data) => {
    try {
      const response = await suppliersAPI.create(data);
      return response.data;
    } catch (err) {
      throw new Error(err.response?.data?.description || err.message);
    }
  }
);

const suppliersSlice = createSlice({
  name: "suppliers",
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
      .addCase(fetchSuppliers.pending, (state) => {
        state.status = "loading";
        state.formStatus = "idle";
        state.list = { content: [], page: {} };
        state.error = null;
      })
      .addCase(fetchSuppliers.fulfilled, (state, action) => {
        state.status = "succeeded";
        state.list = action.payload;
      })
      .addCase(fetchSuppliers.rejected, (state, action) => {
        state.status = "failed";
        state.error = action.error.message;
      })
      .addCase(fetchSupplierById.pending, (state) => {
        state.status = "loading";
        state.current = null;
        state.error = null;
      })
      .addCase(fetchSupplierById.fulfilled, (state, action) => {
        state.status = "succeeded";
        state.current = action.payload;
      })
      .addCase(createSupplier.pending, (state) => {
        state.formStatus = "loading";
        state.error = null;
      })
      .addCase(createSupplier.fulfilled, (state, action) => {
        state.formStatus = "succeeded";
        state.list.content.push(action.payload);
      })
      .addCase(createSupplier.rejected, (state, action) => {
        state.formStatus = "failed";
        state.error = action.error.message;
      });
  },
});

export default suppliersSlice.reducer;
