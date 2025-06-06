import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import categoriesAPI from "../../api/categories";

export const fetchCategories = createAsyncThunk(
  "categories/fetchAll",
  async (params) => {
    const response = await categoriesAPI.list(params);
    return response.data;
  }
);

export const createCategory = createAsyncThunk(
  "categories/create",
  async (data) => {
    try {
      const response = await categoriesAPI.create(data);
      return response.data;
    } catch (err) {
      throw new Error(err.response?.data?.description || err.message);
    }
  }
);

export const updateCategory = createAsyncThunk(
  "categories/update",
  async ({ id, data }) => {
    try {
      const response = await categoriesAPI.update(id, data);
      return response.data;
    } catch (err) {
      throw new Error(err.response?.data?.description || err.message);
    }
  }
);

export const deleteCategory = createAsyncThunk(
  "categories/delete",
  async (id) => {
    try {
      await categoriesAPI.delete(id);
      return id;
    } catch (err) {
      throw new Error(err.response?.data?.description || err.message);
    }
  }
);

const categoriesSlice = createSlice({
  name: "categories",
  initialState: {
    list: { content: [], page: {} },
    status: "idle",
    error: null,
    formStatus: "idle",
  },
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchCategories.pending, (state) => {
        state.status = "loading";
        state.formStatus = "idle";
        state.list = { content: [], page: {} };
        state.error = null;
      })
      .addCase(fetchCategories.fulfilled, (state, action) => {
        state.status = "succeeded";
        state.list = action.payload;
      })
      .addCase(fetchCategories.rejected, (state, action) => {
        state.status = "failed";
        state.error = action.error.message;
      })
      .addCase(createCategory.pending, (state) => {
        state.formStatus = "loading";
        state.error = null;
      })
      .addCase(createCategory.fulfilled, (state, action) => {
        state.formStatus = "succeeded";
        state.list.content.push(action.payload);
      })
      .addCase(createCategory.rejected, (state, action) => {
        state.formStatus = "failed";
        state.error = action.error.message;
      })
      .addCase(updateCategory.pending, (state) => {
        state.formStatus = "loading";
        state.error = null;
      })
      .addCase(updateCategory.fulfilled, (state, action) => {
        state.formStatus = "succeeded";
        const idx = state.list.content.findIndex(
          (c) => c.categoryId === action.payload.categoryId
        );
        if (idx !== -1) state.list.content[idx] = action.payload;
      })
      .addCase(updateCategory.rejected, (state, action) => {
        state.formStatus = "failed";
        state.error = action.error.message;
      })
      .addCase(deleteCategory.pending, (state) => {
        state.formStatus = "loading";
        state.error = null;
      })
      .addCase(deleteCategory.fulfilled, (state, action) => {
        state.formStatus = "succeeded";
        state.list.content = state.list.content.filter(
          (c) => c.categoryId !== action.payload
        );
      })
      .addCase(deleteCategory.rejected, (state, action) => {
        state.formStatus = "failed";
        state.error = action.error.message;
      });
  },
});

export default categoriesSlice.reducer;
