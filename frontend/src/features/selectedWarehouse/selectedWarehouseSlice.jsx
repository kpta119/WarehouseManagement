import { createSlice } from "@reduxjs/toolkit";

const selectedWarehouseSlice = createSlice({
  name: "selectedWarehouse",
  initialState: null,
  reducers: {
    setSelectedWarehouse: (_, action) => action.payload,
  },
});

export const { setSelectedWarehouse } = selectedWarehouseSlice.actions;
export default selectedWarehouseSlice.reducer;
