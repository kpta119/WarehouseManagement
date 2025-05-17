import { useDispatch } from "react-redux";
import { useLocation, useNavigate } from "react-router-dom";
import { logout } from "../../features/auth/authSlice";
import { FaBars, FaSignOutAlt } from "react-icons/fa";
import { useState } from "react";

const titles = {
  "/": "Dashboard",
  "/products": "Products",
  "/categories": "Categories",
  "/warehouses": "Warehouses",
  "/inventory/receive": "Receive Inventory",
  "/inventory/transfer": "Transfer Inventory",
  "/inventory/delivery": "Delivery",
  "/transactions": "Transactions",
  "/clients": "Clients",
  "/suppliers": "Suppliers",
  "/employees": "Employees",
  "/geography": "Geography",
};

export default function Navbar() {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const { pathname } = useLocation();
  const handleLogout = () => {
    dispatch(logout());
    navigate("/login");
  };
  const title =
    titles[pathname] ||
    pathname
      .split("/")
      .filter(Boolean)
      .join(" ")
      .replace(/\b\w/g, (c) => c.toUpperCase());

  return (
    <header className="flex items-center justify-between bg-white shadow p-4">
      <div className="flex items-center">
        <h1 className="text-2xl font-semibold text-gray-800">{title}</h1>
      </div>
      <div className="flex items-center">
        <button
          className="flex items-center text-gray-600 hover:text-pink-800 transition-colors duration-300 cursor-pointer"
          onClick={handleLogout}
        >
          <FaSignOutAlt className="mr-2" />
          Logout
        </button>
      </div>
    </header>
  );
}
