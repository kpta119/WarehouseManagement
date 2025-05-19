import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link } from "react-router-dom";
import {
  fetchCategories,
  deleteCategory,
} from "../features/categories/categoriesSlice";
import { FaPlus, FaTrash, FaEdit } from "react-icons/fa";

const CategoriesPage = () => {
  const dispatch = useDispatch();
  const {
    list: categories,
    status,
    error,
  } = useSelector((state) => state.categories);
  const [searchTerm, setSearchTerm] = useState("");

  useEffect(() => {
    dispatch(fetchCategories());
  }, [dispatch]);

  const filtered = categories.filter((cat) =>
    cat.name.toLowerCase().includes(searchTerm.toLowerCase())
  );

  const handleDelete = (id) => {
    if (window.confirm("Czy na pewno chcesz usunąć tę kategorię?")) {
      dispatch(deleteCategory(id));
    }
  };

  return (
    <div className="space-y-6">
      <div className="flex justify-between items-center">
        <h1 className="text-2xl font-semibold">Kategorie</h1>
        <Link
          to="/categories/new"
          className="flex items-center bg-pink-500 hover:bg-pink-600 text-white px-4 py-2 rounded-lg transition"
        >
          <FaPlus className="mr-2" /> Nowa Kategoria
        </Link>
      </div>

      <div className="flex items-center border border-gray-300 rounded-lg px-3 py-2 w-1/2">
        <input
          type="text"
          placeholder="Szukaj kategorii..."
          className="w-full focus:outline-none"
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
        />
      </div>

      {status === "loading" ? (
        <p>Ładowanie...</p>
      ) : status === "failed" ? (
        <p className="text-red-500">Błąd: {error}</p>
      ) : (
        <div className="bg-white rounded-lg shadow overflow-auto">
          <div className="hidden sm:grid grid-cols-3 gap-4 p-4 bg-gray-50 text-xs font-medium text-gray-500 uppercase tracking-wider">
            <div>Nazwa</div>
            <div>Opis</div>
            <div className="text-center">Akcje</div>
          </div>
          <div className="divide-y divide-gray-200">
            {filtered.map((cat) => (
              <div
                key={cat.categoryId}
                className="grid grid-cols-1 sm:grid-cols-3 items-center gap-4 p-4 hover:bg-pink-50 transition-colors"
              >
                <div className="font-medium text-pink-600">
                  <Link to={`/categories/${cat.categoryId}`}>{cat.name}</Link>
                </div>
                <div className="text-sm text-gray-700">{cat.description}</div>
                <div className="flex justify-center space-x-4 text-gray-600">
                  <Link to={`/categories/${cat.categoryId}/edit`}>
                    <FaEdit />
                  </Link>
                  <button onClick={() => handleDelete(cat.categoryId)}>
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

export default CategoriesPage;
