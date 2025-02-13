import {createContext, useState} from "react";

const AuthenticationContext = createContext(null);

export const AuthenticationProvider = ({ children }) => {
  const [data, setData] = useState({
    email: "",
    password: "",
  });
  const [credentialsInvalid, setCredentialsInvalid] = useState(false);

  const value = {
    email: data.email,
    password: data.password,
    setData,
  };

  return (
    <AuthenticationContext.Provider value={value}>
      {children}
    </AuthenticationContext.Provider>
  )
}