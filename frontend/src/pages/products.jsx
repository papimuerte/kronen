import React, { useEffect, useState } from "react";

const Products = () => {
  const [products, setProducts] = useState([]); // State to store product list
  const [error, setError] = useState(""); // State to store error messages
  const [editProductId, setEditProductId] = useState(null); // Track the product being edited
  const [editedProduct, setEditedProduct] = useState({}); // Track changes for editing
  const [newProduct, setNewProduct] = useState({ // Track input for new product
    name: "",
    description: "",
    unitPrice: "",
    minimumOrderQuantity: "",
    weightGram: "",
  });

  useEffect(() => {
    const fetchProducts = async () => {
      try {
        const response = await fetch("http://localhost:8080/api/products"); // Fetch products from API
        if (!response.ok) {
          throw new Error("Failed to fetch products.");
        }
        const data = await response.json();
        setProducts(data);
      } catch (err) {
        setError(err.message);
      }
    };

    fetchProducts();
  }, []); // Fetch products on component mount

  const handleEditClick = (product) => {
    setEditProductId(product.productId); // Enable editing mode for selected product
    setEditedProduct({ ...product }); // Set initial values for editing
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setEditedProduct((prev) => ({ ...prev, [name]: value })); // Update field values
  };

  const handleSaveClick = async () => {
    try {
      const response = await fetch(`http://localhost:8080/api/products/admin/${editProductId}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(editedProduct),
      });

      if (!response.ok) {
        throw new Error("Failed to update the product.");
      }

      setProducts((prev) =>
        prev.map((product) =>
          product.productId === editProductId ? { ...product, ...editedProduct } : product
        )
      );

      setEditProductId(null); // Reset editing state
      setEditedProduct({}); // Clear edited product state
    } catch (err) {
      setError(err.message);
    }
  };

  const handleDeleteClick = async (productId) => {
    try {
      const response = await fetch(`http://localhost:8080/api/products/admin/${productId}`, {
        method: "DELETE",
      });

      if (!response.ok) {
        throw new Error("Failed to delete the product.");
      }

      setProducts((prev) => prev.filter((product) => product.productId !== productId)); // Remove product from state
    } catch (err) {
      setError(err.message);
    }
  };

  const handleAddClick = async () => {
    try {
      const response = await fetch("http://localhost:8080/api/products/admin/add", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          ...newProduct,
          productId: Date.now().toString(), // Generate a unique ID for new product
        }),
      });

      if (!response.ok) {
        throw new Error("Failed to add the product.");
      }

      setProducts((prev) => [...prev, { ...newProduct, productId: Date.now().toString() }]);
      setNewProduct({ name: "", description: "", unitPrice: "", minimumOrderQuantity: "", weightGram: "" }); // Reset form
    } catch (err) {
      setError(err.message);
    }
  };

  return (
    <div>
      <h1>Products</h1>
      {error && <p className="text-danger">{error}</p>} {/* Display error message if any */}

      {/* Form to add a new product */}
      <div className="mb-4">
        <h2>Add New Product</h2>
        <div className="row g-2">
          <div className="col-md-3">
            <input
              type="text"
              name="name"
              placeholder="Name"
              className="form-control"
              value={newProduct.name}
              onChange={(e) => setNewProduct({ ...newProduct, name: e.target.value })}
            />
          </div>
          <div className="col-md-3">
            <input
              type="text"
              name="description"
              placeholder="Description"
              className="form-control"
              value={newProduct.description}
              onChange={(e) => setNewProduct({ ...newProduct, description: e.target.value })}
            />
          </div>
          <div className="col-md-2">
            <input
              type="number"
              name="unitPrice"
              placeholder="Price"
              className="form-control"
              value={newProduct.unitPrice}
              onChange={(e) => setNewProduct({ ...newProduct, unitPrice: e.target.value })}
            />
          </div>
          <div className="col-md-2">
            <button className="btn btn-success w-100" onClick={handleAddClick}>Add</button>
          </div>
        </div>
      </div>

      {/* Table displaying products */}
      <table className="table table-bordered">
        <thead>
          <tr>
            <th>Product ID</th>
            <th>Name</th>
            <th>Description</th>
            <th>Price</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {products.map((product) => (
            <tr key={product.productId}>
              <td>{product.productId}</td>
              <td>{product.name}</td>
              <td>{product.description}</td>
              <td>{product.unitPrice}</td>
              <td>
                <button className="btn btn-primary btn-sm me-2" onClick={() => handleEditClick(product)}>Edit</button>
                <button className="btn btn-danger btn-sm" onClick={() => handleDeleteClick(product.productId)}>Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default Products; // Export Products component
