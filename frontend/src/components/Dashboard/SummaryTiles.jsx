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
import {
  currencyFormatter,
  dateFormatter,
  numberFormatter,
} from "../../utils/helpers";

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
      label: "Produkty",
      value: numberFormatter(productsCount),
      to: "/products",
    },
    {
      icon: <FaTags className="text-pink-400 w-6 h-6" />,
      label: "Kategorie",
      value: numberFormatter(categoriesCount),
      to: "/categories",
    },
    {
      icon: <FaTruckLoading className="text-pink-400 w-6 h-6" />,
      label: "Przyjęcia (mies.)",
      value: numberFormatter(monthlyReceipts),
      to: "/inventory/receive",
    },
    {
      icon: <FaTruckPickup className="text-pink-400 w-6 h-6" />,
      label: "Wydania (mies.)",
      value: numberFormatter(monthlyDeliveries),
      to: "/inventory/delivery",
    },
    {
      icon: <FaExclamationTriangle className="text-pink-400 w-6 h-6" />,
      label: "Niski stan",
      value: numberFormatter(lowStockCount),
      to: "/products?filter=low-stock",
    },
    {
      icon: <FaStar className="text-pink-400 w-6 h-6" />,
      label: "Bestseller",
      value: topProduct,
      to: topProductId ? `/products/${topProductId}` : "/products",
    },
    {
      icon: <FaDollarSign className="text-pink-400 w-6 h-6" />,
      label: "Wartość zapasów",
      value: currencyFormatter(inventoryValue),
      to: "/warehouses",
    },
    {
      icon: <FaSyncAlt className="text-pink-400 w-6 h-6" />,
      label: "Obrót (ost. tydz.)",
      value: currencyFormatter(turnoverLastWeek),
      to: "/transactions?period=last-week",
    },
    {
      icon: <FaCalendarCheck className="text-pink-400 w-6 h-6" />,
      label: "Ostatnie przyjęcie",
      value: dateFormatter(lastReceiptDate),
      to: lastReceiptId ? `/transactions/${lastReceiptId}` : "/transactions",
    },
    {
      icon: <FaCalendarDay className="text-pink-400 w-6 h-6" />,
      label: "Ostatnie wydanie",
      value: dateFormatter(lastDeliveryDate),
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
