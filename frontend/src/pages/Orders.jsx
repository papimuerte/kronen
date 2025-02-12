import React, { useEffect, useState } from "react";

const Orders = () => {
  const [orders, setOrders] = useState([]);
  const [error, setError] = useState("");
  const [selectedOrder, setSelectedOrder] = useState(null);

  useEffect(() => {
    const fetchOrders = async () => {
      try {
        const query = `
        query AllOrders {
            allOrders {
                id
                customerUsername
                totalAmount
                status
                createdAt
                companyName
                email
                address
                phoneNumber
                notes
                products {
                    productId
                    name
                    quantity
                    unitPrice
                }
            }
        }`;

        const response = await fetch("http://localhost:8080/graphql", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({ query }),
        });

        if (!response.ok) throw new Error("Failed to fetch orders.");

        const responseData = await response.json();
        if (responseData.errors) throw new Error(responseData.errors[0].message);

        setOrders(responseData.data.allOrders);
      } catch (err) {
        setError(err.message);
      }
    };

    fetchOrders();
  }, []);

  const toggleOrderDetails = (orderId) => {
    setSelectedOrder(selectedOrder === orderId ? null : orderId);
  };

  const getStatusColor = (status) => {
    switch (status) {
      case "Pending": return "table-danger"; // Red
      case "Shipped": return "table-warning"; // Orange
      case "Done": return "table-success"; // Green
      default: return "";
    }
  };

  const handleStatusChange = async (orderId, newStatus) => {
    try {
      const mutation = `
        mutation UpdateOrderStatus($orderId: String!, $newStatus: String!) {
          updateOrderStatus(orderId: $orderId, newStatus: $newStatus) {
            id
            status
          }
        }
      `;
  
      // Ensure the JSON structure is correct
      const requestBody = JSON.stringify({
        query: mutation,
        variables: {
          orderId: orderId,
          newStatus: newStatus,
        },
      });
  
      const response = await fetch("http://localhost:8080/graphql", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: requestBody, // Send the JSON formatted request
      });
  
      if (!response.ok) throw new Error("Failed to update order status.");
  
      const responseData = await response.json();
  
      if (responseData.errors) throw new Error(responseData.errors[0].message);
  
      // Update local state to reflect status change
      setOrders((prevOrders) =>
        prevOrders.map((order) =>
          order.id === orderId ? { ...order, status: newStatus } : order
        )
      );
    } catch (error) {
      setError(error.message);
    }
  };
  

  return (
    <div className="container mt-4">
      <h1>Orders</h1>
      {error && <p className="text-danger">{error}</p>}
      <table className="table table-bordered">
        <thead className="table-dark">
          <tr>
            <th>Order ID</th>
            <th>Customer</th>
            <th>Status</th>
            <th>Total Amount</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {orders.map((order) => (
            <React.Fragment key={order.id}>
              {/* Main Order Row */}
              <tr
                onClick={() => toggleOrderDetails(order.id)}
                className={getStatusColor(order.status)}
                style={{ cursor: "pointer" }}
              >
                <td>{order.id}</td>
                <td>{order.customerUsername}</td>
                <td>
                  <select
                    className="form-select"
                    value={order.status}
                    onChange={(e) => handleStatusChange(order.id, e.target.value)}
                  >
                    <option value="Pending">Pending</option>
                    <option value="Shipped">Shipped</option>
                    <option value="Done">Done</option>
                  </select>
                </td>
                <td>${order.totalAmount.toFixed(2)}</td>
                <td>{selectedOrder === order.id ? "▲" : "▼"}</td>
              </tr>

              {/* Expanded Details Row */}
              {selectedOrder === order.id && (
                <tr>
                  <td colSpan="5">
                    <div className="p-3 border rounded bg-light">
                      <p><strong>Company:</strong> {order.companyName}</p>
                      <p><strong>Email:</strong> {order.email}</p>
                      <p><strong>Phone:</strong> {order.phoneNumber}</p>
                      <p><strong>Address:</strong> {order.address}</p>
                      <p><strong>Notes:</strong> {order.notes || "No additional notes"}</p>
                      <h5>Products</h5>
                      <table className="table table-sm">
                        <thead>
                          <tr>
                            <th>Product ID</th>
                            <th>Name</th>
                            <th>Quantity</th>
                            <th>Unit Price</th>
                          </tr>
                        </thead>
                        <tbody>
                          {order.products.map((product) => (
                            <tr key={product.productId}>
                              <td>{product.productId}</td>
                              <td>{product.name}</td>
                              <td>{product.quantity}</td>
                              <td>${product.unitPrice.toFixed(2)}</td>
                            </tr>
                          ))}
                        </tbody>
                      </table>
                    </div>
                  </td>
                </tr>
              )}
            </React.Fragment>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default Orders;
