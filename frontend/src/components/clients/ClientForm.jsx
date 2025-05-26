import { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { createClient } from "../../features/clients/clientsSlice";
import { FaChevronLeft } from "react-icons/fa";
import { Link } from "react-router-dom";

const ClientForm = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const { status, error } = useSelector((state) => state.clients);
  const [form, setForm] = useState({
    name: "",
    email: "",
    phoneNumber: "",
    address: "",
  });
  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm((prev) => ({ ...prev, [name]: value }));
  };
  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await dispatch(createClient(form)).unwrap();
      navigate("/clients");
    } catch {}
  };
  return (
    <div className="max-w-xl mx-auto mt-6 bg-white p-8 rounded-2xl shadow-lg">
      <Link
        to="/clients"
        className="flex items-center text-gray-600 hover:text-pink-500 mb-6"
      >
        <FaChevronLeft className="inline mr-2" /> Powrót do Klientów
      </Link>
      <h1 className="text-3xl font-semibold text-gray-800 mb-6">Nowy Klient</h1>
      <form onSubmit={handleSubmit} className="space-y-5">
        <div>
          <label
            htmlFor="name"
            className="block text-sm font-medium text-gray-700 mb-1"
          >
            Nazwa
          </label>
          <input
            id="name"
            name="name"
            value={form.name}
            onChange={handleChange}
            required
            className="w-full border border-gray-300 rounded-lg px-4 py-2 focus:outline-none focus:ring-1 focus:ring-pink-500 transition"
          />
        </div>
        <div>
          <label
            htmlFor="email"
            className="block text-sm font-medium text-gray-700 mb-1"
          >
            E-mail
          </label>
          <input
            id="email"
            name="email"
            type="email"
            value={form.email}
            onChange={handleChange}
            required
            className="w-full border border-gray-300 rounded-lg px-4 py-2 focus:outline-none focus:ring-1 focus:ring-pink-500 transition"
          />
        </div>
        <div>
          <label
            htmlFor="phoneNumber"
            className="block text-sm font-medium text-gray-700 mb-1"
          >
            Nr. telefonu
          </label>
          <input
            id="phoneNumber"
            name="phoneNumber"
            type="tel"
            value={form.phoneNumber}
            onChange={handleChange}
            required
            className="w-full border border-gray-300 rounded-lg px-4 py-2 focus:outline-none focus:ring-1 focus:ring-pink-500 transition"
          />
        </div>
        <div>
          <label
            htmlFor="address"
            className="block text-sm font-medium text-gray-700 mb-1"
          >
            Adres
          </label>
          <textarea
            id="address"
            name="address"
            rows={3}
            value={form.address}
            onChange={handleChange}
            className="w-full border border-gray-300 rounded-lg px-4 py-2 focus:outline-none focus:ring-1 focus:ring-pink-500 transition"
          />
        </div>
        {error && <p className="text-red-500 text-sm">Error: {error}</p>}
        <button
          type="submit"
          disabled={status === "loading"}
          className="w-full py-3 bg-pink-500 hover:bg-pink-600 text-white font-medium rounded-lg shadow-md transition disabled:opacity-50"
        >
          Stwórz Klienta
        </button>
      </form>
    </div>
  );
};

export default ClientForm;
