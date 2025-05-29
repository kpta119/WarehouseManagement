import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link } from "react-router-dom";
import {
  fetchProducts,
  deleteProduct,
} from "../../features/products/productsSlice";
import { fetchCategories } from "../../features/categories/categoriesSlice";
import { FaEye, FaTrash, FaEdit } from "react-icons/fa";
import { currencyFormatter, numberFormatter } from "../../utils/helpers";
import Pagination from "../helper/Pagination";
import NumberInput from "../helper/NumberInput";
import TextInput from "../helper/TextInput";
import SelectInput from "../helper/SelectInput";
import Spinner from "../helper/Spinner";
import useDebounce from "../../hooks/useDebounce";

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
        page: page || 1,
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
  console.log(products);
  return (
    <>
      <form className="flex space-x-4 justify-between">
        <div className="flex flex-wrap gap-4 items-end">
          <TextInput
            label="Nazwa"
            placeholder="Wyszukaj po nazwie..."
            value={searchTerm}
            setValue={setSearchTerm}
          />
          <SelectInput
            label="Kategoria"
            value={categoryFilter}
            setValue={setCategoryFilter}
          >
            <option value="">Wszystkie Kategorie</option>
            {categories.map((cat) => (
              <option key={cat.categoryId} value={cat.categoryId}>
                {cat.name}
              </option>
            ))}
          </SelectInput>
          <NumberInput
            label="Cena (min)"
            placeholder="Wybierz cenę..."
            isMinus={true}
            value={minPrice}
            setValue={setMinPrice}
          />
          <NumberInput
            label="Cena (max)"
            placeholder="Wybierz cenę..."
            isMinus={false}
            value={maxPrice}
            setValue={setMaxPrice}
          />
          <NumberInput
            label="Wielkość (min)"
            placeholder="Wybierz wielkość..."
            isMinus={true}
            value={minSize}
            setValue={setMinSize}
          />
          <NumberInput
            label="Wielkość (max)"
            placeholder="Wybierz wielkość..."
            isMinus={false}
            value={maxSize}
            setValue={setMaxSize}
          />
          <NumberInput
            label="Stan (min)"
            placeholder="Wybierz stan..."
            isMinus={true}
            value={minInventory}
            setValue={setMinInventory}
          />
          <NumberInput
            label="Stan (max)"
            placeholder="Wybierz stan..."
            isMinus={false}
            value={maxInventory}
            setValue={setMaxInventory}
          />
          <NumberInput
            label="Transakcje (min)"
            placeholder="Wybierz transakcje..."
            isMinus={true}
            value={minTransactions}
            setValue={setMinTransactions}
          />
          <NumberInput
            label="Transakcje (max)"
            placeholder="Wybierz transakcje..."
            isMinus={false}
            value={maxTransactions}
            setValue={setMaxTransactions}
          />
        </div>
        <SelectInput
          label="Sortowanie"
          value={sortOption}
          setValue={setSortOption}
        >
          <option value="">Sortuj przez</option>
          <option value="name">Nazwa (od A do Z)</option>
          <option value="name-reverse">Nazwa (od Z do A)</option>
          <option value="capacity">Pojemność (rosnąco)</option>
          <option value="capacity-reverse">Pojemność (malejąco)</option>
          <option value="occupied">Zajęte (rosnąco)</option>
          <option value="occupied-reverse">Zajęte (malejąco)</option>
          <option value="address">Adres (od A do Z)</option>
          <option value="address-reverse">Adres (od Z do A)</option>
          <option value="employees">Pracownicy (rosnąco)</option>
          <option value="employees-reverse">Pracownicy (malejąco)</option>
          <option value="products">Produkty (rosnąco)</option>
          <option value="products-reverse">Produkty (malejąco)</option>
          <option value="transactions">Transakcje (rosnąco)</option>
          <option value="transactions-reverse">Transakcje (malejąco)</option>
        </SelectInput>
      </form>
      {status === "loading" || status === "idle" ? (
        <Spinner />
      ) : status === "failed" ? (
        <p className="text-red-500">Błąd: {error}</p>
      ) : filtered.length === 0 ? (
        <p className="text-red-500">Nie znaleziono produktu</p>
      ) : (
        <>
          <Pagination
            currentPage={page}
            totalPages={totalPages}
            onPageChange={setPage}
          />
          <div className="bg-white rounded-lg shadow overflow-auto">
            <div className="hidden sm:grid grid-cols-8 gap-4 p-4 bg-gray-50 text-xs font-medium text-gray-500 uppercase tracking-wider">
              <div>Nazwa</div>
              <div>Opis</div>
              <div>Kategoria</div>
              <div className="text-right">Cena</div>
              <div className="text-right">Wielkość</div>
              <div className="text-right">Stan</div>
              <div className="text-right">Transakcje</div>
              <div className="text-center">Akcje</div>
            </div>
            <div className="divide-y divide-gray-200">
              {filtered.map((product) => (
                <div
                  key={product.productId}
                  className="grid grid-cols-1 sm:grid-cols-8 items-center gap-4 p-4 hover:bg-pink-50 transition-colors duration-200"
                >
                  <div>
                    <Link
                      to={`/products/${product.productId}`}
                      className="text-pink-600 hover:underline font-medium"
                    >
                      {product.name}
                    </Link>
                  </div>
                  <div className="text-sm text-gray-700 truncate">
                    {product.description}
                  </div>
                  <div className="text-sm text-gray-700">
                    {product.categoryName}
                  </div>
                  <div className="text-sm text-gray-700 text-right">
                    {currencyFormatter(product.unitPrice)}
                  </div>
                  <div className="text-sm text-gray-700 text-right">
                    {numberFormatter(product.unitSize)}
                  </div>
                  <div className="text-sm text-gray-700 flex items-center justify-end gap-4">
                    {product.inventoryCount < 5 && (
                      <div className="bg-orange-500 px-4 rounded text-white">
                        Niski Stan!
                      </div>
                    )}
                    {numberFormatter(product.inventoryCount)}
                  </div>
                  <div className="text-sm text-gray-700 flex items-center justify-end gap-4">
                    {product.isBestseller && (
                      <div className="bg-green-500 px-4 rounded text-white">
                        Bestseller!
                      </div>
                    )}
                    {numberFormatter(product.transactionCount)}
                  </div>
                  <div className="flex justify-center space-x-4 text-gray-600">
                    <Link
                      to={`/products/${product.productId}`}
                      className="hover:text-pink-500 transition duration-200"
                    >
                      <FaEye />
                    </Link>
                    <Link
                      to={`/products/${product.productId}/edit`}
                      className="hover:text-pink-500 transition duration-200"
                    >
                      <FaEdit />
                    </Link>
                    <button
                      onClick={() => handleDelete(product.productId)}
                      className="hover:text-pink-500 transition duration-200 cursor-pointer"
                    >
                      <FaTrash />
                    </button>
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

export default ProductList;
