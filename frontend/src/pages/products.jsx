import React, { useEffect, useState } from "react";

const Products = () => {
  const [products, setProducts] = useState([]);
  const [error, setError] = useState("");
  const [editProductId, setEditProductId] = useState(null); // For editing
  const [editedProduct, setEditedProduct] = useState({}); // For edited product
  const [newProduct, setNewProduct] = useState({
    name: "",
    description: "",
    unitPrice: "",
    minimumOrderQuantity: "",
    weightGram: "",
  }); // For adding

  useEffect(() => {
    const fetchProducts = async () => {
      try {
        const response = await fetch("http://localhost:8080/api/products");
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
  }, []);

  // Handle editing
  const handleEditClick = (product) => {
    setEditProductId(product.productId);
    setEditedProduct({ ...product });
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setEditedProduct((prev) => ({ ...prev, [name]: value }));
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

      // Update local state
      setProducts((prev) =>
        prev.map((product) =>
          product.productId === editProductId ? { ...product, ...editedProduct } : product
        )
      );

      setEditProductId(null);
      setEditedProduct({});
    } catch (err) {
      setError(err.message);
    }
  };

  // Handle deleting
  const handleDeleteClick = async (productId) => {
    try {
      const response = await fetch(`http://localhost:8080/api/products/admin/${productId}`, {
        method: "DELETE",
      });

      if (!response.ok) {
        throw new Error("Failed to delete the product.");
      }

      // Remove the product from local state
      setProducts((prev) => prev.filter((product) => product.productId !== productId));
    } catch (err) {
      setError(err.message);
    }
  };

  // Handle adding
  const handleAddClick = async () => {
    try {
      const response = await fetch("http://localhost:8080/api/products/admin/add", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          ...newProduct,
          productId: Date.now().toString(), // Generate a unique ID for the new product
        }),
      });

      if (!response.ok) {
        throw new Error("Failed to add the product.");
      }

      // Add the new product to local state
      setProducts((prev) => [...prev, { ...newProduct, productId: Date.now().toString() }]);
      setNewProduct({
        name: "",
        description: "",
        unitPrice: "",
        minimumOrderQuantity: "",
        weightGram: "",
      }); // Reset the form
    } catch (err) {
      setError(err.message);
    }
  };

  return (
    <div>
      <h1>Products</h1>
      {error && <p className="text-danger">{error}</p>}

      {/* Add New Product */}
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
            <input
              type="number"
              name="minimumOrderQuantity"
              placeholder="Min Qty"
              className="form-control"
              value={newProduct.minimumOrderQuantity}
              onChange={(e) =>
                setNewProduct({ ...newProduct, minimumOrderQuantity: e.target.value })
              }
            />
          </div>
          <div className="col-md-2">
            <input
              type="number"
              name="weightGram"
              placeholder="Weight (g)"
              className="form-control"
              value={newProduct.weightGram}
              onChange={(e) => setNewProduct({ ...newProduct, weightGram: e.target.value })}
            />
          </div>
          <div className="col-md-2">
            <button className="btn btn-success w-100" onClick={handleAddClick}>
              Add
            </button>
          </div>
        </div>
      </div>

      {/* Product Table */}
      <table className="table table-bordered">
        <thead>
          <tr>
            <th>Product ID</th>
            <th>Name</th>
            <th>Description</th>
            <th>Price</th>
            <th>Min Qty</th>
            <th>Weight (g)</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {products.map((product) => (
            <tr key={product.productId}>
              <td>{product.productId}</td>
              <td>
                {editProductId === product.productId ? (
                  <input
                    type="text"
                    name="name"
                    value={editedProduct.name || ""}
                    onChange={handleChange}
                    className="form-control"
                  />
                ) : (
                  product.name
                )}
              </td>
              <td>
                {editProductId === product.productId ? (
                  <input
                    type="text"
                    name="description"
                    value={editedProduct.description || ""}
                    onChange={handleChange}
                    className="form-control"
                  />
                ) : (
                  product.description.length > 50
                    ? `${product.description.substring(0, 50)}...`
                    : product.description
                )}
              </td>
              <td>
                {editProductId === product.productId ? (
                  <input
                    type="number"
                    name="unitPrice"
                    value={editedProduct.unitPrice || ""}
                    onChange={handleChange}
                    className="form-control"
                  />
                ) : (
                  product.unitPrice
                )}
              </td>
              <td>
                {editProductId === product.productId ? (
                  <input
                    type="number"
                    name="minimumOrderQuantity"
                    value={editedProduct.minimumOrderQuantity || ""}
                    onChange={handleChange}
                    className="form-control"
                  />
                ) : (
                  product.minimumOrderQuantity
                )}
              </td>
              <td>
                {editProductId === product.productId ? (
                  <input
                    type="number"
                    name="weightGram"
                    value={editedProduct.weightGram || ""}
                    onChange={handleChange}
                    className="form-control"
                  />
                ) : (
                  product.weightGram
                )}
              </td>
              <td>
                {editProductId === product.productId ? (
                  <>
                    <button className="btn btn-success btn-sm me-2" onClick={handleSaveClick}>
                      Save
                    </button>
                    <button
                      className="btn btn-secondary btn-sm"
                      onClick={() => setEditProductId(null)}
                    >
                      Cancel
                    </button>
                  </>
                ) : (
                  <>
                    <button className="btn btn-primary btn-sm me-2" onClick={() => handleEditClick(product)}>
                      Edit
                    </button>
                    <button
                      className="btn btn-danger btn-sm"
                      onClick={() => handleDeleteClick(product.productId)}
                    >
                      Delete
                    </button>
                  </>
                )}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default Products;