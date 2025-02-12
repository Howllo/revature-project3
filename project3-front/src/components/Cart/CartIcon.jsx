import {Box, IconButton, Typography} from "@mui/material";
import ShoppingBagOutlinedIcon from "@mui/icons-material/ShoppingBagOutlined";
import "./CartIcon.css"
import {useState} from "react";

const CartIcon = ({itemNumber, handleCart}) => {
  const [cartItemsTotal, setCartItemsTotal] = useState(itemNumber || 0);

  return (
    <Box>
      <IconButton disableRipple={true} onClick={(e) => handleCart(e)}>
        <ShoppingBagOutlinedIcon className={"CartIcon"}/>

        <Typography variant="h6" color="red" className={"CartNumber"}>
          {cartItemsTotal}
        </Typography>
      </IconButton>
    </Box>
  )
}

export default CartIcon;