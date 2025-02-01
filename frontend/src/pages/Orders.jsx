import React, { useEffect, useState } from "react";

const Orders = () => {
  const [orders, setOrders] = useState([]);
  const [error, setError] = useState("");

  useEffect(() => {
    const fetchOrders = async () => {
      try {
        // GraphQL query for fetching all orders
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
        }
        `;

        // Sending the GraphQL request
        const response = await fetch("http://localhost:8080/graphql", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({ query }),
        });

        if (!response.ok) {
          throw new Error("Failed to fetch orders.");
        }

        const responseData = await response.json();

        if (responseData.errors) {
          throw new Error(responseData.errors[0].message || "Error fetching data.");
        }

        // Update state with the orders data
        setOrders(responseData.data.allOrders);
      } catch (err) {
        setError(err.message);
      }
    };

    fetchOrders();
  }, []);

  return (
    <div>
      <h1>Orders</h1>
      {error && <p className="text-danger">{error}</p>}
      <table className="table table-bordered">
        <thead>
          <tr>
            <th>Order ID</th>
            <th>Customer</th>
            <th>Status</th>
            <th>Total Amount</th>
          </tr>
        </thead>
        <tbody>
          {orders.map((order) => (
            <tr key={order.id}>
              <td>{order.id}</td>
              <td>{order.customerUsername}</td>
              <td>{order.status}</td>
              <td>{order.totalAmount.toFixed(2)}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default Orders;


