import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useParams, Link } from "react-router-dom";
import { fetchProductById } from "../../features/products/productsSlice";
import { FaEdit, FaChevronLeft, FaEye } from "react-icons/fa";
import {
  currencyFormatter,
  dateFormatter,
  numberFormatter,
} from "../../utils/helpers";
import Spinner from "../helper/Spinner";
import {
  ResponsiveContainer,
  BarChart,
  Bar,
  XAxis,
  YAxis,
  Tooltip as BarTooltip,
  LineChart,
  Line,
  CartesianGrid,
  Tooltip,
} from "recharts";

const ProductDetail = () => {
  const { id } = useParams();
  const dispatch = useDispatch();
  const {
    current: product,
    status,
    error,
  } = useSelector((state) => state.products);
  const [transactionsShown, setTransactionsShown] = useState(25);
  useEffect(() => {
    dispatch(fetchProductById(id));
  }, [dispatch, id]);
  if (status === "loading" || status === "idle") {
    return <Spinner />;
  }
  if (status === "failed") {
    return <p className="text-red-500">Błąd: {error}</p>;
  }
  if (!product) {
    return <p className="text-red-500">Nie znaleziono produktu.</p>;
  }
  const {
    name,
    description,
    unitPrice,
    unitSize,
    categoryName,
    categoryId,
    inventory,
    transactions,
  } = product;
  const inventoryData = inventory.map(({ warehouseId, quantity }) => ({
    warehouseId,
    quantity,
  }));
  const dateMap = new Map();
  transactions.forEach((tx) => {
    const d = new Date(tx.date);
    d.setHours(0, 0, 0, 0);
    const ts = d.getTime();
    dateMap.set(ts, {
      quantity: (dateMap.get(ts)?.quantity || 0) + tx.quantity,
      price: tx.price,
    });
  });
  const transactionData = Array.from(dateMap.entries())
    .map(([date, data]) => ({ date, quantity: data.quantity }))
    .sort((a, b) => a.date - b.date);
  const histogramData = Array.from(dateMap.entries())
    .map(([date, data]) => ({ date, price: data.price }))
    .sort((a, b) => a.date - b.date);
  return (
    <div className="max-w-4xl mx-auto bg-white p-6 rounded-lg shadow space-y-6">
      <div className="flex items-center justify-between">
        <Link
          to="/products"
          className="text-gray-600 hover:text-pink-500 transition duration-200"
        >
          <FaChevronLeft className="inline mr-2" /> Powrót do Produktów
        </Link>
        <Link
          to={`/products/${id}/edit`}
          className="flex items-center bg-pink-500 hover:bg-pink-600 text-white px-4 py-2 rounded-lg transition duration-200"
        >
          <FaEdit className="mr-2" /> Edytuj Produkt
        </Link>
      </div>
      <h1 className="text-3xl font-semibold text-gray-800">{name}</h1>
      <p className="text-gray-700 whitespace-pre-line">{description}</p>
      <p>
        <strong>Kategoria: </strong>
        <Link
          className="hover:underline text-pink-500"
          to={`/categories/${categoryId}`}
        >
          {categoryName}
        </Link>
      </p>
      <p>
        <strong>Jednostkowa Cena: </strong>
        {currencyFormatter(unitPrice)}
      </p>
      <p>
        <strong>Jednostkowy Rozmiar: </strong>
        {numberFormatter(unitSize)}
      </p>
      {inventory.length > 0 && (
        <div className="flex gap-4 w-full">
          <div className="w-1/2">
            <h2 className="text-xl font-semibold mb-2">Zasoby na Magazyn</h2>
            <div className="grid grid-cols-2 bg-gray-50 text-xs font-medium text-gray-500 uppercase p-2 rounded-t-lg">
              <div>Magazyn</div>
              <div className="text-right">Ilość</div>
            </div>
            <div className="divide-y divide-gray-200">
              {inventory.map(({ warehouseId, warehouseName, quantity }) => (
                <div
                  key={warehouseId}
                  className="grid grid-cols-2 p-2 text-sm text-gray-700"
                >
                  <div className="text-pink-600">
                    <Link
                      to={`/warehouses/${warehouseId}`}
                      className="hover:underline"
                    >
                      {warehouseName}
                    </Link>
                  </div>
                  <div className="text-right">{numberFormatter(quantity)}</div>
                </div>
              ))}
            </div>
          </div>
          {inventoryData.length > 0 && (
            <div className="w-1/2">
              <h2 className="text-xl font-semibold mb-2">Zapas na magazyn</h2>
              <ResponsiveContainer
                width="100%"
                height={(Object.keys(inventory).length + 1) * 40}
              >
                <BarChart data={inventoryData}>
                  <XAxis dataKey="warehouseId" tick={{ fill: "#6B7280" }} />
                  <YAxis tick={{ fill: "#6B7280" }} />
                  <BarTooltip />
                  <Bar dataKey="quantity" name="Ilość sztuk" fill="#3B82F6" />
                </BarChart>
              </ResponsiveContainer>
            </div>
          )}
        </div>
      )}
      <div className="space-y-8">
        {transactionData.length > 0 && (
          <div>
            <h2 className="text-xl font-semibold mb-2">
              Wolumen transakcji w czasie
            </h2>
            <ResponsiveContainer width="100%" height={250}>
              <LineChart data={transactionData}>
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis
                  dataKey="date"
                  type="number"
                  scale="time"
                  domain={["dataMin", "dataMax"]}
                  tickFormatter={(ts) => dateFormatter(ts)}
                  tick={{ fill: "#6B7280" }}
                />
                <YAxis tick={{ fill: "#6B7280" }} />
                <Tooltip labelFormatter={(ts) => dateFormatter(ts)} />
                <Line
                  type="monotone"
                  dataKey="quantity"
                  name="Ilość sztuk"
                  stroke="#10B981"
                  strokeWidth={2}
                />
              </LineChart>
            </ResponsiveContainer>
          </div>
        )}
        <div>
          <h2 className="text-xl font-semibold mb-2">Historia ceny produktu</h2>
          <ResponsiveContainer width="100%" height={250}>
            <LineChart data={histogramData}>
              <CartesianGrid strokeDasharray="3 3" />
              <XAxis
                dataKey="date"
                type="number"
                scale="time"
                domain={["dataMin", "dataMax"]}
                tickFormatter={(ts) => dateFormatter(ts)}
                tick={{ fill: "#6B7280" }}
              />
              <YAxis tick={{ fill: "#6B7280" }} />
              <Tooltip labelFormatter={(ts) => dateFormatter(ts)} />
              <Line
                type="monotone"
                dataKey="price"
                name="Cena jednostkowa"
                stroke="#10B981"
                strokeWidth={2}
              />
            </LineChart>
          </ResponsiveContainer>
        </div>
      </div>
      {transactions.length === 0 ? (
        <p className="text-red-500">Brak transakcji.</p>
      ) : (
        <div>
          <h2 className="text-xl font-semibold mb-2">Ostatnie Transakcje</h2>
          <div className="grid grid-cols-7 bg-gray-50 text-xs font-medium text-gray-500 uppercase p-2 rounded-t-lg">
            <div>Data</div>
            <div>Przez pracownika</div>
            <div>Typ</div>
            <div className="text-right">Ilość</div>
            <div className="text-right">Cena (szt.)</div>
            <div className="text-right">Łącznie</div>
            <div className="text-center">Detale</div>
          </div>
          <div className="divide-y divide-gray-200">
            {[...transactions]
              .reverse()
              .slice(0, transactionsShown)
              .map((tx) => (
                <div
                  key={tx.transactionId}
                  className="grid grid-cols-7 items-center p-2 text-sm text-gray-700"
                >
                  <div>{dateFormatter(tx.date)}</div>
                  <Link
                    to={`/employees/${tx.employeeId}`}
                    className="text-pink-500 hover:underline"
                  >
                    {tx.employeeName}
                  </Link>
                  <div>
                    {tx.type
                      .toLowerCase()
                      .replace(/_/g, " ")
                      .replace(/\b\w/g, (c) => c.toUpperCase())}
                  </div>
                  <div className="text-right">
                    {numberFormatter(tx.quantity)}
                  </div>
                  <div className="text-right">
                    {currencyFormatter(tx.price)}
                  </div>
                  <div className="text-right">
                    {currencyFormatter(tx.price * tx.quantity)}
                  </div>
                  <div className="flex justify-center text-gray-600">
                    <Link
                      to={`/transactions/${tx.transactionId}`}
                      className="hover:text-pink-500 transition duration-200"
                    >
                      <FaEye />
                    </Link>
                  </div>
                </div>
              ))}
          </div>
          {transactions.length > transactionsShown && (
            <button
              onClick={() => setTransactionsShown((prev) => prev + 25)}
              className="mt-4 bg-pink-500 hover:bg-pink-600 text-white px-4 py-2 rounded-lg transition mx-auto block duration-200"
            >
              Pokaż więcej
            </button>
          )}
        </div>
      )}
    </div>
  );
};

export default ProductDetail;
