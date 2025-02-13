import {BrowserRouter, Route, Routes} from "react-router";
import HomePage from "./Pages/HomePage.jsx";
import SignInPage from "./Pages/SignInPage.jsx";

const AppRoutes = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<HomePage/>}/>
        <Route path="/men" element={<HomePage/>}/>
        <Route path="/women" element={<HomePage/>}/>
        <Route path="/accessories" element={<HomePage/>}/>
        <Route path={"/signin"} element={<SignInPage/>}/>
      </Routes>
    </BrowserRouter>
  )
}

export default AppRoutes;