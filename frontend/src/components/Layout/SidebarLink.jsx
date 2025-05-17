import { NavLink } from "react-router-dom";

const SidebarLink = ({ to, icon: Icon, label }) => {
  return (
    <NavLink
      to={to}
      className={({ isActive }) => `
        flex items-center p-2
        border-2 rounded-lg
        transition-colors duration-300 ${
          isActive
            ? "bg-pink-100 text-pink-800 border-pink-300"
            : "text-gray-600 border-transparent hover:bg-pink-100 hover:text-pink-800 hover:border-pink-300"
        }`}
    >
      <Icon className="mr-4 text-xl" />
      {label}
    </NavLink>
  );
};

export default SidebarLink;
