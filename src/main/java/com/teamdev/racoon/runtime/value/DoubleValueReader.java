package com.teamdev.racoon.runtime.value;

public final class DoubleValueReader implements ValueHolderVisitor {

    public static double readValue(ValueHolder<?> valueHolder) {

        DoubleValueReader reader = new DoubleValueReader();
        valueHolder.accept(reader);

        return reader.value;
    }

    private double value;

    @Override
    public void visit(DoubleValue doubleValue) {

        value = doubleValue.getValue();
    }

    @Override
    public void visit(BooleanValue booleanValue) {

        throw new IllegalArgumentException("Type mismatch: Double expected, but Boolean found.");
    }

}
