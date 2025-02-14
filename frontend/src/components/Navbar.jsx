import React, { useState } from 'react'; // Importing React and useState for state management
import { Link } from 'react-router-dom'; // Importing Link component for navigation without page reload

const Navbar = ({ cartCount }) => {
  // State to manage the toggle of the navbar in mobile view
  const [isOpen, setIsOpen] = useState(false);

  return (
    <nav className="navbar navbar-expand-lg navbar-dark bg-primary">
      <div className="container">
        {/* Brand logo that links to the homepage */}
        <Link className="navbar-brand" to="/">MyShop</Link>

        {/* Button to toggle navigation menu on small screens */}
        <button
          className="navbar-toggler"
          type="button"
          onClick={() => setIsOpen(!isOpen)} // Toggle state on click
        >
          <span className="navbar-toggler-icon"></span> {/* Bootstrap hamburger icon */}
        </button>

        {/* Collapsible navigation menu */}
        <div className={`collapse navbar-collapse ${isOpen ? 'show' : ''}`} id="navbarNav">
          <ul className="navbar-nav ms-auto"> {/* Navigation list aligned to the right */}

            {/* Navigation item for Home */}
            <li className="nav-item">
              <Link className="nav-link" to="/" onClick={() => setIsOpen(false)}>
                Home
              </Link>
            </li>

            {/* Navigation item for Shop */}
            <li className="nav-item">
              <Link className="nav-link" to="/shop" onClick={() => setIsOpen(false)}>
                Shop
              </Link>
            </li>

            {/* Navigation item for Cart */}
            <li className="nav-item">
              <Link className="nav-link position-relative" to="/cart" onClick={() => setIsOpen(false)}>
                Cart
                {/* Display badge only when there are items in the cart */}
                {cartCount > 0 && (
                  <span
                    className="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger"
                    style={{ fontSize: '0.8rem' }}
                  >
                    {cartCount} {/* Display cart item count */}
                  </span>
                )}
              </Link>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  );
};

export default Navbar; // Exporting Navbar component for use in other parts of the application
