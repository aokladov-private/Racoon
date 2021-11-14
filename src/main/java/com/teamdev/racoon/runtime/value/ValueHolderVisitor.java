package com.teamdev.racoon.runtime.value;

public interface ValueHolderVisitor {

    void visit(DoubleValue value);

    void visit(BooleanValue value);
}
