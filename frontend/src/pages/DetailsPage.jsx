import React, { useState, useEffect } from 'react';
import { jwtDecode } from "jwt-decode";
import { CardElement, useStripe, useElements } from "@stripe/react-stripe-js";
import orderImage from "../Home.jpg";
import Navbar from "../components/Navbar";

const CheckoutForm = ({ totalAmount, formData, cart }) => {
  const stripe = useStripe();
  const elements = useElements();
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  const handlePayment = async (event) => {
    event.preventDefault();
    console.log("handlePayment triggered!");
  
    setError("");
    setSuccess("");
    setLoading(true);  // Show loading animation
  
    if (!stripe || !elements) {
      setError("Stripe.js has not loaded yet.");
      setLoading(false);
      return;
    }
  
    const cardElement = elements.getElement(CardElement);
    if (!cardElement) {
      setError("Card element not found.");
      setLoading(false);
      return;
    }
  
    try {
      console.log("Form Data:", formData);

      // 1. Create Payment Method
      const { paymentMethod, error: paymentError } = await stripe.createPaymentMethod({
        type: "card",
        card: cardElement,
        billing_details: {
          name: formData?.name || "Unknown",
          email: formData?.email || "",
          phone: formData?.phoneNumber || "",
          address: {
            line1: formData?.address || "Unknown",
            postal_code: formData?.postcode || "",
          },
        },
      });

      if (paymentError) {
        setError(paymentError.message);
        console.error("Payment Method Error:", paymentError.message);
        setLoading(false);
        return;
      }

      console.log("Payment Method Created:", paymentMethod.id);

      // 2. Send Payment Method ID to the Backend
      const response = await fetch("http://localhost:8080/graphql", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          query: `
          mutation {
            createPayment(
              amount: ${totalAmount}, 
              currency: "eur", 
              paymentMethod: STRIPE, 
              paymentMethodId: "${paymentMethod.id}"
            ) {
              id
              status
              clientSecret
            }
          }
          `,
        }),
      });

      const jsonResponse = await response.json();
      console.log("GraphQL Response:", jsonResponse);
      
      if (!jsonResponse.data || !jsonResponse.data.createPayment || !jsonResponse.data.createPayment.clientSecret) {
        console.error("Client secret missing in response:", jsonResponse);
        setError("Payment failed: Client secret not found.");
        setLoading(false);
        return;
      }

      console.log("Skipping manual confirmation because PaymentIntent is already confirmed.");
      setSuccess("Payment successful!");

      // 3. If payment is successful, place the order
      await placeOrder();

    } catch (err) {
      setError("Payment failed. Please try again.");
      console.error("Payment Error:", err);
      setLoading(false);
    }
};

const placeOrder = async () => {
  try {
    console.log("Placing order...");

    console.log(cart)

    // Prepare products array for GraphQL mutation
    const products = cart.map((item) => ({
      productId: item.productId,
      quantity: item.quantity,
      name: item.name,
      unitPrice: item.unitPrice
    }));

    const orderResponse = await fetch("http://localhost:8080/graphql", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        query: `
        mutation CreateOrder($input: OrderInput!) {
          createOrder(input: $input) {
            id
            customerUsername
            totalAmount
            status
            createdAt
            products {
              productId
              name
              quantity
              unitPrice
            }
          }
        }
        `,
        variables: {
          input: {
            customerUsername: "testUser", // Keep this if your GraphQL schema requires it
            email: formData.email,
            address: formData.address,
            phoneNumber: formData.phoneNumber,
            notes: formData.notes,
            products: products, // Pass the correct product array
          },
        },
      }),
    });

    const orderJson = await orderResponse.json();
    console.log("Order Response:", orderJson);

    if (!orderJson.data || !orderJson.data.createOrder) {
      throw new Error("Order placement failed.");
    }

    console.log("✅ Order placed successfully!");

    // ✅ Redirect to Success Page
    setTimeout(() => {
      window.location.href = "/success"; // Redirect after success
    }, 2000);

  } catch (err) {
    setError("❌ Order placement failed. Please contact support.");
    console.error("Order Error:", err);
  } finally {
    setLoading(false);
  }
};





// Loader Animation
const Loader = () => (
  <div className="loading-spinner">
    <div className="spinner"></div>
  </div>
);

  

  return (
    <div className="payment-form">
      <div className="card p-3 border rounded mt-3">
        <CardElement className="form-control" />
      </div>
      {error && <p className="text-danger mt-2">{error}</p>}
      {success && <p className="text-success mt-2">{success}</p>}
      <button onClick={handlePayment} className="btn btn-brown mt-3 w-100" disabled={!stripe || loading}>
        {loading ? "Processing..." : `Pay €${(totalAmount || 0).toFixed(2)}`}
      </button>
    </div>
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

    const total = savedCart.reduce((sum, item) => {
      const itemPrice = item.price || item.unitPrice || 0;
      return sum + itemPrice * (item.quantity || 1);
    }, 0);

    setTotalAmount(total);

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

      <div className="full-width-image-container">
        <div className="overlay">
          <h1 className="overlay-text">ORDER DETAILS</h1>
        </div>
        <img src={orderImage} alt="Order" className="full-width-image" />
      </div>

      <div className="container mt-4">
        <div className="row">
          <div className="col-md-6">
            <h3 className="mb-3">Customer Information</h3>
            <form onSubmit={(e) => e.preventDefault()}>
              {["name", "address", "postcode", "email", "phoneNumber", "notes"].map((field) => (
                <div className="mb-3" key={field}>
                  <label className="form-label">{field.charAt(0).toUpperCase() + field.slice(1)}</label>
                  <input
                    type="text"
                    name={field}
                    className="form-control"
                    value={formData[field]}
                    onChange={handleChange}
                    required
                  />
                </div>
              ))}
              <div className="d-flex gap-3">
                <button type="button" className={`btn ${paymentMethod === "cash" ? "btn-dark" : "btn-outline-dark"}`} onClick={() => setPaymentMethod("cash")}>Cash</button>
                <button type="button" className={`btn ${paymentMethod === "card" ? "btn-dark" : "btn-outline-dark"}`} onClick={() => setPaymentMethod("card")}>Card</button>
              </div>
              {paymentMethod === "card" && <CheckoutForm totalAmount={totalAmount} formData={formData} cart={cart} />}
            </form>
          </div>

          <div className="col-md-6">
            <h3 className="mb-3">Products in Your Cart</h3>
            {cart.length === 0 ? (
              <p className="text-center text-danger">Your cart is empty.</p>
            ) : (
              <div className="cart-box-container">
                {cart.map((item) => (
                  <div key={item.productId} className="cart-item-box">
                    <h5>{item.name} {item.selectedSize ? `(${item.selectedSize})` : ""}</h5>
                    <p>Price: €{(item.price || item.unitPrice || 0).toFixed(2)}</p>
                    <p>Quantity: {item.quantity}</p>
                  </div>
                ))}
                <h4 className="mt-3 text-end fw-bold">Total: €{(totalAmount || 0).toFixed(2)}</h4>
              </div>
            )}
          </div>
        </div>
      </div>

      {/* Basic CSS Styling */}
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

