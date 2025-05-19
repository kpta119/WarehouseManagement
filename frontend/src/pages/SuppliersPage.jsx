import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link } from "react-router-dom";
import { fetchSuppliers } from "../features/suppliers/suppliersSlice";
import { FaSearch, FaPlus, FaEye } from "react-icons/fa";

const SuppliersPage = () => {
  const dispatch = useDispatch();
  const {
    list: suppliers,
    status,
    error,
  } = useSelector((state) => state.suppliers);
  const [searchTerm, setSearchTerm] = useState("");
  useEffect(() => {
    dispatch(fetchSuppliers());
  }, [dispatch]);
  const filtered = suppliers.filter((s) =>
    s.name.toLowerCase().includes(searchTerm.toLowerCase())
  );
  return (
    <div className="space-y-6">
      <div className="flex justify-between items-center">
        <h1 className="text-2xl font-semibold">Suppliers</h1>
        <Link
          to="/suppliers/new"
          className="flex items-center bg-pink-500 hover:bg-pink-600 text-white px-4 py-2 rounded-lg transition"
        >
          <FaPlus className="mr-2" /> New Supplier
        </Link>
      </div>
      <form className="flex items-center border border-gray-300 rounded-lg px-3 py-2 w-1/2">
        <FaSearch className="text-gray-500 mr-2" />
        <input
          type="text"
          placeholder="Search suppliers..."
          className="w-full focus:outline-none"
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
        />
      </form>
      {status === "loading" ? (
        <p>Loading...</p>
      ) : status === "failed" ? (
        <p className="text-red-500">Error: {error}</p>
      ) : (
        <div className="bg-white rounded-lg shadow overflow-auto">
          <div className="hidden sm:grid grid-cols-5 gap-4 p-4 bg-gray-50 text-xs font-medium text-gray-500 uppercase tracking-wider">
            <div>Name</div>
            <div>Email</div>
            <div>Phone</div>
            <div>Address</div>
            <div className="text-center">Actions</div>
          </div>
          <div className="divide-y divide-gray-200">
            {filtered.map((sup) => (
              <div
                key={sup.supplierId}
                className="grid grid-cols-1 sm:grid-cols-5 items-center gap-4 p-4 hover:bg-pink-50 transition-colors"
              >
                <div className="font-medium text-pink-600">
                  <Link to={`/suppliers/${sup.supplierId}`}>{sup.name}</Link>
                </div>
                <div className="text-sm text-gray-700">{sup.email}</div>
                <div className="text-sm text-gray-700">{sup.phoneNumber}</div>
                <div className="text-sm text-gray-700">
                  {typeof sup.address === "string"
                    ? sup.address
                    : `${sup.address.street} ${sup.address.streetNumber}, ${sup.address.postalCode} ${sup.address.city}, ${sup.address.country}`}
                </div>
                <div className="flex justify-center text-gray-600">
                  <Link to={`/suppliers/${sup.supplierId}`}>
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

export default SuppliersPage;
