package net.revature.project3.result;

public record Result<E extends Enum<E>, T>(E result, T data, String message) { }
