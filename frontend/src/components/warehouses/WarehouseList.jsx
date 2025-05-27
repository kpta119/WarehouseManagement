import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link } from "react-router-dom";
import {
  fetchWarehouses,
  deleteWarehouse,
} from "../../features/warehouses/warehousesSlice";
import { FaEye, FaTrash, FaEdit } from "react-icons/fa";
import { fetchRegions } from "../../features/geography/geographySlice";
import Pagination from "../helper/Pagination";
import SelectInput from "../helper/SelectInput";
import TextInput from "../helper/TextInput";
import NumberInput from "../helper/NumberInput";

const WarehouseList = () => {
  const dispatch = useDispatch();
  const {
    list: warehouses,
    status,
    error,
  } = useSelector((state) => state.warehouses);
  const { regions } = useSelector((state) => state.geography);
  const [searchTerm, setSearchTerm] = useState("");
  const [regionFilter, setRegionFilter] = useState("");
  const [minCapacity, setMinCapacity] = useState("");
  const [maxCapacity, setMaxCapacity] = useState("");
  const [minOccupied, setMinOccupied] = useState("");
  const [maxOccupied, setMaxOccupied] = useState("");
  const [minEmployees, setMinEmployees] = useState("");
  const [maxEmployees, setMaxEmployees] = useState("");
  const [minProducts, setMinProducts] = useState("");
  const [maxProducts, setMaxProducts] = useState("");
  const [minTransactions, setMinTransactions] = useState("");
  const [maxTransactions, setMaxTransactions] = useState("");
  const [sortOption, setSortOption] = useState("");
  const [page, setPage] = useState(1);
  const totalPages = 10;
  useEffect(() => {
    dispatch(fetchWarehouses());
    dispatch(fetchRegions());
  }, [dispatch]);
  const filtered = warehouses
    .filter((wh) => wh.name.toLowerCase().includes(searchTerm.toLowerCase()))
    .sort((a, b) => {
      switch (sortOption) {
        case "name":
          return a.name.localeCompare(b.name);
        case "name-reverse":
          return b.name.localeCompare(a.name);
        case "capacity":
          return a.capacity - b.capacity;
        case "capacity-reverse":
          return b.capacity - a.capacity;
        case "occupied":
          return a.occupiedCapacity - b.occupiedCapacity;
        case "occupied-reverse":
          return b.occupiedCapacity - a.occupiedCapacity;
        case "address":
          return a.address.localeCompare(b.address);
        case "address-reverse":
          return b.address.localeCompare(a.address);
        case "employees":
          return a.employeesCount - b.employeesCount;
        case "employees-reverse":
          return b.employeesCount - a.employeesCount;
        case "products":
          return a.productsCount - b.productsCount;
        case "products-reverse":
          return b.productsCount - a.productsCount;
        case "transactions":
          return a.transactionsCount - b.transactionsCount;
        case "transactions-reverse":
          return b.transactionsCount - a.transactionsCount;
        default:
          return 0;
      }
    });
  const handleDelete = (id) => {
    if (window.confirm("Czy na pewno chcesz usunąć ten magazyn?")) {
      dispatch(deleteWarehouse(id));
    }
  };
  return (
    <>
      <div className="flex justify-between space-x-4">
        <div className="flex flex-wrap gap-4 items-end">
          <TextInput
            label="Nazwa"
            placeholder="Szukaj magazynu..."
            value={searchTerm}
            setValue={setSearchTerm}
          />
          <SelectInput
            label="Region"
            value={regionFilter}
            setValue={setRegionFilter}
          >
            <option value="">Wszystkie Regiony</option>
            {regions.map((reg) => (
              <option key={reg.id} value={reg.id}>
                {reg.name}
              </option>
            ))}
          </SelectInput>
          <NumberInput
            label="Pojemność (min)"
            placeholder="Wybierz pojemność..."
            isMinus={true}
            value={minCapacity}
            setValue={setMinCapacity}
          />
          <NumberInput
            label="Pojemność (max)"
            placeholder="Wybierz pojemność..."
            isMinus={false}
            value={maxCapacity}
            setValue={setMaxCapacity}
          />
          <NumberInput
            label="Zajęte (min)"
            placeholder="Wybierz zajętość..."
            isMinus={true}
            value={minOccupied}
            setValue={setMinOccupied}
          />
          <NumberInput
            label="Zajęte (max)"
            placeholder="Wybierz zajętość..."
            isMinus={false}
            value={maxOccupied}
            setValue={setMaxOccupied}
          />
          <NumberInput
            label="Pracownicy (min)"
            placeholder="Wybierz pracowników..."
            isMinus={true}
            value={minEmployees}
            setValue={setMinEmployees}
          />
          <NumberInput
            label="Pracownicy (max)"
            placeholder="Wybierz pracowników..."
            isMinus={false}
            value={maxEmployees}
            setValue={setMaxEmployees}
          />
          <NumberInput
            label="Produkty (min)"
            placeholder="Wybierz produkty..."
            isMinus={true}
            value={minProducts}
            setValue={setMinProducts}
          />
          <NumberInput
            label="Produkty (max)"
            placeholder="Wybierz produkty..."
            isMinus={false}
            value={maxProducts}
            setValue={setMaxProducts}
          />
          <NumberInput
            label="Transakcje (min)"
            placeholder="Wybierz transakcje..."
            isMinus={true}
            value={minTransactions}
            setValue={setMinTransactions}
          />
          <NumberInput
            label="Transakcje (max)"
            placeholder="Wybierz transakcje..."
            isMinus={false}
            value={maxTransactions}
            setValue={setMaxTransactions}
          />
        </div>
        <SelectInput
          label="Sortowanie"
          value={sortOption}
          setValue={setSortOption}
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
          <option value="transactions">Transakcje (rosnąco)</option>
          <option value="transactions-reverse">Transakcje (malejąco)</option>
        </SelectInput>
      </div>
      {status === "loading" ? (
        <p>Ładowanie...</p>
      ) : status === "failed" ? (
        <p className="text-red-500">Błąd: {error}</p>
      ) : (
        <>
          {" "}
          <Pagination
            currentPage={page}
            totalPages={totalPages}
            onPageChange={setPage}
          />
          <div className="bg-white rounded-lg shadow overflow-auto">
            <div className="hidden sm:grid grid-cols-8 gap-4 p-4 bg-gray-50 text-xs font-medium text-gray-500 uppercase tracking-wider">
              <div>Nazwa</div>
              <div>Pojemność</div>
              <div>Zajęte</div>
              <div>Adres</div>
              <div className="text-right">Pracownicy</div>
              <div className="text-right">Produkty</div>
              <div className="text-right">Liczba transakcji</div>
              <div className="text-center">Akcje</div>
            </div>
            <div className="divide-y divide-gray-200">
              {filtered.map((wh) => (
                <div
                  key={wh.warehouseId}
                  className="grid grid-cols-1 sm:grid-cols-8 items-center gap-4 p-4 hover:bg-pink-50 transition-colors"
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
                  <div className="text-sm text-gray-700 text-right">
                    {wh.transactionsCount}
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
          </div>{" "}
          <Pagination
            currentPage={page}
            totalPages={totalPages}
            onPageChange={setPage}
          />
        </>
      )}
    </>
  );
};

export default WarehouseList;
