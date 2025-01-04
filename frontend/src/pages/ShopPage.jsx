import React from 'react';

const ShopPage = () => {
  const products = [
    { id: 1, name: 'Product A', price: 20, description: 'High-quality product A' },
    { id: 2, name: 'Product B', price: 30, description: 'High-quality product B' },
  ];

  return (
    <div className="container mt-5">
      <h1 className="text-center mb-4">Shop</h1>
      <div className="row">
        {products.map(product => (
          <div className="col-md-4 mb-4" key={product.id}>
            <div className="card h-100 shadow-sm">
              <div className="card-body">
                <h5 className="card-title">{product.name}</h5>
                <p className="card-text">{product.description}</p>
                <p className="text-primary fw-bold">{product.price}€</p>
                <button className="btn btn-success w-100">Add to Cart</button>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default ShopPage;
