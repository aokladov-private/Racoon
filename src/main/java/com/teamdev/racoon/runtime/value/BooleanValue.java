package com.teamdev.racoon.runtime.value;

public final class BooleanValue implements ValueHolder<Boolean> {

    private final boolean value;

    public BooleanValue(boolean value) {

        this.value = value;
    }

    @Override
    public Boolean getValue() {

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
