import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link } from "react-router-dom";
import { fetchTransactions } from "../../features/transactions/transactionsSlice";
import { FaSearch, FaEye, FaChevronDown } from "react-icons/fa";
import { format } from "date-fns";
import { currencyFormatter } from "../../utils/helpers";

const TransactionList = () => {
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
  const [sortOption, setSortOption] = useState("");
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFilters((f) => ({ ...f, [name]: value }));
  };
  useEffect(() => {
    dispatch(
      fetchTransactions({ warehouseId: selectedWarehouse || undefined })
    );
  }, [dispatch, selectedWarehouse]);
  useEffect(() => {
    dispatch(
      fetchTransactions({
        fromDate: filters.fromDate || undefined,
        toDate: filters.toDate || undefined,
        type: filters.type || undefined,
        warehouseId: selectedWarehouse || undefined,
      })
    );
  }, [dispatch, filters, selectedWarehouse]);
  const filtered = [...transactions].sort((a, b) => {
    switch (sortOption) {
      case "date":
        return new Date(a.date) - new Date(b.date);
      case "date-reverse":
        return new Date(b.date) - new Date(a.date);
      case "description":
        return a.description.localeCompare(b.description);
      case "description-reverse":
        return b.description.localeCompare(a.description);
      case "type":
        return a.type.localeCompare(b.type);
      case "type-reverse":
        return b.type.localeCompare(a.type);
      // case "from":
      //   return (a.fromWarehouseId || "").localeCompare(b.fromWarehouseId || "");
      // case "from-reverse":
      //   return (b.fromWarehouseId || "").localeCompare(a.fromWarehouseId || "");
      // case "to":
      //   return (
      //     a.toWarehouseId ||
      //     a.clientId ||
      //     a.supplierId ||
      //     ""
      //   ).localeCompare(b.toWarehouseId || b.clientId || b.supplierId || "");
      // case "to-reverse":
      //   return (
      //     b.toWarehouseId ||
      //     b.clientId ||
      //     b.supplierId ||
      //     ""
      //   ).localeCompare(a.toWarehouseId || a.clientId || a.supplierId || "");
      case "total":
        return a.totalPrice - b.totalPrice;
      case "total-reverse":
        return b.totalPrice - a.totalPrice;
      default:
        return 0;
    }
  });
  return (
    <>
      <form className="flex justify-between items-center space-x-4">
        <div className="flex flex-wrap gap-4 items-end">
          <div>
            <label className="block text-sm font-medium">Od dnia</label>
            <input
              type="date"
              name="fromDate"
              value={filters.fromDate}
              onChange={handleChange}
              className="border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-1 focus:ring-pink-500 focus:border-pink-500 transition-colors duration-300"
            />
          </div>
          <div>
            <label className="block text-sm font-medium">Do dnia</label>
            <input
              type="date"
              name="toDate"
              value={filters.toDate}
              onChange={handleChange}
              className="border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-1 focus:ring-pink-500 focus:border-pink-500 transition-colors duration-300"
            />
          </div>
          <div>
            <label className="block text-sm font-medium">Typ transakcji</label>
            <select
              name="type"
              value={filters.type}
              onChange={handleChange}
              className="border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-1 focus:ring-pink-500 focus:border-pink-500 transition-colors duration-300"
            >
              <option value="">Wszystkie</option>
              <option value="SUPPLIER_TO_WAREHOUSE">
                Supplier to Warehouse
              </option>
              <option value="WAREHOUSE_TO_CUSTOMER">
                Warehouse to Customer
              </option>
              <option value="WAREHOUSE_TO_WAREHOUSE">
                Warehouse to Warehouse
              </option>
            </select>
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
              <option value="date">Data (od najstarszej)</option>
              <option value="date-reverse">Data (od najmłodszej)</option>
              <option value="description">Opis (od A do Z)</option>
              <option value="description-reverse">Opis (od Z do A)</option>
              <option value="type">Typ (od A do Z)</option>
              <option value="type-reverse">Typ (od Z do A)</option>
              <option value="from">Z Miejsca (od A do Z)</option>
              <option value="from-reverse">Z Miejsca (od Z do A)</option>
              <option value="to">Do Miejsca (od A do Z)</option>
              <option value="to-reverse">Do Miejsca (od Z do A)</option>
              <option value="total">Łącznie (rosnąco)</option>
              <option value="total-reverse">Łącznie (malejąco)</option>
            </select>
            <FaChevronDown className="pointer-events-none absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-400" />
          </div>
        </div>
      </form>
      {status === "loading" || status === "idle" ? (
        <p>Ładowanie...</p>
      ) : status === "failed" ? (
        <p className="text-red-500">Error: {error}</p>
      ) : (
        <div className="bg-white rounded-lg shadow overflow-auto">
          <div className="hidden sm:grid grid-cols-7 gap-4 p-4 bg-gray-50 text-xs font-medium text-gray-500 uppercase tracking-wider">
            <div>Data</div>
            <div>Opis</div>
            <div>Typ</div>
            <div>Od miejsca</div>
            <div>Do miejsca</div>
            <div className="text-right">Łącznie</div>
            <div className="text-center">Akcje</div>
          </div>
          <div className="divide-y divide-gray-200">
            {filtered.map((tx) => (
              <div
                key={tx.transactionId}
                className="grid grid-cols-1 sm:grid-cols-7 gap-4 p-4 hover:bg-pink-50 transition-colors"
              >
                <div>{format(new Date(tx.date), "yyyy-MM-dd")}</div>
                <div>{tx.description}</div>
                <div>{tx.type.replace(/_/g, " ")}</div>
                <div>{tx.fromWarehouseId ?? "-"}</div>
                <div>
                  {tx.toWarehouseId ?? tx.clientId ?? tx.supplierId ?? "-"}
                </div>
                <div className="text-right">
                  {currencyFormatter(tx.totalPrice)}
                </div>
                <div className="text-gray-600 flex justify-center">
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
    </>
  );
};

export default TransactionList;
