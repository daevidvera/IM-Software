import React from 'react'
import './SignIn.css'
import { useNavigate } from 'react-router-dom'

const SignIn = () => {
  const navigate = useNavigate()
  const handleBack = () => {
    navigate("/")
  }

  const handleSignIn = () => {
    navigate("/verify")
  }

  return (
      <>
        <div className="position-absolute top-5 start-7 m-3">
          <i className="bi bi-arrow-left icon-black" onClick={handleBack} ></i>
        </div>
  
  
      <div className="d-flex align-items-center justify-content-center" style={{ height: '100vh' }}>
       <div className="container text-center">
          <div className="row gap-3 justify-content-center">
            <h2 className="title col-12">Inventory.</h2>
            <div className="col-12 col-md-6">
              <form>
              <div className="mb-3">
                  <input type="username" className="form-control border-color black" id="exampleInputUsername" placeholder='Username' />
                </div>
                <div className="mb-3">
                  <input type="email" className="form-control border-color black" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder='Email' />
                </div>
                <div className="mb-3">
                  <input type="password" className="form-control border-color" id="exampleInputPassword1" placeholder='Password' />
                </div>
                <div className="mb-3">
                  <input type="password" className="form-control border-color" id="exampleInputPassword2" placeholder=' Re-Type Password' />
                </div>
                <button type="submit" className="btn-color dark w-100 mb-3" onClick={handleSignIn}>Sign In</button>
              </form>      
            </div>
          </div>
        </div>
     </div>
     </>
  )
}

export default SignIn