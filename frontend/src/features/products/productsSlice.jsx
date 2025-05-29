import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import productsAPI from "../../api/products";

export const fetchProducts = createAsyncThunk(
  "products/fetchAll",
  async (params) => {
    const response = await productsAPI.get(params);
    return response.data;
  }
);

export const fetchProductById = createAsyncThunk(
  "products/fetchById",
  async (id) => {
    const response = await productsAPI.getId(id);
    return response.data;
  }
);

export const createProduct = createAsyncThunk(
  "products/create",
  async (data) => {
    try {
      const response = await productsAPI.create(data);
      return response.data;
    } catch (err) {
      throw new Error(err.response?.data?.description || err.message);
    }
  }
);

export const updateProduct = createAsyncThunk(
  "products/update",
  async ({ id, data }) => {
    try {
      const response = await productsAPI.update(id, data);
      return response.data;
    } catch (err) {
      throw new Error(err.response?.data?.description || err.message);
    }
  }
);

export const deleteProduct = createAsyncThunk("products/delete", async (id) => {
  try {
    await productsAPI.delete(id);
    return id;
  } catch (err) {
    throw new Error(err.response?.data?.description || err.message);
  }
});

const productsSlice = createSlice({
  name: "products",
  initialState: {
    list: [],
    current: null,
    status: "idle",
    error: null,
    formStatus: "idle",
  },
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchProducts.pending, (state) => {
        state.status = "loading";
        state.formStatus = "idle";
        state.list = [];
        state.error = null;
      })
      .addCase(fetchProducts.fulfilled, (state, action) => {
        state.status = "succeeded";
        state.list = action.payload;
      })
      .addCase(fetchProducts.rejected, (state, action) => {
        state.status = "failed";
        state.error = action.error.message;
      })
      .addCase(fetchProductById.pending, (state) => {
        state.status = "loading";
        state.current = null;
        state.error = null;
      })
      .addCase(fetchProductById.fulfilled, (state, action) => {
        state.status = "succeeded";
        state.current = action.payload;
      })
      .addCase(createProduct.pending, (state) => {
        state.formStatus = "loading";
        state.error = null;
      })
      .addCase(createProduct.fulfilled, (state, action) => {
        state.formStatus = "succeeded";
        state.list.push(action.payload);
      })
      .addCase(createProduct.rejected, (state, action) => {
        state.formStatus = "failed";
        state.error = action.error.message;
      })
      .addCase(updateProduct.pending, (state) => {
        state.formStatus = "loading";
        state.error = null;
      })
      .addCase(updateProduct.fulfilled, (state, action) => {
        state.formStatus = "succeeded";
        const idx = state.list.findIndex(
          (p) => p.productId === action.payload.productId
        );
        if (idx !== -1) state.list[idx] = action.payload;
      })
      .addCase(updateProduct.rejected, (state, action) => {
        state.formStatus = "failed";
        state.error = action.error.message;
      })
      .addCase(deleteProduct.pending, (state) => {
        state.formStatus = "loading";
        state.error = null;
      })
      .addCase(deleteProduct.fulfilled, (state, action) => {
        state.formStatus = "succeeded";
        state.list = state.list.filter((p) => p.productId !== action.payload);
      })
      .addCase(deleteProduct.rejected, (state, action) => {
        state.formStatus = "failed";
        state.error = action.error.message;
      });
  },
});

export default productsSlice.reducer;
