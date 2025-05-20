import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useParams, Link } from "react-router-dom";
import { fetchEmployeeById } from "../features/employees/employeesSlice";
import { format } from "date-fns";
import { FaChevronLeft, FaEye } from "react-icons/fa";

const EmployeeDetailPage = () => {
  const { id } = useParams();
  const dispatch = useDispatch();
  const { current: employee } = useSelector((state) => state.employees);
  useEffect(() => {
    dispatch(fetchEmployeeById(id));
  }, [dispatch, id]);
  if (!employee) {
    return <p>Ładowanie danych pracownika...</p>;
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
          <Link to="/employees" className="text-gray-600 hover:text-pink-500">
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
      </div>
      <section>
        <h2 className="text-2xl font-semibold mb-4">Historia Transakcji</h2>
        {history.length === 0 ? (
          <p>Brak historii transakcji.</p>
        ) : (
          <div className="bg-white rounded-lg shadow overflow-auto">
            <div className="grid grid-cols-4 bg-gray-50 text-xs font-medium text-gray-500 uppercase p-2 tracking-wide">
              <div>Data</div>
              <div>Typ</div>
              <div>Opis</div>
              <div className="text-center">Szczegóły</div>
            </div>
            <div className="divide-y divide-gray-200">
              {history.map((tx) => (
                <div
                  key={tx.transactionId}
                  className="grid grid-cols-4 items-center p-2 text-sm text-gray-700 hover:bg-pink-50 transition-colors"
                >
                  <div>{format(new Date(tx.date), "yyyy-MM-dd")}</div>
                  <div>{tx.type.replace(/_/g, " ")}</div>
                  <div>{tx.description}</div>
                  <div className="flex justify-center text-gray-600">
                    <Link to={`/transactions/${tx.transactionId}`}>
                      <FaEye />
                    </Link>
                  </div>
                </div>
              ))}
            </div>
          </div>
        )}
      </section>
    </div>
  );
};

export default EmployeeDetailPage;
