import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import {
  fetchWarehouses,
  deleteWarehouse,
} from "../../features/warehouses/warehousesSlice";
import { fetchRegions } from "../../features/geography/geographySlice";
import Spinner from "../helper/Spinner";
import useDebounce from "../../hooks/useDebounce";
import ItemsList from "../Layout/ItemsList";
import FormList from "../Layout/FormList";

const WarehouseList = () => {
  const dispatch = useDispatch();
  const {
    list: data,
    status,
    error,
  } = useSelector((state) => state.warehouses);
  const { content: warehouses, page: pageInfo } = data;
  const { totalPages } = pageInfo;
  const { regions } = useSelector((state) => state.geography);
  const [searchTerm, setSearchTerm] = useState("");
  const [regionFilter, setRegionFilter] = useState("");
  const [minCapacity, setMinCapacity] = useState("");
  const [maxCapacity, setMaxCapacity] = useState("");
  const [minOccupied, setMinOccupied] = useState("");
  const [maxOccupied, setMaxOccupied] = useState("");
  const [minEmployees, setMinEmployees] = useState("");
  const [maxEmployees, setMaxEmployees] = useState("");
  const [minProducts, setMinProducts] = useState("");
  const [maxProducts, setMaxProducts] = useState("");
  const [minTransactions, setMinTransactions] = useState("");
  const [maxTransactions, setMaxTransactions] = useState("");
  const debouncedSearchTerm = useDebounce(searchTerm);
  const debouncedMinCapacity = useDebounce(minCapacity);
  const debouncedMaxCapacity = useDebounce(maxCapacity);
  const debouncedMinOccupied = useDebounce(minOccupied);
  const debouncedMaxOccupied = useDebounce(maxOccupied);
  const debouncedMinEmployees = useDebounce(minEmployees);
  const debouncedMaxEmployees = useDebounce(maxEmployees);
  const debouncedMinProducts = useDebounce(minProducts);
  const debouncedMaxProducts = useDebounce(maxProducts);
  const debouncedMinTransactions = useDebounce(minTransactions);
  const debouncedMaxTransactions = useDebounce(maxTransactions);
  const [sortOption, setSortOption] = useState("");
  const [page, setPage] = useState(1);
  useEffect(() => {
    setPage(1);
  }, [
    debouncedSearchTerm,
    regionFilter,
    debouncedMinCapacity,
    debouncedMaxCapacity,
    debouncedMinOccupied,
    debouncedMaxOccupied,
    debouncedMinEmployees,
    debouncedMaxEmployees,
    debouncedMinProducts,
    debouncedMaxProducts,
    debouncedMinTransactions,
    debouncedMaxTransactions,
  ]);
  useEffect(() => {
    dispatch(fetchRegions());
  }, [dispatch]);
  useEffect(() => {
    dispatch(
      fetchWarehouses({
        name: debouncedSearchTerm || undefined,
        regionId: regionFilter || undefined,
        minCapacity: debouncedMinCapacity
          ? Number(debouncedMinCapacity)
          : undefined,
        maxCapacity: debouncedMaxCapacity
          ? Number(debouncedMaxCapacity)
          : undefined,
        minOccupied: debouncedMinOccupied
          ? Number(debouncedMinOccupied)
          : undefined,
        maxOccupied: debouncedMaxOccupied
          ? Number(debouncedMaxOccupied)
          : undefined,
        minEmployees: debouncedMinEmployees
          ? Number(debouncedMinEmployees)
          : undefined,
        maxEmployees: debouncedMaxEmployees
          ? Number(debouncedMaxEmployees)
          : undefined,
        minProducts: debouncedMinProducts
          ? Number(debouncedMinProducts)
          : undefined,
        maxProducts: debouncedMaxProducts
          ? Number(debouncedMaxProducts)
          : undefined,
        minTransactions: debouncedMinTransactions
          ? Number(debouncedMinTransactions)
          : undefined,
        maxTransactions: debouncedMaxTransactions
          ? Number(debouncedMaxTransactions)
          : undefined,
        page: page - 1 || 0,
      })
    );
  }, [
    dispatch,
    debouncedSearchTerm,
    regionFilter,
    debouncedMinCapacity,
    debouncedMaxCapacity,
    debouncedMinOccupied,
    debouncedMaxOccupied,
    debouncedMinEmployees,
    debouncedMaxEmployees,
    debouncedMinProducts,
    debouncedMaxProducts,
    debouncedMinTransactions,
    debouncedMaxTransactions,
    page,
  ]);
  const filtered = [...warehouses].sort((a, b) => {
    switch (sortOption) {
      case "name":
        return a.name.localeCompare(b.name);
      case "name-reverse":
        return b.name.localeCompare(a.name);
      case "capacity":
        return a.capacity - b.capacity;
      case "capacity-reverse":
        return b.capacity - a.capacity;
      case "occupied":
        return a.occupiedCapacity - b.occupiedCapacity;
      case "occupied-reverse":
        return b.occupiedCapacity - a.occupiedCapacity;
      case "address":
        return a.address.localeCompare(b.address);
      case "address-reverse":
        return b.address.localeCompare(a.address);
      case "employees":
        return a.employeesCount - b.employeesCount;
      case "employees-reverse":
        return b.employeesCount - a.employeesCount;
      case "products":
        return a.productsSum - b.productsSum;
      case "products-reverse":
        return b.productsSum - a.productsSum;
      case "transactions":
        return a.transactionsCount - b.transactionsCount;
      case "transactions-reverse":
        return b.transactionsCount - a.transactionsCount;
      default:
        return 0;
    }
  });
  const handleDelete = (id) => {
    if (window.confirm("Are you sure you want to delete this warehouse?")) {
      dispatch(deleteWarehouse(id));
    }
  };
  return (
    <>
      <FormList
        inputs={[
          {
            type: "text",
            label: "Name",
            placeholder: "Search for Warehouse...",
            value: searchTerm,
            setValue: setSearchTerm,
          },
          {
            type: "select",
            label: "Region",
            value: regionFilter,
            setValue: setRegionFilter,
            options: regions.map((reg) => ({
              value: reg.id,
              label: reg.name,
            })),
          },
          {
            type: "number",
            label: "Capacity (min)",
            placeholder: "Choose amount...",
            isMinus: true,
            value: minCapacity,
            setValue: setMinCapacity,
          },
          {
            type: "number",
            label: "Capacity (max)",
            placeholder: "Choose amount...",
            isMinus: false,
            value: maxCapacity,
            setValue: setMaxCapacity,
          },
          {
            type: "number",
            label: "Occupied (min)",
            placeholder: "Choose amount...",
            isMinus: true,
            value: minOccupied,
            setValue: setMinOccupied,
          },
          {
            type: "number",
            label: "Occupied (max)",
            placeholder: "Choose amount...",
            isMinus: false,
            value: maxOccupied,
            setValue: setMaxOccupied,
          },
          {
            type: "number",
            label: "Employees (min)",
            placeholder: "Choose amount...",
            isMinus: true,
            value: minEmployees,
            setValue: setMinEmployees,
          },
          {
            type: "number",
            label: "Employees (max)",
            placeholder: "Choose amount...",
            isMinus: false,
            value: maxEmployees,
            setValue: setMaxEmployees,
          },
          {
            type: "number",
            label: "Products (min)",
            placeholder: "Choose amount...",
            isMinus: true,
            value: minProducts,
            setValue: setMinProducts,
          },
          {
            type: "number",
            label: "Products (max)",
            placeholder: "Choose amount...",
            isMinus: false,
            value: maxProducts,
            setValue: setMaxProducts,
          },
          {
            type: "number",
            label: "Transactions (min)",
            placeholder: "Choose amount...",
            isMinus: true,
            value: minTransactions,
            setValue: setMinTransactions,
          },
          {
            type: "number",
            label: "Transactions (max)",
            placeholder: "Choose amount...",
            isMinus: false,
            value: maxTransactions,
            setValue: setMaxTransactions,
          },
        ]}
        sorting={{
          sortOption,
          setSortOption,
          options: [
            { value: "name", label: "Name (from A to Z)" },
            { value: "name-reverse", label: "Name (from Z to A)" },
            { value: "capacity", label: "Capacity (asc)" },
            { value: "capacity-reverse", label: "Capacity (desc)" },
            { value: "occupied", label: "Occupied (asc)" },
            { value: "occupied-reverse", label: "Occupied (desc)" },
            { value: "address", label: "Address (from A to Z)" },
            { value: "address-reverse", label: "Address (from Z to A)" },
            { value: "employees", label: "Employees (asc)" },
            { value: "employees-reverse", label: "Employees (desc)" },
            { value: "products", label: "Products (asc)" },
            { value: "products-reverse", label: "Products (desc)" },
            { value: "transactions", label: "Transactions (asc)" },
            { value: "transactions-reverse", label: "Transactions (desc)" },
          ],
        }}
      />
      {status === "loading" || status === "idle" ? (
        <Spinner />
      ) : status === "failed" ? (
        <p className="text-red-500">Error: {error}</p>
      ) : filtered.length === 0 ? (
        <p className="text-red-500">Warehouse not found.</p>
      ) : (
        <>
          {error && <p className="text-red-500">Error: {error}</p>}
          <ItemsList
            pagination={{ page, setPage, totalPages }}
            labels={[
              { name: "Name", type: "Link" },
              { name: "Address", type: "Text" },
              { name: "Capacity", type: "Number", className: "text-right" },
              { name: "Occupied", type: "Number", className: "text-right" },
              { name: "Employees", type: "Number", className: "text-right" },
              { name: "Products", type: "Number", className: "text-right" },
              { name: "Transactions", type: "Number", className: "text-right" },
            ]}
            data={filtered.map((item) => ({
              id: item.warehouseId,
              name: item.name,
              address: item.address,
              capacity: item.capacity,
              occupiedCapacity: item.occupiedCapacity,
              employeesCount: item.employeesCount,
              productsSum: item.productsSum,
              transactionsCount: item.transactionsCount,
            }))}
            actions={{
              get: true,
              put: true,
              delete: true,
            }}
            path="warehouses"
            handleDelete={handleDelete}
          />
        </>
      )}
    </>
  );
};

export default WarehouseList;
