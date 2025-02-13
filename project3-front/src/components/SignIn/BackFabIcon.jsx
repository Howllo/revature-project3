import {Box, Typography} from "@mui/material";
import "./CSS/BackFabIcon.css"
import ArrowBackOutlinedIcon from '@mui/icons-material/ArrowBackOutlined';
import {useState} from "react";
import {useNavigate} from "react-router";

const BackFabIcon = () => {
  const [isHover, setIsHover] = useState(false);
  let timeoutId = 0;
  const nav = useNavigate();

  const handleHover = () => {
    setIsHover(true);
    clearTimeout(timeoutId);
  }

  const handleHoverOut = () => {
    clearTimeout(timeoutId);
    setIsHover(false);
  }

  const timer = () => {
    timeoutId = setTimeout(() => handleHover(), 100);
  }

  const handleClickEvent = (e) => {
    e.preventDefault();
    nav('/')
  }

  return (
    <Box className={"FabContainer"}>
      <Box className={"FabIcon"} onMouseEnter={timer} onMouseLeave={handleHoverOut} onClick={handleClickEvent}>
        <Box className={"ContainerInsideFab"}>
          <ArrowBackOutlinedIcon className={"MuiArrowBackOutlined"}/>
          {isHover ? <Typography variant="h6" component="div" className={"BackText"}>
            BACK
          </Typography> : null}
        </Box>
      </Box>
    </Box>
  )
}

export default BackFabIcon;