package com.teamdev.racoon.runtime.operator;

import com.teamdev.racoon.runtime.value.ValueHolder;

@FunctionalInterface
public interface PrioritizedBinaryOperator {

    int PRIORITY_UNDEFINED = Integer.MIN_VALUE;

    ValueHolder<?> apply(double left, double right);

    default int getPriority() {

        return PRIORITY_UNDEFINED;
    }
}
