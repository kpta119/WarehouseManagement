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
} from "recharts";
import { FaChevronLeft, FaEdit, FaEye } from "react-icons/fa";
import {
  currencyFormatter,
  dateFormatter,
  numberFormatter,
} from "../../utils/helpers";
import Spinner from "../helper/Spinner";

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
  useEffect(() => {
    dispatch(fetchWarehouseById(id));
  }, [dispatch, id]);
  if (status === "loading" || status === "idle") {
    return <Spinner />;
  }
  if (status === "failed") {
    return <p className="text-red-500">Błąd: {error}</p>;
  }
  if (!warehouse) {
    return <p className="text-red-500">Nie znaleziono magazynu.</p>;
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
  return (
    <div className="space-y-6 max-w-6xl mx-auto">
      <div className="flex items-center justify-between">
        <Link
          to="/warehouses"
          className="text-gray-600 hover:text-pink-500 transition duration-200"
        >
          <FaChevronLeft className="inline mr-2" /> Powrót do Magazynów
        </Link>
        <Link
          to={`/warehouses/${id}/edit`}
          className="flex items-center bg-pink-500 hover:bg-pink-600 text-white px-4 py-2 rounded-lg transition duration-200"
        >
          <FaEdit className="mr-2" /> Edytuj Magazyn
        </Link>
      </div>
      <div className="bg-white rounded-lg shadow p-6 grid grid-cols-1 md:grid-cols-3 gap-6">
        <div>
          <h2 className="text-lg font-semibold mb-2">Nazwa</h2>
          <p className="text-gray-800">{name}</p>
        </div>
        <div>
          <h2 className="text-lg font-semibold mb-2">Pojemność</h2>
          <p className="text-gray-800">{capacity}</p>
        </div>
        <div>
          <h2 className="text-lg font-semibold mb-2">Zajęte</h2>
          <p className="text-gray-800">{occupiedCapacity}</p>
        </div>
        <div>
          <h2 className="text-lg font-semibold mb-2">Adres</h2>
          <p className="text-gray-800">{address}</p>
        </div>
        <div>
          <h2 className="text-lg font-semibold mb-2">Ilość przedmiotów</h2>
          <p className="text-gray-800">{numberFormatter(totalItems)}</p>
        </div>
        <div>
          <h2 className="text-lg font-semibold mb-2">Łączna wartość</h2>
          <p className="text-gray-800">{currencyFormatter(totalValue)}</p>
        </div>
      </div>
      <section>
        <h3 className="text-2xl font-semibold mb-4">Historia Zajętości</h3>
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
      </section>
      <section className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <div>
          <h3 className="text-2xl font-semibold mb-4">Pracownicy</h3>
          {employees.length === 0 ? (
            <p className="text-red-500">Brak pracowników.</p>
          ) : (
            <div className="bg-white rounded-lg shadow overflow-auto">
              <div className="grid grid-cols-5 bg-gray-50 text-xs font-medium text-gray-500 uppercase p-2">
                <div>Imię i Nazwisko</div>
                <div className="text-right">E-mail</div>
                <div className="text-right">Nr. telefonu</div>
                <div className="text-right">Stanowisko</div>
                <div className="text-right">Detale</div>
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
              Pokaż więcej
            </button>
          )}
        </div>
        <div>
          <h3 className="text-2xl font-semibold mb-4">Produkty</h3>
          {products.length === 0 ? (
            <p className="text-red-500">Brak produktów.</p>
          ) : (
            <div className="bg-white rounded-lg shadow overflow-auto">
              <div className="grid grid-cols-5 bg-gray-50 text-xs font-medium text-gray-500 uppercase p-2">
                <div>Nazwa</div>
                <div className="text-right">Ilość</div>
                <div className="text-right">Cena za szt.</div>
                <div className="text-right">Wartość</div>
                <div className="text-right">Detale</div>
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
              Pokaż więcej
            </button>
          )}
        </div>
      </section>
      <section>
        <h3 className="text-2xl font-semibold mb-4">Ostatnie Transakcje</h3>
        {transactions.length === 0 ? (
          <p className="text-red-500">Brak transakcji.</p>
        ) : (
          <div className="bg-white rounded-lg shadow overflow-auto">
            <div className="grid grid-cols-5 bg-gray-50 text-xs font-medium text-gray-500 uppercase p-2">
              <div>Data</div>
              <div>Typ</div>
              <div className="text-right">Łącznie</div>
              <div className="text-right">Przez pracownika</div>
              <div className="text-right">Detale</div>
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
            Pokaż więcej
          </button>
        )}
      </section>
    </div>
  );
};

export default WarehouseDetail;
