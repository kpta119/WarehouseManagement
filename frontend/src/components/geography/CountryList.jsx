import { useState, useEffect } from "react";
import { listCountries } from "../../api/geography";

const CountryList = ({ regionId, regionName }) => {
  const [countries, setCountries] = useState([]);
  useEffect(() => {
    if (regionId) {
      listCountries(regionId).then((res) => setCountries(res.data));
    } else {
      setCountries([]);
    }
  }, [regionId]);
  return (
    <div className="bg-white p-6 rounded-lg shadow">
      <h2 className="text-xl font-semibold mb-4">
        Kraje {regionId ? `— wybrano region ${regionName}` : ""}
      </h2>
      {!regionId ? (
        <p className="text-gray-500">Wybierz region, aby zobaczyć kraje.</p>
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
        <p className="text-gray-500">Brak krajów w tym regionie.</p>
      )}
    </div>
  );
};

export default CountryList;
