import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import LoginPage from "../pages/LoginPage";
import DashboardPage from "../pages/DashboardPage";
import ProductsPage from "../pages/ProductsPage";
import ProductDetailPage from "../pages/ProductDetailPage";
import CategoriesPage from "../pages/CategoriesPage";
import WarehousesPage from "../pages/WarehousesPage";
import WarehouseDetailPage from "../pages/WarehouseDetailPage";
import InventoryReceivePage from "../pages/InventoryReceivePage";
import InventoryTransferPage from "../pages/InventoryTransferPage";
import InventoryDeliveryPage from "../pages/InventoryDeliveryPage";
import TransactionsPage from "../pages/TransactionsPage";
import TransactionDetailPage from "../pages/TransactionDetailPage";
import ClientsPage from "../pages/ClientsPage";
import ClientDetailPage from "../pages/ClientDetailPage";
import SuppliersPage from "../pages/SuppliersPage";
import SupplierDetailPage from "../pages/SupplierDetailPage";
import EmployeesPage from "../pages/EmployeesPage";
import EmployeeDetailPage from "../pages/EmployeeDetailPage";
import GeographyPage from "../pages/GeographyPage";
import ProtectedRoute from "../components/Layout/ProtectedRoute";
import DefaultRedirect from "../components/Layout/DefaultRedirect";

const AppRouter = () => (
  <Router>
    <Routes>
      <Route path="/login" element={<LoginPage />} />
      <Route
        path="/"
        element={
          <ProtectedRoute>
            <DashboardPage />
          </ProtectedRoute>
        }
      />
      <Route
        path="/products"
        element={
          <ProtectedRoute>
            <ProductsPage />
          </ProtectedRoute>
        }
      />
      <Route
        path="/products/:id"
        element={
          <ProtectedRoute>
            <ProductDetailPage />
          </ProtectedRoute>
        }
      />
      <Route
        path="/categories"
        element={
          <ProtectedRoute>
            <CategoriesPage />
          </ProtectedRoute>
        }
      />
      <Route
        path="/warehouses"
        element={
          <ProtectedRoute>
            <WarehousesPage />
          </ProtectedRoute>
        }
      />
      <Route
        path="/warehouses/:id"
        element={
          <ProtectedRoute>
            <WarehouseDetailPage />
          </ProtectedRoute>
        }
      />
      <Route
        path="/inventory/receive"
        element={
          <ProtectedRoute>
            <InventoryReceivePage />
          </ProtectedRoute>
        }
      />
      <Route
        path="/inventory/transfer"
        element={
          <ProtectedRoute>
            <InventoryTransferPage />
          </ProtectedRoute>
        }
      />
      <Route
        path="/inventory/delivery"
        element={
          <ProtectedRoute>
            <InventoryDeliveryPage />
          </ProtectedRoute>
        }
      />
      <Route
        path="/transactions"
        element={
          <ProtectedRoute>
            <TransactionsPage />
          </ProtectedRoute>
        }
      />
      <Route
        path="/transactions/:id"
        element={
          <ProtectedRoute>
            <TransactionDetailPage />
          </ProtectedRoute>
        }
      />
      <Route
        path="/clients"
        element={
          <ProtectedRoute>
            <ClientsPage />
          </ProtectedRoute>
        }
      />
      <Route
        path="/clients/:id"
        element={
          <ProtectedRoute>
            <ClientDetailPage />
          </ProtectedRoute>
        }
      />
      <Route
        path="/suppliers"
        element={
          <ProtectedRoute>
            <SuppliersPage />
          </ProtectedRoute>
        }
      />
      <Route
        path="/suppliers/:id"
        element={
          <ProtectedRoute>
            <SupplierDetailPage />
          </ProtectedRoute>
        }
      />
      <Route
        path="/employees"
        element={
          <ProtectedRoute>
            <EmployeesPage />
          </ProtectedRoute>
        }
      />
      <Route
        path="/employees/:id"
        element={
          <ProtectedRoute>
            <EmployeeDetailPage />
          </ProtectedRoute>
        }
      />
      <Route
        path="/geography"
        element={
          <ProtectedRoute>
            <GeographyPage />
          </ProtectedRoute>
        }
      />
      <Route path="*" element={<DefaultRedirect />} />
    </Routes>
  </Router>
);

export default AppRouter;
