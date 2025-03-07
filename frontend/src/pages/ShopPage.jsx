import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { FaShoppingBag } from "react-icons/fa";
import "../index.css";
import { useRef } from "react";
import { Carousel } from "react-bootstrap"; // Corrected import
import Navbar from "../components/Navbar";
import Footer from "../components/Footer";



import { motion, useScroll, useTransform } from "framer-motion";

let image1 = require("../dessert.jpg");
let image2 = require("../drinks.jpg");
let image3 = require("../Home.jpg");
let image4 = require("../pizza.jpg");
let image5 = require("../shrimps.jpg");
let image6 = require("../aperol.jpg");




const ParallaxImage = ({ src, speed, width, margin }) => {
  const ref = useRef(null);
  const { scrollYProgress } = useScroll({ target: ref, offset: ["start end", "end start"] });
  const y = useTransform(scrollYProgress, [0, 1], [0, speed * 1.5]);

  return <motion.img ref={ref} src={src} className="img-fluid" style={{ width: width, height: "auto", y, margin: margin }} />;
};

const HeroSection = () => {
  return (
    <>
      <div className="hero-container">

        <Navbar></Navbar>
        
        {/* Hero Content */}
        <div className="hero-content text-center d-flex flex-column justify-content-center align-items-center">
          <h1 className="display-1 fw-bold text-white text-uppercase hero-title" style={{ fontSize: "8rem" }}>KRONENBRUNNEN</h1>
          <p className="text-white fs-3 fst-italic">Bar &bull; Restaurant &bull; Pizzeria</p>
          <div className="hero-buttons mt-4">
            <a href="/order" className="btn order-btn me-3">Order Online</a>
            <a href="#contact" className="btn order-btn">Reservations</a>
          </div>
        </div>
      </div>
      
      {/* Scrolling Bar (Outside Hero Container) */}
      <div className="container-fluid py-2" style={{ backgroundColor: "#4E342E" }}>
        <marquee className="text-white fw-bold">
          Exclusive Offers &nbsp; | &nbsp; Happy Hour Specials &nbsp; | &nbsp; Musik &nbsp; | &nbsp; Book Your Table Now
        </marquee>
      </div>

      {/* About Section */}
      <section className="container py-5 mt-5" id="about">
        <div className="row align-items-center">
          <div className="col-lg-6">
            <h2 className="fw-bold text-dark about-title" style={{ fontSize: "6rem", fontFamily: "Times New Roman, serif" }}>ABOUT US</h2>
            <p className="text-muted fs-5">
              This is the space to introduce visitors to the business or brand. Briefly explain who's behind it, what it does,
              and what makes it unique. Share its core values and what this site has to offer. Define the qualities and values that make it unique.
            </p>
          </div>
        </div>
      </section>

      {/* Menu Section */}
      <section className="container-fluid d-flex align-items-center" style={{ backgroundColor: "#3E2723"}}>
        <div className="row w-100 d-flex align-items-center" style={{ minHeight: "700px" }}>
          <div className="about-img col-lg-6"></div>
          <div className="col-lg-6 text-center d-flex flex-column justify-content-center align-items-center">
            <h2 className="fw-bold text-white" style={{ fontSize: "6rem", fontFamily: "Times New Roman, serif" }}>OUR MENU</h2>
            <p className="text-white fs-5 w-75 align-center">
              This is the space to introduce the restaurant’s menu. Briefly describe the types of dishes and beverages offered and highlight any special features. Encourage site visitors to view the whole menu by exploring the menu page.
            </p>
            <a href="#menu" className="btn btn-outline-light px-4 py-2">VIEW MENU</a>
          </div>
        </div>
      </section>

        {/* Quote Section with More Spacing */}
      <section className="container py-7 text-center position-relative d-flex flex-column justify-content-center align-items-center" id="quote-section" style={{ minHeight: "1500px", position: "relative" }}>
        <div className="d-flex justify-content-between align-items-center w-100" style={{ marginBottom: "200px" }}>
          <ParallaxImage src={image6} speed={100} width="180px" margin="0 50px" />
          <ParallaxImage src={image2} speed={80} width="240px" margin="0 250px" />
          <ParallaxImage src={image1} speed={200} width="210px" margin="0 150px" />
        </div>
        
        <motion.div initial={{ y: 50, opacity: 0 }} animate={{ y: 0, opacity: 1 }} transition={{ duration: 1 }} className="mt-5">
          <h2 className="fw-bold text-dark" style={{ fontSize: "8rem", fontFamily: "Playfair Display, serif", fontStyle: "italic" }}>
            "The perfect place for any occasion"
          </h2>
        </motion.div>
        
        <div className="d-flex justify-content-between align-items-center w-100 mt-5" style={{ marginTop: "200px" }}>
          <ParallaxImage src={image4} speed={-200} width="220px" margin="0 80px" />
          <ParallaxImage src={image5} speed={-120} width="260px" margin="0 120px" />
          <ParallaxImage src={image3} speed={-90} width="300px" margin="0 80px" />
        </div>
      </section>

      
        {/* Dining Options Section with Centered Images */}
        <section className="container-fluid d-flex align-items-center position-relative" style={{ backgroundColor: "#2D1B14", padding: "6rem 0" }}>
        <div className="row w-100 d-flex align-items-center position-relative" style={{ minHeight: "700px" }}>
          <div className="col-lg-6 text-white text-center d-flex flex-column justify-content-center align-items-center">
            <h2 className="fw-bold text-white" style={{ fontSize: "6rem", fontFamily: "Times New Roman, serif" }}>DINING OPTIONS</h2>
            <p className="text-white fs-5 w-75">
              This is the space to introduce the Services section. Briefly describe the types of services offered and highlight any special benefits or features.
            </p>
            <a href="#order" className="btn btn-outline-light px-4 py-2">ORDER ONLINE</a>
          </div>
          <div className="col-lg-6 position-relative d-flex justify-content-center align-items-center" style={{ height: "500px" }}>
            <img src={image1} alt="Dining Video" className="img-fluid position-absolute" style={{ width: "55%", zIndex: 1, top: "45%", left: "50%", transform: "translate(-50%, -50%)" }} />
            <img src={image2} alt="Dining Table" className="img-fluid position-absolute" style={{ width: "20%", zIndex: 1, top: "-10%", left: "20%", transform: "translate(-50%, -50%)" }} />
            <img src={image3} alt="Chef Cooking" className="img-fluid position-absolute" style={{ width: "45%", zIndex: 2, top: "110%", left: "68%", transform: "translate(-50%, -50%)" }} />
          </div>
        </div>
      </section>

            {/* New Multi-Image Carousel Section */}
            <section className="container-fluid d-flex align-items-center position-relative" style={{ padding: "4rem 0" }}>
        <div className="row w-100 d-flex align-items-center justify-content-center">
          <div className="col-12 d-flex justify-content-center align-items-center">
            <Carousel className="w-100" indicators={false} interval={3000} wrap={true}>
              <Carousel.Item>
                <div className="d-flex justify-content-center">
                  <img src={image1} alt="Slide 1" className="img-fluid-carousel" style={{ width: "20%", height: "400px", objectFit: "cover", margin: "0 10px" }} />
                  <img src={image2} alt="Slide 2" className="img-fluid-carousel" style={{ width: "20%", height: "400px", objectFit: "cover", margin: "0 10px" }} />
                  <img src={image3} alt="Slide 3" className="img-fluid-carousel" style={{ width: "20%", height: "400px", objectFit: "cover", margin: "0 10px" }} />
                  <img src={image3} alt="Slide 3" className="img-fluid-carousel" style={{ width: "20%", height: "400px", objectFit: "cover", margin: "0 10px" }} />
                </div>
              </Carousel.Item>
              <Carousel.Item>
                <div className="d-flex justify-content-center">
                  <img src={image4} alt="Slide 4" className="img-fluid-carousel" style={{ width: "20%", height: "400px", objectFit: "cover", margin: "0 10px" }} />
                  <img src={image5} alt="Slide 5" className="img-fluid-carousel" style={{ width: "20%", height: "400px", objectFit: "cover", margin: "0 10px" }} />
                  <img src={image6} alt="Slide 6" className="img-fluid-carousel" style={{ width: "20%", height: "400px", objectFit: "cover", margin: "0 10px" }} />
                  <img src={image6} alt="Slide 6" className="img-fluid-carousel" style={{ width: "20%", height: "400px", objectFit: "cover", margin: "0 10px" }} />
                </div>
              </Carousel.Item>
            </Carousel>
          </div>
        </div>
      </section>

        {/* Reservation Section */}
        <section id="contact" className="container text-center py-5 mb-2 " style={{ backgroundColor: "#3E2723", color: "white" , borderRadius: "25px"}}>
        <h2 className="fw-bold" style={{ fontSize: "3rem" }}>Make a Reservation</h2>
        <p className="fs-5">Reserve a table in advance to enjoy a seamless dining experience.</p>
        {/* Google Maps Embed */}
        <div className="mt-5">
          <iframe 
            src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d5113.980112072956!2d9.10959117718145!3d50.142617871535066!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x47bd3b5bc9fa32e1%3A0x4514f6d6d574684!2sRistaurante%20Kronenbrunnen!5e0!3m2!1sde!2sde!4v1740419759451!5m2!1sde!2sde" 
            width="100%" 
            height="450" 
            style={{ border: "0", borderRadius: "15px" }} 
            allowFullScreen="" 
            loading="lazy" 
            referrerPolicy="no-referrer-when-downgrade">
          </iframe>
        </div>
        <form className="row g-3 justify-content-center mt-2">
        <div className="col-md-3">
            <input type="text" className="form-control" placeholder="Full Name" required style={{ padding: "12px", borderRadius: "10px", border: "none", boxShadow: "0 4px 6px rgba(0, 0, 0, 0.1)" }} />
          </div>
          <div className="col-md-3">
            <input type="email" className="form-control" placeholder="Email" required style={{ padding: "12px", borderRadius: "10px", border: "none", boxShadow: "0 4px 6px rgba(0, 0, 0, 0.1)" }} />
          </div>
          <div className="col-md-3">
            <input type="text" className="form-control" placeholder="Phone Number" required style={{ padding: "12px", borderRadius: "10px", border: "none", boxShadow: "0 4px 6px rgba(0, 0, 0, 0.1)" }} />
          </div>
          <div className="col-md-3">
            <input type="date" className="form-control" required style={{ padding: "12px", borderRadius: "10px", border: "none", boxShadow: "0 4px 6px rgba(0, 0, 0, 0.1)" }} />
          </div>
          <div className="col-md-3">
            <input type="time" className="form-control" required style={{ padding: "12px", borderRadius: "10px", border: "none", boxShadow: "0 4px 6px rgba(0, 0, 0, 0.1)" }} />
          </div>
          <div className="col-md-3">
            <input type="number" className="form-control" placeholder="Guests" min="1" required style={{ padding: "12px", borderRadius: "10px", border: "none", boxShadow: "0 4px 6px rgba(0, 0, 0, 0.1)" }} />
          </div>
          <div className="col-12 mt-3">
          <button type="submit" className="btn btn-light px-5 py-3 fw-bold" 
            style={{ 
                borderRadius: "50px", 
                backgroundColor: "#f8d7da", 
                border: "none", 
                fontSize: "1.2rem", 
                transition: "all 0.3s ease",
                boxShadow: "0 4px 6px rgba(0, 0, 0, 0.1)"
            }}
            onMouseOver={(e) => e.target.style.backgroundColor = "#e57373"}
            onMouseOut={(e) => e.target.style.backgroundColor = "#f8d7da"}
            >
              Reserve Now
            </button>
            </div>
        </form>
      </section>

      <Footer></Footer>

    </>
  );
};

export default HeroSection;