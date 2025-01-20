import React, { useEffect, useState } from 'react';

const ShopPage = () => {
  const [products, setProducts] = useState([]);
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

  return (
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
                  <button className="btn btn-success w-100">Add to Cart</button>
                </div>
              </div>
            </div>
          ))
        ) : (
          <p className="text-center">Loading products...</p>
        )}
      </div>
    </div>
  );
};

export default ShopPage;