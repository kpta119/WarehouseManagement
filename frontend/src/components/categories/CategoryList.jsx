import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import {
  fetchCategories,
  deleteCategory,
} from "../../features/categories/categoriesSlice";
import Spinner from "../helper/Spinner";
import useDebounce from "../../hooks/useDebounce";
import ItemsList from "../Layout/ItemsList";
import FormList from "../Layout/FormList";

const CategoryList = () => {
  const dispatch = useDispatch();
  const { list: data, status, error } = useSelector((s) => s.categories);
  const { content: categories, page: pageInfo, formStatus } = data;
  const { totalPages } = pageInfo;
  const [searchTerm, setSearchTerm] = useState("");
  const debouncedSearchTerm = useDebounce(searchTerm);
  const [sortOption, setSortOption] = useState("");
  const [page, setPage] = useState(1);
  useEffect(() => {
    setPage(1);
  }, [debouncedSearchTerm]);
  useEffect(() => {
    dispatch(
      fetchCategories({
        name: debouncedSearchTerm || undefined,
        page: page - 1 || 0,
      })
    );
  }, [dispatch, debouncedSearchTerm, page]);
  const filtered = [...categories].sort((a, b) => {
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
      <FormList
        inputs={[
          {
            type: "text",
            label: "Nazwa",
            placeholder: "Wyszukaj po nazwie...",
            value: searchTerm,
            setValue: setSearchTerm,
          },
        ]}
        sorting={{
          sortOption,
          setSortOption,
          options: [
            { value: "name", label: "Nazwa (od A do Z)" },
            { value: "name-reverse", label: "Nazwa (od Z do A)" },
            { value: "description", label: "Opis (od A do Z)" },
            { value: "description-reverse", label: "Opis (od Z do A)" },
          ],
        }}
      />
      {status === "loading" || status === "idle" ? (
        <Spinner />
      ) : status === "failed" ? (
        <p className="text-red-500">Błąd: {error}</p>
      ) : filtered.length === 0 ? (
        <p className="text-red-500">Nie znaleziono kategorii</p>
      ) : (
        <>
          {error && <p className="text-red-500">Błąd: {error}</p>}
          <ItemsList
            pagination={{ page, setPage, totalPages }}
            labels={[
              { name: "Nazwa", type: "Link" },
              { name: "Opis", type: "Text-Long" },
            ]}
            data={filtered.map((item) => ({
              id: item.categoryId,
              name: item.name,
              description: item.description,
            }))}
            actions={{
              get: true,
              put: true,
              delete: true,
            }}
            path="categories"
            handleDelete={handleDelete}
          />
        </>
      )}
    </>
  );
};

export default CategoryList;
