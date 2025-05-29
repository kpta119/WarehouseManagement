import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import LoginPage from "../pages/LoginPage";
import DashboardPage from "../pages/DashboardPage";
import ProductsPage from "../pages/ProductsPage";
import CategoriesPage from "../pages/CategoriesPage";
import WarehousesPage from "../pages/WarehousesPage";
import TransactionsPage from "../pages/TransactionsPage";
import ClientsPage from "../pages/ClientsPage";
import SuppliersPage from "../pages/SuppliersPage";
import EmployeesPage from "../pages/EmployeesPage";
import GeographyPage from "../pages/GeographyPage";
import ProtectedRoute from "../components/Layout/ProtectedRoute";
import Layout from "../components/Layout/Layout";
import DefaultRedirect from "../components/Layout/DefaultRedirect";
import CategoryForm from "../components/categories/CategoryForm";
import CategoryDetail from "../components/categories/CategoryDetail";
import ClientForm from "../components/clients/ClientForm";
import ClientDetail from "../components/clients/ClientDetail";
import EmployeeForm from "../components/employees/EmployeeForm";
import EmployeeDetail from "../components/employees/EmployeeDetail";
import DeliveryPage from "../components/inventory/DeliveryPage";
import ReceivePage from "../components/inventory/ReceivePage";
import TransferPage from "../components/inventory/TransferPage";
import ProductForm from "../components/products/ProductForm";
import ProductDetail from "../components/products/ProductDetail";
import SupplierDetail from "../components/suppliers/SupplierDetail";
import SupplierForm from "../components/suppliers/SupplierForm";
import TransactionDetail from "../components/transactions/TransactionDetail";
import WarehouseDetail from "../components/warehouses/WarehouseDetail";
import WarehouseForm from "../components/warehouses/WarehouseForm";

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
                <Route path="products/new" element={<ProductForm />} />
                <Route path="products/:id/edit" element={<ProductForm />} />
                <Route path="products/:id" element={<ProductDetail />} />
                <Route path="categories" element={<CategoriesPage />} />
                <Route path="categories/new" element={<CategoryForm />} />
                <Route path="categories/:id" element={<CategoryDetail />} />
                <Route path="categories/:id/edit" element={<CategoryForm />} />
                <Route path="warehouses" element={<WarehousesPage />} />
                <Route path="warehouses/new" element={<WarehouseForm />} />
                <Route path="warehouses/:id/edit" element={<WarehouseForm />} />
                <Route path="warehouses/:id" element={<WarehouseDetail />} />
                <Route path="inventory/receive" element={<ReceivePage />} />
                <Route path="inventory/transfer" element={<TransferPage />} />
                <Route path="inventory/delivery" element={<DeliveryPage />} />
                <Route path="transactions" element={<TransactionsPage />} />
                <Route
                  path="transactions/:id"
                  element={<TransactionDetail />}
                />
                <Route path="clients" element={<ClientsPage />} />
                <Route path="clients/new" element={<ClientForm />} />
                <Route path="clients/:id" element={<ClientDetail />} />
                <Route path="suppliers" element={<SuppliersPage />} />
                <Route path="suppliers/new" element={<SupplierForm />} />
                <Route path="suppliers/:id" element={<SupplierDetail />} />
                <Route path="employees" element={<EmployeesPage />} />
                <Route path="employees/new" element={<EmployeeForm />} />
                <Route path="employees/:id" element={<EmployeeDetail />} />
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
