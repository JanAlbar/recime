package com.janalb.recime.exception;

public class RecipeNotFoundException extends RuntimeException {

    public static final String DEFAULT_MESSAGE = "Recipe not found! ";

    public RecipeNotFoundException(String message) {
        super(DEFAULT_MESSAGE + message);
    }
}
