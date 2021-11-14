package com.teamdev.racoon.runtime.function;

public interface ArgumentsValidator {

    boolean validate(int argumentCount);

    static ArgumentsValidator twoAndMore() {

        return argumentCount -> argumentCount >= 2;
    }

    static ArgumentsValidator oneAndMore() {

        return argumentCount -> argumentCount >= 1;
    }

    static ArgumentsValidator zero() {

        return argumentCount -> argumentCount == 0;
    }

    static ArgumentsValidator single() {

        return argumentCount -> argumentCount == 1;
    }
}
