import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link } from "react-router-dom";
import {
  fetchProducts,
  deleteProduct,
} from "../features/products/productsSlice";
import { fetchCategories } from "../features/categories/categoriesSlice";
import {
  FaSearch,
  FaPlus,
  FaEye,
  FaTrash,
  FaEdit,
  FaMinus,
  FaChevronDown,
} from "react-icons/fa";

const ProductsPage = () => {
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
  const [sortOption, setSortOption] = useState("");
  useEffect(() => {
    dispatch(fetchCategories());
  }, [dispatch]);
  useEffect(() => {
    dispatch(
      fetchProducts({
        name: searchTerm || undefined,
        categoryId: categoryFilter || undefined,
        warehouseId: selectedWarehouse || undefined,
      })
    );
  }, [dispatch, searchTerm, categoryFilter, selectedWarehouse]);
  const handleSearch = (e) => {
    e.preventDefault();
  };
  const handleDelete = (id) => {
    if (window.confirm("Are you sure you want to delete this product?")) {
      dispatch(deleteProduct(id));
    }
  };
  return (
    <div className="space-y-6">
      <div className="flex justify-between items-center">
        <h1 className="text-2xl font-semibold text-gray-800">
          Lista produktów
        </h1>
        <Link
          to="/products/new"
          className="flex items-center bg-pink-500 hover:bg-pink-600 text-white px-4 py-2 rounded-lg transition"
        >
          <FaPlus className="mr-2" /> Nowy produkt
        </Link>
      </div>
      <form onSubmit={handleSearch} className="flex space-x-4 justify-between">
        <div className="flex flex-wrap gap-4">
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
          <div className="flex border border-gray-300 rounded-lg items-center px-3 py-2 focus-within:ring-1 focus-within:ring-pink-500 focus-within:border-pink-500 transition-colors duration-300">
            <FaMinus className="text-gray-500 mr-2" />
            <input
              type="number"
              min="0"
              placeholder="Min. Cena"
              className="w-full focus:outline-none"
              value={minPrice}
              onChange={(e) => setMinPrice(e.target.value)}
            />
          </div>
          <div className="flex border border-gray-300 rounded-lg items-center px-3 py-2 focus-within:ring-1 focus-within:ring-pink-500 focus-within:border-pink-500 transition-colors duration-300">
            <FaPlus className="text-gray-500 mr-2" />
            <input
              type="number"
              min="0"
              placeholder="Max. Cena"
              className="w-full focus:outline-none"
              value={maxPrice}
              onChange={(e) => setMaxPrice(e.target.value)}
            />
          </div>
          <button
            type="submit"
            className="bg-pink-500 hover:bg-pink-600 text-white px-8 py-2 rounded-lg transition flex items-center"
          >
            <FaSearch className="mr-2" /> Wyszukaj
          </button>
        </div>
        <div className="relative">
          <select
            className="border appearance-none border-gray-300 rounded-lg px-3 py-2 pr-12 focus:outline-none focus:ring-1 focus:ring-pink-500 focus:border-pink-500 transition-colors duration-300"
            value={sortOption}
            onChange={(e) => setSortOption(e.target.value)}
          >
            <option value="">Sortuj przez</option>
            <option value="name">Nazwa (od A do Z)</option>
            <option value="name-reverse">Nazwa (od Z do A)</option>
            <option value="category">Kategoria (od A do Z)</option>
            <option value="category-reverse">Kategoria (od Z do A)</option>
            <option value="price">Cena (rosnąco)</option>
            <option value="price-reverse">Cena (malejąco)</option>
            <option value="inventory">Stan (rosnąco)</option>
            <option value="inventory-reverse">Stan (malejąco)</option>
            <option value="transactions">Transakcje (rosnąco)</option>
            <option value="transactions-reverse">Transakcje (malejąco)</option>
          </select>
          <FaChevronDown className="pointer-events-none absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-400" />
        </div>
      </form>
      {status === "loading" || status === "idle" ? (
        <p>Loading...</p>
      ) : status === "failed" ? (
        <p className="text-red-500">Error: {error}</p>
      ) : (
        <div className="bg-white rounded-lg shadow overflow-auto">
          <div className="hidden sm:grid grid-cols-6 gap-4 p-4 bg-gray-50 text-xs font-medium text-gray-500 uppercase tracking-wider">
            <div>Nazwa</div>
            <div>Kategoria</div>
            <div className="text-right">Cena</div>
            <div className="text-right">Stan</div>
            <div className="text-right">Transakcje</div>
            <div className="text-center">Akcje</div>
          </div>
          <div className="divide-y divide-gray-200">
            {products.map((product) => (
              <div
                key={product.productId}
                className="grid grid-cols-1 sm:grid-cols-6 items-center gap-4 p-4 hover:bg-pink-50 transition-colors"
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
                  {product.categoryName}
                </div>
                <div className="text-sm text-gray-700 text-right">
                  ${product.unitPrice.toFixed(2)}
                </div>
                <div className="text-sm text-gray-700 flex items-center justify-end gap-4">
                  {product.inventoryCount < 5 && (
                    <div className="bg-orange-500 px-4 rounded text-white">
                      Low Stock!
                    </div>
                  )}
                  {product.inventoryCount}
                </div>
                <div className="text-sm text-gray-700 flex items-center justify-end gap-4">
                  {product.productId < 3 && (
                    <div className="bg-green-500 px-4 rounded text-white">
                      Best Selling!
                    </div>
                  )}
                  {product.transactionsCount}
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
    </div>
  );
};

export default ProductsPage;
