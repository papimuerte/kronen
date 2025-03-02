import React, { useState, useEffect } from 'react';
import { jwtDecode } from "jwt-decode";
import { CardElement, useStripe, useElements } from "@stripe/react-stripe-js";
import orderImage from "../Home.jpg";
import Navbar from "../components/Navbar";

const CheckoutForm = ({ totalAmount }) => {
  const stripe = useStripe();
  const elements = useElements();
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  const handlePayment = async (event) => {
    event.preventDefault();
    setLoading(true);
    setError("");
    setSuccess("");

    if (!stripe || !elements) {
        setError("Stripe is not loaded.");
        setLoading(false);
        return;
    }

    const cardElement = elements.getElement(CardElement);

    try {
        // Create a payment method using Stripe.js
        const { paymentMethod, error: paymentError } = await stripe.createPaymentMethod({
            type: "card",
            card: cardElement,
        });

        if (paymentError) {
            setError(paymentError.message);
            setLoading(false);
            return;
        }

        console.log("Payment Method Created:", paymentMethod.id);

        // Send `paymentMethodId` to your backend
        const response = await fetch("http://localhost:8080/graphql", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                query: `
                mutation {
                    createPayment(
                        amount: ${totalAmount}, 
                        currency: "eur", 
                        paymentMethod: "STRIPE", 
                        paymentMethodId: "${paymentMethod.id}"
                    ) {
                        id
                        status
                    }
                }
                `,
            }),
        });

        const { data } = await response.json();
        console.log("Backend Response:", data);

        if (!data || !data.createPayment) {
            setError("Payment failed. Please try again.");
            setLoading(false);
            return;
        }

        // 3️ Confirm the PaymentIntent with Stripe
        const clientSecret = data.createPayment.id;  // Use PaymentIntent ID
        const { paymentIntent, error: confirmError } = await stripe.confirmCardPayment(clientSecret, {
            payment_method: paymentMethod.id,
        });

        if (confirmError) {
            setError(confirmError.message);
        } else if (paymentIntent.status === "succeeded") {
            setSuccess("Payment successful!");
        }

    } catch (err) {
        setError("Payment failed. Please try again.");
        console.error(err);
    }

    setLoading(false);
};

  

  return (
    <form onSubmit={handlePayment} className="payment-form">
      <div className="card p-3 border rounded mt-3">
        <CardElement className="form-control" />
      </div>
      {error && <p className="text-danger mt-2">{error}</p>}
      {success && <p className="text-success mt-2">{success}</p>}
      <button type="submit" className="btn btn-brown mt-3 w-100" disabled={!stripe || loading}>
        {loading ? "Processing..." : `Pay €${totalAmount.toFixed(2)}`}
      </button>
    </form>
  );
};

const DetailsPage = () => {
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    phoneNumber: '',
    address: '',
    postcode: '',
    etage: '',
    notes: '',
  });

  const [cart, setCart] = useState([]);
  const [totalAmount, setTotalAmount] = useState(0);
  const [paymentMethod, setPaymentMethod] = useState("cash");

  useEffect(() => {
    const savedCart = JSON.parse(localStorage.getItem("cart") || "[]");
    setCart(savedCart);
    setTotalAmount(savedCart.reduce((sum, item) => sum + item.unitPrice * item.quantity, 0));

    const token = localStorage.getItem("token");
    if (token) {
      try {
        const decoded = jwtDecode(token);
        setFormData({
          name: decoded.sub || '',
          email: decoded.email || '',
          phoneNumber: decoded.phoneNumber || '',
          address: decoded.address || '',
          postcode: '',
          etage: '',
          notes: '',
        });
      } catch (error) {
        console.error("Invalid token:", error);
      }
    }
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  return (
    <>
      <Navbar />

      {/* Full-width Image Section with Dark Overlay */}
      <div className="full-width-image-container">
        <div className="overlay">
          <h1 className="overlay-text">ORDER DETAILS</h1>
        </div>
        <img src={orderImage} alt="Order" className="full-width-image" />
      </div>

      <div className="container mt-4">
        <div className="row">
          {/* Left Side: Customer Information & Payment */}
          <div className="col-md-6">
            <h3 className="mb-3">Customer Information</h3>
            <form>
              <div className="mb-3">
                <label className="form-label">Name</label>
                <input type="text" name="name" className="form-control" value={formData.name} onChange={handleChange} required />
              </div>
              <div className="mb-3">
                <label className="form-label">Address</label>
                <textarea name="address" className="form-control" value={formData.address} onChange={handleChange} required></textarea>
              </div>
              <div className="mb-3">
                <label className="form-label">Postcode</label>
                <input type="text" name="postcode" className="form-control" value={formData.postcode} onChange={handleChange} required />
              </div>
              <div className="mb-3">
                <label className="form-label">Email</label>
                <input type="email" name="email" className="form-control" value={formData.email} onChange={handleChange} required />
              </div>
              <div className="mb-3">
                <label className="form-label">Phone Number</label>
                <input type="text" name="phoneNumber" className="form-control" value={formData.phoneNumber} onChange={handleChange} required />
              </div>
              <div className="mb-3">
                <label className="form-label">Kommentare</label>
                <input type="text" name="notes" className="form-control" value={formData.notes} onChange={handleChange} required />
              </div>

              <div className="d-flex gap-3">
                <button type="button" className={`btn ${paymentMethod === "cash" ? "btn-dark" : "btn-outline-dark"}`} onClick={() => setPaymentMethod("cash")}>Cash</button>
                <button type="button" className={`btn ${paymentMethod === "card" ? "btn-dark" : "btn-outline-dark"}`} onClick={() => setPaymentMethod("card")}>Card</button>
              </div>
              {paymentMethod === "card" && <CheckoutForm totalAmount={totalAmount} />}
            </form>
          </div>

          {/* Right Side: Cart Summary */}
          <div className="col-md-6">
            <h3 className="mb-3">Products in Your Cart</h3>
            {cart.length === 0 ? (
              <p className="text-center text-danger">Your cart is empty.</p>
            ) : (
              <div className="cart-box-container">
                {cart.map((item) => (
                  <div key={item.productId} className="cart-item-box">
                    <h5>{item.name}</h5>
                    <p>Price: €{item.unitPrice.toFixed(2)}</p>
                    <p>Quantity: {item.quantity}</p>
                    <p><strong>Subtotal: €{(item.unitPrice * item.quantity).toFixed(2)}</strong></p>
                  </div>
                ))}
                <h4 className="mt-3 text-end fw-bold">Total: €{totalAmount.toFixed(2)}</h4>
              </div>
            )}
          </div>
        </div>
      </div>

      {/* Bootstrap CSS Styling */}
      <style>{`
        .full-width-image-container {
          position: relative;
          width: 100%;
          height: 300px;
        }
        .full-width-image {
          width: 100%;
          height: 100%;
          object-fit: cover;
        }
        .overlay {
          position: absolute;
          width: 100%;
          height: 100%;
          background: rgba(0, 0, 0, 0.5);
          display: flex;
          justify-content: center;
          align-items: center;
        }
        .overlay-text {
          color: white;
          font-size: 3rem;
        }
        .cart-item-box {
          border: 1px solid #ddd;
          padding: 15px;
          border-radius: 8px;
          margin-bottom: 10px;
        }
        .btn-brown {
          background-color: #8B4513;
          color: white;
        }
      `}</style>
    </>
  );
};

export default DetailsPage;
