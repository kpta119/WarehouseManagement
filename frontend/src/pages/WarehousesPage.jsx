import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link } from "react-router-dom";
import {
  fetchWarehouses,
  deleteWarehouse,
} from "../features/warehouses/warehousesSlice";
import {
  FaPlus,
  FaEye,
  FaTrash,
  FaChevronDown,
  FaEdit,
  FaWarehouse,
} from "react-icons/fa";

const WarehousesPage = () => {
  const dispatch = useDispatch();
  const {
    list: warehouses,
    status,
    error,
  } = useSelector((state) => state.warehouses);
  const [searchTerm, setSearchTerm] = useState("");
  const [sortOption, setSortOption] = useState("");
  useEffect(() => {
    dispatch(fetchWarehouses());
  }, [dispatch]);
  const filtered = warehouses.filter((wh) =>
    wh.name.toLowerCase().includes(searchTerm.toLowerCase())
  );
  const handleDelete = (id) => {
    if (window.confirm("Czy na pewno chcesz usunąć ten magazyn?")) {
      dispatch(deleteWarehouse(id));
    }
  };
  return (
    <div className="space-y-6">
      <div className="flex justify-between items-center">
        <div className="flex items-center space-x-2">
          <FaWarehouse className="text-pink-500 w-6 h-6" />
          <h1 className="text-2xl font-semibold text-gray-800">
            Lista magazynów
          </h1>
        </div>
        <Link
          to="/warehouses/new"
          className="flex items-center bg-pink-500 hover:bg-pink-600 text-white px-4 py-2 rounded-lg transition"
        >
          <FaPlus className="mr-2" /> Nowy Magazyn
        </Link>
      </div>
      <div className="flex justify-between items-center space-x-4">
        <div>
          <label className="block text-sm font-medium">Nazwa</label>
          <div className="flex items-center border border-gray-300 rounded-lg px-3 py-2 focus-within:ring-1 focus-within:ring-pink-500 focus-within:border-pink-500 transition-colors duration-300">
            <input
              type="text"
              placeholder="Szukaj magazynu..."
              className="w-full focus:outline-none"
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
            />
          </div>
        </div>
        <div>
          <label className="block text-sm font-medium">Sortowanie</label>
          <div className="relative">
            <select
              className="border appearance-none border-gray-300 rounded-lg px-3 py-2 pr-12 focus:outline-none focus:ring-1 focus:ring-pink-500 focus:border-pink-500 transition-colors duration-300"
              value={sortOption}
              onChange={(e) => setSortOption(e.target.value)}
            >
              <option value="">Sortuj przez</option>
              <option value="name">Nazwa (od A do Z)</option>
              <option value="name-reverse">Nazwa (od Z do A)</option>
              <option value="capacity">Pojemność (rosnąco)</option>
              <option value="capacity-reverse">Pojemność (malejąco)</option>
              <option value="occupied">Zajęte (rosnąco)</option>
              <option value="occupied-reverse">Zajęte (malejąco)</option>
              <option value="address">Adres (od A do Z)</option>
              <option value="address-reverse">Adres (od Z do A)</option>
              <option value="employees">Pracownicy (rosnąco)</option>
              <option value="employees-reverse">Pracownicy (malejąco)</option>
              <option value="products">Produkty (rosnąco)</option>
              <option value="products-reverse">Produkty (malejąco)</option>
            </select>
            <FaChevronDown className="pointer-events-none absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-400" />
          </div>
        </div>
      </div>
      {status === "loading" ? (
        <p>Ładowanie...</p>
      ) : status === "failed" ? (
        <p className="text-red-500">Błąd: {error}</p>
      ) : (
        <div className="bg-white rounded-lg shadow overflow-auto">
          <div className="hidden sm:grid grid-cols-7 gap-4 p-4 bg-gray-50 text-xs font-medium text-gray-500 uppercase tracking-wider">
            <div>Nazwa</div>
            <div>Pojemność</div>
            <div>Zajęte</div>
            <div>Adres</div>
            <div className="text-right">Pracownicy</div>
            <div className="text-right">Produkty</div>
            <div className="text-center">Akcje</div>
          </div>
          <div className="divide-y divide-gray-200">
            {filtered.map((wh) => (
              <div
                key={wh.warehouseId}
                className="grid grid-cols-1 sm:grid-cols-7 items-center gap-4 p-4 hover:bg-pink-50 transition-colors"
              >
                <div>
                  <Link
                    to={`/warehouses/${wh.warehouseId}`}
                    className="text-pink-600 hover:underline font-medium"
                  >
                    {wh.name}
                  </Link>
                </div>
                <div className="text-sm text-gray-700">{wh.capacity}</div>
                <div className="text-sm text-gray-700">
                  {wh.occupiedCapacity}
                </div>
                <div className="text-sm text-gray-700">{wh.address}</div>
                <div className="text-sm text-gray-700 text-right">
                  {wh.employeesCount}
                </div>
                <div className="text-sm text-gray-700 text-right">
                  {wh.productsCount}
                </div>
                <div className="flex justify-center space-x-4 text-gray-600">
                  <Link
                    to={`/warehouses/${wh.warehouseId}`}
                    className="hover:text-pink-500 transition duration-200"
                  >
                    <FaEye />
                  </Link>
                  <Link
                    to={`/warehouses/${wh.warehouseId}/edit`}
                    className="hover:text-pink-500 transition duration-200"
                  >
                    <FaEdit />
                  </Link>
                  <button
                    onClick={() => handleDelete(wh.warehouseId)}
                    className="hover:text-pink-500 transition duration-200 cursor-pointer"
                  >
                    <FaTrash />
                  </button>
                </div>
              </div>
            ))}
          </div>
        </div>
      )}
    </div>
  );
};

export default WarehousesPage;
