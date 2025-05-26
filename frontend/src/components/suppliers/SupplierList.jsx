import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link } from "react-router-dom";
import { fetchSuppliers } from "../../features/suppliers/suppliersSlice";
import { FaSearch, FaEye, FaChevronDown } from "react-icons/fa";

const SupplierList = () => {
  const dispatch = useDispatch();
  const {
    list: suppliers,
    status,
    error,
  } = useSelector((state) => state.suppliers);
  const [searchTerm, setSearchTerm] = useState("");
  const [sortOption, setSortOption] = useState("");
  useEffect(() => {
    dispatch(fetchSuppliers());
  }, [dispatch]);
  const filtered = suppliers
    .filter((s) => s.name.toLowerCase().includes(searchTerm.toLowerCase()))
    .sort((a, b) => {
      switch (sortOption) {
        case "name":
          return a.name.localeCompare(b.name);
        case "name-reverse":
          return b.name.localeCompare(a.name);
        case "email":
          return a.email.localeCompare(b.email);
        case "email-reverse":
          return b.email.localeCompare(a.email);
        case "phone":
          return a.phoneNumber.localeCompare(b.phoneNumber);
        case "phone-reverse":
          return b.phoneNumber.localeCompare(a.phoneNumber);
        case "address":
          return a.address.localeCompare(b.address);
        case "address-reverse":
          return b.address.localeCompare(a.address);
        case "transactions":
          return a.transactionsCount - b.transactionsCount;
        case "transactions-reverse":
          return b.transactionsCount - a.transactionsCount;
        default:
          return 0;
      }
    });
  return (
    <>
      <form className="flex justify-between items-center space-x-4">
        <div>
          <label className="block text-sm font-medium">Nazwa</label>
          <div className="flex items-center border border-gray-300 rounded-lg px-3 py-2">
            <FaSearch className="text-gray-500 mr-2" />
            <input
              type="text"
              placeholder="Search suppliers..."
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
              <option value="transactions">Liczba transakcji (rosnąco)</option>
              <option value="transactions-reverse">
                Liczba transakcji (malejąco)
              </option>
            </select>
            <FaChevronDown className="pointer-events-none absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-400" />
          </div>
        </div>
      </form>
      {status === "loading" ? (
        <p>Ładowanie...</p>
      ) : status === "failed" ? (
        <p className="text-red-500">Error: {error}</p>
      ) : (
        <div className="bg-white rounded-lg shadow overflow-auto">
          <div className="hidden sm:grid grid-cols-6 gap-4 p-4 bg-gray-50 text-xs font-medium text-gray-500 uppercase tracking-wider">
            <div>Nazwa</div>
            <div>E-mail</div>
            <div>Nr. telefonu</div>
            <div>Adres</div>
            <div className="text-right">Liczba transakcji</div>
            <div className="text-center">Akcje</div>
          </div>
          <div className="divide-y divide-gray-200">
            {filtered.map((sup) => (
              <div
                key={sup.supplierId}
                className="grid grid-cols-1 sm:grid-cols-6 items-center gap-4 p-4 hover:bg-pink-50 transition-colors"
              >
                <div className="font-medium text-pink-600">
                  <Link
                    to={`/suppliers/${sup.supplierId}`}
                    className="hover:underline"
                  >
                    {sup.name}
                  </Link>
                </div>
                <div className="text-sm text-gray-700">{sup.email}</div>
                <div className="text-sm text-gray-700">{sup.phoneNumber}</div>
                <div className="text-sm text-gray-700">
                  {typeof sup.address === "string"
                    ? sup.address
                    : `${sup.address.street} ${sup.address.streetNumber}, ${sup.address.postalCode} ${sup.address.city}, ${sup.address.country}`}
                </div>
                <div className="text-sm text-gray-700 text-right">
                  {sup.transactionsCount}
                </div>
                <div className="flex justify-center text-gray-600">
                  <Link
                    to={`/suppliers/${sup.supplierId}`}
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
    </>
  );
};

export default SupplierList;
