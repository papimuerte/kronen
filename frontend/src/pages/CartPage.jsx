import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom'; // Import navigation hook

const CartPage = () => {
  const [cart, setCart] = useState([]); // State to store cart items
  const navigate = useNavigate(); // React Router hook for navigation

  useEffect(() => {
    const savedCart = localStorage.getItem('cart'); // Retrieve cart from localStorage
    if (savedCart) {
      setCart(JSON.parse(savedCart)); // Parse and set cart state
    }
  }, []);

  const handleQuantityChange = (productId, newQuantity) => {
    setCart((prevCart) => {
      const updatedCart = prevCart.map((item) =>
        item.productId === productId
          ? { ...item, quantity: Math.max(1, newQuantity) } // Ensure quantity is at least 1
          : item
      );

      localStorage.setItem('cart', JSON.stringify(updatedCart)); // Update localStorage
      return updatedCart;
    });
  };

  const handleRemoveItem = (productId) => {
    setCart((prevCart) => {
      const updatedCart = prevCart.filter((item) => item.productId !== productId); // Remove item from cart

      localStorage.setItem('cart', JSON.stringify(updatedCart)); // Update localStorage
      return updatedCart;
    });
  };

  const totalAmount = cart.reduce((total, item) => total + item.unitPrice * item.quantity, 0); // Calculate total cost

  return (
    <div className="container mt-5"> {/* Main container with margin-top */}
      <h1 className="text-center mb-4">Your Cart</h1> {/* Cart heading */}
      {cart.length === 0 ? ( // Conditional rendering if cart is empty
        <p className="text-center">Your cart is empty.</p>
      ) : (
        <div>
          <ul className="list-group"> {/* List of cart items */}
            {cart.map((item) => (
              <li className="list-group-item d-flex justify-content-between align-items-center" key={item.productId}> {/* Cart item row */}
                <div>
                  <h5>{item.name}</h5> {/* Item name */}
                  <div className="d-flex align-items-center">
                    <label htmlFor={`quantity-${item.productId}`} className="me-2">Quantity:</label> {/* Quantity label */}
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
                <button className="btn btn-danger btn-sm" onClick={() => handleRemoveItem(item.productId)}> {/* Remove button */}
                  Remove
                </button>
              </li>
            ))}
          </ul>
          <h3 className="mt-4 text-end"> {/* Display total price */}
            Total: {totalAmount.toFixed(2)} {cart[0]?.currency || 'USD'}
          </h3>
          <div className="text-end mt-4"> {/* Proceed button container */}
            <button className="btn btn-primary" onClick={() => navigate('/details')}> {/* Proceed to details page */}
              Proceed to Details
            </button>
          </div>
        </div>
      )}
    </div>
  );
};

export default CartPage; // Export CartPage component
