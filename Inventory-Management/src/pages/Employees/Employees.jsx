import React, { useEffect, useState, useCallback } from 'react';
import './Employees.css';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const FIELD_CONFIGS = [
  { name: 'name', label: 'Name', type: 'text' },
  { name: 'email', label: 'Email', type: 'text' },
  { name: 'salary', label: 'Salary', type: 'number' },
];

const EmployeesFormFields = ({ values = {}, onChange, isEdit = false }) => (
  <>
    <div className="mb-3">
      <label className="form-label">{isEdit ? 'Current Image' : 'Image Upload'}</label>
      {isEdit && (
        <img
          src={values.photoURL || '/default-avatar.png'}
          alt="preview"
          className="img-thumbnail d-block mb-2"
          width="100"
          height="100"
        />
      )}
      <input
        name="image"
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

const Employees = () => {
  const navigate = useNavigate();
  const [editItem, setEditItem] = useState(null);
  const [editImageUrl, setEditImageUrl] = useState('');
  const [searchTerm, setSearchTerm] = useState('');
  const [employees, setEmployees] = useState([]);
  const [uploadedImageUrl, setUploadedImageUrl] = useState('');
  const token = localStorage.getItem('token');
  const headers = { Authorization: `Bearer ${token}` };

  useEffect(() => {
    axios
      .get(`http://localhost:8080/dashboard/employees/user`, { headers })
      .then((res) => setEmployees(res.data))
      .catch((err) => console.error(err));
  }, []);

  const handleBack = useCallback(() => navigate('/dashboard'), [navigate]);

  const handleDelete = useCallback(async (id) => {
    try {
      await axios.delete(`http://localhost:8080/dashboard/employees/${id}`, { headers });
      setEmployees((prev) => prev.filter((item) => item.employeeID !== id));
    } catch (err) {
      console.error('Failed to delete item:', err);
    }
  }, [headers]);

  const handleImageUpload = useCallback(async (e, isEdit = false) => {
    const formData = new FormData();
    formData.append('image', e.target.files[0]);
    try {
      const response = await axios.post('http://localhost:8080/dashboard/employees/upload', formData, {
        headers: { ...headers, 'Content-Type': 'multipart/form-data' },
      });
      isEdit ? setEditImageUrl(response.data) : setUploadedImageUrl(response.data);
    } catch (error) {
      console.error('Image upload failed:', error);
    }
  }, [headers]);

  const handleAddItem = useCallback((e) => {
    e.preventDefault();
    const data = {
      name: e.target.name.value,
      email: e.target.email.value,
      salary: e.target.salary.value,
    };
    if (uploadedImageUrl?.trim()) {
      data.photoURL = uploadedImageUrl;
    }
    axios.post(`http://localhost:8080/dashboard/employees`, JSON.stringify(data), {
      headers: { ...headers, 'Content-Type': 'application/json' },
    })
      .then((res) => {
        setEmployees((prev) => [...prev, res.data]);
        e.target.reset();
        setUploadedImageUrl('');
        document.querySelector('#offCanvasCenter .btn-close').click();
      })
      .catch((err) => console.error(err));
  }, [headers, uploadedImageUrl]);

  const handleEditItem = useCallback((e) => {
    e.preventDefault();
    const data = {
      name: e.target.name.value,
      email: e.target.email.value,
      salary: e.target.salary.value,
    };
    if (editImageUrl?.trim()) {
      data.photoURL = editImageUrl;
    }
    axios.put(`http://localhost:8080/dashboard/employees/${editItem.employeeID}`, JSON.stringify(data), {
      headers: { ...headers, 'Content-Type': 'application/json' },
    })
      .then((res) => {
        setEmployees((prev) => prev.map((item) => (item.employeeID === editItem.employeeID ? res.data : item)));
        e.target.reset();
        setEditImageUrl('');
        document.querySelector('#editOffcanvas .btn-close').click();
      })
      .catch((err) => console.error(err));
  }, [headers, editImageUrl, editItem]);

  const filteredEmployees = employees.filter((employee) => {
    const name = employee?.name?.toLowerCase?.() || '';
    const email = employee?.email?.toLowerCase?.() || '';
    const salary = employee?.salary?.toString?.() || '';
    const search = searchTerm.toLowerCase();
    return name.includes(search) || email.includes(search) || salary.includes(search);
  });

  return (
    <>
      <div className="position-absolute top-0 start-0 m-3">
        <i className="bi bi-arrow-left icon-black fs-4" onClick={handleBack} role="button"></i>
      </div>

      <div className="container py-5">
        <h1 className="text-center mb-4 fw-bold">My Employees</h1>

        <div className="input-group flex-nowrap mb-4 shadow-sm rounded">
          <span className="input-group-text bg-white border-end-0">
            <i className="bi bi-search"></i>
          </span>
          <input
            type="text"
            className="form-control border-start-0"
            placeholder="Search by name, email, or salary"
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
          />
        </div>

        <div className="text-end mb-4">
          <button
            type="button"
            className="btn-color dark w-100 mb-3"
            data-bs-toggle="offcanvas"
            data-bs-target="#offCanvasCenter"
            aria-controls="offCanvasCenter"
          >
            Add Employee
          </button>
        </div>

        <div className="offcanvas offcanvas-top rounded shadow-lg border-0"
          tabIndex="-1"
          id="offCanvasCenter"
          aria-labelledby="offCanvasCenterLabel"
          style={{ top: '5%', height: 'auto', maxWidth: '600px', margin: '0 auto' }}>
          <div className="offcanvas-header">
            <h5 className="offcanvas-title" id="offCanvasCenterLabel">Add New Employee</h5>
            <button type="button" className="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
          </div>
          <div className="offcanvas-body">
            <form onSubmit={handleAddItem}>
              <EmployeesFormFields onChange={handleImageUpload} />
              <button type="submit" className="btn btn-success w-100 shadow-sm rounded">Add Employee</button>
            </form>
          </div>
        </div>

        <div className="table-responsive mt-5">
          <table className="table table-bordered table-hover align-middle shadow-sm rounded text-center">
            <thead className="table-light">
              <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Photo</th>
                <th>Email</th>
                <th>Salary</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {filteredEmployees.map((employee) => (
                <tr key={employee.employeeID}>
                  <th>{employee.employeeID}</th>
                  <td>{employee.name}</td>
                  <td>
                    <img
                      src={employee.photoURL || '/default-avatar.png'}
                      alt="employee"
                      className="img-thumbnail rounded"
                      width="100"
                      height="100"
                    />
                  </td>
                  <td>{employee.email}</td>
                  <td>${Number(employee.salary).toLocaleString()}</td>
                  <td>
                    <div className="d-flex gap-2">
                      <button
                        className="btn btn-sm btn-edit shadow-sm"
                        data-bs-toggle="offcanvas"
                        data-bs-target="#editOffcanvas"
                        onClick={() => {
                          setEditItem(employee);
                          setEditImageUrl(employee.photoURL);
                        }}
                      >
                        <i className="bi bi-pencil me-1"></i>Edit
                      </button>
                      <button className="btn btn-sm btn-delete shadow-sm" onClick={() => handleDelete(employee.employeeID)}>
                        <i className="bi bi-trash me-1"></i>Delete
                      </button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>

        <div className="offcanvas offcanvas-end" tabIndex="-1" id="editOffcanvas" aria-labelledby="editOffcanvasLabel">
          <div className="offcanvas-header">
            <h5 className="offcanvas-title" id="editOffcanvasLabel">Edit Employee</h5>
            <button type="button" className="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
          </div>
          <div className="offcanvas-body">
            {editItem && (
              <form onSubmit={handleEditItem}>
                <EmployeesFormFields
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

export default Employees;
