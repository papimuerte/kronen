import React, { useState, useEffect } from 'react';
import { useNavigate } from "react-router-dom";


const CartSidebar = ({ cartOpen, setCartOpen}) => {
  const [cart, setCart] = useState([]); 
  const navigate = useNavigate(); 

  // Load cart initially
  useEffect(() => {
    const savedCart = localStorage.getItem('cart');
    console.log(savedCart)
    if (savedCart) {
      setCart(JSON.parse(savedCart));
    }
  }, []);

  // Listen for changes in localStorage
  useEffect(() => {
    const handleStorageChange = () => {
      const updatedCart = JSON.parse(localStorage.getItem('cart')) || [];
      setCart(updatedCart);
    };

  
    window.addEventListener('storage', handleStorageChange);

    return () => {
      window.removeEventListener('storage', handleStorageChange);
    };
  }, []);

  // Update cart when localStorage changes
  useEffect(() => {
    const interval = setInterval(() => {
      const updatedCart = JSON.parse(localStorage.getItem('cart')) || [];
      setCart(updatedCart);
    }, 500); // Polling every 500ms for instant update

    return () => clearInterval(interval);
  }, []);

  const handleQuantityChange = (productId, newQuantity) => {
    setCart((prevCart) => {
      const updatedCart = prevCart.map((item) =>
        item.productId === productId
          ? { ...item, quantity: Math.max(1, newQuantity) }
          : item
      );

      localStorage.setItem('cart', JSON.stringify(updatedCart));
      return updatedCart;
    });
  };

  const handleRemoveItem = (productId) => {
    setCart((prevCart) => {
      const updatedCart = prevCart.filter((item) => item.productId !== productId);
      localStorage.setItem('cart', JSON.stringify(updatedCart));
      return updatedCart;
    });
  };

  const totalAmount = cart.reduce((total, item) => total + item.unitPrice * item.quantity, 0);


  return (
    <>
      <div className={`cart-sidebar z-3 ${cartOpen ? "open" : ""}`}>
        <div className="cart-header">
          <h4>Your Cart</h4>
          <button 
            className="close-btn" 
            onClick={() => {
              console.log("Close button clicked");
              setCartOpen(false);
            }}
          >
            ×
          </button>
        </div>
        {cart.length === 0 ? (
          <p className="text-center">Your cart is empty.</p>
        ) : (
          <div>
            <ul className="list-group">
              {cart.map((item) => (
                <li className="list-group-item d-flex justify-content-between align-items-center" key={item.productId}>
                  <div>
                    <h5>{item.name}</h5>
                    <div className="d-flex align-items-center">
                      <label htmlFor={`quantity-${item.productId}`} className="me-2">Qty:</label>
                      <input
                        type="number"
                        id={`quantity-${item.productId}`}
                        className="form-control"
                        style={{ width: "80px" }}
                        value={item.quantity}
                        onChange={(e) => handleQuantityChange(item.productId, parseInt(e.target.value) || 1)}
                        min="1"
                      />
                    </div>
                  </div>
                  <button className="btn btn-danger btn-sm" onClick={() => handleRemoveItem(item.productId)}>Remove</button>
                </li>
              ))}
            </ul>
            <h3 className="mt-4 text-end">Total: {totalAmount.toFixed(2)} {cart[0]?.currency || "USD"}</h3>
            <div className="text-end mt-4">
              <button className="btn btn-primary" onClick={() => navigate("/details")}>Proceed to Details</button>
            </div>
          </div>
        )}
      </div>

      {/* Styles */}
      <style>{`
        .cart-sidebar {
          position: fixed;
          top: 0;
          right: -350px;
          width: 350px;
          height: 100%;
          background: white;
          box-shadow: -2px 0 5px rgba(0, 0, 0, 0.2);
          transition: right 0.3s ease-in-out;
          overflow-y: auto;
          padding: 20px;
          z-index: 1000;
        }
        
        .cart-sidebar.open {
          right: 0;
        }

        .cart-header {
          display: flex;
          justify-content: space-between;
          align-items: center;
          border-bottom: 1px solid #ddd;
          padding-bottom: 10px;
          margin-bottom: 10px;
        }
        
        .close-btn {
          background: none;
          border: none;
          font-size: 1.5rem;
          cursor: pointer;
          pointer-events: all;
        }
      `}</style>
    </>
  );
};

export default CartSidebar;
