package com.teamdev.racoon.runtime.function;

import com.teamdev.racoon.runtime.value.ValueHolder;

import java.util.List;

@FunctionalInterface
public interface MathFunction {

    ValueHolder<?> apply(List<ValueHolder<?>> arguments);

    default ArgumentsValidator getValidator() {

        return arguments -> true;
    }
}
