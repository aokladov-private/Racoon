package com.teamdev.racoon.runtime;

import com.google.common.base.Preconditions;
import com.teamdev.racoon.runtime.operator.PrioritizedBinaryOperator;
import com.teamdev.racoon.runtime.value.DoubleValueReader;
import com.teamdev.racoon.runtime.value.ValueHolder;

import java.util.ArrayDeque;
import java.util.Deque;

public final class ShuntingYardStack {

    private final Deque<ValueHolder<?>> operandStack = new ArrayDeque<>(16);

    private final Deque<PrioritizedBinaryOperator> operatorStack = new ArrayDeque<>(16);

    public void push(ValueHolder<?> operand) {

        operandStack.push(operand);
    }

    public void push(PrioritizedBinaryOperator operator) {

        Preconditions.checkNotNull(operator);

        while (!operatorStack.isEmpty() && operator.getPriority() <= operatorStack.peek().getPriority()) {

            executeTopOperator();
        }

        operatorStack.push(operator);
    }

    ValueHolder<?> popResult() {

        while (!operatorStack.isEmpty()) {

            executeTopOperator();
        }

        if (operandStack.size() != 1) {

            throw new IllegalStateException("Size of operand stack should be 1, but actually " + operandStack.size());
        }

        return operandStack.pop();
    }

    private void executeTopOperator() {

        double rightOperand = DoubleValueReader.readValue(operandStack.pop());
        double leftOperand = DoubleValueReader.readValue(operandStack.pop());

        PrioritizedBinaryOperator topOperator = operatorStack.pop();

        ValueHolder<?> valueHolder = topOperator.apply(leftOperand, rightOperand);

        operandStack.push(valueHolder);
    }

}
