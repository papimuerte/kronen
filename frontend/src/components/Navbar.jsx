import React from 'react';
import { Link } from 'react-router-dom'; // Importing Link component for navigation without page reload

const Navbar = () => {
  return (
    <nav className="navbar navbar-expand-lg navbar-dark bg-primary"> {/* Bootstrap navbar with dark theme and primary background color */}
      <div className="container"> {/* Container to keep navbar content aligned properly */}
        <Link className="navbar-brand" to="/"> {/* Brand logo that links to the homepage */}
          MyShop
        </Link>
        <button
          className="navbar-toggler" // Button to toggle navigation menu in smaller screens
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarNav" // Links to the collapsible menu div below
          aria-controls="navbarNav"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span className="navbar-toggler-icon"></span> {/* Bootstrap icon for the toggle button */}
        </button>
        <div className="collapse navbar-collapse" id="navbarNav"> {/* Collapsible menu for responsive design */}
          <ul className="navbar-nav ms-auto"> {/* Navigation list aligned to the right */}
            <li className="nav-item"> {/* Navigation item for Home */}
              <Link className="nav-link" to="/">
                Home
              </Link>
            </li>
            <li className="nav-item"> {/* Navigation item for Shop */}
              <Link className="nav-link" to="/shop">
                Shop
              </Link>
            </li>
            <li className="nav-item"> {/* Navigation item for Login */}
              <Link className="nav-link" to="/login">
                Login
              </Link>
            </li>
            <li className="nav-item"> {/* Navigation item for Register */}
              <Link className="nav-link" to="/register">
                Register
              </Link>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  );
};

export default Navbar; // Exporting Navbar component for use in other parts of the application
