import { OutlinedInput, InputAdornment, Box } from "@mui/material";
import EmailIcon from "@mui/icons-material/Email";
import "./CSS/EmailField.css"

export const EmailFieldSignIn = ({email, setEmail}) => {
  const handleEmailChange = (e) => {
    setEmail(e.target.value)
  }

  return (
    <Box>
      <OutlinedInput
        autoFocus={true}
        required
        type="email"
        className="OutlineField"
        value={email}
        onChange={(e) => handleEmailChange(e)}
        placeholder="Enter your email address"
        startAdornment={
          <InputAdornment position="start">
            <EmailIcon />
          </InputAdornment>
        }
      />
    </Box>
  );
};

import PropTypes from 'prop-types';

EmailFieldSignIn.propTypes = {
  email: PropTypes.string.isRequired,
  setEmail: PropTypes.func.isRequired,
};

export default EmailFieldSignIn;