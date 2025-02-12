import {Box} from "@mui/material";
import ImageCarousel from "../components/SignIn/ImageCarousel.jsx";
import LogInSide from "../components/SignIn/LogInSide.jsx";
import "./SignInPage.css"

const SignInPage = () => {
  return (
    <Box className={"SignInContainer"}>
      <ImageCarousel/>
      <LogInSide/>
    </Box>
  )
}

export default SignInPage;