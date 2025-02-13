import {Box, Button, Typography} from "@mui/material";
import {HorizontalRule} from "@mui/icons-material";
import "./CSS/LoginSide.css"
import EmailField from "./EmailField.jsx";
import {useState} from "react";
import {RequirementsEmail} from "../../util/RequirementsAccount.js";
import AuthWarning from "../AuthCommon/AuthWarning.jsx";

const LogInSide = () => {
  const [email, setEmail] = useState('');
  const [failToAuth, setFailToAuth] = useState(false);

  const checkEmail = (email) => {
    const checkEmail = RequirementsEmail(email);
    if (checkEmail) {
      setFailToAuth(false);
      setEmail(email);
    } else {
      setFailToAuth(true);
    }
  }

  return (
    <Box className={"MainLoginContainer"}>
      <Box className={"PaddingContainer"}>
        <Box className={"ContentContainer"}>
          <Box className={"SignInContainer"} sx={{flexDirection: "column"}}>
            <Typography variant="h4" className={"SignInText"}>
              Sign In
            </Typography>
            <HorizontalRule className={"SignInLine"}/>
          </Box>

          {failToAuth ? <AuthWarning warningType={'EMAIL'}/> : null}

          <Typography variant="h6" className={"EmailText"}>
            Email address
          </Typography>

          <EmailField email={email} setEmail={setEmail}/>

          <Button
            variant="contained"
            color="primary"
            className={'SignInButton'}
            onClick={() => checkEmail()}
            disableRipple={true}
          >
            Sign In
          </Button>

          <Box className={"BreakContainer"}>
            <Box className={"LineContainer"}>
              <HorizontalRule className={"Line"}/>
            </Box>

            <Typography variant="h6" className={"OrText"}>
              OR
            </Typography>

            <Box className={"LineContainer"}>
              <HorizontalRule className={"Line"}/>
            </Box>
          </Box>

          <Button variant="outlined" className={"ButtonSignUp"} disableRipple={true}>Create an Account</Button>

          <Typography variant="body1" className={"AcknowledgementText"}>
            By signing in or creating a member account, you agree to the Terms of Use and acknowledge the Privacy Policy.
          </Typography>
        </Box>
      </Box>
    </Box>
  )
}

export default LogInSide;