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
        <SidebarLink to="/" end icon={AiOutlineHome} label="Panel główny" />
        <SidebarLink to="/products" icon={FaBoxOpen} label="Produkty" />
        <SidebarLink to="/categories" icon={FaTags} label="Kategorie" />
        <SidebarLink to="/warehouses" icon={FaWarehouse} label="Magazyny" />
        <SidebarLink
          to="/transactions"
          icon={FaClipboardList}
          label="Transakcje"
        />
        <SidebarLink to="/clients" icon={FaUsers} label="Klienci" />
        <SidebarLink to="/suppliers" icon={FaUserTie} label="Dostawcy" />
        <SidebarLink to="/employees" icon={FaUserFriends} label="Pracownicy" />
        <SidebarLink to="/geography" icon={FaGlobe} label="Geografia" />
        <div className="mt-4 border-t pt-4">
          <p className="text-xs uppercase text-gray-500 px-2 mb-2">Magazyn</p>
          <SidebarLink
            to="/inventory/receive"
            icon={FaTruck}
            label="Przyjęcie"
          />
          <SidebarLink
            to="/inventory/transfer"
            icon={FaExchangeAlt}
            label="Przeniesienie"
          />
          <SidebarLink
            to="/inventory/delivery"
            icon={FaTruck}
            label="Wydanie"
          />
        </div>
      </nav>
    </aside>
  );
}
