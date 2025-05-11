import React, {useState} from 'react'
import './Login.css'
import {useNavigate} from 'react-router-dom';
import axios from 'axios';


const Login = () => {
  const navigate = useNavigate();
  const handleBack = () => {
    navigate('/');
  }

  const[errors, setErrors] = useState('');
  const [showPassword, setShowPassword] = useState(false);

  const handleSubmit =  async (e) => {
    e.preventDefault();

    const username = e.target.username.value;
    const password = e.target.password.value;

    // try and catch block
     try {
    const response = await axios.post('http://localhost:8080/api/auth/login', {
      username,
      password
    });

    

    if (response.status === 200) {
      localStorage.setItem('isAuthenticated', 'true');
      console.log('Login successful', response.data);
      navigate('/dashboard');
    }
    
    } catch(errors) {
      if (errors.response && errors.response.data && errors.response.data.error) {
    setErrors(errors.response.data.error);  // error message from the server
    } else {
      setErrors("Can't log in, please try again later.");
    }
  }
  } 

  return (
    
    <>
      <div className="position-absolute top-5 start-7 m-3">
        <i className="bi bi-arrow-left icon-black" onClick={handleBack}></i>
      </div>


    <div className="d-flex align-items-center justify-content-center" style={{ height: '100vh' }}>
     <div className="container text-center">
        <div className="row gap-3 justify-content-center">
          <h2 className="title col-12">Inventory.</h2>
          <div className="col-12 col-md-6">
            <form onSubmit={handleSubmit}>
              <div className="mb-3">
                <input type="username"  name="username" className="form-control border-color black"   placeholder='Username' />
              </div>

              <div className="mb-3 position-relative">
                <input 
                  type={showPassword ? 'text' : 'password'} 
                  name="password"
                  className="form-control border-color black"
                  id="exampleInputPassword1" 
                  placeholder="Password"
                />
                <i
                  className={`bi ${showPassword ? 'bi-eye-slash' : 'bi-eye'} position-absolute top-50 end-0 translate-middle-y me-3`}
                  style={{ cursor: 'pointer', fontSize: '1.2rem', zIndex: 2 }}
                  onClick={() => setShowPassword(prev => !prev)}
                ></i>
              </div>
              <p className="mt-2">
                <span 
                  style={{ cursor: 'pointer', color: 'blue', textDecoration: 'underline' }} 
                  onClick={() => navigate('/login/forgot-password')}
                >
                  Forgot Password?
                </span>
              </p>
              <button type="submit" className="btn-color dark w-100 mb-3">Log in</button>
                {errors && <p className="text-danger mt-2">{errors}</p>}
            </form>
            
          </div>
        </div>
      </div>
   </div>
   </>
  )
}

export default Login