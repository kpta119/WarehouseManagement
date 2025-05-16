import { useLocation } from "react-router-dom";

const TransactionsPage = () => {
  const { search } = useLocation();
  return (
    <div>
      <h1>Transactions Page</h1>
      <p>Query: {search}</p>
    </div>
  );
};

export default TransactionsPage;
