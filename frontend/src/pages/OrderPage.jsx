import React, { useState, useEffect } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import "../index.css";
import Navbar from "../components/Navbar";
import { FaCheckCircle, FaClock, FaMapMarkerAlt, FaShoppingCart } from "react-icons/fa";

let orderingImage = require("../Home.jpg");

const OnlineOrdering = () => {
  const [products, setProducts] = useState([]);
  const [cart, setCart] = useState([]);

  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  // Lade die Produkte von der API
  useEffect(() => {
    fetch("http://localhost:8080/products-data")
      .then((response) => {
        if (!response.ok) {
          throw new Error("Fehler beim Abrufen der Daten");
        }
        return response.json();
      })
      .then((data) => {
        setProducts(data);
        setLoading(false);
      })
      .catch((error) => {
        setError(error.message);
        setLoading(false);
      });
  }, []);

  // Warenkorb aus localStorage laden
  useEffect(() => {
    const savedCart = localStorage.getItem("cart");
    if (savedCart) {
      setCart(JSON.parse(savedCart));
    }
  }, []);

  // Warenkorb in localStorage speichern, wenn sich cart ändert
  useEffect(() => {
    if (cart.length > 0) {
      localStorage.setItem("cart", JSON.stringify(cart));
    }
  }, [cart]);

  // Produkt zum Warenkorb hinzufügen (in Echtzeit sichtbar)
  const addToCart = (product) => {
    setCart((prevCart) => {
      const existingItem = prevCart.find((item) => item.productId === product.productId);
      let updatedCart;

      if (existingItem) {
        updatedCart = prevCart.map((item) =>
          item.productId === product.productId ? { ...item, quantity: item.quantity + 1 } : item
        );
      } else {
        updatedCart = [...prevCart, { ...product, quantity: 1 }];
      }

      return updatedCart;
    });
  };


  // Produkte nach Kategorien filtern
  const categorizeProducts = (start, end) =>
    products.filter((product) => product.productId >= start && product.productId <= end);

  return (
    <>
      <Navbar />

      {/* Hintergrundbild */}
      <div className="position-relative" style={{ height: "50vh", overflow: "hidden" }}>
        <img src={orderingImage} alt="Ordering" className="img-fluid w-100" style={{ objectFit: "cover", height: "100%" }} />
      </div>

      {/* Bestellungsabschnitt */}
      <section className="container text-start py-5" style={{ backgroundColor: "#FCFCF7", maxWidth: "100%" }}>
        <h1 className="fw-bold" style={{ fontSize: "6rem", color: "#4E342E", fontWeight: "900" }}>ONLINE ORDERING</h1>
        <p className="fs-5 text-dark">Bestelle jetzt online! Wähle aus unserer Speisekarte deine Lieblingsgerichte.</p>

        <div className="d-flex align-items-center mb-4">
          <span className="badge bg-light text-dark px-4 py-3" style={{ fontSize: "0.9rem", borderRadius: "25px" }}>
            <FaCheckCircle className="me-2 text-success" /> Bestellungen möglich
          </span>
        </div>

        <div className="btn-group mb-4" role="group">
          <button className="btn btn-dark px-3 py-3 m-2" style={{ fontSize: "1.0rem", borderRadius: "25px" }}>Abholung</button>
          <button className="btn btn-outline-dark px-3 py-3 m-2" style={{ fontSize: "1.0rem", borderRadius: "25px" }}>Lieferung</button>
        </div>

        <p className="text-dark fs-6">
          <FaClock className="me-2" /> Abholzeit: bis zu 30 Minuten{" "}
          <a href="#" className="text-decoration-underline text-dark fw-bold">Ändern</a> &nbsp;
          <FaMapMarkerAlt className="me-2" /> Adresse: 500 Terry Francine, San Francisco, CA
        </p>
      </section>

      {/* Fehler- oder Ladeanzeige */}
      {loading ? (
        <div className="text-center my-5">
          <p className="text-muted">Lade Speisekarte...</p>
        </div>
      ) : error ? (
        <div className="text-center my-5">
          <p className="text-danger">Fehler: {error}</p>
        </div>
      ) : (
        <section className="container text-start py-5" style={{ backgroundColor: "#FFFFFF", maxWidth: "90%" }}>
          <h1 className="fw-bold mt-5" style={{ fontSize: "5rem", color: "#4E342E" }}>Speisekarte</h1>

          {[
            { category: "Vorspeisen", range: [1, 5] },
            { category: "Salate", range: [6, 12] },
            { category: "Pizza", range: [13, 19] },
            { category: "Pasta", range: [20, 23] },
            { category: "Burger", range: [24, 26] },
            { category: "Desserts", range: [27, 29] },
          ].map(({ category, range }) => {
            const categoryProducts = categorizeProducts(range[0], range[1]);
            if (categoryProducts.length === 0) return null;

            return (
              <div key={category} className="mb-5">
                <h3 className="mt-4 text-uppercase fw-bold">{category}</h3>
                <div className="row mt-3">
                  {categoryProducts.map((product) => (
                    <div className="col-md-4 mb-4" key={product.productId}>
                      <div className="p-3 border rounded-3 shadow-sm">
                        <h5 className="fw-bold text-dark">{product.name}</h5>
                        {product.description && <p className="text-muted">{product.description}</p>}
                        <div className="d-flex justify-content-between align-items-center">
                          <span className="fs-5 fw-bold text-dark">€{product.unitPrice.toFixed(2)}</span>
                          <div className="d-flex align-items-center">
                            <button className="btn btn-outline-success" onClick={() => addToCart(product)}>
                              <FaShoppingCart className="me-2" /> In den Warenkorb
                            </button>
                          </div>
                        </div>
                      </div>
                    </div>
                  ))}
                </div>
              </div>
            );
          })}
        </section>
      )}
    </>
  );
};

export default OnlineOrdering;
