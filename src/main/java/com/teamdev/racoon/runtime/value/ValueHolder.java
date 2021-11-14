package com.teamdev.racoon.runtime.value;

public interface ValueHolder<T> {

    T getValue();

    void accept(ValueHolderVisitor visitor);

}
