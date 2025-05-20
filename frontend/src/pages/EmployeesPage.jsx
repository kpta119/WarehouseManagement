import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link } from "react-router-dom";
import {
  FaSearch,
  FaPlus,
  FaEye,
  FaChevronDown,
  FaUserFriends,
} from "react-icons/fa";
import { fetchEmployees } from "../features/employees/employeesSlice";

const EmployeesPage = () => {
  const dispatch = useDispatch();
  const {
    list: employees,
    status,
    error,
  } = useSelector((state) => state.employees);
  const [searchTerm, setSearchTerm] = useState("");
  const [sortOption, setSortOption] = useState("");
  useEffect(() => {
    dispatch(fetchEmployees());
  }, [dispatch]);
  const filtered = employees.filter((emp) =>
    `${emp.name} ${emp.surname}`
      .toLowerCase()
      .includes(searchTerm.toLowerCase())
  );
  return (
    <div className="space-y-6">
      <div className="flex justify-between items-center">
        <div className="flex items-center space-x-2">
          <FaUserFriends className="text-pink-500 w-6 h-6" />
          <h1 className="text-2xl font-semibold text-gray-800">
            Lista pracowników
          </h1>
        </div>
        <Link
          to="/employees/new"
          className="flex items-center bg-pink-500 hover:bg-pink-600 text-white px-4 py-2 rounded-lg transition"
        >
          <FaPlus className="mr-2" /> Nowy pracownik
        </Link>
      </div>
      <form className="flex justify-between items-center space-x-4">
        <div>
          <label className="block text-sm font-medium">Imię i Nazwisko</label>
          <div className="flex items-center border border-gray-300 rounded-lg px-3 py-2 focus-within:ring-1 focus-within:ring-pink-500 focus-within:border-pink-500 transition-colors duration-300">
            <FaSearch className="text-gray-500 mr-2" />
            <input
              type="text"
              placeholder="Search employees..."
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
      {status === "loading" ? (
        <p>Ładowanie...</p>
      ) : status === "failed" ? (
        <p className="text-red-500">Error: {error}</p>
      ) : (
        <div className="bg-white rounded-lg shadow overflow-auto">
          <div className="hidden sm:grid grid-cols-6 gap-4 p-4 bg-gray-50 text-xs font-medium text-gray-500 uppercase tracking-wider">
            <div>Imię i Nazwisko</div>
            <div>E-mail</div>
            <div>Nr. telefonu</div>
            <div>Stanowisko</div>
            <div>Magazyn</div>
            <div className="text-center">Akcje</div>
          </div>
          <div className="divide-y divide-gray-200">
            {filtered.map((emp) => (
              <div
                key={emp.employeeId}
                className="grid grid-cols-1 sm:grid-cols-6 items-center gap-4 p-4 hover:bg-pink-50 transition-colors"
              >
                <div className="font-medium text-pink-600">
                  <Link
                    to={`/employees/${emp.employeeId}`}
                    className="hover:underline"
                  >
                    {emp.name} {emp.surname}
                  </Link>
                </div>
                <div className="text-sm text-gray-700">{emp.email}</div>
                <div className="text-sm text-gray-700">{emp.phoneNumber}</div>
                <div className="text-sm text-gray-700">{emp.position}</div>
                <div className="text-sm text-gray-700">{emp.warehouseName}</div>
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
      )}
    </div>
  );
};

export default EmployeesPage;
