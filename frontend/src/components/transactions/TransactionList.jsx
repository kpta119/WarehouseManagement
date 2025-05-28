import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link } from "react-router-dom";
import { fetchTransactions } from "../../features/transactions/transactionsSlice";
import { FaEye } from "react-icons/fa";
import { format } from "date-fns";
import { currencyFormatter, numberFormatter } from "../../utils/helpers";
import DateInput from "../helper/DateInput";
import SelectInput from "../helper/SelectInput";
import NumberInput from "../helper/NumberInput";
import Pagination from "../helper/Pagination";
import Spinner from "../helper/Spinner";

const TransactionList = () => {
  const dispatch = useDispatch();
  const {
    list: transactions,
    status,
    error,
  } = useSelector((state) => state.transactions);
  const selectedWarehouse = useSelector((state) => state.selectedWarehouse);
  const [fromDate, setFromDate] = useState("");
  const [toDate, setToDate] = useState("");
  const [type, setType] = useState("");
  const [minTotalPrice, setMinTotalPrice] = useState("");
  const [maxTotalPrice, setMaxTotalPrice] = useState("");
  const [minTotalSize, setMinTotalSize] = useState("");
  const [maxTotalSize, setMaxTotalSize] = useState("");
  const [page, setPage] = useState(1);
  const totalPages = 10;
  const [sortOption, setSortOption] = useState("");
  useEffect(() => {
    dispatch(
      fetchTransactions({
        fromDate: fromDate || undefined,
        toDate: toDate || undefined,
        type: type || undefined,
        minTotalPrice: minTotalPrice ? parseFloat(minTotalPrice) : undefined,
        maxTotalPrice: maxTotalPrice ? parseFloat(maxTotalPrice) : undefined,
        minTotalSize: minTotalSize ? parseFloat(minTotalSize) : undefined,
        maxTotalSize: maxTotalSize ? parseFloat(maxTotalSize) : undefined,
        page: page || 1,
        warehouseId: selectedWarehouse || undefined,
      })
    );
  }, [
    dispatch,
    selectedWarehouse,
    page,
    fromDate,
    toDate,
    type,
    minTotalPrice,
    maxTotalPrice,
    minTotalSize,
    maxTotalSize,
  ]);
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
      case "size":
        return a.totalSize - b.totalSize;
      case "size-reverse":
        return b.totalSize - a.totalSize;
      default:
        return 0;
    }
  });
  return (
    <>
      <form className="flex justify-between space-x-4">
        <div className="flex flex-wrap gap-4 items-end">
          <DateInput label="Od dnia" value={fromDate} setValue={setFromDate} />
          <DateInput label="Do dnia" value={toDate} setValue={setToDate} />
          <SelectInput label="Typ transakcji" value={type} setValue={setType}>
            <option value="">Wszystkie</option>
            <option value="SUPPLIER_TO_WAREHOUSE">Supplier to Warehouse</option>
            <option value="WAREHOUSE_TO_CUSTOMER">Warehouse to Customer</option>
            <option value="WAREHOUSE_TO_WAREHOUSE">
              Warehouse to Warehouse
            </option>
          </SelectInput>
          <NumberInput
            label="Łączna kwota (min)"
            isMinus={true}
            placeholder="Wpisz kwotę..."
            value={minTotalPrice}
            setValue={setMinTotalPrice}
          />
          <NumberInput
            label="Łączna kwota (max)"
            isMinus={false}
            placeholder="Wpisz kwotę..."
            value={maxTotalPrice}
            setValue={setMaxTotalPrice}
          />
          <NumberInput
            label="Łączny rozmiar (min)"
            isMinus={true}
            placeholder="Wpisz kwotę..."
            value={minTotalSize}
            setValue={setMinTotalSize}
          />
          <NumberInput
            label="Łączny rozmiar (max)"
            isMinus={false}
            placeholder="Wpisz kwotę..."
            value={maxTotalSize}
            setValue={setMaxTotalPrice}
          />
        </div>
        <SelectInput
          label="Sortowanie"
          value={sortOption}
          setValue={setSortOption}
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
        </SelectInput>
      </form>
      {status === "loading" || status === "idle" ? (
        <Spinner />
      ) : status === "failed" ? (
        <p className="text-red-500">Błąd: {error}</p>
      ) : filtered.length === 0 ? (
        <p className="text-red-500">Nie znaleziono transakcji</p>
      ) : (
        <>
          <Pagination
            currentPage={page}
            totalPages={totalPages}
            onPageChange={setPage}
          />
          <div className="bg-white rounded-lg shadow overflow-auto">
            <div className="hidden sm:grid grid-cols-9 gap-4 p-4 bg-gray-50 text-xs font-medium text-gray-500 uppercase tracking-wider">
              <div>Data</div>
              <div>Opis</div>
              <div>Typ</div>
              <div>Pracownik</div>
              <div>Od miejsca</div>
              <div>Do miejsca</div>
              <div className="text-right">Łączna kwota</div>
              <div className="text-right">Łączny rozmiar</div>
              <div className="text-center">Akcje</div>
            </div>
            <div className="divide-y divide-gray-200">
              {filtered.map((tx) => (
                <div
                  key={tx.transactionId}
                  className="grid grid-cols-1 sm:grid-cols-9 gap-4 p-4 hover:bg-pink-50 transition-colors duration-200"
                >
                  <div>{format(new Date(tx.date), "yyyy-MM-dd")}</div>
                  <div className="truncate">{tx.description}</div>
                  <div>
                    {tx.type
                      .toLowerCase()
                      .replace(/_/g, " ")
                      .replace(/\b\w/g, (c) => c.toUpperCase())}
                  </div>
                  <div>{tx.employeeId}</div>
                  <div>{tx.fromWarehouseId ?? tx.supplierId ?? "-"}</div>
                  <div>{tx.toWarehouseId ?? tx.clientId ?? "-"}</div>
                  <div className="text-right">
                    {currencyFormatter(tx.totalPrice)}
                  </div>
                  <div className="text-right">
                    {numberFormatter(tx.totalSize)}
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

export default TransactionList;
