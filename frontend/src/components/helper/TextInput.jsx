import { FaSearch } from "react-icons/fa";

const TextInput = ({ label, placeholder, value, setValue }) => (
  <div>
    <label className="block text-sm font-medium">{label}</label>
    <div className="flex border-gray-300 items-center border rounded-lg px-3 py-2 focus-within:ring-1 focus-within:ring-pink-500 focus-within:border-pink-500 transition-colors duration-300">
      <FaSearch className="text-gray-500 mr-2" />
      <input
        type="text"
        placeholder={placeholder}
        className="focus:outline-none"
        value={value}
        onChange={(e) => setValue(e.target.value)}
      />
    </div>
  </div>
);

export default TextInput;
