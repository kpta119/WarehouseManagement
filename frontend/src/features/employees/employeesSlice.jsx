import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import employeesAPI from "../../api/employees";

export const fetchEmployees = createAsyncThunk(
  "employees/fetchAll",
  async (params) => {
    const response = await employeesAPI.list(params);
    return response.data;
  }
);

export const fetchEmployeeById = createAsyncThunk(
  "employees/fetchById",
  async (id) => {
    const response = await employeesAPI.get(id);
    return response.data;
  }
);

export const createEmployee = createAsyncThunk(
  "employees/create",
  async (data) => {
    try {
      const response = await employeesAPI.create(data);
      return response.data;
    } catch (err) {
      throw new Error(err.response?.data?.description || err.message);
    }
  }
);

const employeesSlice = createSlice({
  name: "employees",
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
      .addCase(fetchEmployees.pending, (state) => {
        state.status = "loading";
        state.formStatus = "idle";
        state.list = { content: [], page: {} };
        state.error = null;
      })
      .addCase(fetchEmployees.fulfilled, (state, action) => {
        state.status = "succeeded";
        state.list = action.payload;
      })
      .addCase(fetchEmployees.rejected, (state, action) => {
        state.status = "failed";
        state.error = action.error.message;
      })
      .addCase(fetchEmployeeById.pending, (state) => {
        state.status = "loading";
        state.current = null;
        state.error = null;
      })
      .addCase(fetchEmployeeById.fulfilled, (state, action) => {
        state.status = "succeeded";
        state.current = action.payload;
      })
      .addCase(createEmployee.pending, (state) => {
        state.formStatus = "loading";
        state.error = null;
      })
      .addCase(createEmployee.fulfilled, (state, action) => {
        state.formStatus = "succeeded";
        state.list.content.push(action.payload);
      })
      .addCase(createEmployee.rejected, (state, action) => {
        state.formStatus = "failed";
        state.error = action.error.message;
      });
  },
});

export default employeesSlice.reducer;
