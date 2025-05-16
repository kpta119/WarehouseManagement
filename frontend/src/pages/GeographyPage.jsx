import { useLocation } from "react-router-dom";

const GeographyPage = () => {
  const { search } = useLocation();
  return (
    <div>
      <h1>Geography Page</h1>
      <p>Query: {search}</p>
    </div>
  );
};

export default GeographyPage;
