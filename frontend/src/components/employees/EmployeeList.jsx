import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { fetchEmployees } from "../../features/employees/employeesSlice";
import { fetchRegions } from "../../features/geography/geographySlice";
import Spinner from "../helper/Spinner";
import useDebounce from "../../hooks/useDebounce";
import ItemsList from "../Layout/ItemsList";
import FormList from "../Layout/FormList";

const EmployeeList = () => {
  const dispatch = useDispatch();
  const { list: data, status, error } = useSelector((state) => state.employees);
  const { content: employees, page: pageInfo } = data;
  const { totalPages } = pageInfo;
  const { regions } = useSelector((state) => state.geography);
  const [searchTerm, setSearchTerm] = useState("");
  const [selectedRegion, setSelectedRegion] = useState("");
  const [minTransactions, setMinTransactions] = useState("");
  const [maxTransactions, setMaxTransactions] = useState("");
  const debouncedSearchTerm = useDebounce(searchTerm);
  const debouncedMinTransactions = useDebounce(minTransactions);
  const debouncedMaxTransactions = useDebounce(maxTransactions);
  const [sortOption, setSortOption] = useState("");
  const [page, setPage] = useState(1);
  const selectedWarehouse = useSelector((state) => state.selectedWarehouse);
  useEffect(() => {
    dispatch(
      fetchEmployees({
        partOfNameOrSurname: debouncedSearchTerm || undefined,
        regionId: selectedRegion || undefined,
        minTransactions: debouncedMinTransactions
          ? parseInt(debouncedMinTransactions)
          : undefined,
        maxTransactions: debouncedMaxTransactions
          ? parseInt(debouncedMaxTransactions)
          : undefined,
        warehouseId: selectedWarehouse || undefined,
        page: page - 1 || 0,
      })
    );
  }, [
    dispatch,
    debouncedSearchTerm,
    selectedRegion,
    debouncedMinTransactions,
    debouncedMaxTransactions,
    selectedWarehouse,
    page,
  ]);
  useEffect(() => {
    dispatch(fetchRegions());
  }, [dispatch]);
  const filtered = [...employees].sort((a, b) => {
    switch (sortOption) {
      case "name":
        return `${a.name} ${a.surname}`.localeCompare(`${b.name} ${b.surname}`);
      case "name-reverse":
        return `${b.name} ${b.surname}`.localeCompare(`${a.name} ${a.surname}`);
      case "email":
        return a.email.localeCompare(b.email);
      case "email-reverse":
        return b.email.localeCompare(a.email);
      case "phone":
        return a.phoneNumber.localeCompare(b.phoneNumber);
      case "phone-reverse":
        return b.phoneNumber.localeCompare(a.phoneNumber);
      case "positions":
        return a.position.localeCompare(b.position);
      case "positions-reverse":
        return b.position.localeCompare(a.position);
      case "warehouse":
        return a.warehouseName.localeCompare(b.warehouseName);
      case "warehouse-reverse":
        return b.warehouseName.localeCompare(a.warehouseName);
      default:
        return 0;
    }
  });
  return (
    <>
      <FormList
        inputs={[
          {
            type: "text",
            label: "Name",
            placeholder: "Search for Clients...",
            value: searchTerm,
            setValue: setSearchTerm,
          },
          {
            type: "select",
            label: "Region",
            value: selectedRegion,
            setValue: setSelectedRegion,
            options: regions.map((reg) => ({
              value: reg.id,
              label: reg.name,
            })),
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
            { value: "email", label: "E-mail (from A to Z)" },
            { value: "email-reverse", label: "E-mail (from Z to A)" },
            { value: "phone", label: "Phone No. (rosnąco)" },
            { value: "phone-reverse", label: "Phone No. (malejąco)" },
            { value: "address", label: "Address (from A to Z)" },
            { value: "address-reverse", label: "Address (from Z to A)" },
            { value: "transactions", label: "Transactions amount (asc)" },
            {
              value: "transactions-reverse",
              label: "Transactions amount (desc)",
            },
          ],
        }}
      />
      {status === "loading" || status === "idle" ? (
        <Spinner />
      ) : status === "failed" ? (
        <p className="text-red-500">Error: {error}</p>
      ) : filtered.length === 0 ? (
        <p className="text-red-500">Employee not found.</p>
      ) : (
        <ItemsList
          pagination={{ page, setPage, totalPages }}
          labels={[
            { name: "Name", type: "Link" },
            { name: "E-mail", type: "Text-Long" },
            { name: "Phone No.", type: "Text" },
            { name: "Position", type: "Text" },
            { name: "Warehouse", type: "Text" },
            { name: "Transactions", type: "Number" },
          ]}
          data={filtered.map((item) => ({
            id: item.employeeId,
            name: `${item.name} ${item.surname}`,
            email: item.email,
            phoneNumber: item.phoneNumber,
            position: item.position,
            warehouseName: item.warehouseName,
            transactionsCount: item.transactionsCount,
          }))}
          actions={{
            get: true,
          }}
          path="employees"
        />
      )}
    </>
  );
};

export default EmployeeList;
