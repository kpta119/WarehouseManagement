import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { fetchDashboardSummary } from "../features/dashboard/summarySlice";
import SummaryTiles from "../components/Dashboard/SummaryTiles";
import Charts from "../components/Dashboard/Charts";

const DashboardPage = () => {
  const dispatch = useDispatch();
  const selectedWarehouse = useSelector((s) => s.selectedWarehouse);
  const { data: summary, status, error } = useSelector((s) => s.dashboard);
  useEffect(() => {
    dispatch(fetchDashboardSummary(selectedWarehouse));
  }, [dispatch, selectedWarehouse]);
  console.log(summary);
  if (status === "loading" || status === "idle") return <p>Ładowanie...</p>;
  if (status === "failed") return <p className="text-red-500">{error}</p>;
  return (
    <div className="space-y-6">
      <SummaryTiles summary={summary} />
      <Charts summary={summary} />
    </div>
  );
};

export default DashboardPage;
