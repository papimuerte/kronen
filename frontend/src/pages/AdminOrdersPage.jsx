import React from 'react';

const AdminOrdersPage = () => {
  const orders = [
    { id: 1, customer: 'John Doe', total: 50, status: 'Pending' },
    { id: 2, customer: 'Jane Smith', total: 75, status: 'Shipped' },
  ];

  return (
    <div className="container mt-5">
      <h1 className="text-center mb-4">Admin Orders</h1>
      <table className="table table-hover table-bordered">
        <thead>
          <tr>
            <th>Order ID</th>
            <th>Customer</th>
            <th>Total</th>
            <th>Status</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {orders.map(order => (
            <tr key={order.id}>
              <td>{order.id}</td>
              <td>{order.customer}</td>
              <td>{order.total}€</td>
              <td>{order.status}</td>
              <td>
                <button className="btn btn-info btn-sm me-2">Edit</button>
                <button className="btn btn-danger btn-sm">Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default AdminOrdersPage;
