import { FaClipboardList } from "react-icons/fa";
import TransactionList from "../components/transactions/TransactionList";

const TransactionsPage = () => {
  return (
    <div className="space-y-6">
      <div className="flex justify-between items-center">
        <div className="flex items-center space-x-2">
          <FaClipboardList className="text-pink-500 w-6 h-6" />
          <h1 className="text-2xl font-semibold text-gray-800">
            Lista transakcji
          </h1>
        </div>
      </div>
      <TransactionList />
    </div>
  );
};

export default TransactionsPage;
