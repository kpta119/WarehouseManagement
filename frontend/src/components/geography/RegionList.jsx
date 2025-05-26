import { useState, useEffect } from "react";
import { listRegions } from "../../api/geography";

const RegionList = ({ selectedRegion, onSelect }) => {
  const [regions, setRegions] = useState([]);
  useEffect(() => {
    listRegions().then((res) => setRegions(res.data));
  }, []);
  return (
    <div className="bg-white p-6 rounded-lg shadow">
      <h2 className="text-xl font-semibold mb-4">Regiony</h2>
      <ul className="space-y-2">
        {regions.map((r) => (
          <li key={r.id}>
            <button
              onClick={() => onSelect({ id: r.id, name: r.name })}
              className={`
                w-full text-left px-4 py-2 rounded-lg transition
                ${
                  selectedRegion.id === r.id
                    ? "bg-pink-100 text-pink-800"
                    : "hover:bg-gray-100"
                }
              `}
            >
              {r.name}
            </button>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default RegionList;
