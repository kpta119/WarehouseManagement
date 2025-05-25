import { useSelector } from "react-redux";
import { Navigate } from "react-router-dom";

const DefaultRedirect = () => {
  const loggedIn = useSelector((state) => state.auth.loggedIn);
  return loggedIn ? (
    <Navigate to="/" replace />
  ) : (
    <Navigate to="/login" replace />
  );
};

export default DefaultRedirect;
