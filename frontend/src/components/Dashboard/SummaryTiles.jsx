import { Link } from "react-router-dom";
import {
  FaBox,
  FaTags,
  FaTruckLoading,
  FaTruckPickup,
  FaExclamationTriangle,
  FaStar,
  FaDollarSign,
  FaSyncAlt,
  FaCalendarCheck,
  FaCalendarDay,
} from "react-icons/fa";

const SummaryTiles = ({ summary }) => {
  const {
    productsCount,
    categoriesCount,
    monthlyReceipts,
    monthlyDeliveries,
    lowStockCount,
    topProduct,
    inventoryValue,
    turnoverLastWeek,
    lastReceiptDate,
    lastDeliveryDate,
    lastReceiptId,
    lastDeliveryId,
    topProductId,
  } = summary;

  const tiles = [
    {
      icon: <FaBox className="text-pink-400 w-6 h-6" />,
      label: "Products",
      value: productsCount,
      to: "/products",
    },
    {
      icon: <FaTags className="text-pink-400 w-6 h-6" />,
      label: "Categories",
      value: categoriesCount,
      to: "/categories",
    },
    {
      icon: <FaTruckLoading className="text-pink-400 w-6 h-6" />,
      label: "Receipts (M)",
      value: monthlyReceipts,
      to: "/inventory/receive",
    },
    {
      icon: <FaTruckPickup className="text-pink-400 w-6 h-6" />,
      label: "Deliveries (M)",
      value: monthlyDeliveries,
      to: "/inventory/delivery",
    },
    {
      icon: <FaExclamationTriangle className="text-pink-400 w-6 h-6" />,
      label: "Low stock",
      value: lowStockCount,
      to: "/products?filter=low-stock",
    },
    {
      icon: <FaStar className="text-pink-400 w-6 h-6" />,
      label: "Top product",
      value: topProduct,
      to: topProductId ? `/products/${topProductId}` : "/products",
    },
    {
      icon: <FaDollarSign className="text-pink-400 w-6 h-6" />,
      label: "Inventory $",
      value: inventoryValue?.toLocaleString(undefined, {
        style: "currency",
        currency: "USD",
        maximumFractionDigits: 0,
      }),
      to: "/warehouses",
    },
    {
      icon: <FaSyncAlt className="text-pink-400 w-6 h-6" />,
      label: "Turnover LW",
      value: turnoverLastWeek?.toLocaleString(undefined, {
        style: "currency",
        currency: "USD",
        maximumFractionDigits: 0,
      }),
      to: "/transactions?period=last-week",
    },
    {
      icon: <FaCalendarCheck className="text-pink-400 w-6 h-6" />,
      label: "Last receipt",
      value: new Date(lastReceiptDate).toLocaleDateString(),
      to: lastReceiptId ? `/transactions/${lastReceiptId}` : "/transactions",
    },
    {
      icon: <FaCalendarDay className="text-pink-400 w-6 h-6" />,
      label: "Last delivery",
      value: new Date(lastDeliveryDate).toLocaleDateString(),
      to: lastDeliveryId ? `/transactions/${lastDeliveryId}` : "/transactions",
    },
  ];

  return (
    <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-5 gap-4 cursor-pointer">
      {tiles.map(({ icon, label, value, to }) => {
        const tileContent = (
          <div className="bg-white rounded-lg shadow p-4 flex flex-col items-start hover:bg-pink-50 transition-colors">
            <div className="flex items-center mb-2">
              {icon}
              <p className="ml-2 text-sm text-gray-400">{label}</p>
            </div>
            <p className="text-2xl font-semibold text-gray-800">{value}</p>
          </div>
        );
        return to ? (
          <Link key={label} to={to}>
            {tileContent}
          </Link>
        ) : (
          <div key={label}>{tileContent}</div>
        );
      })}
    </div>
  );
};

export default SummaryTiles;
