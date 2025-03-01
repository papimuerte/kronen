import React from "react";

const Footer = () => {
  return (
    <footer className="container-fluid text-white py-5" style={{ backgroundColor: "#4E342E" }}>
      <div className="row text-center">
        <div className="col-12">
          <h1 className="fw-bold" style={{ fontSize: "4rem", color: "#f8d7da" }}>KRONNENBRUNNEN</h1>
          <p className="fst-italic" style={{ fontSize: "1.5rem" }}>Bar &bull; Restaurant &bull; Pizzeria</p>
        </div>
      </div>
      <div className="row text-center mt-4">
        <div className="col-md-4">
          <p>MENU</p>
          <p>ORDER ONLINE</p>
          <p>RESERVATIONS</p>
          <p>ABOUT</p>
          <p>CONTACT</p>
        </div>
        <div className="col-md-4">
          <p className="fw-bold">OPENING HOURS</p>
          <p>Tuesday to Friday: 9am - 7pm</p>
          <p>Saturday & Sunday: 10am - 7pm</p>
        </div>
        <div className="col-md-4">
          <p className="fw-bold">WHERE WE ARE</p>
          <p>500 Terry Francine St, San Francisco</p>
          <p>123-456-7890</p>
          <p>info@mysite.com</p>
        </div>
      </div>
      <div className="row text-center mt-4">
        <div className="col-12">
          <p>Terms & Conditions &bull; Refund Policy &bull; Privacy Policy &bull; Accessibility Statement</p>
        </div>
      </div>
    </footer>
  );
};

export default Footer;
