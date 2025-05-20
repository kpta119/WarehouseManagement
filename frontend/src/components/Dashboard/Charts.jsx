import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  Tooltip,
  Legend,
  ResponsiveContainer,
  PieChart,
  Pie,
  Cell,
} from "recharts";

export default function Charts({ summary }) {
  const barData = [
    {
      name: "Miesięczne",
      Przyjęcia: summary.monthlyReceipts,
      Wydania: summary.monthlyDeliveries,
    },
  ];
  const inStock = summary.productsCount - summary.lowStockCount;
  const pieData = [
    { name: "Niski stan", value: summary.lowStockCount },
    { name: "W magazynie", value: inStock },
  ];
  const COLORS = ["#EF4444", "#10B981"];
  return (
    <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
      <div className="bg-white rounded-lg shadow p-6">
        <h2 className="text-lg font-semibold mb-4">Ruchy miesięczne</h2>
        <ResponsiveContainer width="100%" height={260}>
          <BarChart data={barData}>
            <XAxis dataKey="name" tick={{ fill: "#6B7280" }} />
            <YAxis tick={{ fill: "#6B7280" }} />
            <Tooltip />
            <Legend />
            <Bar dataKey="Przyjęcia" fill="#3B82F6" />
            <Bar dataKey="Wydania" fill="#8B5CF6" />
          </BarChart>
        </ResponsiveContainer>
      </div>
      <div className="bg-white rounded-lg shadow p-6">
        <h2 className="text-lg font-semibold mb-4">Stan zapasów</h2>
        <ResponsiveContainer width="100%" height={260}>
          <PieChart>
            <Pie
              data={pieData}
              dataKey="value"
              nameKey="name"
              innerRadius={50}
              outerRadius={80}
              label
            >
              {pieData.map((_, idx) => (
                <Cell key={idx} fill={COLORS[idx]} />
              ))}
            </Pie>
            <Tooltip />
            <Legend verticalAlign="bottom" height={36} />
          </PieChart>
        </ResponsiveContainer>
      </div>
    </div>
  );
}
