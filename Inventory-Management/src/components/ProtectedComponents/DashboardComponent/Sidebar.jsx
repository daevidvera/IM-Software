import React from 'react';
import './Sidebar.css';
import UserImage from '../../../assets/user.png';
import { useNavigate } from 'react-router-dom';

const Sidebar = ({ isOpen, toggleSidebar }) => {
  const navigate = useNavigate();
  const username = localStorage.getItem('username');

  const handleLogout = () => {
    localStorage.removeItem('isAuthenticated');
    navigate('/login');
  };

  const handleInventory = () => { 
    navigate("/dashboard/inventory");
  }

    const handleEmployee = () => { 
    navigate("/dashboard/employees");
  }

  return (
    <div className={`d-flex flex-column flex-shrink-0 p-3 sidebar-container ${isOpen ? 'show' : ''}`}>
      <h2 className="title text-center mb-4">Inventory.</h2>

      <div className="text-center mb-3 user-profile">
        <img
          src={UserImage}
          alt="User"
          className="rounded-circle user-image"
          width="60"
          height="60"
        />
        <div className="mt-2 fw-bold">{username}</div>
      </div>

      <hr />

      <ul className="nav nav-pills flex-column mb-auto">
        <li className="nav-item">
          <a href="#" className="nav-link link-dark">
            <i className="bi bi-house-door me-2"></i>
            Dashboard
          </a>
        </li>

        {/*  inventory  */}
        <li>
          <a href="#" className="nav-link link-dark" onClick={handleInventory}>
            <i className="bi bi-box-seam me-2"></i>
            Inventory
          </a>
        </li>

        {/* employees */}
        <li>
          <a href="#" className="nav-link link-dark" onClick={handleEmployee}>
            <i className="bi bi-file-person me-2"></i>
            Employees
          </a>
        </li>

        
        <li>
          <a href="#" className="nav-link link-dark" onClick={handleLogout}>
            <i className="bi bi-box-arrow-right me-2"></i>
            Logout
          </a>
        </li>
      </ul>
    </div>
  );
};

export default Sidebar;
