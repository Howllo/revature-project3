import {Box, IconButton, Typography} from "@mui/material";
import {Link, useNavigate} from "react-router";
import Search from "../components/Search/Search.jsx";
import "./HomePage.css"
import AccountCircleOutlinedIcon from '@mui/icons-material/AccountCircleOutlined';
import FavoriteBorderOutlinedIcon from '@mui/icons-material/FavoriteBorderOutlined';
import CartIcon from "../components/Cart/CartIcon.jsx";

const HomePage = ({children}) => {
  const navigate = useNavigate();

  const handleClick = (e) => {
    e.preventDefault();
    e.stopPropagation();
    navigate("/");
  }

  const handleSignUp = (e) => {
    e.preventDefault();
    e.stopPropagation();
    navigate("/signin");
  }

  const handleFavorite = (e) => {
    e.preventDefault();
    e.stopPropagation();
    navigate("/favorite");
  }

  const handleCart = (e) => {
    e.preventDefault();
    e.stopPropagation();
    navigate("/cart");
  }

  return (
    <Box className={"AppBar"}>
      <Box className={"IconContainer"}>
        <IconButton className={"IconButton"} onClick={(e) => handleClick(e)} disableRipple={true}>
          <Typography variant="h4" color="inherit" className={"ShopName"}>
            RevShop
          </Typography>
        </IconButton>

        <Box className={"MenuBar"}>
          <Link to="/women" className={"Link"}>
            <Typography variant="h6" color="inherit" className={"Women"}>
              Women
            </Typography>
          </Link>

          <Link to="/men" className={"Link"}>
            <Typography variant="h6" color="inherit" className={"Men"}>
              Men
            </Typography>
          </Link>

          <Link to="/accessories" className={"Link"}>
            <Typography variant="h6" color="inherit" className={"Accessories"}>
              Accessories
            </Typography>
          </Link>
          <Search/>

          <IconButton disableRipple={true} onClick={(e) => handleSignUp(e)}>
            <AccountCircleOutlinedIcon className={"LoginIcon"}/>
          </IconButton>

          <IconButton disableRipple={true} onClick={(e) => handleFavorite(e)}>
            <FavoriteBorderOutlinedIcon className={"FavoriteIcon"}/>
          </IconButton>

          <CartIcon handleCart={handleCart} />
        </Box>
      </Box>
    </Box>
  )
}

export default HomePage;