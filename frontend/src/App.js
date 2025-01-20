import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import AuthPage from './pages/AuthPage'; // Import your pages
import AdminOrdersPage from './pages/AdminOrdersPage';
import ShopPage from './pages/ShopPage';
import 'bootstrap/dist/css/bootstrap.min.css'; // Ensure Bootstrap is loaded

const App = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<AuthPage />} />
        <Route path="/admin-orders" element={<AdminOrdersPage />} />
        <Route path="/shop" element={<ShopPage />} />
        <Route path="*" element={<div className="text-center mt-5">Page Not Found</div>} />
      </Routes>
    </Router>
  );
};

export default App;