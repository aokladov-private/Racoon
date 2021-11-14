package com.teamdev.racoon.runtime.operator;

import com.teamdev.racoon.runtime.value.BooleanValue;

public final class BooleanBinaryOperatorFactory extends AbstractBinaryOperatorFactory {

    public BooleanBinaryOperatorFactory() {

        registerOperator(">", () -> (left, right) -> new BooleanValue(left > right));
        registerOperator(">=", () -> (left, right) -> new BooleanValue(left >= right));

        registerOperator("<", () -> (left, right) -> new BooleanValue(left < right));
        registerOperator("<=", () -> (left, right) -> new BooleanValue(left <= right));

        registerOperator("==", () -> (left, right) -> new BooleanValue(Double.valueOf(left).equals(right)));
        registerOperator("!=", () -> (left, right) -> new BooleanValue(!Double.valueOf(left).equals(right)));
    }
}
