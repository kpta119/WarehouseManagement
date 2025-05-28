import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import * as geographyAPI from "../../api/geography";

export const fetchRegions = createAsyncThunk(
  "geography/fetchRegions",
  async () => {
    const response = await geographyAPI.listRegions();
    return response.data;
  }
);

export const fetchCountries = createAsyncThunk(
  "geography/fetchCountries",
  async (regionId) => {
    const response = await geographyAPI.listCountries(regionId);
    return response.data;
  }
);

export const createAddress = createAsyncThunk(
  "geography/createAddress",
  async (data) => {
    const response = await geographyAPI.createAddress(data);
    return response.data;
  }
);

const geographySlice = createSlice({
  name: "geography",
  initialState: {
    regions: [],
    countries: [],
    status: "idle",
    error: null,
  },
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchRegions.pending, (state) => {
        state.status = "loading";
      })
      .addCase(fetchRegions.fulfilled, (state, action) => {
        state.status = "succeeded";
        state.regions = action.payload;
      })
      .addCase(fetchRegions.rejected, (state, action) => {
        state.status = "failed";
        state.error = action.error.message;
      })
      .addCase(fetchCountries.pending, (state) => {
        state.status = "loading";
      })
      .addCase(fetchCountries.fulfilled, (state, action) => {
        state.status = "succeeded";
        state.countries = action.payload;
      })
      .addCase(fetchCountries.rejected, (state, action) => {
        state.status = "failed";
        state.error = action.error.message;
      })
      .addCase(createAddress.fulfilled, (state, action) => {
        state.list.push(action.payload);
      });
  },
});

export default geographySlice.reducer;
