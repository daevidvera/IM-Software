import React from 'react';
import './Verification.css';
import { useNavigate } from 'react-router-dom';




const Verification = () => {
    const navigate = useNavigate();
    const handleBack = () => {
        navigate("/signin");
    };
  return (
    <>
    <div className="position-absolute top-5 start-7 m-3">
    <i className="bi bi-arrow-left icon-black" onClick={handleBack} ></i>
  </div>
    <div className="d-flex align-items-center justify-content-center" style={{ height: '100vh' }}>
      <div className="card p-4" style={{ width: '100%', maxWidth: '400px' }}>
        <div className="card-body">
          <h5 className="card-title text-center title">Verification</h5>
          <form>
            <div className="mb-3">
              <input
                type="text"
                className="form-control border-color black"
                id="verificationCode"
                placeholder="Verification Code"
              />
            </div>
            <button type="submit" className="btn-color dark w-100 mb-3">Verify</button>
          </form>
        </div>
      </div>
    </div>
    </>
  );
};

export default Verification;