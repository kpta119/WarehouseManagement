import { useEffect } from "react";
import { fetchRegions } from "../../features/geography/geographySlice";
import { useDispatch, useSelector } from "react-redux";
import Spinner from "../helper/Spinner";

const RegionList = ({ selectedRegion, onSelect }) => {
  const dispatch = useDispatch();
  const { regions, status, error } = useSelector((state) => state.geography);
  useEffect(() => {
    dispatch(fetchRegions());
  }, [dispatch]);
  return status === "loading" || status === "idle" ? (
    <Spinner />
  ) : status === "failed" ? (
    <p className="text-red-500">Error: {error}</p>
  ) : (
    <div className="bg-white p-6 rounded-lg shadow">
      <h2 className="text-xl font-semibold mb-4">Regions</h2>
      <ul className="space-y-2">
        {regions.map((r) => (
          <li
            key={r.id}
            className={`px-4 py-2 border border-gray-200 rounded-lg transition cursor-pointer
            ${
              selectedRegion.id === r.id
                ? "bg-pink-100 text-pink-800"
                : "hover:bg-gray-100 transition duration-200"
            }`}
            onClick={() => onSelect({ id: r.id, name: r.name })}
          >
            <button
              className={`
                w-full text-left rounded-lg cursor-pointer
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
