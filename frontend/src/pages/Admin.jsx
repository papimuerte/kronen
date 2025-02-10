import React from "react";
import { BrowserRouter as Router, Routes, Route, NavLink } from "react-router-dom";
import Orders from "./Orders";
import Products from "./products";
import Inventory from "./Inventory";

const AdminPage = () => {
  return (
    <div className="d-flex vh-100">
      {/* Sidebar Navigation */}
      <div className="bg-primary text-white p-4" style={{ width: "250px" }}>
        <h3>Admin Panel</h3>
        <nav className="nav flex-column mt-4">
          <NavLink
            to="/admin-orders"
            className="nav-link text-white"
            activeClassName="active"
          >
            Orders
          </NavLink>
          <NavLink
            to="/admin-products"
            className="nav-link text-white"
            activeClassName="active"
          >
            Products
          </NavLink>
          <NavLink
            to="/admin-inventory"
            className="nav-link text-white"
            activeClassName="active"
          >
            Inventory
          </NavLink>
          <NavLink
            to="/admin-users"
            className="nav-link text-white"
            activeClassName="active"
          >
            Users
          </NavLink>
        </nav>
      </div>
    </div>
  );
};

export default AdminPage;