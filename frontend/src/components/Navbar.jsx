import React, { useState, useEffect } from "react";
import { FaShoppingBag } from "react-icons/fa";
import "bootstrap/dist/css/bootstrap.min.css";
import "../index.css";
import CartSidebar from "./CartSidebar"; // Import the new component

const OnlineOrdering = () => {
  const [navbarBg, setNavbarBg] = useState("transparent");
  const [cartOpen, setCartOpen] = useState(false);
  const [cart, setCart] = useState([]);

  useEffect(() => {
    const handleScroll = () => {
      setNavbarBg(window.scrollY > 50 ? "#4E342E" : "transparent");
    };
    window.addEventListener("scroll", handleScroll);
    return () => window.removeEventListener("scroll", handleScroll);
  }, []);

  useEffect(() => {
    const savedCart = localStorage.getItem("cart");
    if (savedCart) {
      setCart(JSON.parse(savedCart));
    }
  }, []);

  const handleQuantityChange = (productId, newQuantity) => {
    setCart((prevCart) => {
      const updatedCart = prevCart.map((item) =>
        item.productId === productId
          ? { ...item, quantity: Math.max(1, newQuantity) }
          : item
      );
      localStorage.setItem("cart", JSON.stringify(updatedCart));
      return updatedCart;
    });
  };

  const handleRemoveItem = (productId) => {
    setCart((prevCart) => {
      const updatedCart = prevCart.filter((item) => item.productId !== productId);
      localStorage.setItem("cart", JSON.stringify(updatedCart));
      return updatedCart;
    });
  };

  return (
    <>
      {/* Navigation Bar */}
      <nav
        className="navbar navbar-expand-lg fixed-top px-4"
        style={{ backgroundColor: navbarBg, transition: "background-color 0.3s ease-in-out" }}
      >
        <div className="container-fluid d-flex justify-content-between align-items-center position-relative">
          <div className="d-flex align-items-center">
            <ul className="navbar-nav d-flex flex-row gap-3">
              <li className="nav-item"><a className="nav-link text-white" href="/menu">Menu</a></li>
              <li className="nav-item"><a className="nav-link text-white" href="/order">Order Online</a></li>
              <li className="nav-item"><a className="nav-link text-white" href="#reservations">Reservations</a></li>
              <li className="nav-item"><a className="nav-link text-white" href="#about">About</a></li>
              <li className="nav-item"><a className="nav-link text-white" href="#contact">Contact</a></li>
            </ul>
          </div>
          <div className="position-absolute start-50 translate-middle-x">
            <a className="navbar-brand fs-2 fw-bold text-white text-center" href="/">Kronenbrunnen</a>
          </div>
          <div className="d-flex align-items-center">
            <a href="/order" className="btn order-btn me-3">Order Now</a>
            <FaShoppingBag 
              className="text-white fs-4" 
              style={{ cursor: "pointer" }} 
              onClick={() => {
                console.log("Cart icon clicked");
                setCartOpen((prev) => !prev); // Toggle sidebar open/close
              }} 
            />
          </div>
        </div>
      </nav>

      {/* Cart Sidebar Component */}
      <CartSidebar 
        cartOpen={cartOpen}
        setCartOpen={setCartOpen}
        cart={cart}
        handleQuantityChange={handleQuantityChange}
        handleRemoveItem={handleRemoveItem}
      />
    </>
  );
};

export default OnlineOrdering;
