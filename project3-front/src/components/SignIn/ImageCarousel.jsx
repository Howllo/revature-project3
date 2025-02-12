﻿import {Box} from "@mui/material";
import "./CSS/ImageCarousel.css";
import BackFabIcon from "./BackFabIcon.jsx";
import { useState } from "react";

const ImageCarousel = () => {
  const [currentImage, setCurrentImage] = useState(0);
  const imageList = [
    "https://images.lululemon.com/is/image/lululemon/NA_Jul24_Membership_Partner-Perks_LogInScreen_Main_Sign-in_D_PartnerPerks?q=100&w=3840",
    "https://images.lululemon.com/is/image/lululemon/NA_Jul24_Membership_Partner-Perks_LogInScreen_Main_Sign-in_D_Membership?q=100&w=3840"
  ]
  setInterval(() => handleCarousel(), 10000);

  const handleCarousel = () => {
    if(currentImage >= imageList.length - 1) {
      setCurrentImage(0);
    } else {
      setCurrentImage(currentImage + 1);
    }
  }

  return (
    <Box className={"ImageCarousel"}>
      <img className="FabImageCarousel" src={imageList[currentImage]} alt="ImageCarousel" />
      <BackFabIcon/>
    </Box>
  )
}

export default ImageCarousel;