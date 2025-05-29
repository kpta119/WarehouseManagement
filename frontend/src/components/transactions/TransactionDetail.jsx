import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useParams, Link } from "react-router-dom";
import { fetchTransactionById } from "../../features/transactions/transactionsSlice";
import { FaChevronLeft } from "react-icons/fa";
import { format } from "date-fns";
import { currencyFormatter, numberFormatter } from "../../utils/helpers";
import Spinner from "../helper/Spinner";

const TransactionDetail = () => {
  const { id } = useParams();
  const dispatch = useDispatch();
  const {
    current: tx,
    status,
    error,
  } = useSelector((state) => state.transactions);
  useEffect(() => {
    dispatch(fetchTransactionById(id));
  }, [dispatch, id]);
  if (status === "loading" || status === "idle") {
    return <Spinner />;
  }
  if (status === "failed") {
    return <p className="text-red-500">Błąd: {error}</p>;
  }
  if (!tx) {
    return <p className="text-red-500">Nie znaleziono transakcji.</p>;
  }
  const {
    transactionId,
    date,
    description,
    type,
    employeeId,
    fromWarehouseId,
    toWarehouseId,
    clientId,
    supplierId,
    products,
    supplierName,
    fromWarehouseName,
    toWarehouseName,
    employeeName,
    clientName,
  } = tx;
  return (
    <div className="max-w-4xl mx-auto bg-white p-6 rounded-lg shadow space-y-6">
      <Link
        to="/transactions"
        className="text-gray-600 hover:text-pink-500 transition duration-200"
      >
        <FaChevronLeft className="inline-block mr-2" /> Powrót do Transakcji
      </Link>
      <h1 className="text-3xl font-semibold mt-4">
        Transakcja #{transactionId}
      </h1>
      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
        <div>
          <strong>Data:</strong> {format(new Date(date), "yyyy-MM-dd")}
        </div>
        <div>
          <strong>Typ:</strong>{" "}
          {type
            .toLowerCase()
            .replace(/_/g, " ")
            .replace(/\b\w/g, (c) => c.toUpperCase())}
        </div>
        <div>
          <strong>Opis:</strong> {description}
        </div>
        <div>
          <strong>Wykonana przez:</strong>{" "}
          <Link
            to={`/employees/${employeeId}`}
            className="text-pink-600 hover:underline"
          >
            {employeeName}
          </Link>
        </div>
        {supplierId && (
          <div>
            <strong>Dostawca:</strong>{" "}
            <Link
              to={`/suppliers/${supplierId}`}
              className="text-pink-600 hover:underline"
            >
              {supplierName}
            </Link>
          </div>
        )}
        {clientId && (
          <div>
            <strong>Klient:</strong>{" "}
            <Link
              to={`/clients/${clientId}`}
              className="text-pink-600 hover:underline"
            >
              {clientName}
            </Link>
          </div>
        )}
        {fromWarehouseId && (
          <div>
            <strong>Z magazynu:</strong>{" "}
            <Link
              to={`/warehouses/${fromWarehouseId}`}
              className="text-pink-600 hover:underline"
            >
              {fromWarehouseName}
            </Link>
          </div>
        )}
        {toWarehouseId && (
          <div>
            <strong>Do magazynu:</strong>{" "}
            <Link
              to={`/warehouses/${toWarehouseId}`}
              className="text-pink-600 hover:underline"
            >
              {toWarehouseName}
            </Link>
          </div>
        )}
      </div>
      {products.length === 0 ? (
        <p className="text-red-500">Brak produktów</p>
      ) : (
        <section>
          <h2 className="text-xl font-semibold mb-4">Produkty</h2>
          <div className="bg-gray-50 grid grid-cols-5 p-2 text-xs uppercase font-medium text-gray-500 rounded-t-lg">
            <div>Produkt</div>
            <div className="text-right">Ilość</div>
            <div className="text-right">Cena jednostkowa</div>
            <div className="text-right">Kategoria</div>
            <div className="text-right">Łącznie</div>
          </div>
          <div className="divide-y divide-gray-200">
            {products.map((p) => (
              <div
                key={p.productId}
                className="grid grid-cols-5 p-2 text-sm text-gray-700 items-center"
              >
                <div>
                  <Link
                    to={`/products/${p.productId}`}
                    className="text-pink-600 hover:underline"
                  >
                    {p.name}
                  </Link>
                </div>
                <div className="text-right">{numberFormatter(p.quantity)}</div>
                <div className="text-right">
                  {currencyFormatter(p.unitPrice)}
                </div>
                <div className="text-right">{p.categoryName}</div>
                <div className="text-right">
                  {currencyFormatter(p.quantity * p.unitPrice)}
                </div>
              </div>
            ))}
          </div>
          <div className="text-right p-2 font-bold">
            {currencyFormatter(
              products.reduce((acc, p) => acc + p.quantity * p.unitPrice, 0)
            )}
          </div>
        </section>
      )}
    </div>
  );
};

export default TransactionDetail;
