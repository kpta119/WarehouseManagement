const DateInput = ({ label, value, setValue }) => (
  <div>
    <label className="block text-sm font-medium">{label}</label>
    <input
      type="date"
      name="fromDate"
      value={value}
      onChange={setValue}
      className="border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-1 focus:ring-pink-500 focus:border-pink-500 transition-colors duration-300"
    />
  </div>
);

export default DateInput;
