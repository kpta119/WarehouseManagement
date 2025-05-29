import { FaMinus, FaPlus } from "react-icons/fa";

const NumberInput = ({ label, placeholder, isMinus, value, setValue }) => (
  <div>
    <label className="block text-sm font-medium">{label}</label>
    <div className="flex border border-gray-300 rounded-lg items-center px-3 py-2 focus-within:ring-1 focus-within:ring-pink-500 focus-within:border-pink-500 transition-colors duration-300">
      {isMinus ? (
        <FaMinus className="text-gray-500 mr-2" />
      ) : (
        <FaPlus className="text-gray-500 mr-2" />
      )}
      <input
        type="number"
        min="0"
        placeholder={placeholder}
        className="w-full focus:outline-none"
        value={value}
        onChange={(e) => setValue(e.target.value)}
      />
    </div>
  </div>
);

export default NumberInput;
