import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link } from "react-router-dom";
import { fetchClients } from "../features/clients/clientsSlice";
import {
  FaSearch,
  FaEye,
  FaPlus,
  FaChevronDown,
  FaUsers,
} from "react-icons/fa";

const ClientsPage = () => {
  const dispatch = useDispatch();
  const { list: clients, status, error } = useSelector((s) => s.clients);
  const [searchTerm, setSearchTerm] = useState("");
  const [sortOption, setSortOption] = useState("");
  useEffect(() => {
    dispatch(fetchClients());
  }, [dispatch]);
  const filtered = clients.filter((c) =>
    c.name.toLowerCase().includes(searchTerm.toLowerCase())
  );
  return (
    <div className="space-y-6">
      <div className="flex justify-between items-center">
        <div className="flex items-center space-x-2">
          <FaUsers className="text-pink-500 w-6 h-6" />
          <h1 className="text-2xl font-semibold text-gray-800">
            Lista klientów
          </h1>
        </div>
        <Link
          to="/clients/new"
          className="flex items-center bg-pink-500 hover:bg-pink-600 text-white px-4 py-2 rounded-lg transition"
        >
          <FaPlus className="mr-2" /> Nowy Klient
        </Link>
      </div>
      <form className="flex justify-between items-center space-x-4">
        <div>
          <label className="block text-sm font-medium">Nazwa</label>
          <div className="flex items-center border border-gray-300 rounded-lg px-3 py-2 focus-within:ring-1 focus-within:ring-pink-500 focus-within:border-pink-500 transition-colors duration-300">
            <FaSearch className="text-gray-500 mr-2" />
            <input
              type="text"
              placeholder="Szukaj klientów..."
              className="w-full focus:outline-none"
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
            />
          </div>
        </div>
        <div>
          <label className="block text-sm font-medium">Sortowanie</label>
          <div className="relative">
            <select
              className="border appearance-none border-gray-300 rounded-lg px-3 py-2 pr-12 focus:outline-none focus:ring-1 focus:ring-pink-500 focus:border-pink-500 transition-colors duration-300"
              value={sortOption}
              onChange={(e) => setSortOption(e.target.value)}
            >
              <option value="">Sortuj przez</option>
              <option value="name">Nazwa (od A do Z)</option>
              <option value="name-reverse">Nazwa (od Z do A)</option>
              <option value="email">E-mail (od A do Z)</option>
              <option value="email-reverse">E-mail (od Z do A)</option>
              <option value="phone">Nr. telefonu (rosnąco)</option>
              <option value="phone-reverse">Nr. telefonu (malejąco)</option>
              <option value="address">Adres (od A do Z)</option>
              <option value="address-reverse">Adres (od Z do A)</option>
            </select>
            <FaChevronDown className="pointer-events-none absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-400" />
          </div>
        </div>
      </form>
      {status === "loading" ? (
        <p>Ładowanie...</p>
      ) : status === "failed" ? (
        <p className="text-red-500">Błąd: {error}</p>
      ) : (
        <div className="bg-white rounded-lg shadow overflow-auto">
          <div className="hidden sm:grid grid-cols-5 gap-4 p-4 bg-gray-50 text-xs font-medium text-gray-500 uppercase tracking-wider">
            <div>Nazwa</div>
            <div>E-mail</div>
            <div>Nr. telefonu</div>
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
                  <Link
                    to={`/clients/${c.clientId}`}
                    className="hover:underline"
                  >
                    {c.name}
                  </Link>
                </div>
                <div className="text-sm text-gray-700">{c.email}</div>
                <div className="text-sm text-gray-700">{c.phoneNumber}</div>
                <div className="text-sm text-gray-700">{c.address}</div>
                <div className="flex justify-center text-gray-600">
                  <Link
                    to={`/clients/${c.clientId}`}
                    className="hover:text-pink-500 transition duration-200"
                  >
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
