import { createContext, useContext, useState } from "react";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  // Change to false
  const [loggedIn, setLoggedIn] = useState(false);
  const login = (login, password) => {
    if (login === "admin" && password === "admin") {
      setLoggedIn(true);
    }
  };
  const logout = () => {
    setLoggedIn(false);
  };
  return (
    <AuthContext.Provider value={{ loggedIn, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);
