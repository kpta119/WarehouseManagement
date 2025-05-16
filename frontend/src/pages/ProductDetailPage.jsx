import { useParams, useLocation } from "react-router-dom";

const ProductDetailPage = () => {
  const { id } = useParams();
  const { search } = useLocation();
  return (
    <div>
      <h1>Product Detail Page</h1>
      <p>Params: {JSON.stringify({ id })}</p>
      <p>Query: {search}</p>
    </div>
  );
};

export default ProductDetailPage;
