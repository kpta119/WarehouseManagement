import { Link } from "react-router-dom";
import { FaPlus, FaUsers } from "react-icons/fa";
import ClientList from "../components/clients/ClientList";

const ClientsPage = () => {
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
      <ClientList />
    </div>
  );
};

export default ClientsPage;
