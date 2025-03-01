import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

const CartPage = () => {
  const [cart, setCart] = useState([]); 
  const navigate = useNavigate(); 

  // Load cart initially
  useEffect(() => {
    const savedCart = localStorage.getItem('cart');
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
    <div className="container mt-5">
      <h1 className="text-center mb-4">Your Cart</h1>
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
                    <label htmlFor={`quantity-${item.productId}`} className="me-2">Quantity:</label>
                    <input
                      type="number"
                      id={`quantity-${item.productId}`}
                      className="form-control"
                      style={{ width: '80px' }}
                      value={item.quantity}
                      onChange={(e) => handleQuantityChange(item.productId, parseInt(e.target.value) || 1)}
                      min="1"
                    />
                  </div>
                </div>
                <button className="btn btn-danger btn-sm" onClick={() => handleRemoveItem(item.productId)}>
                  Remove
                </button>
              </li>
            ))}
          </ul>
          <h3 className="mt-4 text-end">
            Total: {totalAmount.toFixed(2)} {cart[0]?.currency || 'USD'}
          </h3>
          <div className="text-end mt-4">
            <button className="btn btn-primary" onClick={() => navigate('/details')}>
              Proceed to Details
            </button>
          </div>
        </div>
      )}
    </div>
  );
};

export default CartPage;
