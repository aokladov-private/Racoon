package com.teamdev.racoon.runtime.operator;

abstract class AbstractBinaryOperator implements PrioritizedBinaryOperator {

    private final int priority;

    AbstractBinaryOperator(int priority) {

        this.priority = priority;
    }

    @Override
    public int getPriority() {

        return priority;
    }
}
