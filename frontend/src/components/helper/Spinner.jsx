const Spinner = ({ color }) => (
  <div className="flex justify-center items-center">
    <div
      className={`animate-spin h-8 w-8 border-4 ${
        color === "white" ? "border-white-500" : "border-pink-500"
      } border-t-transparent rounded-full`}
    />
  </div>
);

export default Spinner;
