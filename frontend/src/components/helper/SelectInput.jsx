import { FaChevronDown } from "react-icons/fa";

const SelectInput = ({ children, label, value, setValue }) => (
  <div>
    <label className="block text-sm font-medium">{label}</label>
    <div className="relative">
      <select
        className="border border-gray-300 rounded-lg px-3 py-2 pr-12 appearance-none focus:outline-none focus:ring-1 focus:ring-pink-500 focus:border-pink-500 transition-colors duration-300"
        value={value}
        onChange={(e) => setValue(e.target.value)}
      >
        {children}
      </select>
      <FaChevronDown className="pointer-events-none absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-400" />
    </div>
  </div>
);

export default SelectInput;
