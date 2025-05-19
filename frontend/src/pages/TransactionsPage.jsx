import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link } from "react-router-dom";
import { fetchTransactions } from "../features/transactions/transactionsSlice";
import { FaSearch, FaEye } from "react-icons/fa";
import { format } from "date-fns";

const TransactionsPage = () => {
  const dispatch = useDispatch();
  const {
    list: transactions,
    status,
    error,
  } = useSelector((state) => state.transactions);
  const selectedWarehouse = useSelector((state) => state.selectedWarehouse);
  const [filters, setFilters] = useState({
    fromDate: "",
    toDate: "",
    type: "",
  });
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFilters((f) => ({ ...f, [name]: value }));
  };
  const handleSubmit = (e) => {
    e.preventDefault();
    dispatch(
      fetchTransactions({
        fromDate: filters.fromDate || undefined,
        toDate: filters.toDate || undefined,
        type: filters.type || undefined,
        warehouseId: selectedWarehouse || undefined,
      })
    );
  };
  useEffect(() => {
    dispatch(
      fetchTransactions({ warehouseId: selectedWarehouse || undefined })
    );
  }, [dispatch, selectedWarehouse]);
  return (
    <div className="space-y-6">
      <div className="flex justify-between items-center">
        <h1 className="text-2xl font-semibold">Transactions</h1>
      </div>
      <form onSubmit={handleSubmit} className="flex flex-wrap gap-4 items-end">
        <div>
          <label className="block text-sm font-medium">From</label>
          <input
            type="date"
            name="fromDate"
            value={filters.fromDate}
            onChange={handleChange}
            className="border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-1 focus:ring-pink-500 focus:border-pink-500"
          />
        </div>
        <div>
          <label className="block text-sm font-medium">To</label>
          <input
            type="date"
            name="toDate"
            value={filters.toDate}
            onChange={handleChange}
            className="border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-1 focus:ring-pink-500 focus:border-pink-500"
          />
        </div>
        <div>
          <label className="block text-sm font-medium">Type</label>
          <select
            name="type"
            value={filters.type}
            onChange={handleChange}
            className="border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-1 focus:ring-pink-500 focus:border-pink-500"
          >
            <option value="">All</option>
            <option value="SUPPLIER_TO_WAREHOUSE">Supplier to Warehouse</option>
            <option value="WAREHOUSE_TO_CUSTOMER">Warehouse to Customer</option>
            <option value="WAREHOUSE_TO_WAREHOUSE">
              Warehouse to Warehouse
            </option>
          </select>
        </div>
        <button
          type="submit"
          className="flex items-center bg-pink-500 hover:bg-pink-600 text-white px-4 py-2 rounded-lg transition"
        >
          <FaSearch className="mr-2" /> Filter
        </button>
      </form>
      {status === "loading" || status === "idle" ? (
        <p>Loading...</p>
      ) : status === "failed" ? (
        <p className="text-red-500">Error: {error}</p>
      ) : (
        <div className="bg-white rounded-lg shadow overflow-auto">
          <div className="hidden sm:grid grid-cols-6 gap-4 p-4 bg-gray-50 text-xs font-medium text-gray-500 uppercase tracking-wider">
            <div>Date</div>
            <div>Type</div>
            <div>From</div>
            <div>To</div>
            <div className="text-right">Total</div>
            <div className="text-center">Actions</div>
          </div>
          <div className="divide-y divide-gray-200">
            {transactions.map((tx) => (
              <div
                key={tx.transactionId}
                className="grid grid-cols-1 sm:grid-cols-6 items-center gap-4 p-4 hover:bg-pink-50 transition-colors"
              >
                <div>{format(new Date(tx.date), "yyyy-MM-dd")}</div>
                <div>{tx.type.replace(/_/g, " ")}</div>
                <div>{tx.fromWarehouseId ?? "-"}</div>
                <div>
                  {tx.toWarehouseId ?? tx.clientId ?? tx.supplierId ?? "-"}
                </div>
                <div className="text-right">${tx.totalPrice.toFixed(2)}</div>
                <div className="text-gray-600">
                  <Link
                    to={`/transactions/${tx.transactionId}`}
                    className="flex justify-center"
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

export default TransactionsPage;
