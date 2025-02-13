import React, { useState, useEffect } from 'react';
import { jwtDecode } from "jwt-decode"; // Import JWT decoding library

const DetailsPage = ({ token }) => {
  const [formData, setFormData] = useState({ // State to store user details
    name: '',
    email: '',
    phoneNumber: '',
    companyName: '',
    address: '',
  });

  useEffect(() => {
    const token = localStorage.token; // Retrieve token from localStorage
    console.log(localStorage);

    if (token) {
      const decoded = jwtDecode(token); // Decode JWT token
      console.log(decoded);
      setFormData({
        name: decoded.sub || '', // Extract user details from decoded token
        email: decoded.email || '',
        address: decoded.address || '',
        phoneNumber: decoded.phoneNumber || '',
        companyName: decoded.companyName || '',
      });
    }
  }, [token]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Retrieve and parse the cart data from localStorage
    const cartData = JSON.parse(localStorage.getItem("cart") || "[]");
    
    // Extract relevant product details
    const products = cartData.map(({ productId, name, unitPrice, quantity }) => ({
      productId,
      name,
      unitPrice,
      quantity,
    }));

    const mutation = `
      mutation CreateOrder($input: OrderInput!) {
        createOrder(input: $input) {
          id
          customerUsername
          totalAmount
          status
          createdAt
        }
      }
    `;

    const input = {
      customerUsername: formData.name,
      email: formData.email || '',
      address: formData.address || '',
      phoneNumber: formData.phoneNumber || '',
      companyName: formData.companyName || '',
      products, // Include extracted products
    };

    try {
      const response = await fetch("http://localhost:8080/graphql", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ query: mutation, variables: { input } }),
      });

      if (!response.ok) throw new Error("Failed to create the order.");
      const responseData = await response.json();

      if (responseData.errors) {
        throw new Error(responseData.errors[0].message || "Error creating order.");
      }

      console.log("Order created successfully:", responseData.data.createOrder);
      alert("Order created successfully!");

      // Clear cart after order
      localStorage.removeItem("cart");
    } catch (error) {
      console.error("Error creating order:", error.message);
      alert("Failed to create the order. Please try again.");
    }
  };

  return (
    <div className="container mt-5"> {/* Main container */}
      <h1 className="text-center mb-4">Order Details</h1> {/* Page title */}
      <form onSubmit={handleSubmit}> {/* Form submission handler */}
        <div className="mb-3"> {/* Name field */}
          <label htmlFor="name" className="form-label">Name</label>
          <input
            type="text"
            id="name"
            name="name"
            className="form-control"
            value={formData.name}
            onChange={handleChange}
            required
          />
        </div>
        <div className="mb-3"> {/* Email field */}
          <label htmlFor="email" className="form-label">Email</label>
          <input
            type="email"
            id="email"
            name="email"
            className="form-control"
            value={formData.email}
            onChange={handleChange}
            required
          />
        </div>
        <div className="mb-3"> {/* Phone number field */}
          <label htmlFor="phoneNumber" className="form-label">Phone Number</label>
          <input
            type="text"
            id="phoneNumber"
            name="phoneNumber"
            className="form-control"
            value={formData.phoneNumber}
            onChange={handleChange}
            required
          />
        </div>
        <div className="mb-3"> {/* Address field */}
          <label htmlFor="address" className="form-label">Address</label>
          <textarea
            id="address"
            name="address"
            className="form-control"
            value={formData.address}
            onChange={handleChange}
            required
          ></textarea>
        </div>
        <div className="mb-3"> {/* Company name field */}
          <label htmlFor="companyName" className="form-label">Company Name</label>
          <textarea
            id="companyName"
            name="companyName"
            className="form-control"
            value={formData.companyName}
            onChange={handleChange}
            required
          ></textarea>
        </div>
        <button type="submit" className="btn btn-success">Submit Order</button> {/* Submit button */}
      </form>
    </div>
  );
};

export default DetailsPage; // Export component for usage
