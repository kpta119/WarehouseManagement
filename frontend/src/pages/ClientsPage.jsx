import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link } from "react-router-dom";
import { fetchClients } from "../features/clients/clientsSlice";
import { FaSearch, FaEye, FaPlus } from "react-icons/fa";

const ClientsPage = () => {
  const dispatch = useDispatch();
  const { list: clients, status, error } = useSelector((s) => s.clients);
  const [searchTerm, setSearchTerm] = useState("");
  useEffect(() => {
    dispatch(fetchClients());
  }, [dispatch]);
  const filtered = clients.filter((c) =>
    c.name.toLowerCase().includes(searchTerm.toLowerCase())
  );
  return (
    <div className="space-y-6">
      <div className="flex justify-between items-center">
        <h1 className="text-2xl font-semibold">Klienci</h1>
        <Link
          to="/clients/new"
          className="flex items-center bg-pink-500 hover:bg-pink-600 text-white px-4 py-2 rounded-lg transition"
        >
          <FaPlus className="mr-2" /> Nowy Klient
        </Link>
      </div>

      <form className="flex items-center border border-gray-300 rounded-lg px-3 py-2 w-1/2">
        <FaSearch className="text-gray-500 mr-2" />
        <input
          type="text"
          placeholder="Szukaj klientów..."
          className="w-full focus:outline-none"
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
        />
      </form>
      {status === "loading" ? (
        <p>Ładowanie...</p>
      ) : status === "failed" ? (
        <p className="text-red-500">Błąd: {error}</p>
      ) : (
        <div className="bg-white rounded-lg shadow overflow-auto">
          <div className="hidden sm:grid grid-cols-5 gap-4 p-4 bg-gray-50 text-xs font-medium text-gray-500 uppercase tracking-wider">
            <div>Nazwa</div>
            <div>Email</div>
            <div>Telefon</div>
            <div>Adres</div>
            <div className="text-center">Akcje</div>
          </div>
          <div className="divide-y divide-gray-200">
            {filtered.map((c) => (
              <div
                key={c.clientId}
                className="grid grid-cols-1 sm:grid-cols-5 items-center gap-4 p-4 hover:bg-pink-50 transition-colors"
              >
                <div className="font-medium text-pink-600">
                  <Link to={`/clients/${c.clientId}`}>{c.name}</Link>
                </div>
                <div className="text-sm text-gray-700">{c.email}</div>
                <div className="text-sm text-gray-700">{c.phoneNumber}</div>
                <div className="text-sm text-gray-700">{c.address}</div>
                <div className="flex justify-center text-gray-600">
                  <Link to={`/clients/${c.clientId}`}>
                    <FaEye />
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

export default ClientsPage;
