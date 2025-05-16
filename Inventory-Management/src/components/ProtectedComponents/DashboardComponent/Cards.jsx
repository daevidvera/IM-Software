import React from 'react';
import './Cards.css';

const Cards = () => {
  return (
    <div className="cards-wrapper">
      <div className="cards-container">
        <div className="custom-card d-flex flex-row align-items-center p-3">
          <div className="icon-wrapper me-3">
            <i className="bi bi-box-seam"></i>
          </div>
          <div className="card-body p-0">
            <h5 className="card-title mb-0">Inventory</h5>
          </div>
        </div>

        <div className="custom-card d-flex flex-row align-items-center p-3">
          <div className="icon-wrapper me-3">
            <i className="bi bi-cart4"></i>
          </div>
          <div className="card-body p-0">
            <h5 className="card-title mb-0">Cart</h5>
          </div>
        </div>

         <div className="custom-card d-flex flex-row align-items-center p-3">
          <div className="icon-wrapper me-3">
            <i class="bi bi-file-person"></i>
          </div>
          <div className="card-body p-0">
            <h5 className="card-title mb-0">Employees</h5>
          </div>
        </div>


        <div className="custom-card d-flex flex-row align-items-center p-3">
          <div className="icon-wrapper me-3">
            <i class="bi bi-box-seam-fill"></i>
          </div>
          <div className="card-body p-0">
            <h5 className="card-title mb-0">Orders</h5>
          </div>
        </div>

        <div className="custom-card d-flex flex-row align-items-center p-3">
          <div className="icon-wrapper me-3">
            <i class="bi bi-currency-dollar"></i>
          </div>
          <div className="card-body p-0">
            <h5 className="card-title mb-0">Profit</h5>
          </div>
        </div>

        
        
         

      </div>
    </div>
  );
};

export default Cards;
