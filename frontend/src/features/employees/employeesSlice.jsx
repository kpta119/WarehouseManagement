import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import * as employeesAPI from "../../api/employees";

export const fetchEmployees = createAsyncThunk(
  "employees/fetchAll",
  async (params) => {
    const response = await employeesAPI.listEmployees(params);
    return response.data;
  }
);
export const fetchEmployeeById = createAsyncThunk(
  "employees/fetchById",
  async (id) => {
    const response = await employeesAPI.getEmployeeById(id);
    return response.data;
  }
);
export const createEmployee = createAsyncThunk(
  "employees/create",
  async (data) => {
    const response = await employeesAPI.createEmployee(data);
    return response.data;
  }
);

const employeesSlice = createSlice({
  name: "employees",
  initialState: { list: [], current: null, status: "idle", error: null },
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchEmployees.pending, (state) => {
        state.status = "loading";
      })
      .addCase(fetchEmployees.fulfilled, (state, action) => {
        state.status = "succeeded";
        state.list = action.payload;
      })
      .addCase(fetchEmployees.rejected, (state, action) => {
        state.status = "failed";
        state.error = action.error.message;
      })
      .addCase(fetchEmployeeById.fulfilled, (state, action) => {
        state.current = action.payload;
      })
      .addCase(createEmployee.fulfilled, (state, action) => {
        state.list.push(action.payload);
      });
  },
});

export default employeesSlice.reducer;
