import React from "react";
import { BrowserRouter as Router, Routes, Route, NavLink } from "react-router-dom"; // Importing necessary components from react-router-dom for routing
import Orders from "./Orders"; // Importing Orders component
import Products from "./products"; // Importing Products component
import Inventory from "./Inventory"; // Importing Inventory component

const AdminPage = () => {
  return (
    <div className="d-flex vh-100"> // Full height flex container
      {/* Sidebar Navigation */}
      <div className="bg-primary text-white p-4" style={{ width: "250px" }}> // Sidebar with primary background and padding
        <h3>Admin Panel</h3> // Title for the admin panel
        <nav className="nav flex-column mt-4"> // Vertical navigation menu
          <NavLink
            to="/admin-orders"
            className="nav-link text-white"
            activeClassName="active" // Highlights the active link
          >
            Orders
          </NavLink>
          <NavLink
            to="/admin-products"
            className="nav-link text-white"
            activeClassName="active" // Highlights the active link
          >
            Products
          </NavLink>
          <NavLink
            to="/admin-inventory"
            className="nav-link text-white"
            activeClassName="active" // Highlights the active link
          >
            Inventory
          </NavLink>
          <NavLink
            to="/admin-users"
            className="nav-link text-white"
            activeClassName="active" // Highlights the active link
          >
            Users
          </NavLink>
        </nav>
      </div>
    </div>
  );
};

export default AdminPage; // Exporting AdminPage component for use in other parts of the application
