import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useParams, Link } from "react-router-dom";
import { fetchWarehouseById } from "../../features/warehouses/warehousesSlice";
import {
  ResponsiveContainer,
  LineChart,
  Line,
  XAxis,
  YAxis,
  Tooltip,
  CartesianGrid,
  PieChart,
  Pie,
  Cell,
  Legend,
  BarChart,
  Bar,
} from "recharts";
import { FaChevronLeft, FaEdit, FaEye } from "react-icons/fa";
import {
  currencyFormatter,
  dateFormatter,
  numberFormatter,
} from "../../utils/helpers";
import Spinner from "../helper/Spinner";
import { fetchProducts } from "../../features/products/productsSlice";

const WarehouseDetail = () => {
  const { id } = useParams();
  const dispatch = useDispatch();
  const [employeesShown, setEmployeesShown] = useState(25);
  const [productsShown, setProductsShown] = useState(25);
  const [transactionsShown, setTransactionsShown] = useState(25);
  const {
    current: warehouse,
    status,
    error,
  } = useSelector((state) => state.warehouses);
  const { list: data } = useSelector((state) => state.products);
  const { content: productsData } = data;
  useEffect(() => {
    dispatch(fetchWarehouseById(id));
  }, [dispatch, id]);
  useEffect(() => {
    dispatch(fetchProducts({ all: true }));
  }, [dispatch, id]);
  if (status === "loading" || status === "idle") {
    return <Spinner />;
  }
  if (status === "failed") {
    return <p className="text-red-500">Error: {error}</p>;
  }
  if (!warehouse) {
    return <p className="text-red-500">Warehouse not found.</p>;
  }
  const {
    name,
    capacity,
    occupiedCapacity,
    address,
    employees,
    products,
    transactions,
    occupancyHistory,
    totalItems,
    totalValue,
  } = warehouse;
  const pieData = Object.entries(
    warehouse.products
      .filter(
        (product) =>
          productsData.find((p) => p.productId === product.productId)
            ?.categoryName
      )
      .map((product) => ({
        name: productsData.find((p) => p.productId === product.productId)
          ?.categoryName,
        value: product.quantity * product.unitPrice,
      }))
      .reduce((acc, curr) => {
        acc[curr.name] = (acc[curr.name] || 0) + curr.value;
        return acc;
      }, {})
  ).map(([name, value]) => ({
    name,
    value,
  }));
  const PIE_COLORS = ["#3B82F6", "#10B981", "#F59E0B", "#EF4444"];
  const empCounts = warehouse.transactions.reduce((acc, tx) => {
    if (
      !employees
        .map((emp) => `${emp.name} ${emp.surname}`)
        .includes(tx.employeeName)
    )
      return acc;
    acc[tx.employeeName] = (acc[tx.employeeName] || 0) + 1;
    return acc;
  }, {});
  const barData = Object.entries(empCounts).map(([employeeName, count]) => ({
    employeeName: employeeName.split(" ")[0],
    count,
  }));
  return (
    <div className="space-y-6 max-w-6xl mx-auto">
      <div className="flex items-center justify-between">
        <Link
          to="/warehouses"
          className="text-gray-600 hover:text-pink-500 transition duration-200"
        >
          <FaChevronLeft className="inline mr-2" /> Return to Warehouses
        </Link>
        <Link
          to={`/warehouses/${id}/edit`}
          className="flex items-center bg-pink-500 hover:bg-pink-600 text-white px-4 py-2 rounded-lg transition duration-200"
        >
          <FaEdit className="mr-2" /> Edit Warehouse
        </Link>
      </div>
      <div className="bg-white rounded-lg shadow p-6 grid grid-cols-1 md:grid-cols-3 gap-6">
        <div>
          <h2 className="text-lg font-semibold mb-2">Name</h2>
          <p className="text-gray-800">{name}</p>
        </div>
        <div>
          <h2 className="text-lg font-semibold mb-2">Capacity</h2>
          <p className="text-gray-800">{capacity}</p>
        </div>
        <div>
          <h2 className="text-lg font-semibold mb-2">Occupied</h2>
          <p className="text-gray-800">{occupiedCapacity}</p>
        </div>
        <div>
          <h2 className="text-lg font-semibold mb-2">Address</h2>
          <p className="text-gray-800">{address}</p>
        </div>
        <div>
          <h2 className="text-lg font-semibold mb-2">Items Amount</h2>
          <p className="text-gray-800">{numberFormatter(totalItems)}</p>
        </div>
        <div>
          <h2 className="text-lg font-semibold mb-2">Total Value</h2>
          <p className="text-gray-800">{currencyFormatter(totalValue)}</p>
        </div>
      </div>
      <section>
        <h3 className="text-2xl font-semibold mb-4">Occupancy History</h3>
        <div className="bg-white rounded-lg shadow p-6">
          <ResponsiveContainer width="100%" height={300}>
            <LineChart data={occupancyHistory}>
              <CartesianGrid strokeDasharray="3 3" />
              <XAxis dataKey="date" />
              <YAxis />
              <Tooltip />
              <Line
                type="monotone"
                dataKey="occupiedCapacity"
                stroke="#3B82F6"
                strokeWidth={2}
              />
            </LineChart>
          </ResponsiveContainer>
        </div>
        <div className="flex gap-4 my-8">
          {pieData.length > 0 && (
            <div className="w-1/2">
              <h3 className="text-xl font-semibold mb-2 text-center">
                Inventory Value by Category
              </h3>
              <ResponsiveContainer width="100%" height={250}>
                <PieChart>
                  <Pie
                    data={pieData}
                    dataKey="value"
                    nameKey="name"
                    cx="50%"
                    cy="50%"
                    innerRadius={50}
                    outerRadius={80}
                    label
                  >
                    {pieData.map((_, idx) => (
                      <Cell
                        key={idx}
                        fill={PIE_COLORS[idx % PIE_COLORS.length]}
                      />
                    ))}
                  </Pie>
                  <Legend verticalAlign="bottom" height={36} />
                  <Tooltip />
                </PieChart>
              </ResponsiveContainer>
            </div>
          )}
          {barData.length > 0 && (
            <div className="w-1/2">
              <h3 className="text-xl font-semibold mb-2 text-center">
                Transactions by Employee
              </h3>
              <ResponsiveContainer width="100%" height={250}>
                <BarChart
                  data={barData}
                  margin={{ top: 5, right: 20, bottom: 5, left: 0 }}
                >
                  <CartesianGrid strokeDasharray="3 3" />
                  <XAxis dataKey="employeeName" tick={{ fill: "#6B7280" }} />
                  <YAxis tick={{ fill: "#6B7280" }} />
                  <Tooltip formatter={(value) => numberFormatter(value)} />
                  <Bar dataKey="count" name="Transakcje" fill="#10B981" />
                </BarChart>
              </ResponsiveContainer>
            </div>
          )}
        </div>
      </section>
      <section className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <div>
          <h3 className="text-2xl font-semibold mb-4">Employees</h3>
          {employees.length === 0 ? (
            <p className="text-red-500">No Employees.</p>
          ) : (
            <div className="bg-white rounded-lg shadow overflow-auto">
              <div className="grid grid-cols-5 bg-gray-50 text-xs font-medium text-gray-500 uppercase p-2">
                <div>Name and Surname</div>
                <div className="text-right">E-mail</div>
                <div className="text-right">Phone No.</div>
                <div className="text-right">Position</div>
                <div className="text-right">Details</div>
              </div>
              <div className="divide-y divide-gray-200">
                {employees.slice(0, employeesShown).map((emp) => (
                  <div
                    key={emp.employeeId}
                    className="grid grid-cols-5 p-2 text-sm text-gray-700"
                  >
                    <Link
                      to={`/employees/${emp.employeeId}`}
                      className="text-pink-500 hover:underline"
                    >
                      {emp.name} {emp.surname}
                    </Link>
                    <div className="text-right truncate">{emp.email}</div>
                    <div className="text-right">{emp.phoneNumber}</div>
                    <div className="text-right">{emp.position}</div>
                    <div className="flex justify-end text-gray-600">
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
          {employees.length > employeesShown && (
            <button
              onClick={() => setEmployeesShown((prev) => prev + 25)}
              className="mt-4 bg-pink-500 hover:bg-pink-600 text-white px-4 py-2 rounded-lg transition mx-auto block"
            >
              Show More
            </button>
          )}
        </div>
        <div>
          <h3 className="text-2xl font-semibold mb-4">Products</h3>
          {products.length === 0 ? (
            <p className="text-red-500">No Products.</p>
          ) : (
            <div className="bg-white rounded-lg shadow overflow-auto">
              <div className="grid grid-cols-5 bg-gray-50 text-xs font-medium text-gray-500 uppercase p-2">
                <div>Name</div>
                <div className="text-right">Amount</div>
                <div className="text-right">Unit Price</div>
                <div className="text-right">Value</div>
                <div className="text-right">Details</div>
              </div>
              <div className="divide-y divide-gray-200">
                {products.slice(0, productsShown).map((pr) => (
                  <div
                    key={pr.productId}
                    className="grid grid-cols-5 p-2 text-sm text-gray-700"
                  >
                    <Link
                      to={`/products/${pr.productId}`}
                      className="text-pink-500 hover:underline"
                    >
                      {pr.name}
                    </Link>
                    <div className="text-right">
                      {numberFormatter(pr.quantity)}
                    </div>
                    <div className="text-right">
                      {currencyFormatter(pr.unitPrice)}
                    </div>
                    <div className="text-right">
                      {currencyFormatter(pr.quantity * pr.unitPrice)}
                    </div>
                    <div className="flex justify-end text-gray-600">
                      <Link
                        to={`/products/${pr.productId}`}
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
          {products.length > productsShown && (
            <button
              onClick={() => setProductsShown((prev) => prev + 25)}
              className="mt-4 bg-pink-500 hover:bg-pink-600 text-white px-4 py-2 rounded-lg transition mx-auto block"
            >
              Show More
            </button>
          )}
        </div>
      </section>
      <section>
        <h3 className="text-2xl font-semibold mb-4">Recent Transactions</h3>
        {transactions.length === 0 ? (
          <p className="text-red-500">No Transactions.</p>
        ) : (
          <div className="bg-white rounded-lg shadow overflow-auto">
            <div className="grid grid-cols-5 bg-gray-50 text-xs font-medium text-gray-500 uppercase p-2">
              <div>Date</div>
              <div>Type</div>
              <div className="text-right">Total</div>
              <div className="text-right">By Employee</div>
              <div className="text-right">Details</div>
            </div>
            <div className="divide-y divide-gray-200">
              {[...transactions]
                .reverse()
                .slice(0, transactionsShown)
                .map((tx) => (
                  <div
                    key={tx.transactionId}
                    className="grid grid-cols-5 items-center p-2 text-sm text-gray-700"
                  >
                    <div>{dateFormatter(tx.date)}</div>
                    <div>
                      {tx.type
                        .toLowerCase()
                        .replace(/_/g, " ")
                        .replace(/\b\w/g, (c) => c.toUpperCase())}
                    </div>
                    <div className="text-right">
                      {currencyFormatter(tx.totalPrice)}
                    </div>
                    <Link
                      className="text-right text-pink-500 hover:underline"
                      to={`/employees/${tx.employeeId}`}
                    >
                      {tx.employeeName}
                    </Link>
                    <div className="flex justify-end text-gray-600">
                      <Link
                        to={`/transactions/${tx.transactionId}`}
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
        {transactions.length > transactionsShown && (
          <button
            onClick={() => setTransactionsShown((prev) => prev + 25)}
            className="mt-4 bg-pink-500 hover:bg-pink-600 text-white px-4 py-2 rounded-lg transition mx-auto block"
          >
            Show More
          </button>
        )}
      </section>
    </div>
  );
};

export default WarehouseDetail;
