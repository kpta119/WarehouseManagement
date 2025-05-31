import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { fetchTransactions } from "../../features/transactions/transactionsSlice";
import Spinner from "../helper/Spinner";
import useDebounce from "../../hooks/useDebounce";
import ItemsList from "../Layout/ItemsList";
import FormList from "../Layout/FormList";

const TransactionList = () => {
  const dispatch = useDispatch();
  const {
    list: data,
    status,
    error,
  } = useSelector((state) => state.transactions);
  const { content: transactions, page: pageInfo } = data;
  const { totalPages } = pageInfo;
  const selectedWarehouse = useSelector((state) => state.selectedWarehouse);
  const [fromDate, setFromDate] = useState("");
  const [toDate, setToDate] = useState("");
  const [type, setType] = useState("");
  const [minTotalPrice, setMinTotalPrice] = useState("");
  const [maxTotalPrice, setMaxTotalPrice] = useState("");
  const [minTotalSize, setMinTotalSize] = useState("");
  const [maxTotalSize, setMaxTotalSize] = useState("");
  const debouncedMinTotalPrice = useDebounce(minTotalPrice);
  const debouncedMaxTotalPrice = useDebounce(maxTotalPrice);
  const debouncedMinTotalSize = useDebounce(minTotalSize);
  const debouncedMaxTotalSize = useDebounce(maxTotalSize);
  const [page, setPage] = useState(1);
  const [sortOption, setSortOption] = useState("");
  useEffect(() => {
    dispatch(
      fetchTransactions({
        fromDate: fromDate || undefined,
        toDate: toDate || undefined,
        type: type || undefined,
        minTotalPrice: debouncedMinTotalPrice
          ? parseFloat(debouncedMinTotalPrice)
          : undefined,
        maxTotalPrice: debouncedMaxTotalPrice
          ? parseFloat(debouncedMaxTotalPrice)
          : undefined,
        minTotalSize: debouncedMinTotalSize
          ? parseFloat(debouncedMinTotalSize)
          : undefined,
        maxTotalSize: debouncedMaxTotalSize
          ? parseFloat(debouncedMaxTotalSize)
          : undefined,
        page: page - 1 || 0,
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
    debouncedMinTotalPrice,
    debouncedMaxTotalPrice,
    debouncedMinTotalSize,
    debouncedMaxTotalSize,
  ]);
  if (!transactions) {
    return <Spinner />;
  }
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
      case "employee":
        return (a.employeeName || "").localeCompare(b.employeeName || "");
      case "employee-reverse":
        return (b.employeeName || "").localeCompare(a.employeeName || "");
      case "from":
        return (a.fromWarehouseName || "").localeCompare(
          b.fromWarehouseName || ""
        );
      case "from-reverse":
        return (b.fromWarehouseName || "").localeCompare(
          a.fromWarehouseName || ""
        );
      case "to":
        return (
          a.toWarehouseName ||
          a.clientName ||
          a.supplierName ||
          ""
        ).localeCompare(
          b.toWarehouseName || b.clientName || b.supplierName || ""
        );
      case "to-reverse":
        return (
          b.toWarehouseName ||
          b.clientName ||
          b.supplierName ||
          ""
        ).localeCompare(
          a.toWarehouseName || a.clientName || a.supplierName || ""
        );
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
      <FormList
        inputs={[
          {
            type: "date",
            label: "Od dnia",
            value: fromDate,
            setValue: setFromDate,
          },
          {
            type: "date",
            label: "Do dnia",
            value: toDate,
            setValue: setToDate,
          },
          {
            type: "select",
            label: "Typ transakcji",
            value: type,
            setValue: setType,
            options: [
              {
                value: "SUPPLIER_TO_WAREHOUSE",
                label: "Supplier to Warehouse",
              },
              {
                value: "WAREHOUSE_TO_CUSTOMER",
                label: "Warehouse to Customer",
              },
              {
                value: "WAREHOUSE_TO_WAREHOUSE",
                label: "Warehouse to Warehouse",
              },
            ],
          },
          {
            type: "number",
            label: "Kwota (min)",
            isMinus: true,
            placeholder: "Wpisz kwotę...",
            value: minTotalPrice,
            setValue: setMinTotalPrice,
          },
          {
            type: "number",
            label: "Kwota (max)",
            isMinus: false,
            placeholder: "Wpisz kwotę...",
            value: maxTotalPrice,
            setValue: setMaxTotalPrice,
          },
          {
            type: "number",
            label: "Rozmiar (min)",
            isMinus: true,
            placeholder: "Wpisz rozmiar...",
            value: minTotalSize,
            setValue: setMinTotalSize,
          },
          {
            type: "number",
            label: "Rozmiar (max)",
            isMinus: false,
            placeholder: "Wpisz rozmiar...",
            value: maxTotalSize,
            setValue: setMaxTotalSize,
          },
        ]}
        sorting={{
          sortOption,
          setSortOption,
          options: [
            { value: "date", label: "Data (od najstarszej)" },
            { value: "date-reverse", label: "Data (od najmłodszej)" },
            { value: "description", label: "Opis (od A do Z)" },
            { value: "description-reverse", label: "Opis (od Z do A)" },
            { value: "type", label: "Typ (od A do Z)" },
            { value: "type-reverse", label: "Typ (od Z do A)" },
            { value: "employee", label: "Pracownik (od A do Z)" },
            { value: "employee-reverse", label: "Pracownik (od Z do A)" },
            { value: "from", label: "Z Miejsca (od A do Z)" },
            { value: "from-reverse", label: "Z Miejsca (od Z do A)" },
            { value: "to", label: "Do Miejsca (od A do Z)" },
            { value: "to-reverse", label: "Do Miejsca (od Z do A)" },
            { value: "total", label: "Łącznie (rosnąco)" },
            { value: "total-reverse", label: "Łącznie (malejąco)" },
          ],
        }}
      />
      {status === "loading" || status === "idle" ? (
        <Spinner />
      ) : status === "failed" ? (
        <p className="text-red-500">Błąd: {error}</p>
      ) : filtered.length === 0 ? (
        <p className="text-red-500">Nie znaleziono transakcji</p>
      ) : (
        <ItemsList
          pagination={{ page, setPage, totalPages }}
          labels={[
            { name: "Data", type: "Date" },
            { name: "Opis", type: "Text-Long" },
            { name: "Typ", type: "Type" },
            { name: "Pracownik", type: "Text" },
            { name: "Od miejsca", type: "Text" },
            { name: "Do miejsca", type: "Text" },
            { name: "Kwota", type: "Currency" },
            { name: "Rozmiar", type: "Number" },
          ]}
          data={filtered.map((item) => ({
            id: item.transactionId,
            date: item.date,
            description: item.description,
            type: item.type,
            employeeName: item.employeeName,
            ["fromWarehouseName" in item
              ? "fromWarehouseName"
              : "supplierName"]: item.fromWarehouseName || item.supplierName,
            ["toWarehouseName" in item ? "toWarehouseName" : "clientName"]:
              item.toWarehouseName || item.clientName,
            totalPrice: item.totalPrice,
            totalSize: item.totalSize,
          }))}
          actions={{
            get: true,
          }}
          path="transactions"
        />
      )}
    </>
  );
};

export default TransactionList;
