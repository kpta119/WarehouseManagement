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
  const { list: data, status, error } = useSelector((state) => state.products);
  const { list: dataProducts } = useSelector((state) => state.categories);
  const { content: categories } = dataProducts;
  const { content: products, page: pageInfo } = data;
  const { totalPages } = pageInfo;
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
    setPage(1);
  }, [
    debouncedSearchTerm,
    categoryFilter,
    debouncedMinPrice,
    debouncedMaxPrice,
    debouncedMinSize,
    debouncedMaxSize,
    debouncedMinInventory,
    debouncedMaxInventory,
    debouncedMinTransactions,
    debouncedMaxTransactions,
    selectedWarehouse,
  ]);
  useEffect(() => {
    dispatch(fetchCategories({ all: true }));
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
  return (
    <>
      <FormList
        inputs={[
          {
            type: "text",
            label: "Name",
            placeholder: "Search by Name...",
            value: searchTerm,
            setValue: setSearchTerm,
          },
          {
            type: "select",
            label: "Category",
            value: categoryFilter,
            setValue: setCategoryFilter,
            options: categories.map((cat) => ({
              value: cat.categoryId,
              label: cat.name,
            })),
          },
          {
            type: "number",
            label: "Price (min)",
            placeholder: "Choose price...",
            isMinus: true,
            value: minPrice,
            setValue: setMinPrice,
          },
          {
            type: "number",
            label: "Price (max)",
            placeholder: "Choose price...",
            isMinus: false,
            value: maxPrice,
            setValue: setMaxPrice,
          },
          {
            type: "number",
            label: "Size (min)",
            placeholder: "Choose amount...",
            isMinus: true,
            value: minSize,
            setValue: setMinSize,
          },
          {
            type: "number",
            label: "Size (max)",
            placeholder: "Choose amount...",
            isMinus: false,
            value: maxSize,
            setValue: setMaxSize,
          },
          {
            type: "number",
            label: "Stock (min)",
            placeholder: "Choose amount...",
            isMinus: true,
            value: minInventory,
            setValue: setMinInventory,
          },
          {
            type: "number",
            label: "Stock (max)",
            placeholder: "Choose amount...",
            isMinus: false,
            value: maxInventory,
            setValue: setMaxInventory,
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
            { value: "category", label: "Category (from A to Z)" },
            { value: "category-reverse", label: "Category (from Z to A)" },
            { value: "price", label: "Price (asc)" },
            { value: "price-reverse", label: "Price (desc)" },
            { value: "size", label: "Size (asc)" },
            { value: "size-reverse", label: "Size (desc)" },
            { value: "inventory", label: "Stock (asc)" },
            { value: "inventory-reverse", label: "Stock (desc)" },
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
        <p className="text-red-500">No Products found</p>
      ) : (
        <>
          {error && <p className="text-red-500">Error: {error}</p>}
          <ItemsList
            pagination={{ page, setPage, totalPages }}
            labels={[
              { name: "Name", type: "Link" },
              { name: "Description", type: "Text-Long" },
              { name: "Category", type: "Text" },
              { name: "Price", type: "Currency", className: "text-right" },
              { name: "Size", type: "Number", className: "text-right" },
              { name: "Stock", type: "Number", className: "text-right" },
              { name: "Transactions", type: "Number", className: "text-right" },
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
              extra: {
                bestSelling: item.bestSelling,
                lowStock: item.lowStock,
              },
            }))}
            actions={{
              get: true,
              put: true,
              delete: true,
            }}
            path="products"
            handleDelete={handleDelete}
            extra={{
              6: {
                key: "bestSelling",
                label: "Bestseller!",
                classes: "bg-green-500 text-white",
              },
              5: {
                key: "lowStock",
                label: "Low Stock!",
                classes: "bg-orange-500 text-white",
              },
            }}
          />
        </>
      )}
    </>
  );
};

export default ProductList;
