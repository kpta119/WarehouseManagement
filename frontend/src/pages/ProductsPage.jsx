import { Link } from "react-router-dom";
import { FaPlus, FaBoxOpen } from "react-icons/fa";
import ProductList from "../components/products/ProductList";

const ProductsPage = () => {
  return (
    <div className="space-y-6">
      <div className="flex justify-between items-center">
        <div className="flex items-center space-x-2">
          <FaBoxOpen className="text-pink-500 w-6 h-6" />
          <h1 className="text-2xl font-semibold text-gray-800">
            Products List
          </h1>
        </div>
        <Link
          to="/products/new"
          className="flex items-center bg-pink-500 hover:bg-pink-600 text-white px-4 py-2 rounded-lg transition"
        >
          <FaPlus className="mr-2" /> New Product
        </Link>
      </div>
      <ProductList />
    </div>
  );
};

export default ProductsPage;
