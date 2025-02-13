import {Box, Button, Checkbox, Typography} from "@mui/material";
import {HorizontalRule} from "@mui/icons-material";
import "./CSS/LoginSide.css"
import EmailField from "./EmailField.jsx";
import {useState} from "react";
import {RequirementsEmail} from "../../util/RequirementsAccount.js";
import AuthWarning from "../AuthCommon/AuthWarning.jsx";
import PasswordField from "./PasswordField.jsx";

const LogInSide = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [failToAuth, setFailToAuth] = useState(false);
  const [signUp, setSignUp] = useState(false);

  const checkEmail = (email) => {
    const checkEmail = RequirementsEmail(email);
    if (checkEmail) {
      setFailToAuth(false);
      setEmail(email);
    } else {
      setFailToAuth(true);
    }
  }

  const handleSignButtonSwitch = () => {
    setSignUp(!signUp);
  }

  return (
    <Box className={"MainLoginContainer"}>
      <Box className={"PaddingContainer"}>
        <Box className={"ContentContainer"}>
          <Box className={"SignInContainer"} sx={{flexDirection: "column"}}>
            <Typography variant="h4" className={"SignInText"}>
              {signUp ? "Create an account" : "Sign in"}
            </Typography>
            <HorizontalRule className={"SignInLine"}/>
          </Box>

          {failToAuth ? <AuthWarning warningType={'EMAIL'}/> : null}

          <Typography variant="h6" className={"EmailText"}>
            Email address
          </Typography>

          <EmailField email={email} setEmail={setEmail}/>

          {signUp ?
            <Typography variant="h6" className={"EmailText"}>
              Password
            </Typography> : null
          }

          {signUp ? <PasswordField password={password} setPassword={setPassword}/> : null}

          {signUp ? <Typography variant="body1" className={"AcknowledgementText"}>
            Your password must contain at least 8 characters, including one uppercase, an one lowercase, a number, and
            an special character.
          </Typography> : null}

          {signUp ? <Box className={"CheckboxContainer"}>
            <Checkbox defaultChecked className={"Checkbox"}/>
            <Typography variant="body1" className={"CheckboxText"}>
              Opt in to receive our weekly emails and member communications.
            </Typography>
          </Box> : null}

          <Button
            variant="contained"
            color="primary"
            className={'SignInButton'}
            onClick={() => checkEmail()}
            disableRipple={true}
          >
            {signUp ? "Create an Account" : "Sign In"}
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

          <Button
            variant="outlined"
            className={"ButtonSignUp"}
            disableRipple={true}
            onClick={handleSignButtonSwitch}
          >
            {signUp ? "Sign In" : "Create an Account"}
          </Button>

          <Typography variant="body1" className={"AcknowledgementText"}>
            By signing in or creating a member account, you agree to the Terms of Use and acknowledge the Privacy Policy.
          </Typography>
        </Box>
      </Box>
    </Box>
  )
}

export default LogInSide;