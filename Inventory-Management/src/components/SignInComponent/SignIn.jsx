import React, {useState} from 'react'
import './SignIn.css'
import { useNavigate } from 'react-router-dom'
import axios from 'axios'

const SignIn = () => {
  const navigate = useNavigate()
  const handleBack = () => {
    navigate("/")
  }

  const [errors, setErrors] = useState('');
 
  const [fieldErrors, setFieldErrors] = useState({
  username: false,
  email: false,
  password: false,
  rePassword: false
  });

  const [isSubmitting, setIsSubmitting] = useState(false);

  const [showPassword, setShowPassword] = useState(false);
 

  const handleSubmit = async (e) => {
    e.preventDefault(); // stops the page from refreshing

     if (isSubmitting) return;

    const username = e.target.username.value;
    const email = e.target.email.value;
    const password = e.target.password.value;
    const rePassword = e.target.rePassword.value;


    setFieldErrors({
      username: false,
      email: false,
      password: false,
      rePassword: false
    });

    // checking if password and rePassword are the same
    if (password !== rePassword) {
      setErrors("Passwords do not match");
      setFieldErrors(prev => ({ ...prev, password: true }));
      return;
    }

    //checking if username is valid
    if (username.length <= 0) {
      setErrors("Username is required");
      setFieldErrors(prev => ({ ...prev, username: true }));
      return;
    }

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    //checking if email is empty
    if (email.length <= 0) {
      setErrors("Email is required");
      setFieldErrors(prev => ({ ...prev, email: true }));
      return;
    }

    // checking if email is valid
    if (!emailRegex.test(email)) {
      setErrors("Please enter a valid email address");
      setFieldErrors(prev => ({ ...prev, email: true }));
      return;
    }

    const passwordRegex = /^(?=.*[A-Z]).{5,}$/;

    if (!passwordRegex.test(password)) {
      setErrors("Password must be at least 5 characters and contain at least one uppercase letter.");
      setFieldErrors(prev => ({ ...prev, password: true }));
      return;
    }

     setIsSubmitting(true);

    try {
      const response = await axios.post('http://localhost:8080/req/signup', {
        username,
        email,
        password
      });
      console.log('Sign up successful', response.data);
      alert("Registration successful! Please check your email to verify your account before logging in.");
      navigate('/login');

    }  catch (error) {
    console.error('Error signing up', error);
      if (error.response && error.response.data && error.response.data.error) {
        setErrors(error.response.data.error); // Use backend message
      } else {
        setErrors("Could not register, please try again later.");
      }
    }


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
              <form onSubmit={handleSubmit}>
              <div className="mb-3">
                  <input 
                  type="text" 
                  name='username' 
                  className={`form-control border-color black ${fieldErrors.username ? 'is-invalid' : ''}`}
                  id="exampleInputUsername"
                   placeholder='Username' />
                </div>
                <div className="mb-3">
                  <input 
                  type="email" 
                   name= 'email'
                  className={`form-control border-color black ${fieldErrors.email ? 'is-invalid' : ''}`}
                  id="exampleInputEmail1" 
                  aria-describedby="emailHelp" 
                  placeholder='Email' />
                </div>
                <div className="mb-3 position-relative">
                    <input 
                      type={showPassword ? 'text' : 'password'} 
                      name="password"
                      className={`form-control border-color black ${fieldErrors.password ? 'is-invalid' : ''}`}
                      id="exampleInputPassword1" 
                      placeholder="Password"
                    />
                    <i
                      className={`bi ${showPassword ? 'bi-eye-slash' : 'bi-eye'} position-absolute top-50 end-0 translate-middle-y me-3`}
                      style={{ cursor: 'pointer', fontSize: '1.2rem', zIndex: 2 }}
                      onClick={() => setShowPassword(prev => !prev)}
                    ></i>
                </div>
                <div className="mb-3">
                  <input 
                  type="password" 
                  name='rePassword' 
                  className={`form-control border-color black ${fieldErrors.email ? 'is-invalid' : ''}`}
                  id="exampleInputPassword2" 
                  placeholder=' Re-Type Password' />
                </div>
                {errors && <p className="text-danger mb-2">{errors}</p>}
                <button 
                type="submit" 
                className="btn-color dark w-100 mb-3"  
                disabled={isSubmitting}>
                 {isSubmitting ? 'Submitting...' : 'Sign In'}
                </button>
                
              </form>      
            </div>
          </div>
        </div>
     </div>
     </>
  )
}

export default SignIn