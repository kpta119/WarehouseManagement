import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import * as categoriesAPI from "../../api/categories";

export const fetchCategories = createAsyncThunk(
  "categories/fetchAll",
  async (params) => {
    const response = await categoriesAPI.listCategories(params);
    return response.data;
  }
);
export const createCategory = createAsyncThunk(
  "categories/create",
  async (data) => {
    const response = await categoriesAPI.createCategory(data);
    return response.data;
  }
);
export const updateCategory = createAsyncThunk(
  "categories/update",
  async ({ id, data }) => {
    const response = await categoriesAPI.updateCategory(id, data);
    return response.data;
  }
);
export const deleteCategory = createAsyncThunk(
  "categories/delete",
  async (id) => {
    await categoriesAPI.deleteCategory(id);
    return id;
  }
);

const categoriesSlice = createSlice({
  name: "categories",
  initialState: { list: [], status: "idle", error: null },
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchCategories.pending, (state) => {
        state.status = "loading";
      })
      .addCase(fetchCategories.fulfilled, (state, action) => {
        state.status = "succeeded";
        state.list = action.payload;
      })
      .addCase(fetchCategories.rejected, (state, action) => {
        state.status = "failed";
        state.error = action.error.message;
      })
      .addCase(createCategory.fulfilled, (state, action) => {
        state.list.push(action.payload);
      })
      .addCase(updateCategory.fulfilled, (state, action) => {
        const idx = state.list.findIndex(
          (c) => c.categoryId === action.payload.categoryId
        );
        if (idx !== -1) state.list[idx] = action.payload;
      })
      .addCase(deleteCategory.fulfilled, (state, action) => {
        state.list = state.list.filter((c) => c.categoryId !== action.payload);
      });
  },
});

export default categoriesSlice.reducer;
