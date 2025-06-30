import { createSlice } from "@reduxjs/toolkit";

const persisted = localStorage.getItem("loggedIn") === "true";

const authSlice = createSlice({
  name: "auth",
  initialState: { loggedIn: persisted, error: null },
  reducers: {
    login: (state, action) => {
      const { username, password } = action.payload;
      if (username === "admin" && password === "admin") {
        state.loggedIn = true;
        state.error = null;
        localStorage.setItem("loggedIn", "true");
      } else {
        state.loggedIn = false;
        state.error = "Incorrect username or password";
        localStorage.removeItem("loggedIn");
      }
    },
    logout: (state) => {
      state.loggedIn = false;
      state.error = null;
      localStorage.removeItem("loggedIn");
    },
  },
});

export const { login, logout } = authSlice.actions;
export default authSlice.reducer;
