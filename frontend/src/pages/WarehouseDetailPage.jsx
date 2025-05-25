import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useParams, Link } from "react-router-dom";
import { fetchWarehouseById } from "../features/warehouses/warehousesSlice";
import {
  ResponsiveContainer,
  LineChart,
  Line,
  XAxis,
  YAxis,
  Tooltip,
  CartesianGrid,
} from "recharts";
import { FaChevronLeft, FaEdit } from "react-icons/fa";

const WarehouseDetailPage = () => {
  const { id } = useParams();
  const dispatch = useDispatch();
  const { current: warehouse } = useSelector((state) => state.warehouses);
  useEffect(() => {
    dispatch(fetchWarehouseById(id));
  }, [dispatch, id]);
  if (!warehouse) return <p>Loading warehouse details...</p>;
  const {
    name,
    capacity,
    occupiedCapacity,
    address,
    employees,
    products,
    transactions,
    occupancyHistory,
  } = warehouse;
  return (
    <div className="space-y-6 max-w-6xl mx-auto">
      <div className="flex items-center justify-between">
        <Link to="/warehouses" className="text-gray-600 hover:text-pink-500">
          <FaChevronLeft className="inline mr-2" /> Powrót do Magazynów
        </Link>
        <Link
          to={`/warehouses/${id}/edit`}
          className="flex items-center bg-pink-500 hover:bg-pink-600 text-white px-4 py-2 rounded-lg transition"
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
        <div className="md:col-span-3">
          <h2 className="text-lg font-semibold mb-2">Adres</h2>
          <p className="text-gray-800">{address}</p>
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
          <div className="bg-white rounded-lg shadow overflow-auto">
            <div className="grid grid-cols-5 bg-gray-50 text-xs font-medium text-gray-500 uppercase p-2">
              <div>Imię i Nazwisko</div>
              <div className="text-right">E-mail</div>
              <div className="text-right">Nr. telefonu</div>
              <div className="text-right">Stanowisko</div>
              <div className="text-right">Detale</div>
            </div>
            <div className="divide-y divide-gray-200">
              {employees.map((emp) => (
                <div
                  key={emp.employeeId}
                  className="grid grid-cols-5 p-2 text-sm text-gray-700"
                >
                  <div>
                    {emp.name} {emp.surname}
                  </div>
                  <div className="text-right">{emp.email}</div>
                  <div className="text-right">{emp.phoneNumber}</div>
                  <div className="text-right">{emp.position}</div>
                  <div className="text-right">
                    <Link
                      to={`/employees/${emp.employeeId}`}
                      className="text-pink-600 hover:underline"
                    >
                      Zobacz
                    </Link>
                  </div>
                </div>
              ))}
            </div>
          </div>
        </div>
        <div>
          <h3 className="text-2xl font-semibold mb-4">Produkty</h3>
          <div className="bg-white rounded-lg shadow overflow-auto">
            <div className="grid grid-cols-5 bg-gray-50 text-xs font-medium text-gray-500 uppercase p-2">
              <div>Nazwa</div>
              <div className="text-right">Ilość</div>
              <div className="text-right">Cena za szt.</div>
              <div className="text-right">Wartość</div>
              <div className="text-right">Detale</div>
            </div>
            <div className="divide-y divide-gray-200">
              {products.map((pr) => (
                <div
                  key={pr.productId}
                  className="grid grid-cols-5 p-2 text-sm text-gray-700"
                >
                  <div>{pr.name}</div>
                  <div className="text-right">{pr.quantity}</div>
                  <div className="text-right">${pr.unitPrice.toFixed(2)}</div>
                  <div className="text-right">
                    ${(pr.quantity * pr.unitPrice).toFixed(2)}
                  </div>
                  <div className="text-right">
                    <Link
                      to={`/products/${pr.productId}`}
                      className="text-pink-600 hover:underline"
                    >
                      Zobacz
                    </Link>
                  </div>
                </div>
              ))}
            </div>
          </div>
        </div>
      </section>
      <section>
        <h3 className="text-2xl font-semibold mb-4">Ostatnie Transakcje</h3>
        <div className="bg-white rounded-lg shadow overflow-auto">
          <div className="grid grid-cols-5 bg-gray-50 text-xs font-medium text-gray-500 uppercase p-2">
            <div>Data</div>
            <div>Typ</div>
            <div className="text-right">Łącznie</div>
            <div className="text-right">Przez pracownika</div>
            <div className="text-right">Detale</div>
          </div>
          <div className="divide-y divide-gray-200">
            {transactions.map((tx) => (
              <div
                key={tx.transactionId}
                className="grid grid-cols-5 items-center p-2 text-sm text-gray-700"
              >
                <div>{new Date(tx.date).toLocaleDateString()}</div>
                <div>{tx.type.replace(/_/g, " ")}</div>
                <div className="text-right">${tx.totalPrice.toFixed(2)}</div>
                <div className="text-right">{tx.employeeId}</div>
                <div className="text-right">
                  <Link
                    to={`/transactions/${tx.transactionId}`}
                    className="text-pink-600 hover:underline"
                  >
                    Zobacz
                  </Link>
                </div>
              </div>
            ))}
          </div>
        </div>
      </section>
    </div>
  );
};

export default WarehouseDetailPage;
