import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import * as clientsAPI from "../../api/clients";

export const fetchClients = createAsyncThunk("clients/fetchAll", async () => {
  const response = await clientsAPI.listClients();
  return response.data;
});
export const fetchClientById = createAsyncThunk(
  "clients/fetchById",
  async (id) => {
    const response = await clientsAPI.getClientById(id);
    return response.data;
  }
);
export const createClient = createAsyncThunk("clients/create", async (data) => {
  const response = await clientsAPI.createClient(data);
  return response.data;
});

const clientsSlice = createSlice({
  name: "clients",
  initialState: { list: [], current: null, status: "idle", error: null },
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchClients.pending, (state) => {
        state.status = "loading";
      })
      .addCase(fetchClients.fulfilled, (state, action) => {
        state.status = "succeeded";
        state.list = action.payload;
      })
      .addCase(fetchClients.rejected, (state, action) => {
        state.status = "failed";
        state.error = action.error.message;
      })
      .addCase(fetchClientById.fulfilled, (state, action) => {
        state.current = action.payload;
      })
      .addCase(createClient.fulfilled, (state, action) => {
        state.list.push(action.payload);
      });
  },
});

export default clientsSlice.reducer;
