import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link, useNavigate, useParams } from "react-router-dom";
import {
  fetchProductById,
  createProduct,
  updateProduct,
} from "../../features/products/productsSlice";
import { fetchCategories } from "../../features/categories/categoriesSlice";
import { FaChevronDown, FaChevronLeft } from "react-icons/fa";
import Spinner from "../helper/Spinner";

const ProductForm = () => {
  const { id } = useParams();
  const isEdit = Boolean(id);
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const {
    current: product,
    status: prodStatus,
    error: prodError,
    formStatus,
  } = useSelector((state) => state.products);
  const { list: dataCategories } = useSelector((state) => state.categories);
  const { content: categories } = dataCategories;
  const [form, setForm] = useState({
    name: "",
    description: "",
    unitPrice: "",
    unitSize: "",
    categoryId: "",
  });
  useEffect(() => {
    dispatch(fetchCategories({ all: true }));
    if (isEdit) dispatch(fetchProductById(id));
  }, [dispatch, id, isEdit]);
  useEffect(() => {
    if (isEdit && product) {
      setForm({
        name: product.name || "",
        description: product.description || "",
        unitPrice: product.unitPrice?.toString() || "",
        unitSize: product.unitSize?.toString() || "",
        categoryId: product.categoryId?.toString() || "",
      });
    }
  }, [product, isEdit]);
  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm((f) => ({ ...f, [name]: value }));
  };
  const handleSubmit = async (e) => {
    e.preventDefault();
    const payload = {
      ...form,
      unitPrice: parseFloat(form.unitPrice),
      unitSize: parseFloat(form.unitSize),
      categoryId: parseInt(form.categoryId, 10),
    };
    let result;
    if (isEdit) {
      result = await dispatch(
        updateProduct({ id: parseInt(id, 10), data: payload })
      );
    } else {
      result = await dispatch(createProduct(payload));
    }
    if (result.meta.requestStatus === "fulfilled") {
      navigate("/products");
    }
  };
  if ((isEdit && prodStatus === "loading") || prodStatus === "idle") {
    return <Spinner />;
  }
  if (prodStatus === "failed") {
    return <p className="text-red-500">Error: {prodError}</p>;
  }
  if (isEdit && !product) {
    return <p className="text-red-500">Product not found.</p>;
  }
  return (
    <div className="max-w-lg mx-auto bg-white p-6 rounded-lg shadow">
      <Link
        to="/products"
        className="flex items-center text-gray-600 hover:text-pink-500 mb-6 transition duration-200"
      >
        <FaChevronLeft className="inline mr-2" /> Return to Products
      </Link>
      <h1 className="text-2xl font-semibold mb-4">
        {isEdit ? "Edit Product" : "New Product"}
      </h1>
      {formStatus === "failed" && (
        <p className="text-red-500 mb-4">Error: {prodError}</p>
      )}
      <form onSubmit={handleSubmit} className="space-y-4">
        <div>
          <label htmlFor="name" className="block text-sm font-medium">
            Name
          </label>
          <input
            id="name"
            name="name"
            value={form.name}
            onChange={handleChange}
            className="w-full border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-1 focus:ring-pink-500 focus:border-pink-500 transition-colors duration-300"
            required
          />
        </div>
        <div>
          <label htmlFor="description" className="block text-sm font-medium">
            Description
          </label>
          <textarea
            id="description"
            name="description"
            value={form.description}
            onChange={handleChange}
            required
            className="w-full border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-1 focus:ring-pink-500 focus:border-pink-500 transition-colors duration-300"
          />
        </div>
        <div className="grid grid-cols-2 gap-4">
          <div>
            <label htmlFor="unitPrice" className="block text-sm font-medium">
              Unit Price
            </label>
            <input
              id="unitPrice"
              name="unitPrice"
              type="number"
              step="0.01"
              value={form.unitPrice}
              onChange={handleChange}
              className="w-full border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-1 focus:ring-pink-500 focus:border-pink-500 transition-colors duration-300"
              required
            />
          </div>
          <div>
            <label htmlFor="unitSize" className="block text-sm font-medium">
              Unit Size
            </label>
            <input
              id="unitSize"
              name="unitSize"
              type="number"
              step="0.01"
              value={form.unitSize}
              onChange={handleChange}
              className="w-full border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-1 focus:ring-pink-500 focus:border-pink-500 transition-colors duration-300"
              required
            />
          </div>
        </div>
        <div>
          <label htmlFor="categoryId" className="block text-sm font-medium">
            Category
          </label>
          <div className="relative">
            <select
              id="categoryId"
              name="categoryId"
              value={form.categoryId}
              onChange={handleChange}
              className="w-full border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-1 focus:ring-pink-500 focus:border-pink-500 transition-colors duration-300 appearance-none"
              required
            >
              <option value="">Choose Category</option>
              {categories.map((cat) => (
                <option key={cat.categoryId} value={cat.categoryId}>
                  {cat.name}
                </option>
              ))}
            </select>
            <FaChevronDown className="pointer-events-none absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-400" />
          </div>
        </div>
        <button
          type="submit"
          className="w-full py-3 bg-pink-500 hover:bg-pink-600 text-white rounded-lg transition cursor-pointer duration-200"
        >
          {formStatus === "loading" ? (
            <Spinner color="white" />
          ) : isEdit ? (
            "Update Product"
          ) : (
            "Create Product"
          )}
        </button>
      </form>
    </div>
  );
};

export default ProductForm;
