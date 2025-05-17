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
  } = summary;
  const tiles = [
    {
      icon: <FaBox className="text-pink-400 w-6 h-6" />,
      label: "Products",
      value: productsCount,
    },
    {
      icon: <FaTags className="text-pink-400 w-6 h-6" />,
      label: "Categories",
      value: categoriesCount,
    },
    {
      icon: <FaTruckLoading className="text-pink-400 w-6 h-6" />,
      label: "Receipts (M)",
      value: monthlyReceipts,
    },
    {
      icon: <FaTruckPickup className="text-pink-400 w-6 h-6" />,
      label: "Deliveries (M)",
      value: monthlyDeliveries,
    },
    {
      icon: <FaExclamationTriangle className="text-pink-400 w-6 h-6" />,
      label: "Low stock",
      value: lowStockCount,
    },
    {
      icon: <FaStar className="text-pink-400 w-6 h-6" />,
      label: "Top product",
      value: topProduct,
    },
    {
      icon: <FaDollarSign className="text-pink-400 w-6 h-6" />,
      label: "Inventory $",
      value: inventoryValue?.toLocaleString(undefined, {
        style: "currency",
        currency: "USD",
        maximumFractionDigits: 0,
      }),
    },
    {
      icon: <FaSyncAlt className="text-pink-400 w-6 h-6" />,
      label: "Turnover LW",
      value: turnoverLastWeek?.toLocaleString(undefined, {
        style: "currency",
        currency: "USD",
        maximumFractionDigits: 0,
      }),
    },
    {
      icon: <FaCalendarCheck className="text-pink-400 w-6 h-6" />,
      label: "Last receipt",
      value: new Date(lastReceiptDate).toLocaleDateString(),
    },
    {
      icon: <FaCalendarDay className="text-pink-400 w-6 h-6" />,
      label: "Last delivery",
      value: new Date(lastDeliveryDate).toLocaleDateString(),
    },
  ];
  return (
    <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-5 gap-4">
      {tiles.map(({ icon, label, value }) => (
        <div
          key={label}
          className="bg-white rounded-lg shadow p-4 flex flex-col items-start"
        >
          <div className="flex items-center mb-2">
            {icon}
            <p className="ml-2 text-sm text-gray-400">{label}</p>
          </div>
          <p className="text-2xl font-semibold text-gray-800">{value}</p>
        </div>
      ))}
    </div>
  );
};

export default SummaryTiles;
