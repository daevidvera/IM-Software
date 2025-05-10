import React, { useEffect, useState } from 'react';
import { useSearchParams, useNavigate } from 'react-router-dom';
import axios from 'axios';

const Verify = () => {
  const [searchParams] = useSearchParams();
  const token = searchParams.get('token');
  const [message, setMessage] = useState("Verifying...");
  const navigate = useNavigate();

  useEffect(() => {
    if (token) {
      axios.get(`http://localhost:8080/req/signup/verify`, {
        params: { token }
      })
     .then(res => {
        if (res.status >= 200 && res.status < 300) {
            setMessage("Email successfully verified! Redirecting to login...");
            setTimeout(() => navigate('/login'), 3000);
        }
     })
      .catch(err => {
        console.error(err);
        setMessage("Verification failed or token expired.");
      });
    } else {
      setMessage("Invalid or missing token.");
    }
  }, [token, navigate]);

  return (
    <div className="d-flex justify-content-center align-items-center" style={{ height: '100vh' }}>
      <h3>{message}</h3>
    </div>
  );
};

export default Verify;
