import React from 'react';
import './WelcomePage.css';
import {useNavigate} from 'react-router-dom';


const WelcomePage = () => {

  const navigate = useNavigate();

  // Function to handle login button click
  const handleLogin = () => {
    navigate('/login');
  };

  // Function to handle sign in button click
  const handleSignIn = () => {
    navigate('/signin');
  }

  return (
    <div className="d-flex align-items-center justify-content-center" style={{ height: '100vh' }}>
      <div className="container text-center">
        <div className="row gap-3 justify-content-center">
          <h2 className="title col-12">Inventory.</h2>
          <div className="col-12 col-md-6">
            <button type="button" className="btn-color dark  w-100 mb-3" onClick={handleLogin}>
              Log in
            </button>
            <button type="button" className="btn-color dark w-100" onClick={handleSignIn}>
              Sign in
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default WelcomePage;