import React, { useState } from 'react';

const ShopPage = () => {
  const [products, setProducts] = useState([]);
  const [error, setError] = useState('');

  return (
    <div className="container mt-5">
      <h1 className="text-center mb-4">Shop</h1>
    </div>
  );
};

export default ShopPage;
