import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link } from "react-router-dom";
import {
  fetchCategories,
  deleteCategory,
} from "../../features/categories/categoriesSlice";
import {
  FaSearch,
  FaTrash,
  FaEdit,
  FaChevronDown,
  FaEye,
} from "react-icons/fa";

const CategoryList = () => {
  const dispatch = useDispatch();
  const { list: categories, status, error } = useSelector((s) => s.categories);
  const [searchTerm, setSearchTerm] = useState("");
  const [sortOption, setSortOption] = useState("");
  useEffect(() => {
    dispatch(fetchCategories());
  }, [dispatch]);
  const filtered = categories
    .filter((cat) => cat.name.toLowerCase().includes(searchTerm.toLowerCase()))
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
        default:
          return 0;
      }
    });
  const handleDelete = (id) => {
    if (window.confirm("Czy na pewno chcesz usunąć tę kategorię?")) {
      dispatch(deleteCategory(id));
    }
  };
  if (status === "loading") return <p>Ładowanie...</p>;
  if (status === "failed") return <p className="text-red-500">Błąd: {error}</p>;
  return (
    <>
      <div className="flex items-center space-x-2 justify-between">
        <div>
          <label className="block text-sm font-medium">Nazwa</label>
          <div className="flex items-center border border-gray-300 rounded-lg px-3 py-2 focus-within:ring-1 focus-within:ring-pink-500 focus-within:border-pink-500 transition-colors duration-300">
            <FaSearch className="text-gray-500 mr-2" />
            <input
              type="text"
              placeholder="Szukaj kategorii..."
              className="w-full focus:outline-none"
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
            />
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
            </select>
            <FaChevronDown className="pointer-events-none absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-400" />
          </div>
        </div>
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
                  <Link
                    to={`/categories/${cat.categoryId}`}
                    className="hover:underline"
                  >
                    {cat.name}
                  </Link>
                </div>
                <div className="text-sm text-gray-700">{cat.description}</div>
                <div className="flex justify-center space-x-4 text-gray-600">
                  <Link
                    to={`/categories/${cat.categoryId}`}
                    className="hover:text-pink-500 transition duration-200"
                  >
                    <FaEye />
                  </Link>
                  <Link
                    to={`/categories/${cat.categoryId}/edit`}
                    className="hover:text-pink-500 transition duration-200"
                  >
                    <FaEdit />
                  </Link>
                  <button
                    onClick={() => handleDelete(cat.categoryId)}
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

export default CategoryList;
