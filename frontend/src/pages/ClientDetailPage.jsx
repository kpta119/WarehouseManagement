import { useParams } from "react-router-dom";

const ClientDetailPage = () => {
  const { id } = useParams();
  return (
    <div>
      <h1>Client Detail Page</h1>
      <p>Params: {JSON.stringify({ id })}</p>
    </div>
  );
};

export default ClientDetailPage;
