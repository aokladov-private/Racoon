package com.teamdev.racoon.fsm;

public class OutputChain {

    private final StringBuilder buffer = new StringBuilder(10);

    public void append(char symbol) {

        buffer.append(symbol);
    }

    public String value() {

        return buffer.toString();
    }

    public void clear() {

        buffer.setLength(0);
    }

    @Override
    public String toString() {

        return value();
    }
}
