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
import Layout from "../components/Layout/Layout";
import DefaultRedirect from "../components/Layout/DefaultRedirect";
import ProductFormPage from "../pages/ProductFormPage";
import CategoryFormPage from "../pages/CategoryFormPage";
import WarehousesFormPage from "../pages/WarehousesFormPage";
import ClientFormPage from "../pages/ClientFormPage";
import EmployeeFormPage from "../pages/EmployeeFormPage";
import SupplierFormPage from "../pages/SupplierFormPage";
import CategoryDetailPage from "../pages/CategoryDetailPage";

const AppRouter = () => (
  <Router>
    <Routes>
      <Route path="/login" element={<LoginPage />} />
      <Route
        path="/*"
        element={
          <ProtectedRoute>
            <Layout>
              <Routes>
                <Route index element={<DashboardPage />} />
                <Route path="products" element={<ProductsPage />} />
                <Route path="products/new" element={<ProductFormPage />} />
                <Route path="products/:id/edit" element={<ProductFormPage />} />
                <Route path="products/:id" element={<ProductDetailPage />} />
                <Route path="categories" element={<CategoriesPage />} />
                <Route path="categories/new" element={<CategoryFormPage />} />
                <Route path="categories/:id" element={<CategoryDetailPage />} />
                <Route
                  path="categories/:id/edit"
                  element={<CategoryFormPage />}
                />
                <Route path="warehouses" element={<WarehousesPage />} />
                <Route path="warehouses/new" element={<WarehousesFormPage />} />
                <Route
                  path="warehouses/:id/edit"
                  element={<WarehousesFormPage />}
                />
                <Route
                  path="warehouses/:id"
                  element={<WarehouseDetailPage />}
                />
                <Route
                  path="inventory/receive"
                  element={<InventoryReceivePage />}
                />
                <Route
                  path="inventory/transfer"
                  element={<InventoryTransferPage />}
                />
                <Route
                  path="inventory/delivery"
                  element={<InventoryDeliveryPage />}
                />
                <Route path="transactions" element={<TransactionsPage />} />
                <Route
                  path="transactions/:id"
                  element={<TransactionDetailPage />}
                />
                <Route path="clients" element={<ClientsPage />} />
                <Route path="clients/new" element={<ClientFormPage />} />
                <Route path="clients/:id" element={<ClientDetailPage />} />
                <Route path="suppliers" element={<SuppliersPage />} />
                <Route path="suppliers/new" element={<SupplierFormPage />} />
                <Route path="suppliers/:id" element={<SupplierDetailPage />} />
                <Route path="employees" element={<EmployeesPage />} />
                <Route path="employees/new" element={<EmployeeFormPage />} />
                <Route path="employees/:id" element={<EmployeeDetailPage />} />
                <Route path="geography" element={<GeographyPage />} />
                <Route path="*" element={<DefaultRedirect />} />
              </Routes>
            </Layout>
          </ProtectedRoute>
        }
      />
    </Routes>
  </Router>
);

export default AppRouter;
