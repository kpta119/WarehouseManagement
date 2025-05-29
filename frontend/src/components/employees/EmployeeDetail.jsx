import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useParams, Link } from "react-router-dom";
import { fetchEmployeeById } from "../../features/employees/employeesSlice";
import { FaChevronLeft, FaEye } from "react-icons/fa";
import {
  currencyFormatter,
  dateFormatter,
  numberFormatter,
} from "../../utils/helpers";
import Spinner from "../helper/Spinner";

const EmployeeDetail = () => {
  const { id } = useParams();
  const dispatch = useDispatch();
  const [transactionsShown, setTransactionsShown] = useState(25);
  const {
    current: employee,
    status,
    error,
  } = useSelector((state) => state.employees);
  useEffect(() => {
    dispatch(fetchEmployeeById(id));
  }, [dispatch, id]);
  if (status === "loading" || status === "idle") {
    return <Spinner />;
  }
  if (status === "failed") {
    return <p className="text-red-500">Błąd: {error}</p>;
  }
  if (!employee) {
    return <p className="text-red-500">Nie znaleziono pracownika.</p>;
  }
  const {
    name,
    surname,
    email,
    phoneNumber,
    position,
    warehouseId,
    warehouseName,
    history = [],
  } = employee;
  return (
    <div className="space-y-6 max-w-4xl mx-auto">
      <div className="bg-white p-6 rounded-lg shadow space-y-2">
        <div>
          <Link
            to="/employees"
            className="text-gray-600 hover:text-pink-500 transition duration-200"
          >
            <FaChevronLeft className="inline-block mr-2" /> Powrót do
            Pracowników
          </Link>
        </div>
        <h1 className="text-3xl font-semibold text-gray-800">
          {name} {surname}
        </h1>
        <p>
          <strong>Stanowisko:</strong> {position}
        </p>
        <p>
          <strong>Email:</strong> {email}
        </p>
        <p>
          <strong>Telefon:</strong> {phoneNumber}
        </p>
        <p>
          <strong>Magazyn:</strong>{" "}
          <Link
            to={`/warehouses/${warehouseId}`}
            className="text-pink-600 hover:underline"
          >
            {warehouseName}
          </Link>
        </p>
        <section>
          <h2 className="text-2xl font-semibold mb-4">Historia Transakcji</h2>
          {history.length === 0 ? (
            <p className="text-red-500">Brak historii transakcji.</p>
          ) : (
            <>
              <div className="bg-white rounded-lg shadow overflow-auto">
                <div className="grid grid-cols-6 bg-gray-50 text-xs font-medium text-gray-500 uppercase p-2 tracking-wide">
                  <div>Data</div>
                  <div>Typ</div>
                  <div>Opis</div>
                  <div className="text-right">Ilość przedmiotów</div>
                  <div className="text-right">Łączna kwota</div>
                  <div className="text-center">Szczegóły</div>
                </div>
                <div className="divide-y divide-gray-200">
                  {[...history]
                    .reverse()
                    .slice(0, transactionsShown)
                    .map((tx) => (
                      <div
                        key={tx.transactionId}
                        className="grid grid-cols-6 items-center p-2 text-sm text-gray-700 hover:bg-pink-50 transition-colors duration-200"
                      >
                        <div>{dateFormatter(tx.date)}</div>
                        <div>
                          {tx.type
                            .toLowerCase()
                            .replace(/_/g, " ")
                            .replace(/\b\w/g, (c) => c.toUpperCase())}
                        </div>
                        <div className="truncate">{tx.description}</div>
                        <div className="text-right">
                          {numberFormatter(tx.itemsCount)}
                        </div>
                        <div className="text-right">
                          {currencyFormatter(tx.totalPrice)}
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
              </div>
              {history.length > transactionsShown && (
                <button
                  onClick={() => setTransactionsShown((prev) => prev + 25)}
                  className="mt-4 bg-pink-500 hover:bg-pink-600 text-white px-4 py-2 rounded-lg transition mx-auto block"
                >
                  Pokaż więcej
                </button>
              )}
            </>
          )}
        </section>
      </div>
    </div>
  );
};

export default EmployeeDetail;
