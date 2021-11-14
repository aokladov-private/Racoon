package com.teamdev.racoon;

final class RacoonExecutionException extends Exception {

    private final int errorPosition;

    RacoonExecutionException(String message, int errorPosition) {

        super(message);
        this.errorPosition = errorPosition;
    }

    int getErrorPosition() {

        return errorPosition;
    }
}
