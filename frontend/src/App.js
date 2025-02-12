import React, { useState } from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import AuthPage from './pages/AuthPage'; 
import AdminOrdersPage from './pages/Orders';
import ShopPage from './pages/ShopPage';
import { jwtDecode } from "jwt-decode";
import CartPage from './pages/CartPage';
import AdminPage from './pages/Admin';
import ProductPage from './pages/products.jsx';
import InventoryPage from './pages/Inventory.jsx';
import DetailsPage from './pages/DetailsPage';
import UsersPage from './pages/UsersPage.jsx';
import 'bootstrap/dist/css/bootstrap.min.css';

// PrivateRoute 
const PrivateRoute = ({ element, token }) => {
  return token ? element : <Navigate to="/" />;
};

// AdminRoute 
const AdminRoute = ({ element, token }) => {
  return token?.role === "ADMIN" ? element : <Navigate to="/" />;
};

const App = () => {
  const [cart] = useState([]);

  // Sicherstellen, dass Token vorhanden ist, um Fehler zu vermeiden
  const token = localStorage.getItem("token");

  let decodedToken = null;
  try {
    if (token) {
      decodedToken = jwtDecode(token);
    }
  } catch (error) {
    console.error("Invalid token:", error);
  }

  return (
    <Router>
      <Routes>
        {/* Public Route */}
        <Route path="/" element={<AuthPage />} />

        {/* Protected Routes (Require Authentication) */}
        <Route path="/shop" element={<PrivateRoute element={<ShopPage />} token={decodedToken} />} />
        <Route path="/cart" element={<PrivateRoute element={<CartPage />} token={decodedToken} />} />
        <Route path="/details" element={<PrivateRoute element={<DetailsPage tokenData={decodedToken} />} token={decodedToken} />} />

        {/* Admin Routes (Require Admin Role) */}
        <Route path="/admin" element={<AdminRoute element={<AdminPage />} token={decodedToken} />} />
        <Route path="/admin-orders" element={<AdminRoute element={<AdminOrdersPage />} token={decodedToken} />} />
        <Route path="/admin-products" element={<AdminRoute element={<ProductPage />} token={decodedToken} />} />
        <Route path="/admin-inventory" element={<AdminRoute element={<InventoryPage />} token={decodedToken} />} />
        <Route path="/admin-users" element={<AdminRoute element={<UsersPage />} token={decodedToken} />} />

        {/* 404 Page */}
        <Route path="*" element={<Navigate to="/" />} />
      </Routes>
    </Router>
  );
};

export default App;
