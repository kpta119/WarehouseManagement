import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate, useParams, Link } from "react-router-dom";
import { FaChevronDown, FaChevronLeft } from "react-icons/fa";
import { createEmployee } from "../../features/employees/employeesSlice";
import { fetchWarehouses } from "../../features/warehouses/warehousesSlice";
import Spinner from "../helper/Spinner";

const EmployeeForm = () => {
  const { id } = useParams();
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const { list: data } = useSelector((state) => state.warehouses);
  const { content: warehouses } = data;
  const { error, formStatus } = useSelector((state) => state.employees);
  const [form, setForm] = useState({
    name: "",
    surname: "",
    email: "",
    phoneNumber: "",
    position: "",
    warehouseId: "",
  });
  useEffect(() => {
    dispatch(fetchWarehouses({ all: true }));
  }, [dispatch, id]);
  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm((prev) => ({ ...prev, [name]: value }));
  };
  const handleSubmit = async (e) => {
    e.preventDefault();
    const payload = {
      name: form.name,
      surname: form.surname,
      email: form.email,
      phoneNumber: form.phoneNumber,
      position: form.position,
      warehouseId: parseInt(form.warehouseId, 10),
    };
    const result = await dispatch(createEmployee(payload));
    if (result.meta.requestStatus === "fulfilled") {
      navigate("/employees");
    }
  };
  return (
    <div className="max-w-lg mx-auto bg-white p-6 rounded-lg shadow-lg">
      <Link
        to="/employees"
        className="flex items-center text-gray-600 hover:text-pink-500 mb-6 transition duration-200"
      >
        <FaChevronLeft className="mr-2" /> Powrót do Pracowników
      </Link>
      <h1 className="text-3xl font-semibold text-gray-800 mb-6">
        Nowy Pracownik
      </h1>
      {formStatus === "failed" && (
        <p className="text-red-500 mb-4">Błąd: {error}</p>
      )}
      <form onSubmit={handleSubmit} className="space-y-5">
        <div>
          <label
            htmlFor="name"
            className="block text-sm font-medium text-gray-700 mb-1"
          >
            Imię
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
            htmlFor="surname"
            className="block text-sm font-medium text-gray-700 mb-1"
          >
            Nazwisko
          </label>
          <input
            id="surname"
            name="surname"
            value={form.surname}
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
            Telefon
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
            htmlFor="position"
            className="block text-sm font-medium text-gray-700 mb-1"
          >
            Stanowisko
          </label>
          <input
            id="position"
            name="position"
            value={form.position}
            onChange={handleChange}
            required
            className="w-full border border-gray-300 rounded-lg px-4 py-2 focus:outline-none focus:ring-1 focus:ring-pink-500 transition"
          />
        </div>
        <div>
          <label
            htmlFor="warehouseId"
            className="block text-sm font-medium text-gray-700 mb-1"
          >
            Magazyn
          </label>
          <div className="relative">
            <select
              id="warehouseId"
              name="warehouseId"
              value={form.warehouseId}
              onChange={handleChange}
              required
              className="w-full border border-gray-300 rounded-lg px-4 py-2 focus:outline-none focus:ring-1 focus:ring-pink-500 transition appearance-none"
            >
              <option value="">Wybierz magazyn</option>
              {warehouses.map((wh) => (
                <option key={wh.warehouseId} value={wh.warehouseId}>
                  {wh.name}
                </option>
              ))}
            </select>
            <FaChevronDown className="pointer-events-none absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-400" />
          </div>
        </div>
        <button
          type="submit"
          className="w-full py-3 bg-pink-500 hover:bg-pink-600 text-white rounded-lg transition cursor-pointer duration-200"
        >
          {formStatus === "loading" ? (
            <Spinner color="white" />
          ) : (
            "Utwórz Pracownika"
          )}
        </button>
      </form>
    </div>
  );
};

export default EmployeeForm;
