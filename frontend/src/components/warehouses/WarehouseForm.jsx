import { useState, useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate, useParams } from "react-router-dom";
import {
  createWarehouse,
  updateWarehouse,
  fetchWarehouseById,
} from "../../features/warehouses/warehousesSlice";
import { listRegions, listCountries } from "../../api/geography";
import { FaChevronDown } from "react-icons/fa";

const WarehousesForm = () => {
  const { id } = useParams();
  const isEdit = Boolean(id);
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const {
    current: warehouse,
    status,
    error,
  } = useSelector((state) => state.warehouses);
  const [regions, setRegions] = useState([]);
  const [countries, setCountries] = useState([]);
  const [form, setForm] = useState({
    name: "",
    capacity: "",
    regionId: "",
    countryId: "",
    city: "",
    postalCode: "",
    street: "",
    streetNumber: "",
  });
  useEffect(() => {
    listRegions().then((res) => setRegions(res.data));
  }, []);
  useEffect(() => {
    if (form.regionId) {
      listCountries(Number(form.regionId)).then((res) =>
        setCountries(res.data)
      );
    } else {
      setCountries([]);
    }
  }, [form.regionId]);
  useEffect(() => {
    if (isEdit) dispatch(fetchWarehouseById(id));
  }, [dispatch, id, isEdit]);
  useEffect(() => {
    if (isEdit && warehouse) {
      setForm({
        name: warehouse.name || "",
        capacity: warehouse.capacity?.toString() || "",
        regionId: warehouse.regionId?.toString() || "",
        countryId: warehouse.countryId?.toString() || "",
        city: warehouse.city || "",
        postalCode: warehouse.postalCode || "",
        street: warehouse.street || "",
        streetNumber: warehouse.streetNumber?.toString() || "",
      });
    }
  }, [warehouse, isEdit]);
  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm((f) => ({ ...f, [name]: value }));
  };
  const handleSubmit = (e) => {
    e.preventDefault();
    const payload = {
      name: form.name,
      capacity: parseInt(form.capacity, 10),
      regionId: parseInt(form.regionId, 10),
      countryId: parseInt(form.countryId, 10),
      city: form.city,
      postalCode: form.postalCode,
      street: form.street,
      streetNumber: form.streetNumber,
    };
    if (isEdit) {
      dispatch(updateWarehouse({ id: parseInt(id, 10), data: payload })).then(
        () => navigate("/warehouses")
      );
    } else {
      dispatch(createWarehouse(payload)).then(() => navigate("/warehouses"));
    }
  };
  if ((isEdit && status === "loading") || status === "idle") {
    return <p>Loading...</p>;
  }
  return (
    <div className="max-w-lg mx-auto bg-white p-6 rounded-lg shadow">
      <h1 className="text-2xl font-semibold mb-4">
        {isEdit ? "Edytuj Magazyn" : "Nowy Magazyn"}
      </h1>
      {status === "failed" && <p className="text-red-500">Error: {error}</p>}
      <form onSubmit={handleSubmit} className="space-y-4">
        <div>
          <label htmlFor="name" className="block text-sm font-medium">
            Nazwa
          </label>
          <input
            id="name"
            name="name"
            value={form.name}
            onChange={handleChange}
            required
            className="w-full border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-1 focus:ring-pink-500 focus:border-pink-500 transition-colors duration-300"
          />
        </div>
        <div>
          <label htmlFor="capacity" className="block text-sm font-medium">
            Pojemność
          </label>
          <input
            id="capacity"
            name="capacity"
            type="number"
            value={form.capacity}
            onChange={handleChange}
            required
            className="w-full border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-1 focus:ring-pink-500 focus:border-pink-500 transition-colors duration-300"
          />
        </div>
        <div>
          <label htmlFor="regionId" className="block text-sm font-medium">
            Region
          </label>
          <div className="relative">
            <select
              id="regionId"
              name="regionId"
              value={form.regionId}
              onChange={handleChange}
              required
              className="w-full border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-1 focus:ring-pink-500 focus:border-pink-500 transition-colors duration-300 appearance-none"
            >
              <option value="">Wybierz region</option>
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
          <label htmlFor="countryId" className="block text-sm font-medium">
            Kraj
          </label>
          <div className="relative">
            <select
              id="countryId"
              name="countryId"
              value={form.countryId}
              onChange={handleChange}
              required
              disabled={!form.regionId}
              className="w-full border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-1 focus:ring-pink-500 focus:border-pink-500 transition-colors duration-300 appearance-none"
            >
              <option value="">Wybierz region, a potem kraj</option>
              {countries.map((c) => (
                <option key={c.countryId} value={c.countryId}>
                  {c.name}
                </option>
              ))}
            </select>
            <FaChevronDown className="pointer-events-none absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-400" />
          </div>
        </div>
        <div>
          <label htmlFor="city" className="block text-sm font-medium">
            Miasto
          </label>
          <input
            id="city"
            name="city"
            value={form.city}
            onChange={handleChange}
            required
            className="w-full border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-1 focus:ring-pink-500 focus:border-pink-500 transition-colors duration-300"
          />
        </div>
        <div>
          <label htmlFor="postalCode" className="block text-sm font-medium">
            Kod pocztowy
          </label>
          <input
            id="postalCode"
            name="postalCode"
            value={form.postalCode}
            onChange={handleChange}
            required
            className="w-full border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-1 focus:ring-pink-500 focus:border-pink-500 transition-colors duration-300"
          />
        </div>
        <div>
          <label htmlFor="street" className="block text-sm font-medium">
            Ulica
          </label>
          <input
            id="street"
            name="street"
            value={form.street}
            onChange={handleChange}
            required
            className="w-full border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-1 focus:ring-pink-500 focus:border-pink-500 transition-colors duration-300"
          />
        </div>
        <div>
          <label htmlFor="streetNumber" className="block text-sm font-medium">
            Numer domu
          </label>
          <input
            id="streetNumber"
            name="streetNumber"
            value={form.streetNumber}
            onChange={handleChange}
            required
            className="w-full border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-1 focus:ring-pink-500 focus:border-pink-500 transition-colors duration-300"
          />
        </div>
        <button
          type="submit"
          className="w-full py-3 bg-pink-500 hover:bg-pink-600 text-white rounded-lg transition cursor-pointer"
        >
          {isEdit ? "Zaktualizuj Magazyn" : "Stwórz Magazyn"}
        </button>
      </form>
    </div>
  );
};

export default WarehousesForm;
