import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import * as productsAPI from "../../api/products";

export const fetchProducts = createAsyncThunk(
  "products/fetchAll",
  async (params) => {
    const response = await productsAPI.searchProducts(params);
    return response.data;
  }
);
export const fetchProductById = createAsyncThunk(
  "products/fetchById",
  async (id) => {
    const response = await productsAPI.getProductById(id);
    return response.data;
  }
);
export const createProduct = createAsyncThunk(
  "products/create",
  async (data) => {
    const response = await productsAPI.createProduct(data);
    return response.data;
  }
);
export const updateProduct = createAsyncThunk(
  "products/update",
  async ({ id, data }) => {
    const response = await productsAPI.updateProduct(id, data);
    return response.data;
  }
);
export const deleteProduct = createAsyncThunk("products/delete", async (id) => {
  await productsAPI.deleteProduct(id);
  return id;
});

const productsSlice = createSlice({
  name: "products",
  initialState: { list: [], current: null, status: "idle", error: null },
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchProducts.pending, (state) => {
        state.status = "loading";
      })
      .addCase(fetchProducts.fulfilled, (state, action) => {
        state.status = "succeeded";
        state.list = action.payload;
      })
      .addCase(fetchProducts.rejected, (state, action) => {
        state.status = "failed";
        state.error = action.error.message;
      })
      .addCase(fetchProductById.fulfilled, (state, action) => {
        state.current = action.payload;
      })
      .addCase(createProduct.fulfilled, (state, action) => {
        state.list.push(action.payload);
      })
      .addCase(updateProduct.fulfilled, (state, action) => {
        const idx = state.list.findIndex(
          (p) => p.productId === action.payload.productId
        );
        if (idx !== -1) state.list[idx] = action.payload;
      })
      .addCase(deleteProduct.fulfilled, (state, action) => {
        state.list = state.list.filter((p) => p.productId !== action.payload);
      });
  },
});

export default productsSlice.reducer;
