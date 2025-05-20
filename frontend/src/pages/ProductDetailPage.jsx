import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useParams, Link } from "react-router-dom";
import { fetchProductById } from "../features/products/productsSlice";
import { format } from "date-fns";
import { FaEdit, FaChevronLeft } from "react-icons/fa";

const ProductDetailPage = () => {
  const { id } = useParams();
  const dispatch = useDispatch();
  const { current: product } = useSelector((state) => state.products);
  useEffect(() => {
    dispatch(fetchProductById(id));
  }, [dispatch, id]);
  if (!product) {
    return <p>Loading product details...</p>;
  }
  const {
    name,
    description,
    unitPrice,
    unitSize,
    categoryName,
    inventory,
    transactions,
  } = product;
  return (
    <div className="max-w-4xl mx-auto bg-white p-6 rounded-lg shadow space-y-6">
      <div className="flex items-center justify-between">
        <Link to="/products" className="text-gray-600 hover:text-pink-500">
          <FaChevronLeft className="inline mr-2" /> Powrót do Produktów
        </Link>
        <Link
          to={`/products/${id}/edit`}
          className="flex items-center bg-pink-500 hover:bg-pink-600 text-white px-4 py-2 rounded-lg transition"
        >
          <FaEdit className="mr-2" /> Edytuj Produkt
        </Link>
      </div>
      <h1 className="text-3xl font-semibold text-gray-800">{name}</h1>
      <p className="text-sm text-gray-500">Kategoria: {categoryName}</p>
      <p className="text-lg font-medium">${unitPrice.toFixed(2)}</p>
      <p className="text-sm text-gray-600">Rozmiar: {unitSize}</p>
      {description && (
        <div>
          <h2 className="text-xl font-semibold mb-2">Opis</h2>
          <p className="text-gray-700 whitespace-pre-line">{description}</p>
        </div>
      )}
      {inventory && Object.keys(inventory).length > 0 && (
        <div>
          <h2 className="text-xl font-semibold mb-2">Zasoby na Magazyn</h2>
          <div className="grid grid-cols-2 bg-gray-50 text-xs font-medium text-gray-500 uppercase p-2 rounded-t-lg">
            <div>ID Magazynu</div>
            <div className="text-right">Ilość</div>
          </div>
          <div className="divide-y divide-gray-200">
            {Object.entries(inventory).map(([warehouseId, qty]) => (
              <div
                key={warehouseId}
                className="grid grid-cols-2 p-2 text-sm text-gray-700"
              >
                <div>{warehouseId}</div>
                <div className="text-right">{qty}</div>
              </div>
            ))}
          </div>
        </div>
      )}
      {transactions && transactions.length > 0 && (
        <div>
          <h2 className="text-xl font-semibold mb-2">Ostatnie Transakcje</h2>
          <div className="grid grid-cols-5 bg-gray-50 text-xs font-medium text-gray-500 uppercase p-2 rounded-t-lg">
            <div>Data</div>
            <div>Typ</div>
            <div className="text-right">Ilość</div>
            <div className="text-right">Cena</div>
            <div className="text-center">Detale</div>
          </div>
          <div className="divide-y divide-gray-200">
            {transactions.map((tx) => (
              <div
                key={tx.transactionId}
                className="grid grid-cols-5 items-center p-2 text-sm text-gray-700"
              >
                <div>{format(new Date(tx.date), "yyyy-MM-dd")}</div>
                <div>{tx.type.replace(/_/g, " ")}</div>
                <div className="text-right">{tx.quantity}</div>
                <div className="text-right">${tx.price.toFixed(2)}</div>
                <div className="text-center">
                  <Link
                    to={`/transactions/${tx.transactionId}`}
                    className="text-pink-600 hover:underline"
                  >
                    Zobacz
                  </Link>
                </div>
              </div>
            ))}
          </div>
        </div>
      )}
    </div>
  );
};

export default ProductDetailPage;
