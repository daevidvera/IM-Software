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

  const handleSubmit =  async (e) => {
    e.preventDefault();

    const email = e.target.email.value;
    const password = e.target.password.value;

    // try and catch block
     try {
    const response = await axios.post('http://localhost:8080/api/auth/login', {
      email,
      password
    });

    console.log('Login successful', response.data);
    // Redirect to the dashboard
    navigate('/dashboard');


    } catch(errors) {
    console.error('Error logging in', errors);
    setErrors("Can't log in, please check your email and password");
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
                <input type="email"  name="email" className="form-control border-color black" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder='Email' />
              </div>
              <div className="mb-3">
                <input type="password"  name="password" className="form-control border-color" id="exampleInputPassword1" placeholder='Password' />
              </div>
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