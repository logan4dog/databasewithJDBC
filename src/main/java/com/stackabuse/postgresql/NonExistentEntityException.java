package com.stackabuse.postgresql;

public class NonExistentEntityException extends Exception {
    private static final long serialVersionUID = -3760558819369784286L;

    public NonExistentEntityException(String message) {
        super(message);
    }
}
