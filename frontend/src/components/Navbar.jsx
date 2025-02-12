import React, { useState } from 'react';
import { Link } from 'react-router-dom';

const Navbar = ({ cartCount }) => {
  const [isOpen, setIsOpen] = useState(false);

  return (
    <nav className="navbar navbar-expand-lg navbar-dark bg-primary">
      <div className="container">
        <Link className="navbar-brand" to="/">
          MyShop
        </Link>
        <button
          className="navbar-toggler"
          type="button"
          onClick={() => setIsOpen(!isOpen)}
          aria-controls="navbarNav"
          aria-expanded={isOpen}
          aria-label="Toggle navigation"
        >
          <span className="navbar-toggler-icon"></span>
        </button>
        <div className={`collapse navbar-collapse ${isOpen ? 'show' : ''}`} id="navbarNav">
          <ul className="navbar-nav ms-auto">
            <li className="nav-item">
              <Link className="nav-link" to="/" onClick={() => setIsOpen(false)}>
                Home
              </Link>
            </li>
            <li className="nav-item">
              <Link className="nav-link" to="/shop" onClick={() => setIsOpen(false)}>
                Shop
              </Link>
            </li>
            <li className="nav-item">
              <Link className="nav-link position-relative" to="/cart" onClick={() => setIsOpen(false)}>
                Cart
                {cartCount > 0 && (
                  <span
                    className="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger"
                    style={{ fontSize: '0.8rem' }}
                  >
                    {cartCount}
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

export default Navbar;
