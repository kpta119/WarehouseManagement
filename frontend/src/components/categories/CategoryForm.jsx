import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link, useNavigate, useParams } from "react-router-dom";
import {
  fetchCategories,
  createCategory,
  updateCategory,
} from "../../features/categories/categoriesSlice";
import { FaChevronLeft } from "react-icons/fa";
import Spinner from "../helper/Spinner";

const CategoryForm = () => {
  const { id } = useParams();
  const isEdit = Boolean(id);
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const {
    list: data,
    status,
    error,
    formStatus,
  } = useSelector((state) => state.categories);
  const { content: categories } = data;
  const [form, setForm] = useState({ name: "", description: "" });
  useEffect(() => {
    if (status === "idle") {
      dispatch(fetchCategories({ all: true }));
    }
  }, [dispatch, status]);
  const cat = categories.find((c) => String(c.categoryId) === String(id));
  useEffect(() => {
    if (isEdit && status === "succeeded") {
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
    let result;
    if (isEdit) {
      result = await dispatch(
        updateCategory({ id: Number(id), data: payload })
      );
    } else {
      result = await dispatch(createCategory(payload));
    }
    if (result.meta.requestStatus === "fulfilled") {
      navigate("/categories");
    }
  };
  if (isEdit && status === "loading") {
    return <Spinner />;
  }
  if (status === "failed") {
    return <p className="text-red-500">Błąd: {error}</p>;
  }
  if (isEdit && !cat) {
    return <p className="text-red-500">Nie znaleziono kategorii.</p>;
  }
  return (
    <div className="max-w-lg mx-auto bg-white p-6 rounded-lg shadow">
      <Link
        to="/categories"
        className="flex items-center text-gray-600 hover:text-pink-500 mb-6 transition duration-200"
      >
        <FaChevronLeft className="inline mr-2" /> Powrót do Kategorii
      </Link>
      <h1 className="text-2xl font-semibold mb-4">
        {isEdit ? "Edytuj Kategorię" : "Nowa Kategoria"}
      </h1>
      {formStatus === "failed" && (
        <p className="text-red-500 mb-4">
          Błąd: {error || "Nie udało się zapisać kategorii."}
        </p>
      )}
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
            required
            className="w-full border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-1 focus:ring-pink-500 focus:border-pink-500 transition-colors duration-300"
          />
        </div>
        <button
          type="submit"
          className="w-full py-3 bg-pink-500 hover:bg-pink-600 text-white rounded-lg transition cursor-pointer duration-200"
        >
          {formStatus === "loading" ? (
            <Spinner color="white" />
          ) : isEdit ? (
            "Zapisz"
          ) : (
            "Utwórz"
          )}
        </button>
      </form>
    </div>
  );
};

export default CategoryForm;
