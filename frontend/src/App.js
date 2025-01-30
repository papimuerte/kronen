import React from 'react';
import { useState } from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import AuthPage from './pages/AuthPage'; // Import your pages
import AdminOrdersPage from './pages/Orders';
import ShopPage from './pages/ShopPage';
import { jwtDecode } from "jwt-decode";
import CartPage from './pages/CartPage';
import AdminPage from './pages/Admin';
import ProductPage from './pages/products.jsx';
import InventoryPage from './pages/Inventory.jsx';
import DetailsPage from './pages/DetailsPage';
import 'bootstrap/dist/css/bootstrap.min.css'; // Ensure Bootstrap is loaded

  // PrivateRoute 
  const PrivateRoute = ({ element, token }) => {
    return token ? element : <Navigate to="/" replace />;
  };
  
  // AdminRoute 
  const AdminRoute = ({ element, token }) => {
    return token?.role === "admin" ? element : <Navigate to="/" replace />;
  };


const App = () => {
  const [cart] = useState([]); // Move cart state to a higher level

  const token = localStorage.token; // Retrieve Bearer token

  const decodedToken = jwtDecode(token);



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

      {/* 404 Page */}
      <Route path="*" element={<Navigate to="/" />} />
    </Routes>
  </Router>
  );
};

export default App;