import { useParams } from "react-router-dom";

const WarehouseDetailPage = () => {
  const { id } = useParams();
  return (
    <div>
      <h1>Warehouse Detail Page</h1>
      <p>Params: {JSON.stringify({ id })}</p>
    </div>
  );
};

export default WarehouseDetailPage;
