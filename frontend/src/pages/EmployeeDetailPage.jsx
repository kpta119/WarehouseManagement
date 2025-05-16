import { useParams } from "react-router-dom";

const EmployeeDetailPage = () => {
  const { id } = useParams();
  return (
    <div>
      <h1>Employee Detail Page</h1>
      <p>Params: {JSON.stringify({ id })}</p>
    </div>
  );
};

export default EmployeeDetailPage;
