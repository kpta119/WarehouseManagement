import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link } from "react-router-dom";
import {
  fetchCategories,
  deleteCategory,
} from "../../features/categories/categoriesSlice";
import { FaTrash, FaEdit, FaEye } from "react-icons/fa";
import Pagination from "../helper/Pagination";
import TextInput from "../helper/TextInput";
import SelectInput from "../helper/SelectInput";
import Spinner from "../helper/Spinner";

const CategoryList = () => {
  const dispatch = useDispatch();
  const { list: categories, status, error } = useSelector((s) => s.categories);
  const [searchTerm, setSearchTerm] = useState("");
  const [sortOption, setSortOption] = useState("");
  const [page, setPage] = useState(1);
  const totalPages = 10;
  useEffect(() => {
    dispatch(
      fetchCategories({
        searchTerm: searchTerm || undefined,
        page: page || 1,
      })
    );
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
  return (
    <>
      <form className="flex items-center space-x-2 justify-between">
        <div className="flex flex-wrap gap-4 items-end">
          <TextInput
            label="Nazwa"
            placeholder="Szukaj kategorii..."
            value={searchTerm}
            setValue={setSearchTerm}
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
          <option value="description">Opis (od A do Z)</option>
          <option value="description-reverse">Opis (od Z do A)</option>
        </SelectInput>
      </form>
      {status === "loading" || status === "idle" ? (
        <Spinner />
      ) : status === "failed" ? (
        <p className="text-red-500">Błąd: {error}</p>
      ) : filtered.length === 0 ? (
        <p className="text-red-500">Nie znaleziono kategorii</p>
      ) : (
        <>
          <Pagination
            currentPage={page}
            totalPages={totalPages}
            onPageChange={setPage}
          />
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
                  className="grid grid-cols-1 sm:grid-cols-3 items-center gap-4 p-4 hover:bg-pink-50 transition-colors duration-200"
                >
                  <div className="font-medium text-pink-600">
                    <Link
                      to={`/categories/${cat.categoryId}`}
                      className="hover:underline"
                    >
                      {cat.name}
                    </Link>
                  </div>
                  <div className="text-sm text-gray-700 truncate">
                    {cat.description}
                  </div>
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

export default CategoryList;
