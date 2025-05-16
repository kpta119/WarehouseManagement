import { useParams } from "react-router-dom";

const TransactionDetailPage = () => {
  const { id } = useParams();
  return (
    <div>
      <h1>Transaction Detail Page</h1>
      <p>Params: {JSON.stringify({ id })}</p>
    </div>
  );
};

export default TransactionDetailPage;
