
import './App.css'
import { BrowserRouter, Routes, Route } from 'react-router-dom';

import WelcomePage from './components/WelcomePageComponent/WelcomePage';
import Login from './components/LoginComponent/Login';
import SignIn from './components/SignInComponent/SignIn';

function App() {

  return (
    <>
    <BrowserRouter>
      <Routes>
        <Route path ='/' element={<WelcomePage/>}/>
        <Route path ='/login' element={<Login/>}/>
        <Route path ='/signin' element={<SignIn/>}/>
        </Routes>
      </BrowserRouter>
    </>
  )
}

export default App