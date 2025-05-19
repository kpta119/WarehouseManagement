import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link } from "react-router-dom";
import { FaSearch, FaPlus, FaEye } from "react-icons/fa";
import { fetchEmployees } from "../features/employees/employeesSlice";

const EmployeesPage = () => {
  const dispatch = useDispatch();
  const {
    list: employees,
    status,
    error,
  } = useSelector((state) => state.employees);
  const [searchTerm, setSearchTerm] = useState("");
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
        <h1 className="text-2xl font-semibold">Employees</h1>
        <Link
          to="/employees/new"
          className="flex items-center bg-pink-500 hover:bg-pink-600 text-white px-4 py-2 rounded-lg transition"
        >
          <FaPlus className="mr-2" /> New Employee
        </Link>
      </div>
      <div className="flex items-center border border-gray-300 rounded-lg px-3 py-2 w-1/2">
        <FaSearch className="text-gray-500 mr-2" />
        <input
          type="text"
          placeholder="Search employees..."
          className="w-full focus:outline-none"
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
        />
      </div>
      {status === "loading" ? (
        <p>Loading...</p>
      ) : status === "failed" ? (
        <p className="text-red-500">Error: {error}</p>
      ) : (
        <div className="bg-white rounded-lg shadow overflow-auto">
          <div className="hidden sm:grid grid-cols-6 gap-4 p-4 bg-gray-50 text-xs font-medium text-gray-500 uppercase tracking-wider">
            <div>Name</div>
            <div>Email</div>
            <div>Phone</div>
            <div>Position</div>
            <div>Warehouse</div>
            <div className="text-center">Actions</div>
          </div>
          <div className="divide-y divide-gray-200">
            {filtered.map((emp) => (
              <div
                key={emp.employeeId}
                className="grid grid-cols-1 sm:grid-cols-6 items-center gap-4 p-4 hover:bg-pink-50 transition-colors"
              >
                <div className="font-medium text-pink-600">
                  <Link to={`/employees/${emp.employeeId}`}>
                    {emp.name} {emp.surname}
                  </Link>
                </div>
                <div className="text-sm text-gray-700">{emp.email}</div>
                <div className="text-sm text-gray-700">{emp.phoneNumber}</div>
                <div className="text-sm text-gray-700">{emp.position}</div>
                <div className="text-sm text-gray-700">{emp.warehouseName}</div>
                <div className="flex justify-center text-gray-600">
                  <Link to={`/employees/${emp.employeeId}`}>
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
