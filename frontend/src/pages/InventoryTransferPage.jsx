import { useState, useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { fetchWarehouses } from "../features/warehouses/warehousesSlice";
import { transferInventory } from "../features/inventory/transferSlice";
import { searchProducts } from "../api/products";
import { FaPlus, FaTrash } from "react-icons/fa";

const InventoryTransferPage = () => {
  const dispatch = useDispatch();
  const { list: warehouses } = useSelector((s) => s.warehouses);
  const { status, error, transaction } = useSelector(
    (s) => s.inventory.transfer
  );
  const [form, setForm] = useState({
    fromWarehouseId: "",
    toWarehouseId: "",
    items: [{ productId: "", quantity: "" }],
  });
  const [products, setProducts] = useState([]);
  useEffect(() => {
    dispatch(fetchWarehouses());
    searchProducts({}).then((res) => setProducts(res.data));
  }, [dispatch]);
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
  const handleSubmit = (e) => {
    e.preventDefault();
    const itemsPayload = {};
    form.items.forEach(({ productId, quantity }) => {
      if (productId && quantity) {
        itemsPayload[productId] = Number(quantity);
      }
    });
    dispatch(
      transferInventory({
        fromWarehouseId: Number(form.fromWarehouseId),
        toWarehouseId: Number(form.toWarehouseId),
        items: itemsPayload,
      })
    );
  };
  return (
    <div className="max-w-3xl mx-auto space-y-6">
      <h1 className="text-3xl font-semibold">Transfer Inventory</h1>
      <form
        onSubmit={handleSubmit}
        className="bg-white p-6 rounded-lg shadow space-y-4"
      >
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div>
            <label className="block text-sm font-medium mb-1">
              From Warehouse
            </label>
            <select
              required
              value={form.fromWarehouseId}
              onChange={(e) =>
                setForm((f) => ({ ...f, fromWarehouseId: e.target.value }))
              }
              className="w-full border-gray-300 rounded-lg px-3 py-2 focus:ring-pink-500 focus:border-pink-500"
            >
              <option value="">Select source</option>
              {warehouses.map((w) => (
                <option key={w.warehouseId} value={w.warehouseId}>
                  {w.name}
                </option>
              ))}
            </select>
          </div>
          <div>
            <label className="block text-sm font-medium mb-1">
              To Warehouse
            </label>
            <select
              required
              value={form.toWarehouseId}
              onChange={(e) =>
                setForm((f) => ({ ...f, toWarehouseId: e.target.value }))
              }
              className="w-full border-gray-300 rounded-lg px-3 py-2 focus:ring-pink-500 focus:border-pink-500"
            >
              <option value="">Select destination</option>
              {warehouses.map((w) => (
                <option key={w.warehouseId} value={w.warehouseId}>
                  {w.name}
                </option>
              ))}
            </select>
          </div>
        </div>
        <div className="space-y-3">
          <label className="text-sm font-medium">Products & Quantities</label>
          {form.items.map((item, idx) => (
            <div key={idx} className="grid grid-cols-5 gap-2 items-end">
              <div className="col-span-3">
                <select
                  required
                  value={item.productId}
                  onChange={(e) =>
                    handleItemChange(idx, "productId", e.target.value)
                  }
                  className="w-full border-gray-300 rounded-lg px-3 py-2 focus:ring-pink-500 focus:border-pink-500"
                >
                  <option value="">Select product</option>
                  {products.map((p) => (
                    <option key={p.productId} value={p.productId}>
                      {p.name}
                    </option>
                  ))}
                </select>
              </div>
              <div>
                <input
                  type="number"
                  min="1"
                  required
                  placeholder="Qty"
                  value={item.quantity}
                  onChange={(e) =>
                    handleItemChange(idx, "quantity", e.target.value)
                  }
                  className="w-full border-gray-300 rounded-lg px-3 py-2 focus:ring-pink-500 focus:border-pink-500"
                />
              </div>
              <button
                type="button"
                onClick={() => handleRemoveRow(idx)}
                className="flex justify-center align-center text-red-500 hover:text-red-700"
              >
                <FaTrash />
              </button>
            </div>
          ))}
          <button
            type="button"
            onClick={handleAddRow}
            className="flex items-center text-pink-600 hover:text-pink-800"
          >
            <FaPlus className="mr-1" /> Add another product
          </button>
        </div>
        <div className="pt-4 border-t">
          {error && <p className="text-red-500 mb-2">Error: {error}</p>}
          {status === "succeeded" && (
            <p className="text-green-600 mb-2">
              Transferred successfully! Transaction ID:{" "}
              {transaction?.transactionId}
            </p>
          )}
          <button
            type="submit"
            disabled={status === "loading"}
            className="w-full py-3 bg-pink-500 hover:bg-pink-600 text-white rounded-lg transition disabled:opacity-50"
          >
            {status === "loading" ? "Processing..." : "Transfer Inventory"}
          </button>
        </div>
      </form>
    </div>
  );
};

export default InventoryTransferPage;
