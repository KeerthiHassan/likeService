package com.maveric.likeservice.exception;

public class AlreadyLiked extends RuntimeException {
    public AlreadyLiked(String message) {
        super(message);
    }
}
