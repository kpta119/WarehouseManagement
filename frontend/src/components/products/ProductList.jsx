import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link } from "react-router-dom";
import {
  fetchProducts,
  deleteProduct,
} from "../../features/products/productsSlice";
import { fetchCategories } from "../../features/categories/categoriesSlice";
import {
  FaSearch,
  FaPlus,
  FaEye,
  FaTrash,
  FaEdit,
  FaMinus,
  FaChevronDown,
} from "react-icons/fa";

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
  const [sortOption, setSortOption] = useState("");
  useEffect(() => {
    dispatch(fetchCategories());
  }, [dispatch]);
  useEffect(() => {
    dispatch(
      fetchProducts({
        name: searchTerm || undefined,
        categoryId: categoryFilter || undefined,
        minPrice: minPrice ? parseFloat(minPrice) : undefined,
        maxPrice: maxPrice ? parseFloat(maxPrice) : undefined,
        minSize: minSize ? parseFloat(minSize) : undefined,
        maxSize: maxSize ? parseFloat(maxSize) : undefined,
        warehouseId: selectedWarehouse || undefined,
      })
    );
  }, [
    dispatch,
    searchTerm,
    categoryFilter,
    selectedWarehouse,
    minPrice,
    maxPrice,
    minSize,
    maxSize,
  ]);
  const filtered = products
    .filter((p) => p.name.toLowerCase().includes(searchTerm.toLowerCase()))
    .sort((a, b) => {
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
  return (
    <>
      <form className="flex space-x-4 justify-between">
        <div className="flex flex-wrap gap-4 items-end">
          <div>
            <label className="block text-sm font-medium">Nazwa</label>
            <div className="flex border-gray-300 items-center border rounded-lg px-3 py-2 focus-within:ring-1 focus-within:ring-pink-500 focus-within:border-pink-500 transition-colors duration-300">
              <FaSearch className="text-gray-500 mr-2" />
              <input
                type="text"
                placeholder="Wyszukaj po nazwie..."
                className="focus:outline-none"
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
              />
            </div>
          </div>
          <div>
            <label className="block text-sm font-medium">Kategoria</label>
            <div className="relative">
              <select
                className="border border-gray-300 rounded-lg px-3 py-2 pr-12 appearance-none focus:outline-none focus:ring-1 focus:ring-pink-500 focus:border-pink-500 transition-colors duration-300"
                value={categoryFilter}
                onChange={(e) => setCategoryFilter(e.target.value)}
              >
                <option value="">Wszystkie Kategorie</option>
                {categories.map((cat) => (
                  <option key={cat.categoryId} value={cat.categoryId}>
                    {cat.name}
                  </option>
                ))}
              </select>
              <FaChevronDown className="pointer-events-none absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-400" />
            </div>
          </div>
          <div>
            <label className="block text-sm font-medium">Minimalna Cena</label>
            <div className="flex border border-gray-300 rounded-lg items-center px-3 py-2 focus-within:ring-1 focus-within:ring-pink-500 focus-within:border-pink-500 transition-colors duration-300">
              <FaMinus className="text-gray-500 mr-2" />
              <input
                type="number"
                min="0"
                placeholder="Wybierz cenę..."
                className="w-full focus:outline-none"
                value={minPrice}
                onChange={(e) => setMinPrice(e.target.value)}
              />
            </div>
          </div>
          <div>
            <label className="block text-sm font-medium">Maksymalna Cena</label>
            <div className="flex border border-gray-300 rounded-lg items-center px-3 py-2 focus-within:ring-1 focus-within:ring-pink-500 focus-within:border-pink-500 transition-colors duration-300">
              <FaPlus className="text-gray-500 mr-2" />
              <input
                type="number"
                min="0"
                placeholder="Wybierz cenę..."
                className="w-full focus:outline-none"
                value={maxPrice}
                onChange={(e) => setMaxPrice(e.target.value)}
              />
            </div>
          </div>
          <div>
            <label className="block text-sm font-medium">
              Minimalna Wielkość
            </label>
            <div className="flex border border-gray-300 rounded-lg items-center px-3 py-2 focus-within:ring-1 focus-within:ring-pink-500 focus-within:border-pink-500 transition-colors duration-300">
              <FaPlus className="text-gray-500 mr-2" />
              <input
                type="number"
                min="0"
                placeholder="Wybierz wielkość..."
                className="w-full focus:outline-none"
                value={minSize}
                onChange={(e) => setMinSize(e.target.value)}
              />
            </div>
          </div>
          <div>
            <label className="block text-sm font-medium">
              Maksymalna Wielkość
            </label>
            <div className="flex border border-gray-300 rounded-lg items-center px-3 py-2 focus-within:ring-1 focus-within:ring-pink-500 focus-within:border-pink-500 transition-colors duration-300">
              <FaPlus className="text-gray-500 mr-2" />
              <input
                type="number"
                min="0"
                placeholder="Wybierz wielkość..."
                className="w-full focus:outline-none"
                value={maxSize}
                onChange={(e) => setMaxSize(e.target.value)}
              />
            </div>
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
              <option value="name">Nazwa (od A do Z)</option>
              <option value="name-reverse">Nazwa (od Z do A)</option>
              <option value="description">Opis (od A do Z)</option>
              <option value="description-reverse">Opis (od Z do A)</option>
              <option value="category">Kategoria (od A do Z)</option>
              <option value="category-reverse">Kategoria (od Z do A)</option>
              <option value="price">Cena (rosnąco)</option>
              <option value="price-reverse">Cena (malejąco)</option>
              <option value="size">Wielkość (rosnąco)</option>
              <option value="size-reverse">Wielkość (malejąco)</option>
              <option value="inventory">Stan (rosnąco)</option>
              <option value="inventory-reverse">Stan (malejąco)</option>
              <option value="transactions">Transakcje (rosnąco)</option>
              <option value="transactions-reverse">
                Transakcje (malejąco)
              </option>
            </select>
            <FaChevronDown className="pointer-events-none absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-400" />
          </div>
        </div>
      </form>
      {status === "loading" || status === "idle" ? (
        <p>Loading...</p>
      ) : status === "failed" ? (
        <p className="text-red-500">Error: {error}</p>
      ) : (
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
                className="grid grid-cols-1 sm:grid-cols-8 items-center gap-4 p-4 hover:bg-pink-50 transition-colors"
              >
                <div>
                  <Link
                    to={`/products/${product.productId}`}
                    className="text-pink-600 hover:underline font-medium"
                  >
                    {product.name}
                  </Link>
                </div>
                <div className="text-sm text-gray-700">
                  {product.description}
                </div>
                <div className="text-sm text-gray-700">
                  {product.categoryName}
                </div>
                <div className="text-sm text-gray-700 text-right">
                  ${product.unitPrice.toFixed(2)}
                </div>
                <div className="text-sm text-gray-700 text-right">
                  {product.unitSize}
                </div>
                <div className="text-sm text-gray-700 flex items-center justify-end gap-4">
                  {product.inventoryCount < 5 && (
                    <div className="bg-orange-500 px-4 rounded text-white">
                      Niski Stan!
                    </div>
                  )}
                  {product.inventoryCount}
                </div>
                <div className="text-sm text-gray-700 flex items-center justify-end gap-4">
                  {product.productId < 3 && (
                    <div className="bg-green-500 px-4 rounded text-white">
                      Bestseller!
                    </div>
                  )}
                  {product.transactionCount}
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
      )}
    </>
  );
};

export default ProductList;
