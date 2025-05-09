import React from 'react';
import { useNavigate } from 'react-router-dom';

const Dashboard = () => {
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem('isAuthenticated'); // remove auth flag
    navigate('/login'); // redirect to login
  };

  return (
    <div className="container mt-5 text-center">
      <h2>Welcome to the Dashboard</h2>
      <button className="btn btn-danger mt-4" onClick={handleLogout}>
        Log Out
      </button>
    </div>
  );
};

export default Dashboard;
