import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { fetchSuppliers } from "../../features/suppliers/suppliersSlice";
import { fetchRegions } from "../../features/geography/geographySlice";
import Spinner from "../helper/Spinner";
import useDebounce from "../../hooks/useDebounce";
import ItemsList from "../Layout/ItemsList";
import FormList from "../Layout/FormList";

const SupplierList = () => {
  const dispatch = useDispatch();
  const { list: data, status, error } = useSelector((state) => state.suppliers);
  const { content: suppliers, page: pageInfo } = data;
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
      fetchSuppliers({
        name: debouncedSearchTerm || undefined,
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
  if (!suppliers) {
    return <Spinner />;
  }
  const filtered = [...suppliers].sort((a, b) => {
    switch (sortOption) {
      case "name":
        return a.name.localeCompare(b.name);
      case "name-reverse":
        return b.name.localeCompare(a.name);
      case "email":
        return a.email.localeCompare(b.email);
      case "email-reverse":
        return b.email.localeCompare(a.email);
      case "phone":
        return a.phoneNumber.localeCompare(b.phoneNumber);
      case "phone-reverse":
        return b.phoneNumber.localeCompare(a.phoneNumber);
      case "address":
        return a.address.localeCompare(b.address);
      case "address-reverse":
        return b.address.localeCompare(a.address);
      case "transactions":
        return a.transactionsCount - b.transactionsCount;
      case "transactions-reverse":
        return b.transactionsCount - a.transactionsCount;
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
            label: "Nazwa",
            placeholder: "Szukaj klientów...",
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
            label: "Transakcje (min)",
            placeholder: "Wybierz transakcje...",
            isMinus: true,
            value: minTransactions,
            setValue: setMinTransactions,
          },
          {
            type: "number",
            label: "Transakcje (max)",
            placeholder: "Wybierz transakcje...",
            isMinus: false,
            value: maxTransactions,
            setValue: setMaxTransactions,
          },
        ]}
        sorting={{
          sortOption,
          setSortOption,
          options: [
            { value: "name", label: "Nazwa (od A do Z)" },
            { value: "name-reverse", label: "Nazwa (od Z do A)" },
            { value: "email", label: "E-mail (od A do Z)" },
            { value: "email-reverse", label: "E-mail (od Z do A)" },
            { value: "phone", label: "Nr. telefonu (rosnąco)" },
            { value: "phone-reverse", label: "Nr. telefonu (malejąco)" },
            { value: "address", label: "Adres (od A do Z)" },
            { value: "address-reverse", label: "Adres (od Z do A)" },
            { value: "transactions", label: "Liczba transakcji (rosnąco)" },
            {
              value: "transactions-reverse",
              label: "Liczba transakcji (malejąco)",
            },
          ],
        }}
      />
      {status === "loading" || status === "idle" ? (
        <Spinner />
      ) : status === "failed" ? (
        <p className="text-red-500">Błąd: {error}</p>
      ) : filtered.length === 0 ? (
        <p className="text-red-500">Nie znaleziono dostawcy</p>
      ) : (
        <ItemsList
          pagination={{ page, setPage, totalPages }}
          labels={[
            { name: "Nazwa", type: "Link" },
            { name: "E-mail", type: "Text-Long" },
            { name: "Nr. telefonu", type: "Text" },
            { name: "Adres", type: "Text" },
            { name: "Transakcje", type: "Number" },
          ]}
          data={filtered.map((item) => ({
            id: item.supplierId,
            name: item.name,
            email: item.email,
            phoneNumber: item.phoneNumber,
            address: item.address,
            transactionsCount: item.transactionsCount,
          }))}
          actions={{
            get: true,
          }}
          path="suppliers"
        />
      )}
    </>
  );
};

export default SupplierList;
