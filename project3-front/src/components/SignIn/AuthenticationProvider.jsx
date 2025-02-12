import {createContext} from "react";

const AuthenticationContext = createContext(null);

export const AuthenticationProvider = ({ children }) => {
  const [data, setData] = useState({
    email: "",
    password: "",
  });
  const [credentialsInvalid, setCredentialsInvalid] = useState(false);

  const value = {

  };

  return (
    <AuthenticationContext.Provider value={value}>
      {children}
    </AuthenticationContext.Provider>
  )
}