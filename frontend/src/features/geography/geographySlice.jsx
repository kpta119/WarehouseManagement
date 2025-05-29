import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import geographyAPI from "../../api/geography";

export const fetchRegions = createAsyncThunk(
  "geography/fetchRegions",
  async () => {
    const response = await geographyAPI.regions();
    return response.data;
  }
);

export const fetchCountries = createAsyncThunk(
  "geography/fetchCountries",
  async (regionId) => {
    const response = await geographyAPI.countries(regionId);
    return response.data;
  }
);

export const createAddress = createAsyncThunk(
  "geography/createAddress",
  async (data) => {
    try {
      const response = await geographyAPI.create(data);
      return response.data;
    } catch (err) {
      throw new Error(err.response?.data?.description || err.message);
    }
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
        state.regions = [];
        state.error = null;
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
        state.countries = [];
        state.error = null;
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
