package com.teamdev.racoon.runtime;

import com.teamdev.racoon.runtime.value.ValueHolder;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public final class RuntimeEnvironment {

    private final Deque<ShuntingYardStack> stack = new ArrayDeque<>();

    private final Map<String, ValueHolder<?>> variables = new HashMap<>();

    private final ByteArrayOutputStream actualOutput = new ByteArrayOutputStream();

    private final PrintStream output = new PrintStream(actualOutput);

    public RuntimeEnvironment() {

        newStack();
    }

    public ShuntingYardStack topStack() {

        return stack.peek();
    }

    public void newStack() {

        stack.push(new ShuntingYardStack());
    }

    public ValueHolder<?> closeTopStack() {

        return stack.pop().popResult();
    }

    public void setVariableValue(String variableName, ValueHolder<?> variableValue) {

        variables.put(variableName, variableValue);
    }

    public ValueHolder<?> getVariableValue(String variableName) {

        return variables.get(variableName);
    }

    public boolean isVariableInitialized(String variableName) {

        return variables.containsKey(variableName);
    }

    public PrintStream printStream() {
        return output;
    }

    public String programOutput() {

        return actualOutput.toString();
    }
}
