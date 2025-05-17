import Logo from "../../assets/images/logo.png";
import SidebarLink from "./SidebarLink";
import { AiOutlineHome } from "react-icons/ai";
import {
  FaBoxOpen,
  FaTags,
  FaWarehouse,
  FaTruck,
  FaExchangeAlt,
  FaClipboardList,
  FaUsers,
  FaUserTie,
  FaUserFriends,
  FaGlobe,
} from "react-icons/fa";

export default function Sidebar() {
  return (
    <aside className="w-64 h-screen bg-white shadow-lg p-4 fixed">
      <nav className="flex flex-col space-y-2">
        <div className="flex items-center mb-4">
          <img src={Logo} alt="Logo" className="h-10 w-10 rounded-full mr-2" />
          <span className="text-xl font-bold">BD2 gr. 03</span>
        </div>

        <SidebarLink to="/" end icon={AiOutlineHome} label="Dashboard" />
        <SidebarLink to="/products" icon={FaBoxOpen} label="Products" />
        <SidebarLink to="/categories" icon={FaTags} label="Categories" />
        <SidebarLink to="/warehouses" icon={FaWarehouse} label="Warehouses" />
        <SidebarLink
          to="/transactions"
          icon={FaClipboardList}
          label="Transactions"
        />
        <SidebarLink to="/clients" icon={FaUsers} label="Clients" />
        <SidebarLink to="/suppliers" icon={FaUserTie} label="Suppliers" />
        <SidebarLink to="/employees" icon={FaUserFriends} label="Employees" />
        <SidebarLink to="/geography" icon={FaGlobe} label="Geography" />

        <div className="mt-4 border-t pt-4">
          <p className="text-xs uppercase text-gray-500 px-2">Inventory</p>
          <SidebarLink to="/inventory/receive" icon={FaTruck} label="Receive" />
          <SidebarLink
            to="/inventory/transfer"
            icon={FaExchangeAlt}
            label="Transfer"
          />
          <SidebarLink
            to="/inventory/delivery"
            icon={FaTruck}
            label="Delivery"
          />
        </div>
      </nav>
    </aside>
  );
}
