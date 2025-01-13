import React from 'react';

const ShoppingCart = () => {
  const cart = [
    { id: 1, name: 'Product A', quantity: 2, price: 20 },
    { id: 2, name: 'Product B', quantity: 1, price: 30 },
  ];

  return (
    <div className="container mt-5">
      <h1 className="text-center mb-4">Shopping Cart</h1>
      <table className="table table-striped table-bordered">
        <thead>
          <tr>
            <th>Product</th>
            <th>Quantity</th>
            <th>Price</th>
            <th>Total</th>
          </tr>
        </thead>
        <tbody>
          {cart.map(item => (
            <tr key={item.id}>
              <td>{item.name}</td>
              <td>{item.quantity}</td>
              <td>{item.price}€</td>
              <td>{item.quantity * item.price}€</td>
            </tr>
          ))}
        </tbody>
      </table>
      <div className="text-end">
        <button className="btn btn-primary">Checkout</button>
      </div>
    </div>
  );
};

export default ShoppingCart;
