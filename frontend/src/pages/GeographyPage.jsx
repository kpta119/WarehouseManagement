import { useState, useEffect } from "react";
import { listRegions, listCountries } from "../api/geography";
import { FaGlobe } from "react-icons/fa";
const GeographyPage = () => {
  const [regions, setRegions] = useState([]);
  const [countries, setCountries] = useState([]);
  const [selectedRegion, setSelectedRegion] = useState(null);
  useEffect(() => {
    listRegions().then((res) => setRegions(res.data));
  }, []);
  useEffect(() => {
    if (selectedRegion) {
      listCountries(selectedRegion).then((res) => setCountries(res.data));
    } else {
      setCountries([]);
    }
  }, [selectedRegion]);
  return (
    <div className="space-y-6">
      <div className="flex items-center space-x-2">
        <FaGlobe className="text-pink-500 w-6 h-6" />
        <h1 className="text-3xl font-semibold">Geography</h1>
      </div>
      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
        <div className="bg-white p-6 rounded-lg shadow">
          <h2 className="text-xl font-semibold mb-4">Regions</h2>
          <ul className="space-y-2">
            {regions.map((r) => (
              <li key={r.regionId}>
                <button
                  onClick={() => setSelectedRegion(r.regionId)}
                  className={`
                    w-full text-left px-4 py-2 rounded-lg transition 
                    ${
                      selectedRegion === r.regionId
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
        <div className="bg-white p-6 rounded-lg shadow">
          <h2 className="text-xl font-semibold mb-4">
            Countries{" "}
            {selectedRegion &&
              `in ${regions.find((r) => r.regionId === selectedRegion)?.name}`}
          </h2>

          {!selectedRegion ? (
            <p className="text-gray-500">
              Select a region to view its countries.
            </p>
          ) : countries.length > 0 ? (
            <ul className="space-y-2">
              {countries.map((c) => (
                <li
                  key={c.countryId}
                  className="px-4 py-2 border border-gray-200 rounded-lg"
                >
                  {c.name}
                </li>
              ))}
            </ul>
          ) : (
            <p className="text-gray-500">No countries found for this region.</p>
          )}
        </div>
      </div>
    </div>
  );
};

export default GeographyPage;
