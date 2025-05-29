import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link } from "react-router-dom";
import { FaSearch, FaEye, FaChevronDown } from "react-icons/fa";
import { fetchEmployees } from "../../features/employees/employeesSlice";
import { fetchRegions } from "../../features/geography/geographySlice";
import TextInput from "../helper/TextInput";
import SelectInput from "../helper/SelectInput";
import NumberInput from "../helper/NumberInput";
import Pagination from "../helper/Pagination";
import { numberFormatter } from "../../utils/helpers";
import Spinner from "../helper/Spinner";

const EmployeeList = () => {
  const dispatch = useDispatch();
  const {
    list: employees,
    status,
    error,
  } = useSelector((state) => state.employees);
  const { regions } = useSelector((state) => state.geography);
  const [searchTerm, setSearchTerm] = useState("");
  const [selectedRegion, setSelectedRegion] = useState("");
  const [minTransactions, setMinTransactions] = useState("");
  const [maxTransactions, setMaxTransactions] = useState("");
  const [sortOption, setSortOption] = useState("");
  const [page, setPage] = useState(1);
  const totalPages = 10;
  const selectedWarehouse = useSelector((state) => state.selectedWarehouse);
  useEffect(() => {
    dispatch(
      fetchEmployees({
        name: searchTerm || undefined,
        regionId: selectedRegion || undefined,
        minTransactions: minTransactions
          ? parseInt(minTransactions)
          : undefined,
        maxTransactions: maxTransactions
          ? parseInt(maxTransactions)
          : undefined,
        warehouseId: selectedWarehouse || undefined,
        page: page || 1,
      })
    );
  }, [
    dispatch,
    searchTerm,
    selectedRegion,
    minTransactions,
    maxTransactions,
    selectedWarehouse,
    page,
  ]);
  useEffect(() => {
    dispatch(fetchRegions());
  }, [dispatch]);
  const filtered = employees
    .filter((emp) =>
      `${emp.name} ${emp.surname}`
        .toLowerCase()
        .includes(searchTerm.toLowerCase())
    )
    .sort((a, b) => {
      switch (sortOption) {
        case "name":
          return `${a.name} ${a.surname}`.localeCompare(
            `${b.name} ${b.surname}`
          );
        case "name-reverse":
          return `${b.name} ${b.surname}`.localeCompare(
            `${a.name} ${a.surname}`
          );
        case "email":
          return a.email.localeCompare(b.email);
        case "email-reverse":
          return b.email.localeCompare(a.email);
        case "phone":
          return a.phoneNumber.localeCompare(b.phoneNumber);
        case "phone-reverse":
          return b.phoneNumber.localeCompare(a.phoneNumber);
        case "positions":
          return a.position.localeCompare(b.position);
        case "positions-reverse":
          return b.position.localeCompare(a.position);
        case "warehouse":
          return a.warehouseName.localeCompare(b.warehouseName);
        case "warehouse-reverse":
          return b.warehouseName.localeCompare(a.warehouseName);
        default:
          return 0;
      }
    });
  return (
    <>
      <form className="flex justify-between items-center space-x-4">
        <div className="flex justify-between items-center space-x-4">
          <TextInput
            label="Nazwa"
            placeholder="Szukaj klientów..."
            value={searchTerm}
            setValue={setSearchTerm}
          />
          <SelectInput
            label="Region"
            value={selectedRegion}
            setValue={setSelectedRegion}
          >
            <option value="">Wszystkie Regiony</option>
            {regions.map((reg) => (
              <option key={reg.id} value={reg.id}>
                {reg.name}
              </option>
            ))}
          </SelectInput>
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
        <div>
          <label className="block text-sm font-medium">Sortowanie</label>
          <div className="relative">
            <select
              className="border appearance-none border-gray-300 rounded-lg px-3 py-2 pr-12 focus:outline-none focus:ring-1 focus:ring-pink-500 focus:border-pink-500 transition-colors duration-300"
              value={sortOption}
              onChange={(e) => setSortOption(e.target.value)}
            >
              <option value="">Sortuj przez</option>
              <option value="name">Imię i Nazwisko (od A do Z)</option>
              <option value="name-reverse">Imię i Nazwisko (od Z do A)</option>
              <option value="email">E-mail (od A do Z)</option>
              <option value="email-reverse">E-mail (od Z do A)</option>
              <option value="phone">Nr. telefonu (rosnąco)</option>
              <option value="phone-reverse">Nr. telefonu (malejąco)</option>
              <option value="positions">Stanowisko (od A do Z)</option>
              <option value="positions-reverse">Stanowisko (od Z do A)</option>
              <option value="warehouse">Magazyn (od A do Z)</option>
              <option value="warehouse-reverse">Magazyn (od Z do A)</option>
            </select>
            <FaChevronDown className="pointer-events-none absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-400" />
          </div>
        </div>
      </form>
      {status === "loading" || status === "idle" ? (
        <Spinner />
      ) : status === "failed" ? (
        <p className="text-red-500">Błąd: {error}</p>
      ) : filtered.length === 0 ? (
        <p className="text-red-500">Nie znaleziono pracowników</p>
      ) : (
        <>
          <Pagination
            currentPage={page}
            totalPages={totalPages}
            onPageChange={setPage}
          />
          <div className="bg-white rounded-lg shadow overflow-auto">
            <div className="hidden sm:grid grid-cols-7 gap-4 p-4 bg-gray-50 text-xs font-medium text-gray-500 uppercase tracking-wider">
              <div>Imię i Nazwisko</div>
              <div>E-mail</div>
              <div>Nr. telefonu</div>
              <div>Stanowisko</div>
              <div>Magazyn</div>
              <div className="text-right">Liczba transakcji</div>
              <div className="text-center">Akcje</div>
            </div>
            <div className="divide-y divide-gray-200">
              {filtered.map((emp) => (
                <div
                  key={emp.employeeId}
                  className="grid grid-cols-1 sm:grid-cols-7 items-center gap-4 p-4 hover:bg-pink-50 transition-colors duration-200"
                >
                  <div className="font-medium text-pink-600">
                    <Link
                      to={`/employees/${emp.employeeId}`}
                      className="hover:underline"
                    >
                      {emp.name} {emp.surname}
                    </Link>
                  </div>
                  <div className="text-sm text-gray-700 truncate">
                    {emp.email}
                  </div>
                  <div className="text-sm text-gray-700">{emp.phoneNumber}</div>
                  <div className="text-sm text-gray-700">{emp.position}</div>
                  <div className="text-sm text-gray-700">
                    {emp.warehouseName}
                  </div>
                  <div className="text-sm text-gray-700 text-right">
                    {numberFormatter(emp.transactionsCount)}
                  </div>
                  <div className="flex justify-center text-gray-600">
                    <Link
                      to={`/employees/${emp.employeeId}`}
                      className="hover:text-pink-500 transition duration-200"
                    >
                      <FaEye />
                    </Link>
                  </div>
                </div>
              ))}
            </div>
          </div>
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

export default EmployeeList;
