import { Navigate } from "react-router-dom";
import { useSelector } from "react-redux";

const ProtectedRoute = ({ children }) => {
  const loggedIn = useSelector((state) => state.auth.loggedIn);
  return loggedIn ? children : <Navigate to="/login" />;
};

export default ProtectedRoute;
