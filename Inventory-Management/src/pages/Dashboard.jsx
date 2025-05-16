import React from 'react';
import Sidebar from '../components/ProtectedComponents/DashboardComponent/Sidebar';
import Cards from '../components/ProtectedComponents/DashboardComponent/Cards';
import './Dashboard.css'; 

const Dashboard = () => {
  return (
    <div className="dashboard-layout">
      <Sidebar />
      <main className="main-content">
        <Cards />
      </main>
    </div>
  );
};

export default Dashboard;
