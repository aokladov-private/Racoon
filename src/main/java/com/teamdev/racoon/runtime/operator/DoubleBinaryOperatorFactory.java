package com.teamdev.racoon.runtime.operator;

import com.teamdev.racoon.runtime.value.DoubleValue;
import com.teamdev.racoon.runtime.value.ValueHolder;

public final class DoubleBinaryOperatorFactory extends AbstractBinaryOperatorFactory {

    enum Priority {

        LOW,
        MEDIUM,
        HIGH
    }

    public DoubleBinaryOperatorFactory() {

        registerOperator("+", () -> new AbstractBinaryOperator(Priority.LOW.ordinal()) {
            @Override
            public ValueHolder<?> apply(double left, double right) {
                return new DoubleValue(left + right);
            }
        });

        registerOperator("-", () -> new AbstractBinaryOperator(Priority.LOW.ordinal()) {

            @Override
            public ValueHolder<?> apply(double left, double right) {
                return new DoubleValue(left - right);
            }
        });

        registerOperator("*", () -> new AbstractBinaryOperator(Priority.MEDIUM.ordinal()) {

            @Override
            public ValueHolder<?> apply(double left, double right) {
                return new DoubleValue(left * right);
            }
        });

        registerOperator("/", () -> new AbstractBinaryOperator(Priority.MEDIUM.ordinal()) {

            @Override
            public ValueHolder<?> apply(double left, double right) {
                return new DoubleValue(left / right);
            }
        });

        registerOperator("^", () -> new AbstractBinaryOperator(Priority.HIGH.ordinal()) {

            @Override
            public ValueHolder<?> apply(double left, double right) {
                return new DoubleValue(StrictMath.pow(left, right));
            }
        });
    }

}
