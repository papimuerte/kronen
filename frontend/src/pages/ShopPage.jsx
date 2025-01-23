import React, { useEffect, useState } from 'react';
import Navbar from '../components/Navbar';

const ShopPage = () => {
  const [products, setProducts] = useState([]); // Store products fetched from the API
  const [cart, setCart] = useState(() => {
    // Initialize cart state from localStorage or as an empty array
    const savedCart = localStorage.getItem('cart');
    return savedCart ? JSON.parse(savedCart) : [];
  });
  const [error, setError] = useState('');

  useEffect(() => {
    // Fetch products from the API
    const fetchProducts = async () => {
      try {
        const response = await fetch('http://localhost:8080/api/products');
        if (!response.ok) {
          throw new Error('Failed to fetch products.');
        }
        const data = await response.json();
        setProducts(data);
      } catch (err) {
        setError(err.message);
      }
    };

    fetchProducts();
  }, []);

  useEffect(() => {
    // Save cart to localStorage whenever it changes
    localStorage.setItem('cart', JSON.stringify(cart));
  }, [cart]);

  // Function to handle adding an item to the cart
  const handleAddToCart = (product) => {
    setCart((prevCart) => {
      const existingProduct = prevCart.find((item) => item.productId === product.productId);

      if (existingProduct) {
        // Update the quantity of the existing product
        return prevCart.map((item) =>
          item.productId === product.productId
            ? { ...item, quantity: item.quantity + 1 }
            : item
        );
      } else {
        // Add the new product to the cart with a quantity of 1
        return [...prevCart, { ...product, quantity: 1 }];
      }
    });
  };

  return (
    <div>
      {/* Pass the cart count to Navbar */}
      <Navbar cartCount={cart.reduce((total, item) => total + item.quantity, 0)} />

      {/* Main content of the Shop page */}
      <div className="container mt-5">
        <h1 className="text-center mb-4">Shop</h1>
        {error && <p className="text-danger text-center">{error}</p>}
        <div className="row">
          {products.length > 0 ? (
            products.map((product) => (
              <div className="col-md-4 mb-4" key={product.productId}>
                <div className="card h-100 shadow-sm">
                  <div className="card-body">
                    <h5 className="card-title">{product.name}</h5>
                    <p className="card-text">{product.description}</p>
                    <p className="text-primary fw-bold">
                      {product.unitPrice} {product.currency}
                    </p>
                    <p className="text-secondary">
                      Available: {product.availableQuantity}
                    </p>
                    <button
                      className="btn btn-success w-100"
                      onClick={() => handleAddToCart(product)} // Add to Cart action
                    >
                      Add to Cart
                    </button>
                  </div>
                </div>
              </div>
            ))
          ) : (
            <p className="text-center">Loading products...</p>
          )}
        </div>
      </div>
    </div>
  );
};

export default ShopPage;
