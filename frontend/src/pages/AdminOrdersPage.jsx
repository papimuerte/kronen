import React from 'react'; // Importing React library

const AdminOrdersPage = () => {
  // Sample order data
  const orders = [
    { id: 1, customer: 'John Doe', total: 50, status: 'Pending' },
    { id: 2, customer: 'Jane Smith', total: 75, status: 'Shipped' },
  ];

  return (
    <div className="container mt-5"> {/* Main container with margin-top for spacing */}
      <h1 className="text-center mb-4">Admin Orders</h1> {/* Page title */}
      <table className="table table-hover table-bordered"> {/* Bootstrap styled table with hover and border effects */}
        <thead>
          <tr> {/* Table header row */}
            <th>Order ID</th>
            <th>Customer</th>
            <th>Total</th>
            <th>Status</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {orders.map(order => (
            <tr key={order.id}> {/* Mapping over orders array and rendering table rows */}
              <td>{order.id}</td> {/* Displaying order ID */}
              <td>{order.customer}</td> {/* Displaying customer name */}
              <td>{order.total}€</td> {/* Displaying order total with currency symbol */}
              <td>{order.status}</td> {/* Displaying order status */}
              <td>
                <button className="btn btn-info btn-sm me-2">Edit</button> {/* Edit button */}
                <button className="btn btn-danger btn-sm">Delete</button> {/* Delete button */}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default AdminOrdersPage; // Exporting AdminOrdersPage component for use in other parts of the application

