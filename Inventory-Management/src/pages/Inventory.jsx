import React, { useEffect, useState, useCallback } from 'react';
import { useNavigate } from 'react-router-dom';
import './Inventory.css';
import axios from 'axios';

const FIELD_CONFIGS = [
  { name: 'productName', label: 'Product Name', type: 'text' },
  { name: 'category', label: 'Category', type: 'text' },
  { name: 'priceBought', label: 'Price Bought', type: 'number' },
  { name: 'sellingPrice', label: 'Selling Price', type: 'number' },
  { name: 'quantity', label: 'Quantity', type: 'number' }
];

const InventoryFormFields = ({ values = {}, onChange, isEdit = false }) => (
  <>
    <div className="mb-3">
      <label className="form-label">{isEdit ? 'Current Image' : 'Image Upload'}</label>
      {isEdit && values.photoURL && (
        <img src={values.photoURL} alt="preview" className="img-thumbnail d-block mb-2" width="100" height="100" />
      )}
      <input
        type="file"
        accept="image/png, image/jpeg"
        onChange={onChange}
        className="form-control"
      />
    </div>
    {FIELD_CONFIGS.map((field, i) => (
      <div className="mb-3" key={i}>
        <label className="form-label">{field.label}</label>
        <input
          name={field.name}
          defaultValue={values[field.name]}
          type={field.type}
          className="form-control shadow-sm rounded"
          required
        />
      </div>
    ))}
  </>
);

const Inventory = () => {
  const navigate = useNavigate();
  const [editItem, setEditItem] = useState(null);
  const [editImageUrl, setEditImageUrl] = useState('');
  const [searchTerm, setSearchTerm] = useState('');
  const [inventoryItems, setInventoryItems] = useState([]);
  const [uploadedImageUrl, setUploadedImageUrl] = useState('');
  const token = localStorage.getItem('token');
  const headers = { Authorization: `Bearer ${token}` };

  useEffect(() => {
    axios
      .get(`http://localhost:8080/dashboard/inventory/user`, { headers })
      .then((res) => setInventoryItems(res.data))
      .catch((err) => console.error(err));
  }, []);

  const handleBack = useCallback(() => navigate('/dashboard'), [navigate]);

  const handleDelete = useCallback(async (id) => {
    try {
      await axios.delete(`http://localhost:8080/dashboard/inventory/${id}`, { headers });
      setInventoryItems((prev) => prev.filter((item) => item.productID !== id));
    } catch (err) {
      console.error('Failed to delete item:', err);
    }
  }, [headers]);

  const handleImageUpload = useCallback(async (e, isEdit = false) => {
    const formData = new FormData();
    formData.append("image", e.target.files[0]);
    try {
      const res = await axios.post("http://localhost:8080/dashboard/inventory/upload", formData, {
        headers: { ...headers, "Content-Type": "multipart/form-data" },
      });
      isEdit ? setEditImageUrl(res.data) : setUploadedImageUrl(res.data);
    } catch (err) {
      console.error("Image upload failed", err);
    }
  }, [headers]);

  const handleAddItem = useCallback((e) => {
    e.preventDefault();
    const formData = new FormData(e.target);
    const data = Object.fromEntries(formData.entries());
    data.photoURL = uploadedImageUrl;
    axios.post(`http://localhost:8080/dashboard/inventory`, data, { headers })
      .then((res) => {
        setInventoryItems(prev => [...prev, res.data]);
        e.target.reset();
        setUploadedImageUrl('');
        document.querySelector('#offcanvasCenter .btn-close').click();
      })
      .catch((err) => console.error(err));
  }, [headers, uploadedImageUrl]);

  const handleEditItem = useCallback((e) => {
    e.preventDefault();
    const formData = new FormData(e.target);
    const updatedData = Object.fromEntries(formData.entries());
    updatedData.photoURL = editImageUrl;
    axios.put(`http://localhost:8080/dashboard/inventory/${editItem.productID}`, updatedData, { headers })
      .then((res) => {
        setInventoryItems(prev => prev.map(item => item.productID === res.data.productID ? res.data : item));
        document.querySelector('#editOffcanvas .btn-close').click();
      })
      .catch((err) => console.error(err));
  }, [headers, editImageUrl, editItem]);

  const filteredItems = inventoryItems.filter((item) => {
    const name = item?.productName?.toLowerCase?.() || '';
    const category = item?.category?.toLowerCase?.() || '';
    return name.includes(searchTerm.toLowerCase()) || category.includes(searchTerm.toLowerCase());
  });

  return (
    <>
      {/* go back */}
      <div className="position-absolute top-0 start-0 m-3">
        <i className="bi bi-arrow-left icon-black fs-4" onClick={handleBack} role="button"></i>
      </div>

      {/* title */}
      <div className="container py-5">
        <h1 className="text-center mb-4 fw-bold">My Inventory</h1>

        {/* search bar */}
        <div className="input-group flex-nowrap mb-4 shadow-sm rounded">
          <span className="input-group-text bg-white border-end-0">
            <i className="bi bi-search"></i>
          </span>
          <input
            type="text"
            className="form-control border-start-0"
            placeholder="Search by name or category"
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
          />
        </div>
        {/* add item button */}
        <div className="text-end mb-4">
          <button
            type="button"
            className="btn-color dark w-100 mb-3"
            data-bs-toggle="offcanvas"
            data-bs-target="#offcanvasCenter"
            aria-controls="offcanvasCenter"
          >
            Add Item
          </button>
        </div>

        {/* offcanvas for adding new item */}
        <div className="offcanvas offcanvas-top rounded shadow-lg border-0"
          tabIndex="-1"
          id="offcanvasCenter"
          aria-labelledby="offcanvasCenterLabel"
          style={{ top: '5%', height: 'auto', maxWidth: '600px', margin: '0 auto' }}>
          <div className="offcanvas-header">
            <h5 className="offcanvas-title" id="offcanvasCenterLabel">Add New Inventory Item</h5>
            <button type="button" className="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
          </div>
          <div className="offcanvas-body">
            <form onSubmit={handleAddItem}>
              <InventoryFormFields onChange={handleImageUpload} />
              <button type="submit" className="btn btn-success w-100 shadow-sm rounded">Add Item</button>
            </form>
          </div>
        </div>

        {/* inventory table */}
        <div className="table-responsive mt-5">
          <table className="table table-bordered table-hover align-middle shadow-sm rounded text-center">
            <thead className="table-light">
              <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Photo</th>
                <th>Category</th>
                <th>Price Bought</th>
                <th>Selling Price</th>
                <th>Quantity</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {filteredItems.map((item) => (
                <tr key={item.productID}>
                  <th>{item.productID}</th>
                  <td>{item.productName}</td>
                  <td><img src={item.photoURL} alt="item" className="img-thumbnail rounded" width="100" height="100" /></td>
                  <td>{item.category}</td>
                  <td>${item.priceBought.toFixed(2)}</td>
                  <td>${item.sellingPrice.toFixed(2)}</td>
                  <td>{item.quantity}</td>
                  <td>
                    <div className="d-flex gap-2">
                      <button
                        className="btn btn-sm btn-edit shadow-sm"
                        data-bs-toggle="offcanvas"
                        data-bs-target="#editOffcanvas"
                        onClick={() => {
                          setEditItem(item);
                          setEditImageUrl(item.photoURL);
                        }}
                      >
                        <i className="bi bi-pencil me-1"></i>Edit
                      </button>
                      <button className="btn btn-sm btn-delete shadow-sm" onClick={() => handleDelete(item.productID)}>
                        <i className="bi bi-trash me-1"></i>Delete
                      </button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>

        {/* offcanvas for editing item */}
        <div className="offcanvas offcanvas-end" tabIndex="-1" id="editOffcanvas" aria-labelledby="editOffcanvasLabel">
          <div className="offcanvas-header">
            <h5 className="offcanvas-title" id="editOffcanvasLabel">Edit Inventory Item</h5>
            <button type="button" className="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
          </div>
          <div className="offcanvas-body">
            {editItem && (
              <form onSubmit={handleEditItem}>
                <InventoryFormFields
                  values={{ ...editItem, photoURL: editImageUrl }}
                  onChange={(e) => handleImageUpload(e, true)}
                  isEdit
                />
                <button type="submit" className="btn btn-success w-100 shadow-sm rounded">Save Changes</button>
              </form>
            )}
          </div>
        </div>
      </div>
    </>
  );
};

export default Inventory;