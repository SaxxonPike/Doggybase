package com.saxxon;

public class DogCeoException extends Exception {
    public DogCeoException(String message, DogCeoResponse response) {
        super(message);
        Response = response;
    }

    public final DogCeoResponse Response;
}
