import React, { useEffect, useState } from "react";

const Inventory = () => {
  const [inventory, setInventory] = useState([]);
  const [error, setError] = useState("");
  const [editProductId, setEditProductId] = useState(null); // Track the product being edited
  const [editedProduct, setEditedProduct] = useState({}); // Track changes

  useEffect(() => {
    const fetchInventory = async () => {
      try {
        const response = await fetch("http://localhost:8080/api/products"); // Fetch products
        if (!response.ok) {
          throw new Error("Failed to fetch inventory.");
        }
        const data = await response.json();
        setInventory(data);
      } catch (err) {
        setError(err.message);
      }
    };

    fetchInventory();
  }, []);

  const handleEditClick = (product) => {
    setEditProductId(product.productId); // Enable editing for this product
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

      // Update local state
      setInventory((prev) =>
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

  const handleCancelClick = () => {
    setEditProductId(null);
    setEditedProduct({});
  };

  return (
    <div>
      <h1>Inventory</h1>
      {error && <p className="text-danger">{error}</p>}
      <table className="table table-bordered">
        <thead>
          <tr>
            <th>Product ID</th>
            <th>Name</th>
            <th>Available Quantity</th>
            <th>Supplier</th>
            <th>Lead Time (Days)</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {inventory.map((item) => (
            <tr key={item.productId}>
              <td>{item.productId}</td>
              <td>
                {editProductId === item.productId ? (
                  <input
                    type="text"
                    name="name"
                    value={editedProduct.name || ""}
                    onChange={handleChange}
                    className="form-control"
                  />
                ) : (
                  item.name
                )}
              </td>
              <td>
                {editProductId === item.productId ? (
                  <input
                    type="number"
                    name="availableQuantity"
                    value={editedProduct.availableQuantity || ""}
                    onChange={handleChange}
                    className="form-control"
                  />
                ) : (
                  item.availableQuantity
                )}
              </td>
              <td>
                {editProductId === item.productId ? (
                  <input
                    type="text"
                    name="supplier"
                    value={editedProduct.supplier || ""}
                    onChange={handleChange}
                    className="form-control"
                  />
                ) : (
                  item.supplier
                )}
              </td>
              <td>
                {editProductId === item.productId ? (
                  <input
                    type="number"
                    name="leadTimeDays"
                    value={editedProduct.leadTimeDays || ""}
                    onChange={handleChange}
                    className="form-control"
                  />
                ) : (
                  item.leadTimeDays
                )}
              </td>
              <td>
                {editProductId === item.productId ? (
                  <>
                    <button
                      className="btn btn-success btn-sm me-2"
                      onClick={handleSaveClick}
                    >
                      Save
                    </button>
                    <button
                      className="btn btn-secondary btn-sm"
                      onClick={handleCancelClick}
                    >
                      Cancel
                    </button>
                  </>
                ) : (
                  <button
                    className="btn btn-primary btn-sm"
                    onClick={() => handleEditClick(item)}
                  >
                    Edit
                  </button>
                )}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default Inventory;
