
import './App.css'
import { BrowserRouter, Routes, Route } from 'react-router-dom';

import WelcomePage from './components/WelcomePageComponent/WelcomePage';
import Login from './components/LoginComponent/Login';
import SignIn from './components/SignInComponent/SignIn';
import Verification from './components/VerificationComponent/Verification';
import Dashboard from './components/DashboardComponent/Dashboard';


function App() {

  return (
    <>
    <BrowserRouter>
      <Routes>
        <Route path ='/' element={<WelcomePage/>}/>
        <Route path ='/login' element={<Login/>}/>
        <Route path ='/signin' element={<SignIn/>}/>
        <Route path ='/verify' element={<Verification/>}/>
        <Route path ='/dashboard' element={<Dashboard/>}/>
        </Routes>
      </BrowserRouter>
    </>
  )
}

export default App