package com.teamdev.racoon;

final class InvalidMathExpressionException extends Exception {

    private final int errorPosition;

    InvalidMathExpressionException(String message, int errorPosition) {

        super(message);
        this.errorPosition = errorPosition;
    }

    int getErrorPosition() {

        return errorPosition;
    }
}
