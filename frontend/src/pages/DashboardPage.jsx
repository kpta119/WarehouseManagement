import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { fetchDashboardSummary } from "../features/dashboard/summarySlice";
import SummaryTiles from "../components/Dashboard/SummaryTiles";
import Charts from "../components/Dashboard/Charts";
import Spinner from "../components/helper/Spinner";

const DashboardPage = () => {
  const dispatch = useDispatch();
  const selectedWarehouse = useSelector((s) => s.selectedWarehouse);
  const { data: summary, status, error } = useSelector((s) => s.dashboard);
  useEffect(() => {
    dispatch(fetchDashboardSummary(selectedWarehouse));
  }, [dispatch, selectedWarehouse]);
  if (status === "loading" || status === "idle") return <Spinner />;
  if (status === "failed") return <p className="text-red-500">{error}</p>;
  if (!summary)
    return <p className="text-red-500">Brak danych do wyświetlenia.</p>;
  return (
    <div className="space-y-6">
      <SummaryTiles summary={summary} />
      <Charts summary={summary} />
    </div>
  );
};

export default DashboardPage;
