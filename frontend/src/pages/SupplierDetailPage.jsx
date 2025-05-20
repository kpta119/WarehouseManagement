import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useParams, Link } from "react-router-dom";
import { fetchSupplierById } from "../features/suppliers/suppliersSlice";
import { format } from "date-fns";
import { FaChevronLeft, FaEye } from "react-icons/fa";

const SupplierDetailPage = () => {
  const { id } = useParams();
  const dispatch = useDispatch();
  const { current: supplier } = useSelector((state) => state.suppliers);
  useEffect(() => {
    dispatch(fetchSupplierById(id));
  }, [dispatch, id]);
  if (!supplier) {
    return <p>Ładowanie danych dostawcy...</p>;
  }
  const { name, email, phoneNumber, address, history = [] } = supplier;
  const addr = address.street
    ? `${address.street} ${address.streetNumber}, ${address.postalCode} ${address.city}, ${address.country}`
    : address;
  return (
    <div className="space-y-6 max-w-4xl mx-auto">
      <div className="bg-white p-6 rounded-lg shadow space-y-4">
        <div>
          <Link to="/suppliers" className="text-gray-600 hover:text-pink-500">
            <FaChevronLeft className="inline-block mr-2" /> Wróć do Dostawców
          </Link>
        </div>
        <h1 className="text-3xl font-semibold text-gray-800">{name}</h1>
        <p>
          <strong>E-mail:</strong> {email}
        </p>
        <p>
          <strong>Nr. telefonu:</strong> {phoneNumber}
        </p>
        <p>
          <strong>Adres:</strong> {addr}
        </p>
      </div>
      <section>
        <h2 className="text-2xl font-semibold mb-4">Historia transakcji</h2>
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
                  <div className="flex justify-center text-pink-600">
                    <Link
                      to={`/transactions/${tx.transactionId}`}
                      className="hover:underline"
                    >
                      Zobacz
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

export default SupplierDetailPage;
