import React from 'react';
import { useState } from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import AuthPage from './pages/AuthPage'; // Import your pages
import AdminOrdersPage from './pages/AdminOrdersPage';
import ShopPage from './pages/ShopPage';
import CartPage from './pages/CartPage';
import 'bootstrap/dist/css/bootstrap.min.css'; // Ensure Bootstrap is loaded

const App = () => {
  const [cart, setCart] = useState([]); // Move cart state to a higher level

  return (
    <Router>
      <Routes>
        <Route path="/" element={<AuthPage />} />
        <Route path="/admin-orders" element={<AdminOrdersPage />} />
        <Route path="/cart" element={<CartPage cart={cart} />} />
        <Route path="/shop" element={<ShopPage />} />
        <Route path="*" element={<div className="text-center mt-5">Page Not Found</div>} />
      </Routes>
    </Router>
  );
};

export default App;