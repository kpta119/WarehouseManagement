import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import clientsAPI from "../../api/clients";

export const fetchClients = createAsyncThunk(
  "clients/fetchAll",
  async (params) => {
    const response = await clientsAPI.list(params);
    return response.data;
  }
);

export const fetchClientById = createAsyncThunk(
  "clients/fetchById",
  async (id) => {
    const response = await clientsAPI.getId(id);
    return response.data;
  }
);

export const createClient = createAsyncThunk("clients/create", async (data) => {
  try {
    const response = await clientsAPI.create(data);
    return response.data;
  } catch (err) {
    throw new Error(err.response?.data?.description || err.message);
  }
});

const clientsSlice = createSlice({
  name: "clients",
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
      .addCase(fetchClients.pending, (state) => {
        state.status = "loading";
        state.formStatus = "idle";
        state.list = { content: [], page: {} };
        state.error = null;
      })
      .addCase(fetchClients.fulfilled, (state, action) => {
        state.status = "succeeded";
        state.list = action.payload;
      })
      .addCase(fetchClients.rejected, (state, action) => {
        state.status = "failed";
        state.error = action.error.message;
      })
      .addCase(fetchClientById.pending, (state) => {
        state.status = "loading";
        state.current = null;
        state.error = null;
      })
      .addCase(fetchClientById.fulfilled, (state, action) => {
        state.status = "succeeded";
        state.current = action.payload;
      })
      .addCase(createClient.pending, (state) => {
        state.formStatus = "loading";
        state.error = null;
      })
      .addCase(createClient.fulfilled, (state, action) => {
        state.formStatus = "succeeded";
        state.list.content.push(action.payload);
      })
      .addCase(createClient.rejected, (state, action) => {
        state.formStatus = "failed";
        state.error = action.error.message;
      });
  },
});

export default clientsSlice.reducer;
