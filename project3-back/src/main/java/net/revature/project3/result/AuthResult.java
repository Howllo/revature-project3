package net.revature.project3.result;

import net.revature.project3.dto.AuthRequestDto;
import net.revature.project3.entity.AppUser;
import net.revature.project3.enumerator.AuthEnum;

public class AuthResult {
    final private AuthEnum result;
    final private AuthRequestDto user;
    final private String token;
    final private AppUser appUser;


    public AuthResult(AuthEnum authEnum, AuthRequestDto user, String token, AppUser appUser){
        this.result = authEnum;
        this.appUser = appUser;
        this.token = token;
        this.user = user;
    }

    public AuthEnum getResult() {
        return result;
    }

    public AuthRequestDto getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }

    public AppUser getAppUser() {
        return appUser;
    }
}
