import { useState } from "react";
import { FaGlobe } from "react-icons/fa";
import RegionList from "../components/geography/RegionList";
import CountryList from "../components/geography/CountryList";

const GeographyPage = () => {
  const [selectedRegion, setSelectedRegion] = useState({});
  return (
    <div className="space-y-6">
      <div className="flex items-center space-x-2">
        <FaGlobe className="text-pink-500 w-6 h-6" />
        <h1 className="text-2xl font-semibold text-gray-800">
          Regions and Countries Lists
        </h1>
      </div>
      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
        <RegionList
          selectedRegion={selectedRegion}
          onSelect={setSelectedRegion}
        />
        <CountryList
          regionId={selectedRegion.id}
          regionName={selectedRegion.name}
        />
      </div>
    </div>
  );
};

export default GeographyPage;
