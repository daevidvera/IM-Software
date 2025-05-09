import React from 'react';
import { Navigate } from 'react-router-dom';

/**
 * Protects routes by checking if the user is authenticated.
 * If not, it redirects to the login page.
 */
const ProtectedRoute = ({ children }) => {
  
  const isAuth = localStorage.getItem("isAuthenticated");
  return isAuth ? children : <Navigate to="/login" replace />;
};

export default ProtectedRoute;
// This component checks if the user is authenticated by looking for an "auth" item in local storage.