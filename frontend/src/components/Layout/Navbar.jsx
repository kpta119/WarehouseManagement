import { useDispatch, useSelector } from "react-redux";
import { useLocation, useNavigate } from "react-router-dom";
import { logout } from "../../features/auth/authSlice";
import { fetchWarehouses } from "../../features/warehouses/warehousesSlice";
import { setSelectedWarehouse } from "../../features/selectedWarehouse/selectedWarehouseSlice";
import { FaChevronDown, FaSignOutAlt } from "react-icons/fa";
import { useEffect } from "react";

const titles = {
  "/": "Panel główny",
  "/products": "Produkty",
  "/products/new": "Nowy produkt",
  "/categories": "Kategorie",
  "/warehouses": "Magazyny",
  "/warehouses/new": "Nowy magazyn",
  "/inventory/receive": "Przyjęcie",
  "/inventory/transfer": "Przeniesienie",
  "/inventory/delivery": "Wydanie",
  "/transactions": "Transakcje",
  "/clients": "Klienci",
  "/clients/new": "Nowy klient",
  "/suppliers": "Dostawcy",
  "/suppliers/new": "Nowy dostawca",
  "/employees": "Pracownicy",
  "/employees/new": "Nowy pracownik",
  "/geography": "Geografia",
};

export default function Navbar() {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const { pathname } = useLocation();
  const { list: data } = useSelector((s) => s.warehouses);
  const { content: warehouses } = data;
  const selectedWarehouse = useSelector((s) => s.selectedWarehouse);
  useEffect(() => {
    dispatch(fetchWarehouses({ all: true }));
  }, [dispatch]);
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
      {[
        "/products",
        "/",
        "/transactions",
        "/employees",
        "/suppliers",
        "/clients",
      ].includes(pathname) && (
        <div className="relative mx-4">
          <select
            className="
            block
            appearance-none
            w-80
            bg-white
            border border-gray-300
            text-gray-700
            py-2 px-3 pr-8
            rounded-md
            leading-tight
            focus:outline-none focus:ring-1 focus:ring-pink-500 focus:border-pink-500
            transition-colors duration-300
          "
            value={selectedWarehouse ?? ""}
            onChange={(e) => {
              const val = e.target.value;
              dispatch(setSelectedWarehouse(val === "" ? null : Number(val)));
            }}
          >
            <option value="">Wszystkie Magazyny</option>
            {warehouses.map((w) => (
              <option key={w.warehouseId} value={w.warehouseId}>
                {w.name}
              </option>
            ))}
          </select>
          <FaChevronDown className="pointer-events-none absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-400" />
        </div>
      )}
      <div className="flex items-center">
        <button
          className="flex items-center text-gray-600 hover:text-pink-800 transition-colors duration-300 cursor-pointer"
          onClick={handleLogout}
        >
          <FaSignOutAlt className="mr-2" />
          Wyloguj Się
        </button>
      </div>
    </header>
  );
}
