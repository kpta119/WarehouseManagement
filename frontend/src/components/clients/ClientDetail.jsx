import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useParams, Link } from "react-router-dom";
import { fetchClientById } from "../../features/clients/clientsSlice";
import { FaChevronLeft, FaEye } from "react-icons/fa";
import {
  currencyFormatter,
  dateFormatter,
  numberFormatter,
} from "../../utils/helpers";
import Spinner from "../helper/Spinner";
import {
  ResponsiveContainer,
  LineChart,
  Line,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip as LineTooltip,
  BarChart,
  Bar,
  Tooltip as BarTooltip,
} from "recharts";

const ClientDetail = () => {
  const { id } = useParams();
  const dispatch = useDispatch();
  const { current: client, status, error } = useSelector((s) => s.clients);
  const [transactionsShown, setTransactionsShown] = useState(25);
  useEffect(() => {
    dispatch(fetchClientById(id));
  }, [dispatch, id]);
  if (status === "loading" || status === "idle") {
    return <Spinner />;
  }
  if (status === "failed") {
    return <p className="text-red-500">Error: {error}</p>;
  }
  if (!client) {
    return <p className="text-red-500">Client not found.</p>;
  }
  const { name, email, phoneNumber, address, history = [] } = client;
  const monthlyData = Object.values(
    history.reduce((acc, { date, totalPrice, totalItems }) => {
      const month = date.slice(0, 7);
      if (!acc[month]) {
        acc[month] = { month, totalPrice: 0, totalItems: 0 };
      }
      acc[month].totalPrice += totalPrice;
      acc[month].totalItems += totalItems;
      return acc;
    }, {})
  );
  return (
    <div className="space-y-6 max-w-4xl mx-auto">
      <div className="bg-white p-6 rounded-lg shadow space-y-4">
        <div>
          <Link
            to="/clients"
            className="text-gray-600 hover:text-pink-500 transition duration-200"
          >
            <FaChevronLeft className="inline-block mr-2" /> Return to Clients
          </Link>
        </div>
        <h1 className="text-3xl font-semibold text-gray-800">{name}</h1>
        <div className="p-6 grid grid-cols-1 md:grid-cols-3 gap-6">
          <div>
            <h2 className="text-lg font-semibold mb-2">E-mail</h2>
            <p className="text-gray-800">{email}</p>
          </div>
          <div>
            <h2 className="text-lg font-semibold mb-2">Phone No.</h2>
            <p className="text-gray-800">{phoneNumber}</p>
          </div>
          <div>
            <h2 className="text-lg font-semibold mb-2">Address</h2>
            <p className="text-gray-800">{address}</p>
          </div>
          <div>
            <h2 className="text-lg font-semibold mb-2">Transactions Amt.</h2>
            <p className="text-gray-800">{numberFormatter(history.length)}</p>
          </div>
          <div>
            <h2 className="text-lg font-semibold mb-2">Bought Items</h2>
            <p className="text-gray-800">
              {numberFormatter(
                history.reduce((acc, tx) => acc + tx.totalItems, 0)
              )}
            </p>
          </div>
          <div>
            <h2 className="text-lg font-semibold mb-2">Total Income</h2>
            <p className="text-gray-800">
              {currencyFormatter(
                history.reduce((acc, tx) => acc + tx.totalPrice, 0)
              )}
            </p>
          </div>
        </div>
        <section>
          {history.length > 0 && (
            <div className="space-y-8 flex gap-4">
              <div className="w-1/2">
                <h3 className="text-xl font-semibold mb-2 text-center">
                  Income per month
                </h3>
                <ResponsiveContainer width="100%" height={250}>
                  <LineChart
                    data={monthlyData}
                    margin={{ left: 50, right: 5, top: 5 }}
                  >
                    <CartesianGrid strokeDasharray="3 3" />
                    <XAxis dataKey="month" tick={{ fill: "#6B7280" }} />
                    <YAxis
                      tickFormatter={(v) => currencyFormatter(v)}
                      tick={{ fill: "#6B7280" }}
                    />
                    <LineTooltip
                      formatter={(value) => currencyFormatter(value)}
                    />
                    <Line
                      type="monotone"
                      dataKey="totalPrice"
                      name="Total Amount"
                      stroke="#3B82F6"
                      strokeWidth={2}
                    />
                  </LineChart>
                </ResponsiveContainer>
              </div>
              <div className="w-1/2">
                <h3 className="text-xl font-semibold mb-2 text-center">
                  Amount of items per month
                </h3>
                <ResponsiveContainer width="100%" height={250}>
                  <BarChart data={monthlyData} margin={{ top: 5 }}>
                    <XAxis dataKey="month" tick={{ fill: "#6B7280" }} />
                    <YAxis tick={{ fill: "#6B7280" }} />
                    <BarTooltip formatter={(v) => numberFormatter(v)} />
                    <Bar
                      dataKey="totalItems"
                      name="Amount per Month"
                      fill="#10B981"
                    />
                  </BarChart>
                </ResponsiveContainer>
              </div>
            </div>
          )}
          <h2 className="text-2xl font-semibold mb-4">Transaction History</h2>
          {history.length === 0 ? (
            <p className="text-red-500">No transaction history.</p>
          ) : (
            <>
              <div className="bg-white rounded-lg shadow overflow-auto">
                <div className="grid grid-cols-5 bg-gray-50 text-xs font-medium text-gray-500 uppercase p-2 tracking-wide">
                  <div>Date</div>
                  <div>Description</div>
                  <div className="text-right">Items Amount</div>
                  <div className="text-right">Total Price</div>
                  <div className="text-center">Details</div>
                </div>
                <div className="divide-y divide-gray-200">
                  {[...history]
                    .reverse()
                    .slice(0, transactionsShown)
                    .map((tx) => (
                      <div
                        key={tx.transactionId}
                        className="grid grid-cols-5 items-center p-2 text-sm text-gray-700 hover:bg-pink-50 transition-colors duration-200"
                      >
                        <div>{dateFormatter(tx.date)}</div>
                        <div className="truncate">{tx.description}</div>
                        <div className="text-right">
                          {numberFormatter(tx.totalItems)}
                        </div>
                        <div className="text-right">
                          {currencyFormatter(tx.totalPrice)}
                        </div>
                        <div className="flex justify-center text-gray-600">
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
              {history.length > transactionsShown && (
                <button
                  onClick={() => setTransactionsShown((prev) => prev + 25)}
                  className="mt-4 bg-pink-500 hover:bg-pink-600 text-white px-4 py-2 rounded-lg transition mx-auto block"
                >
                  Show More
                </button>
              )}
            </>
          )}
        </section>
      </div>
    </div>
  );
};

export default ClientDetail;
