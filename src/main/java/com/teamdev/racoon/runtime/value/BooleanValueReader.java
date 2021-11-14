package com.teamdev.racoon.runtime.value;

public final class BooleanValueReader implements ValueHolderVisitor {

    public static boolean readValue(ValueHolder<?> valueHolder) {

        BooleanValueReader reader = new BooleanValueReader();
        valueHolder.accept(reader);

        return reader.value;
    }

    private boolean value;

    @Override
    public void visit(DoubleValue doubleValue) {

        throw new IllegalArgumentException("Type mismatch: Boolean expected, but Double found.");

    }

    @Override
    public void visit(BooleanValue booleanValue) {

        value = booleanValue.getValue();
    }
}
