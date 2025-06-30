import { Link } from "react-router-dom";
import { FaPlus, FaUserFriends } from "react-icons/fa";
import EmployeeList from "../components/employees/EmployeeList";

const EmployeesPage = () => {
  return (
    <div className="space-y-6">
      <div className="flex justify-between items-center">
        <div className="flex items-center space-x-2">
          <FaUserFriends className="text-pink-500 w-6 h-6" />
          <h1 className="text-2xl font-semibold text-gray-800">
            Employees List
          </h1>
        </div>
        <Link
          to="/employees/new"
          className="flex items-center bg-pink-500 hover:bg-pink-600 text-white px-4 py-2 rounded-lg transition"
        >
          <FaPlus className="mr-2" /> New Employee
        </Link>
      </div>
      <EmployeeList />
    </div>
  );
};

export default EmployeesPage;
