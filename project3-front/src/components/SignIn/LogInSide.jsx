import {Box, Typography} from "@mui/material";
import {HorizontalRule} from "@mui/icons-material";
import "./CSS/LoginSide.css"
import EmailField from "./EmailField.jsx";
import {useState} from "react";

const LogInSide = () => {
  const [email, setEmail] = useState('');

  return (
    <Box className={"LogInSide"}>
      <Box className={"SignInContainer"} sx={{flexDirection: "column"}}>
        <Typography variant="h4" className={"SignInText"}>
          Sign In
        </Typography>
        <HorizontalRule className={"SignInLine"}/>
      </Box>

      <Typography variant="h6" className={"EmailText"}>
        Email address
      </Typography>

      <EmailField email={email} setEmail={setEmail}/>

      <Box className={"BreakContainer"}>
        <HorizontalRule className={"FirstLine"}/>

        <Typography variant="h6" className={"OrText"}>
          OR
        </Typography>

        <HorizontalRule className={"SecondLine"}/>
      </Box>


    </Box>
  )
}

export default LogInSide;