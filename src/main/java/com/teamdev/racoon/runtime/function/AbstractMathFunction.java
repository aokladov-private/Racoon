package com.teamdev.racoon.runtime.function;

import com.google.common.base.Preconditions;

public abstract class AbstractMathFunction implements MathFunction {

    private final ArgumentsValidator validator;

    AbstractMathFunction(ArgumentsValidator validator) {

        this.validator = Preconditions.checkNotNull(validator);
    }

    @Override
    public ArgumentsValidator getValidator() {

        return validator;
    }
}
