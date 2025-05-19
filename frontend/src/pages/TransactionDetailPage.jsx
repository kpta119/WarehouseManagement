import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useParams, Link } from "react-router-dom";
import { fetchTransactionById } from "../features/transactions/transactionsSlice";
import { FaChevronLeft } from "react-icons/fa";
import { format } from "date-fns";

const TransactionDetailPage = () => {
  const { id } = useParams();
  const dispatch = useDispatch();
  const { current: tx } = useSelector((state) => state.transactions);
  useEffect(() => {
    dispatch(fetchTransactionById(id));
  }, [dispatch, id]);
  if (!tx) return <p>Loading...</p>;
  const {
    transactionId,
    date,
    description,
    type,
    employeeId,
    fromWarehouseId,
    toWarehouseId,
    clientId,
    supplierId,
    products,
  } = tx;
  return (
    <div className="max-w-4xl mx-auto bg-white p-6 rounded-lg shadow space-y-6">
      <Link to="/transactions" className="text-gray-600 hover:text-pink-500">
        <FaChevronLeft className="inline-block mr-2" /> Back to Transactions
      </Link>
      <h1 className="text-3xl font-semibold mt-2">
        Transaction #{transactionId}
      </h1>
      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
        <div>
          <strong>Date:</strong> {format(new Date(date), "yyyy-MM-dd")}
        </div>
        <div>
          <strong>Type:</strong> {type.replace(/_/g, " ")}
        </div>
        <div>
          <strong>Description:</strong> {description}
        </div>
        <div>
          <strong>Handled by:</strong>{" "}
          <Link to={`/employees/${employeeId}`} className="text-pink-600">
            Employee {employeeId}
          </Link>
        </div>
        {supplierId && (
          <div>
            <strong>Supplier:</strong>{" "}
            <Link to={`/suppliers/${supplierId}`} className="text-pink-600">
              Supplier {supplierId}
            </Link>
          </div>
        )}
        {clientId && (
          <div>
            <strong>Client:</strong>{" "}
            <Link to={`/clients/${clientId}`} className="text-pink-600">
              Client {clientId}
            </Link>
          </div>
        )}
        {fromWarehouseId && (
          <div>
            <strong>From Warehouse:</strong>{" "}
            <Link
              to={`/warehouses/${fromWarehouseId}`}
              className="text-pink-600"
            >
              Warehouse {fromWarehouseId}
            </Link>
          </div>
        )}
        {toWarehouseId && (
          <div>
            <strong>To Warehouse:</strong>{" "}
            <Link to={`/warehouses/${toWarehouseId}`} className="text-pink-600">
              Warehouse {toWarehouseId}
            </Link>
          </div>
        )}
      </div>
      {products && products.length > 0 && (
        <section>
          <h2 className="text-xl font-semibold mb-4">Products</h2>
          <div className="bg-gray-50 grid grid-cols-5 p-2 text-xs uppercase font-medium text-gray-500 rounded-t-lg">
            <div>Product</div>
            <div className="text-right">Quantity</div>
            <div className="text-right">Unit Price</div>
            <div className="text-right">Category</div>
            <div className="text-right">Total</div>
          </div>
          <div className="divide-y divide-gray-200">
            {products.map((p) => (
              <div
                key={p.productId}
                className="grid grid-cols-5 p-2 text-sm text-gray-700 items-center"
              >
                <div>
                  <Link
                    to={`/products/${p.productId}`}
                    className="text-pink-600 hover:underline"
                  >
                    {p.name}
                  </Link>
                </div>
                <div className="text-right">{p.quantity}</div>
                <div className="text-right">${p.unitPrice.toFixed(2)}</div>
                <div className="text-right">{p.categoryName}</div>
                <div className="text-right">
                  ${(p.quantity * p.unitPrice).toFixed(2)}
                </div>
              </div>
            ))}
          </div>
          <div className="text-right p-2">
            $
            {products
              .reduce((acc, p) => acc + p.quantity * p.unitPrice, 0)
              .toFixed(2)}
          </div>
        </section>
      )}
    </div>
  );
};

export default TransactionDetailPage;
