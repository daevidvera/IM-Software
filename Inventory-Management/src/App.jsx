
import './App.css'
import { BrowserRouter, Routes, Route } from 'react-router-dom';

import WelcomePage from './components/WelcomePageComponent/WelcomePage';
import Login from './components/LoginComponent/Login';
import SignIn from './components/SignInComponent/SignIn';
import Dashboard from './pages/Dashboard';
import ProtectedRoute from './components/ProtectedComponents/ProtectedRouterComponent/ProtectedRoute';
import Verify from './components/VerifyComponent/Verify';
import ForgotPassword from './components/LoginComponent/PasswordComponents/ForgotPassword';
import ResetPassword from './components/LoginComponent/PasswordComponents/ResetPassword';
import Inventory from './pages/Inventory';



function App() {

  return (
    <>
    <BrowserRouter>
      <Routes>
        <Route path ='/' element={<WelcomePage/>}/>
        <Route path ='/login' element={<Login/>}/>
        <Route path ='/signin' element={<SignIn/>}/>
        <Route path ="/req/signup/verify" element={<Verify/>}/>
        <Route path="/login/forgot-password" element={<ForgotPassword />} />
        <Route path="/req/reset-password" element={<ResetPassword />} />
        
        {/* Protected Route */}
        <Route path ='/dashboard' element={
          <ProtectedRoute>
            <Dashboard/>
          </ProtectedRoute>
        }/>

        {/* Inventory */}
        <Route path ='/dashboard/inventory' element={
          <ProtectedRoute>
            <Inventory/>
          </ProtectedRoute>
        }/>

       
        </Routes>
      </BrowserRouter>
    </>
  )
}

export default App