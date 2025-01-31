package net.revature.project3.result;

import lombok.Getter;

@Getter
public class Result<E extends Enum<E>, T> {
    final private E result;
    final private String token;
    final private T data;

    public Result(E result, String token, T data){
        this.result = result;
        this.token = token;
        this.data = data;
    }
}
