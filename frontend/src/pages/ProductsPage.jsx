import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link } from "react-router-dom";
import {
  fetchProducts,
  deleteProduct,
} from "../features/products/productsSlice";
import { fetchCategories } from "../features/categories/categoriesSlice";
import { FaSearch, FaPlus, FaEye, FaTrash, FaEdit } from "react-icons/fa";

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
        <h1 className="text-2xl font-semibold">Products</h1>
        <Link
          to="/products/new"
          className="flex items-center bg-pink-500 hover:bg-pink-600 text-white px-4 py-2 rounded-lg transition"
        >
          <FaPlus className="mr-2" /> New Product
        </Link>
      </div>
      <form
        onSubmit={handleSearch}
        className="flex flex-wrap gap-4 items-center"
      >
        <div className="flex border-gray-300 items-center border rounded-lg px-3 py-2">
          <FaSearch className="text-gray-500 mr-2" />
          <input
            type="text"
            placeholder="Search by name..."
            className="focus:outline-none"
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
          />
        </div>
        <select
          className="border border-gray-300 rounded-lg px-3 py-2"
          value={categoryFilter}
          onChange={(e) => setCategoryFilter(e.target.value)}
        >
          <option value="">All Categories</option>
          {categories.map((cat) => (
            <option key={cat.categoryId} value={cat.categoryId}>
              {cat.name}
            </option>
          ))}
        </select>
      </form>
      {status === "loading" || status === "idle" ? (
        <p>Loading...</p>
      ) : status === "failed" ? (
        <p className="text-red-500">Error: {error}</p>
      ) : (
        <div className="bg-white rounded-lg shadow overflow-auto">
          <div className="hidden sm:grid grid-cols-6 gap-4 p-4 bg-gray-50 text-xs font-medium text-gray-500 uppercase tracking-wider">
            <div>Name</div>
            <div>Category</div>
            <div className="text-right">Price</div>
            <div className="text-right">In Stock</div>
            <div className="text-right">Transactions</div>
            <div className="text-center">Actions</div>
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
                <div className="text-sm text-gray-700 text-right">
                  {product.inventoryCount}
                </div>
                <div className="text-sm text-gray-700 text-right">
                  {product.transactionsCount}
                </div>
                <div className="flex justify-center space-x-4 text-gray-600">
                  <Link to={`/products/${product.productId}`}>
                    <FaEye />
                  </Link>
                  <Link to={`/products/${product.productId}/edit`}>
                    <FaEdit />
                  </Link>
                  <button onClick={() => handleDelete(product.productId)}>
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
