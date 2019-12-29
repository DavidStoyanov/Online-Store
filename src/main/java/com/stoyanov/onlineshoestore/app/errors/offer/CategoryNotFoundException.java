package com.stoyanov.onlineshoestore.app.errors.offer;

public class CategoryNotFoundException extends NullPointerException {

    private static final String EXCEPTION_MESSAGE = "Category not found!";

    public CategoryNotFoundException() {
        this(EXCEPTION_MESSAGE);
    }

    public CategoryNotFoundException(String s) {
        super(s);
    }
}
