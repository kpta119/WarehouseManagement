import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate, useParams } from "react-router-dom";
import {
  fetchProductById,
  createProduct,
  updateProduct,
} from "../features/products/productsSlice";
import { fetchCategories } from "../features/categories/categoriesSlice";

const ProductFormPage = () => {
  const { id } = useParams();
  const isEdit = Boolean(id);
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const {
    current: product,
    status: prodStatus,
    error: prodError,
  } = useSelector((state) => state.products);
  const { list: categories } = useSelector((state) => state.categories);
  const [form, setForm] = useState({
    name: "",
    description: "",
    unitPrice: "",
    unitSize: "",
    categoryId: "",
  });
  useEffect(() => {
    dispatch(fetchCategories());
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
  const handleSubmit = (e) => {
    e.preventDefault();
    const payload = {
      ...form,
      unitPrice: parseFloat(form.unitPrice),
      unitSize: parseFloat(form.unitSize),
      categoryId: parseInt(form.categoryId, 10),
    };
    if (isEdit) {
      dispatch(updateProduct({ id: parseInt(id, 10), data: payload })).then(
        () => navigate("/products")
      );
    } else {
      dispatch(createProduct(payload)).then(() => navigate("/products"));
    }
  };
  if ((isEdit && prodStatus === "loading") || prodStatus === "idle") {
    return <p>Ładowanie...</p>;
  }
  return (
    <div className="max-w-lg mx-auto bg-white p-6 rounded-lg shadow">
      <h1 className="text-2xl font-semibold mb-4">
        {isEdit ? "Edytuj Produkt" : "Nowy Produkt"}
      </h1>
      <form onSubmit={handleSubmit} className="space-y-4">
        <div>
          <label htmlFor="name" className="block text-sm font-medium">
            Nazwa
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
            Opis
          </label>
          <textarea
            id="description"
            name="description"
            value={form.description}
            onChange={handleChange}
            className="w-full border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-1 focus:ring-pink-500 focus:border-pink-500 transition-colors duration-300"
          />
        </div>
        <div className="grid grid-cols-2 gap-4">
          <div>
            <label htmlFor="unitPrice" className="block text-sm font-medium">
              Cena jednostkowa
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
              Rozmiar jednostkowy
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
            Kategoria
          </label>
          <select
            id="categoryId"
            name="categoryId"
            value={form.categoryId}
            onChange={handleChange}
            className="w-full border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-1 focus:ring-pink-500 focus:border-pink-500 transition-colors duration-300"
            required
          >
            <option value="">Wybierz kategorię</option>
            {categories.map((cat) => (
              <option key={cat.categoryId} value={cat.categoryId}>
                {cat.name}
              </option>
            ))}
          </select>
        </div>
        {prodError && <p className="text-red-500">{prodError}</p>}
        <button
          type="submit"
          className="w-full py-3 bg-pink-500 hover:bg-pink-600 text-white rounded-lg transition cursor-pointer"
        >
          {isEdit ? "Aktualizuj Produkt" : "Stwórz Produkt"}
        </button>
      </form>
    </div>
  );
};

export default ProductFormPage;
