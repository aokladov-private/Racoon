package com.teamdev.racoon.runtime.value;

public final class DoubleValue implements ValueHolder<Double> {

    private final double value;

    public DoubleValue(double value) {

        this.value = value;
    }

    @Override
    public Double getValue() {

        return value;
    }

    @Override
    public void accept(ValueHolderVisitor visitor) {

        visitor.visit(this);
    }

    @Override
    public String toString() {

        return String.valueOf(getValue());
    }
}
