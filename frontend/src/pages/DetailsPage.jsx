import React, { useState, useEffect } from 'react';
import { jwtDecode } from "jwt-decode";

const DetailsPage = ({ token }) => {
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    phoneNumber: '',
    companyName: '',
    address: '',
  });

  useEffect(() => {
    const token = localStorage.token;

    if (token) {
      // Decode the token and prefill the form
      const decoded = jwtDecode(token);
      console.log(decoded);
      setFormData({
        name: decoded.sub || '',
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

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log('Order Details:', formData);
  };

  return (
    <div className="container mt-5">
      <h1 className="text-center mb-4">Order Details</h1>
      <form onSubmit={handleSubmit}>
        <div className="mb-3">
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
        <div className="mb-3">
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
        <div className="mb-3">
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
        <div className="mb-3">
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
        <div className="mb-3">
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
        <button type="submit" className="btn btn-success">Submit Order</button>
      </form>
    </div>
  );
};

export default DetailsPage;