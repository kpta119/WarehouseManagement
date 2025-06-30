import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate, useParams, Link } from "react-router-dom";
import { FaChevronDown, FaChevronLeft } from "react-icons/fa";
import { createEmployee } from "../../features/employees/employeesSlice";
import { fetchWarehouses } from "../../features/warehouses/warehousesSlice";
import Spinner from "../helper/Spinner";
import {
  fetchCountries,
  fetchRegions,
} from "../../features/geography/geographySlice";

const EmployeeForm = () => {
  const { id } = useParams();
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const { regions, countries } = useSelector((state) => state.geography);
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
    regionId: "",
    countryId: "",
    city: "",
    postalCode: "",
    street: "",
    streetNumber: "",
  });
  useEffect(() => {
    dispatch(fetchWarehouses({ all: true }));
    dispatch(fetchRegions());
  }, [dispatch, id]);
  useEffect(() => {
    if (form.regionId) {
      dispatch(fetchCountries(form.regionId));
    } else {
      dispatch(fetchCountries(null));
    }
  }, [dispatch, form.regionId]);
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
      address: {
        street: form.street,
        streetNumber: form.streetNumber,
        postalCode: form.postalCode,
        city: form.city,
        countryId: Number(form.countryId),
        regionId: Number(form.regionId),
      },
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
        <FaChevronLeft className="mr-2" /> Return to Employees
      </Link>
      <h1 className="text-3xl font-semibold text-gray-800 mb-6">
        New Employee
      </h1>
      {formStatus === "failed" && (
        <p className="text-red-500 mb-4">Error: {error}</p>
      )}
      <form onSubmit={handleSubmit} className="space-y-5">
        <div>
          <label
            htmlFor="name"
            className="block text-sm font-medium text-gray-700 mb-1"
          >
            Name
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
            Surname
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
            Phone No.
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
            Position
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
            Warehouse
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
              <option value="">Choose Warehouse</option>
              {warehouses.map((wh) => (
                <option key={wh.warehouseId} value={wh.warehouseId}>
                  {wh.name}
                </option>
              ))}
            </select>
            <FaChevronDown className="pointer-events-none absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-400" />
          </div>
        </div>
        <div>
          <label
            htmlFor="regionId"
            className="block text-sm font-medium text-gray-700 mb-1"
          >
            Region
          </label>
          <div className="relative">
            <select
              id="regionId"
              name="regionId"
              value={form.regionId}
              onChange={handleChange}
              required
              className="w-full border border-gray-300 rounded-lg px-4 py-2 focus:outline-none focus:ring-1 focus:ring-pink-500 transition appearance-none"
            >
              <option value="">Choose region</option>
              {regions.map((r) => (
                <option key={r.id} value={r.id}>
                  {r.name}
                </option>
              ))}
            </select>
            <FaChevronDown className="pointer-events-none absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-400" />
          </div>
        </div>
        <div>
          <label
            htmlFor="countryId"
            className="block text-sm font-medium text-gray-700 mb-1"
          >
            Country
          </label>
          <div className="relative">
            <select
              id="countryId"
              name="countryId"
              value={form.countryId}
              onChange={handleChange}
              required
              disabled={!form.regionId}
              className="w-full border border-gray-300 rounded-lg px-4 py-2 focus:outline-none focus:ring-1 focus:ring-pink-500 transition appearance-none"
            >
              <option value="">Choose Region and then Country</option>
              {countries.map((c) => (
                <option key={c.id} value={c.id}>
                  {c.name}
                </option>
              ))}
            </select>
            <FaChevronDown className="pointer-events-none absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-400" />
          </div>
        </div>
        <div>
          <label
            htmlFor="city"
            className="block text-sm font-medium text-gray-700 mb-1"
          >
            City
          </label>
          <input
            type="text"
            id="city"
            name="city"
            value={form.city}
            onChange={handleChange}
            required
            className="w-full border border-gray-300 rounded-lg px-4 py-2 focus:outline-none focus:ring-1 focus:ring-pink-500 transition"
          />
        </div>
        <div>
          <label
            htmlFor="postalCode"
            className="block text-sm font-medium text-gray-700 mb-1"
          >
            Postal Code
          </label>
          <input
            type="text"
            id="postalCode"
            name="postalCode"
            value={form.postalCode}
            onChange={handleChange}
            required
            className="w-full border border-gray-300 rounded-lg px-4 py-2 focus:outline-none focus:ring-1 focus:ring-pink-500 transition"
          />
        </div>
        <div>
          <label
            htmlFor="street"
            className="block text-sm font-medium text-gray-700 mb-1"
          >
            Street
          </label>
          <input
            type="text"
            id="street"
            name="street"
            value={form.street}
            onChange={handleChange}
            required
            className="w-full border border-gray-300 rounded-lg px-4 py-2 focus:outline-none focus:ring-1 focus:ring-pink-500 transition"
          />
        </div>
        <div>
          <label
            htmlFor="streetNumber"
            className="block text-sm font-medium text-gray-700 mb-1"
          >
            Street Number
          </label>
          <input
            type="number"
            id="streetNumber"
            name="streetNumber"
            value={form.streetNumber}
            onChange={handleChange}
            required
            className="w-full border border-gray-300 rounded-lg px-4 py-2 focus:outline-none focus:ring-1 focus:ring-pink-500 transition"
          />
        </div>
        <button
          type="submit"
          className="w-full py-3 bg-pink-500 hover:bg-pink-600 text-white rounded-lg transition cursor-pointer duration-200"
        >
          {formStatus === "loading" ? (
            <Spinner color="white" />
          ) : (
            "Create Employee"
          )}
        </button>
      </form>
    </div>
  );
};

export default EmployeeForm;
