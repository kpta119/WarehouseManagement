import { useState, useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { fetchWarehouses } from "../../features/warehouses/warehousesSlice";
import { deliverInventory } from "../../features/inventory/deliverySlice";
import { fetchProducts } from "../../features/products/productsSlice";
import { fetchClients } from "../../features/clients/clientsSlice";
import { fetchEmployees } from "../../features/employees/employeesSlice";
import { FaChevronDown, FaPlus, FaTrash, FaTruck } from "react-icons/fa";
import Spinner from "../helper/Spinner";
import { Link } from "react-router-dom";
import { currencyFormatter } from "../../utils/helpers";

const DeliveryPage = () => {
  const dispatch = useDispatch();
  const { list: dataWarehouses } = useSelector((s) => s.warehouses);
  const { content: warehouses } = dataWarehouses;
  const { list: dataClients } = useSelector((s) => s.clients);
  const { content: clients } = dataClients;
  const { list: dataProducts } = useSelector((s) => s.products);
  const { content: products } = dataProducts;
  const { list: dataEmployees } = useSelector((s) => s.employees);
  const { content: employees } = dataEmployees;
  const { status, error, transaction } = useSelector(
    (s) => s.inventory.delivery
  );
  const [form, setForm] = useState({
    warehouseId: "",
    clientId: "",
    employeeId: "",
    items: [{ productId: "", quantity: "" }],
  });
  useEffect(() => {
    dispatch(
      fetchWarehouses({
        all: true,
      })
    );
    dispatch(
      fetchClients({
        all: true,
      })
    );
    dispatch(
      fetchProducts({
        all: true,
      })
    );
  }, [dispatch]);
  useEffect(() => {
    dispatch(
      fetchEmployees({
        warehouseId: form.warehouseId ? Number(form.warehouseId) : undefined,
        all: true,
      })
    );
  }, [dispatch, form.warehouseId]);
  const handleAddRow = () =>
    setForm((f) => ({
      ...f,
      items: [...f.items, { productId: "", quantity: "" }],
    }));
  const handleRemoveRow = (idx) =>
    setForm((f) => ({
      ...f,
      items: f.items.filter((_, i) => i !== idx),
    }));
  const handleItemChange = (idx, field, value) => {
    const items = [...form.items];
    items[idx][field] = value;
    setForm((f) => ({ ...f, items }));
  };
  const handleSubmit = async (e) => {
    e.preventDefault();
    const itemsPayload = {};
    form.items.forEach(({ productId, quantity }) => {
      if (productId && quantity) {
        itemsPayload[productId] = Number(quantity);
      }
    });
    const result = await dispatch(
      deliverInventory({
        warehouseId: Number(form.warehouseId),
        clientId: Number(form.clientId),
        employeeId: Number(form.employeeId),
        items: itemsPayload,
      })
    );
    if (result.meta.requestStatus === "fulfilled") {
      setForm(() => ({
        warehouseId: "",
        clientId: "",
        employeeId: "",
        items: [{ productId: "", quantity: "" }],
      }));
    }
  };
  return (
    <div className="max-w-3xl mx-auto space-y-6">
      <div className="flex items-center space-x-2">
        <div className="flex items-center w-1/2 space-x-2">
          <FaTruck className="text-pink-500 w-6 h-6" />
          <h1 className="text-2xl font-semibold text-gray-800">
            Goods Delivery
          </h1>
        </div>
        {status === "failed" && (
          <p className="text-red-500 ml-4">Error: {error}</p>
        )}
        {status === "succeeded" && (
          <p className="text-green-600 ml-4">
            Successfuly delivered! Transaction ID:{" "}
            <Link
              to={`/transactions/${transaction?.transactionId}`}
              className="text-pink-600 hover:underline"
            >
              {transaction?.transactionId}
            </Link>
          </p>
        )}
      </div>
      <form
        onSubmit={handleSubmit}
        className="bg-white p-6 rounded-lg shadow space-y-4"
      >
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div>
            <label className="block text-sm font-medium mb-1">Warehouse</label>
            <div className="relative">
              <select
                required
                value={form.warehouseId}
                onChange={(e) =>
                  setForm((f) => ({ ...f, warehouseId: e.target.value }))
                }
                className="w-full border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-1 focus:ring-pink-500 focus:border-pink-500 transition-colors duration-300 appearance-none"
              >
                <option value="">Choose Warehouse</option>
                {warehouses.map((w) => (
                  <option key={w.warehouseId} value={w.warehouseId}>
                    {w.name}
                  </option>
                ))}
              </select>
              <FaChevronDown className="pointer-events-none absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-400" />
            </div>
          </div>
          <div>
            <label className="block text-sm font-medium mb-1">Client</label>
            <div className="relative">
              <select
                required
                value={form.clientId}
                onChange={(e) =>
                  setForm((f) => ({ ...f, clientId: e.target.value }))
                }
                className="w-full border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-1 focus:ring-pink-500 focus:border-pink-500 transition-colors duration-300 appearance-none"
              >
                <option value="">Choose Client</option>
                {clients.map((c) => (
                  <option key={c.clientId} value={c.clientId}>
                    {c.name}
                  </option>
                ))}
              </select>
              <FaChevronDown className="pointer-events-none absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-400" />
            </div>
          </div>
          <div>
            <label className="block text-sm font-medium mb-1">Employee</label>
            <div className="relative">
              <select
                required
                value={form.employeeId}
                onChange={(e) =>
                  setForm((f) => ({ ...f, employeeId: e.target.value }))
                }
                className="w-full border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-1 focus:ring-pink-500 focus:border-pink-500 transition-colors duration-300 appearance-none"
              >
                <option value="">Choose Employee</option>
                {employees.map((e) => (
                  <option key={e.employeeId} value={e.employeeId}>
                    {e.name} {e.surname}
                  </option>
                ))}
              </select>
              <FaChevronDown className="pointer-events-none absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-400" />
            </div>
          </div>
        </div>
        <div className="space-y-3">
          <label className="text-sm font-medium">
            Products and their amounts
          </label>
          {form.items.map((item, idx) => (
            <div key={idx} className="grid grid-cols-5 gap-2 items-end">
              <div className="col-span-2">
                <div className="relative">
                  <select
                    required
                    value={item.productId}
                    onChange={(e) =>
                      handleItemChange(idx, "productId", e.target.value)
                    }
                    className="w-full border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-1 focus:ring-pink-500 focus:border-pink-500 transition-colors duration-300 appearance-none"
                  >
                    <option value="">Choose Product</option>
                    {products.map((p) => (
                      <option key={p.productId} value={p.productId}>
                        {p.name}
                      </option>
                    ))}
                  </select>
                  <FaChevronDown className="pointer-events-none absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-400" />
                </div>
              </div>
              <div>
                <input
                  type="number"
                  min="1"
                  required
                  placeholder="Amount"
                  value={item.quantity}
                  onChange={(e) =>
                    handleItemChange(idx, "quantity", e.target.value)
                  }
                  className="w-full border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-1 focus:ring-pink-500 focus:border-pink-500 transition-colors duration-300 appearance-none"
                />
              </div>
              <div className="text-gray-800 flex items-center justify-center h-full">
                {products.find(
                  (product) => product.productId === +item.productId
                ) && (
                  <p>
                    {currencyFormatter(
                      products.find(
                        (product) => product.productId === +item.productId
                      )?.unitPrice * item.quantity
                    )}
                  </p>
                )}
              </div>
              <button
                type="button"
                onClick={() => handleRemoveRow(idx)}
                className="flex justify-center items-center text-red-500 hover:text-red-700 h-full cursor-pointer transition duration-200"
              >
                <FaTrash />
              </button>
            </div>
          ))}
          <button
            type="button"
            onClick={handleAddRow}
            className="flex items-center text-pink-600 hover:text-pink-800 mt-4 transition duration-200"
          >
            <FaPlus className="mr-1" /> Add another Product
          </button>
        </div>
        <div className="pt-4 border-t">
          <h3 className="text-lg font-semibold mb-2">
            Total:{" "}
            <span className="text-pink-600">
              {currencyFormatter(
                form.items.reduce((total, item) => {
                  const product = products.find(
                    (p) => p.productId === +item.productId
                  );
                  return (
                    total + (product ? product.unitPrice * item.quantity : 0)
                  );
                }, 0)
              )}
            </span>
          </h3>
          <button
            type="submit"
            disabled={status === "loading"}
            className="w-full py-3 bg-pink-500 hover:bg-pink-600 text-white rounded-lg transition disabled:opacity-50 duration-200"
          >
            {status === "loading" ? <Spinner color="white" /> : "Deliver Goods"}
          </button>
        </div>
      </form>
    </div>
  );
};

export default DeliveryPage;
