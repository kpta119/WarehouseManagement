import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useParams, Link } from "react-router-dom";
import { fetchCategories } from "../../features/categories/categoriesSlice";
import { fetchProducts } from "../../features/products/productsSlice";
import { FaChevronLeft, FaEdit } from "react-icons/fa";
import { currencyFormatter } from "../../utils/helpers";

const CategoryDetail = () => {
  const { id } = useParams();
  const dispatch = useDispatch();
  const { list: categories, status } = useSelector((s) => s.categories);
  const { list: products, status: prodStatus } = useSelector((s) => s.products);
  useEffect(() => {
    if (status === "idle") dispatch(fetchCategories());
    dispatch(fetchProducts({ categoryId: id }));
  }, [dispatch, status]);
  const cat = categories.find((c) => String(c.categoryId) === String(id));
  if (status === "loading") return <p>Ładowanie...</p>;
  if (!cat) return <p>Nie znaleziono kategorii.</p>;
  return (
    <div className="space-y-6 max-w-lg mx-auto bg-white p-6 rounded-lg shadow">
      <div className="flex items-center justify-between">
        <Link to="/products" className="text-gray-600 hover:text-pink-500">
          <FaChevronLeft className="inline mr-2" /> Powrót do Kategorii
        </Link>
        <Link
          to={`/categories/${cat.categoryId}/edit`}
          className="flex items-center bg-pink-500 hover:bg-pink-600 text-white px-4 py-2 rounded-lg transition"
        >
          <FaEdit className="mr-2" /> Edytuj Produkt
        </Link>
      </div>
      <h1 className="text-3xl font-semibold text-gray-800">{cat.name}</h1>
      <p className="text-gray-700 whitespace-pre-line">
        {cat.description || "Brak opisu."}
      </p>
      <section>
        <h2 className="text-2xl font-semibold mt-6 mb-4">
          Produkty w tej kategorii
        </h2>
        {prodStatus === "loading" ? (
          <p>Ładowanie produktów…</p>
        ) : products.length === 0 ? (
          <p>Brak produktów w tej kategorii.</p>
        ) : (
          <ul className="divide-y divide-gray-200">
            {products.map((p) => (
              <li
                key={p.productId}
                className="p-2 hover:bg-gray-50 flex items-center justify-between"
              >
                <Link
                  to={`/products/${p.productId}`}
                  className="text-pink-600 hover:underline"
                >
                  {p.name}
                </Link>
                <span className="ml-2">{currencyFormatter(p.unitPrice)}</span>
              </li>
            ))}
          </ul>
        )}
      </section>
    </div>
  );
};

export default CategoryDetail;
