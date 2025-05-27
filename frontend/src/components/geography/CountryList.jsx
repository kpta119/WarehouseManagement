import { useEffect } from "react";
import { fetchCountries } from "../../features/geography/geographySlice";
import { useDispatch, useSelector } from "react-redux";
import Spinner from "../helper/Spinner";

const CountryList = ({ regionId, regionName }) => {
  const dispatch = useDispatch();
  const { countries, status, error } = useSelector((state) => state.geography);
  useEffect(() => {
    if (regionId) {
      dispatch(fetchCountries(regionId));
    }
  }, [dispatch, regionId]);
  return status === "loading" || status === "idle" ? (
    <Spinner />
  ) : status === "failed" ? (
    <p className="text-red-500">Error: {error}</p>
  ) : (
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
              key={c.id}
              className="px-4 py-2 border border-gray-200 rounded-lg hover:bg-gray-100 transition duration-200"
            >
              {c.name}
            </li>
          ))}
        </ul>
      ) : (
        <p className="text-red-500">Brak krajów w tym regionie.</p>
      )}
    </div>
  );
};

export default CountryList;
