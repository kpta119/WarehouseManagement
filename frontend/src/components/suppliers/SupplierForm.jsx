import { useState, useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate, Link } from "react-router-dom";
import { FaChevronDown, FaChevronLeft } from "react-icons/fa";
import { createSupplier } from "../../features/suppliers/suppliersSlice";
import {
  fetchRegions,
  fetchCountries,
} from "../../features/geography/geographySlice";
import Spinner from "../helper/Spinner";

const SupplierForm = () => {
  const dispatch = useDispatch();
  const { regions, countries } = useSelector((state) => state.geography);
  const { formStatus, error } = useSelector((state) => state.suppliers);
  const navigate = useNavigate();
  const [form, setForm] = useState({
    name: "",
    email: "",
    phoneNumber: "",
    regionId: "",
    countryId: "",
    city: "",
    postalCode: "",
    street: "",
    streetNumber: "",
  });
  useEffect(() => {
    dispatch(fetchRegions());
  }, [dispatch]);
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
      email: form.email,
      phoneNumber: form.phoneNumber,
      address: {
        street: form.street,
        streetNumber: form.streetNumber,
        postalCode: form.postalCode,
        city: form.city,
        countryId: Number(form.countryId),
        regionId: Number(form.regionId),
      },
    };
    const result = await dispatch(createSupplier(payload));
    if (result.meta.requestStatus === "fulfilled") {
      navigate("/suppliers");
    }
  };
  return (
    <div className="max-w-lg mx-auto bg-white p-6 rounded-lg shadow-lg">
      <Link
        to="/suppliers"
        className="flex items-center text-gray-600 hover:text-pink-500 mb-6 transition duration-200"
      >
        <FaChevronLeft className="inline mr-2" /> Return to Suppliers
      </Link>
      <h1 className="text-3xl font-semibold text-gray-800 mb-6">
        New Supplier
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
            type="text"
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
            type="email"
            id="email"
            name="email"
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
            type="tel"
            id="phoneNumber"
            name="phoneNumber"
            value={form.phoneNumber}
            onChange={handleChange}
            required
            className="w-full border border-gray-300 rounded-lg px-4 py-2 focus:outline-none focus:ring-1 focus:ring-pink-500 transition"
          />
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
              <option value="">Choose Region</option>
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
            "Create Supplier"
          )}
        </button>
      </form>
    </div>
  );
};

export default SupplierForm;
