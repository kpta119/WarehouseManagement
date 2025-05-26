import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link, useNavigate, useParams } from "react-router-dom";
import {
  fetchCategories,
  createCategory,
  updateCategory,
} from "../../features/categories/categoriesSlice";
import { FaChevronLeft } from "react-icons/fa";

const CategoryForm = () => {
  const { id } = useParams();
  const isEdit = Boolean(id);
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const {
    list: categories,
    status,
    error,
  } = useSelector((state) => state.categories);
  const [form, setForm] = useState({ name: "", description: "" });
  useEffect(() => {
    if (status === "idle") {
      dispatch(fetchCategories());
    }
  }, [dispatch, status]);
  useEffect(() => {
    if (isEdit && status === "succeeded") {
      const cat = categories.find((c) => String(c.categoryId) === String(id));
      if (cat) {
        setForm({ name: cat.name, description: cat.description });
      }
    }
  }, [isEdit, status, categories, id]);
  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm((f) => ({ ...f, [name]: value }));
  };
  const handleSubmit = async (e) => {
    e.preventDefault();
    const payload = { name: form.name, description: form.description };
    if (isEdit) {
      await dispatch(updateCategory({ id: Number(id), data: payload }));
    } else {
      await dispatch(createCategory(payload));
    }
    navigate("/categories");
  };
  if (isEdit && status === "loading") {
    return <p>Ładowanie danych kategorii...</p>;
  }
  if (status === "failed") {
    return <p className="text-red-500">Błąd: {error}</p>;
  }
  return (
    <div className="max-w-lg mx-auto bg-white p-6 rounded-lg shadow">
      <Link
        to="/categories"
        className="flex items-center text-gray-600 hover:text-pink-500 mb-6"
      >
        <FaChevronLeft className="inline mr-2" /> Powrót do Kategorii
      </Link>
      <h1 className="text-2xl font-semibold mb-4">
        {isEdit ? "Edytuj Kategorię" : "Nowa Kategoria"}
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
            required
            className="w-full border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-1 focus:ring-pink-500 focus:border-pink-500 transition-colors duration-300"
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
        <button
          type="submit"
          className="w-full py-3 bg-pink-500 hover:bg-pink-600 text-white rounded-lg transition"
        >
          {isEdit ? "Zapisz" : "Utwórz"}
        </button>
      </form>
    </div>
  );
};

export default CategoryForm;
