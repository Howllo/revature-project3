import './App.css'
import {BrowserRouter, Route, Routes} from "react-router";
import HomePage from "./Pages/HomePage.jsx";
import {AuthProvider} from "./util/auth/AuthProvider.jsx";
import SignInPage from "./Pages/SignInPage.jsx";

function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<HomePage/>}/>
          <Route path="/men" element={<HomePage/>}/>
          <Route path="/women" element={<HomePage/>}/>
          <Route path="/accessories" element={<HomePage/>}/>
          <Route path={"/signin"} element={<SignInPage/>}/>
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  )
}

export default App
