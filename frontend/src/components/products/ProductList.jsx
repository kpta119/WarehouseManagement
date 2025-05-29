import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import {
  fetchProducts,
  deleteProduct,
} from "../../features/products/productsSlice";
import { fetchCategories } from "../../features/categories/categoriesSlice";
import Spinner from "../helper/Spinner";
import useDebounce from "../../hooks/useDebounce";
import ItemsList from "../Layout/ItemsList";
import FormList from "../Layout/FormList";

const ProductList = () => {
  const dispatch = useDispatch();
  const {
    list: products,
    status,
    error,
  } = useSelector((state) => state.products);
  const { list: categories } = useSelector((state) => state.categories);
  const selectedWarehouse = useSelector((state) => state.selectedWarehouse);
  const [searchTerm, setSearchTerm] = useState("");
  const [categoryFilter, setCategoryFilter] = useState("");
  const [minPrice, setMinPrice] = useState("");
  const [maxPrice, setMaxPrice] = useState("");
  const [minSize, setMinSize] = useState("");
  const [maxSize, setMaxSize] = useState("");
  const [minInventory, setMinInventory] = useState("");
  const [maxInventory, setMaxInventory] = useState("");
  const [minTransactions, setMinTransactions] = useState("");
  const [maxTransactions, setMaxTransactions] = useState("");
  const debouncedSearchTerm = useDebounce(searchTerm);
  const debouncedMinPrice = useDebounce(minPrice);
  const debouncedMaxPrice = useDebounce(maxPrice);
  const debouncedMinSize = useDebounce(minSize);
  const debouncedMaxSize = useDebounce(maxSize);
  const debouncedMinInventory = useDebounce(minInventory);
  const debouncedMaxInventory = useDebounce(maxInventory);
  const debouncedMinTransactions = useDebounce(minTransactions);
  const debouncedMaxTransactions = useDebounce(maxTransactions);
  const [page, setPage] = useState(1);
  const [sortOption, setSortOption] = useState("");
  useEffect(() => {
    dispatch(fetchCategories());
  }, [dispatch]);
  useEffect(() => {
    dispatch(
      fetchProducts({
        name: debouncedSearchTerm || undefined,
        categoryId: categoryFilter || undefined,
        minPrice: debouncedMinPrice ? parseFloat(debouncedMinPrice) : undefined,
        maxPrice: debouncedMaxPrice ? parseFloat(debouncedMaxPrice) : undefined,
        minSize: debouncedMinSize ? parseFloat(debouncedMinSize) : undefined,
        maxSize: debouncedMaxSize ? parseFloat(debouncedMaxSize) : undefined,
        warehouseId: selectedWarehouse || undefined,
        minInventory: debouncedMinInventory
          ? parseInt(debouncedMinInventory)
          : undefined,
        maxInventory: debouncedMaxInventory
          ? parseInt(debouncedMaxInventory)
          : undefined,
        minTransactions: debouncedMinTransactions
          ? parseInt(debouncedMinTransactions)
          : undefined,
        maxTransactions: debouncedMaxTransactions
          ? parseInt(debouncedMaxTransactions)
          : undefined,
        page: page - 1 || 0,
      })
    );
  }, [
    dispatch,
    debouncedSearchTerm,
    categoryFilter,
    selectedWarehouse,
    debouncedMinPrice,
    debouncedMaxPrice,
    debouncedMinSize,
    debouncedMaxSize,
    debouncedMinInventory,
    debouncedMaxInventory,
    debouncedMinTransactions,
    debouncedMaxTransactions,
    page,
  ]);
  const filtered = [...products].sort((a, b) => {
    switch (sortOption) {
      case "name":
        return a.name.localeCompare(b.name);
      case "name-reverse":
        return b.name.localeCompare(a.name);
      case "description":
        return a.description.localeCompare(b.description);
      case "description-reverse":
        return b.description.localeCompare(a.description);
      case "category":
        return a.categoryName.localeCompare(b.categoryName);
      case "category-reverse":
        return b.categoryName.localeCompare(a.categoryName);
      case "price":
        return a.unitPrice - b.unitPrice;
      case "price-reverse":
        return b.unitPrice - a.unitPrice;
      case "size":
        return a.unitSize - b.unitSize;
      case "size-reverse":
        return b.unitSize - a.unitSize;
      case "inventory":
        return a.inventoryCount - b.inventoryCount;
      case "inventory-reverse":
        return b.inventoryCount - a.inventoryCount;
      case "transactions":
        return a.transactionCount - b.transactionCount;
      case "transactions-reverse":
        return b.transactionCount - a.transactionCount;
      default:
        return 0;
    }
  });
  const handleDelete = (id) => {
    if (window.confirm("Are you sure you want to delete this product?")) {
      dispatch(deleteProduct(id));
    }
  };
  const totalPages = 10;
  return (
    <>
      <FormList
        inputs={[
          {
            type: "text",
            label: "Nazwa",
            placeholder: "Wyszukaj po nazwie...",
            value: searchTerm,
            setValue: setSearchTerm,
          },
          {
            type: "select",
            label: "Kategoria",
            value: categoryFilter,
            setValue: setCategoryFilter,
            options: categories.map((cat) => ({
              value: cat.categoryId,
              label: cat.name,
            })),
          },
          {
            type: "number",
            label: "Cena (min)",
            placeholder: "Wybierz cenę...",
            isMinus: true,
            value: minPrice,
            setValue: setMinPrice,
          },
          {
            type: "number",
            label: "Cena (max)",
            placeholder: "Wybierz cenę...",
            isMinus: false,
            value: maxPrice,
            setValue: setMaxPrice,
          },
          {
            type: "number",
            label: "Wielkość (min)",
            placeholder: "Wybierz wielkość...",
            isMinus: true,
            value: minSize,
            setValue: setMinSize,
          },
          {
            type: "number",
            label: "Wielkość (max)",
            placeholder: "Wybierz wielkość...",
            isMinus: false,
            value: maxSize,
            setValue: setMaxSize,
          },
          {
            type: "number",
            label: "Stan (min)",
            placeholder: "Wybierz stan...",
            isMinus: true,
            value: minInventory,
            setValue: setMinInventory,
          },
          {
            type: "number",
            label: "Stan (max)",
            placeholder: "Wybierz stan...",
            isMinus: false,
            value: maxInventory,
            setValue: setMaxInventory,
          },
          {
            type: "number",
            label: "Transakcje (min)",
            placeholder: "Wybierz transacje...",
            isMinus: true,
            value: minTransactions,
            setValue: setMinTransactions,
          },
          {
            type: "number",
            label: "Transakcje (max)",
            placeholder: "Wybierz transacje...",
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
            { value: "capacity", label: "Pojemność (rosnąco)" },
            { value: "capacity-reverse", label: "Pojemność (malejąco)" },
            { value: "occupied", label: "Zajęte (rosnąco)" },
            { value: "occupied-reverse", label: "Zajęte (malejąco)" },
            { value: "address", label: "Adres (od A do Z)" },
            { value: "address-reverse", label: "Adres (od Z do A)" },
            { value: "employees", label: "Pracownicy (rosnąco)" },
            { value: "employees-reverse", label: "Pracownicy (malejąco)" },
            { value: "products", label: "Produkty (rosnąco)" },
            { value: "products-reverse", label: "Produkty (malejąco)" },
            { value: "transactions", label: "Transakcje (rosnąco)" },
            { value: "transactions-reverse", label: "Transakcje (malejąco)" },
          ],
        }}
      />
      {status === "loading" || status === "idle" ? (
        <Spinner />
      ) : status === "failed" ? (
        <p className="text-red-500">Błąd: {error}</p>
      ) : filtered.length === 0 ? (
        <p className="text-red-500">Nie znaleziono produktu</p>
      ) : (
        <ItemsList
          pagination={{ page, setPage, totalPages }}
          labels={[
            { name: "Nazwa", type: "Link" },
            { name: "Opis", type: "Text-Long" },
            { name: "Kategoria", type: "Text" },
            { name: "Cena", type: "Currency", className: "text-right" },
            { name: "Wielkość", type: "Number", className: "text-right" },
            { name: "Stan", type: "Number", className: "text-right" },
            { name: "Transakcje", type: "Number", className: "text-right" },
          ]}
          data={filtered.map((item) => ({
            id: item.productId,
            name: item.name,
            description: item.description,
            categoryName: item.categoryName,
            unitPrice: item.unitPrice,
            unitSize: item.unitSize,
            inventoryCount: item.inventoryCount,
            transactionCount: item.transactionCount,
          }))}
          actions={{
            get: true,
            put: true,
            delete: true,
          }}
          path="products"
          handleDelete={handleDelete}
        />
      )}
    </>
  );
};

export default ProductList;
