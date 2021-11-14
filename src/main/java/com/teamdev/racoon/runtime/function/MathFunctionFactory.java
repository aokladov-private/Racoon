package com.teamdev.racoon.runtime.function;

import com.teamdev.racoon.runtime.value.DoubleValue;
import com.teamdev.racoon.runtime.value.DoubleValueReader;
import com.teamdev.racoon.runtime.value.ValueHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class MathFunctionFactory {

    private final Map<String, MathFunctionCreator> creators = new HashMap<>();

    public MathFunctionFactory() {

        creators.put("min", () -> new AbstractMathFunction(ArgumentsValidator.twoAndMore()) {
            @Override
            public ValueHolder<?> apply(List<ValueHolder<?>> arguments) {

                double result = arguments.stream().mapToDouble(DoubleValueReader::readValue).min().getAsDouble();
                return new DoubleValue(result);
            }
        });

        creators.put("max", () -> new AbstractMathFunction(ArgumentsValidator.twoAndMore()) {
            @Override
            public ValueHolder<?> apply(List<ValueHolder<?>> arguments) {

                double result = arguments.stream().mapToDouble(DoubleValueReader::readValue).max().getAsDouble();
                return new DoubleValue(result);
            }
        });

        creators.put("log2", () -> new AbstractMathFunction(ArgumentsValidator.single()) {
            @Override
            public ValueHolder<?> apply(List<ValueHolder<?>> arguments) {

                double result = StrictMath.log(DoubleValueReader.readValue(arguments.get(0))) / StrictMath.log(2);
                return new DoubleValue(result);
            }
        });

        creators.put("pi", () -> new AbstractMathFunction(ArgumentsValidator.zero()) {
            @Override
            public ValueHolder<?> apply(List<ValueHolder<?>> arguments) {
                return new DoubleValue(Math.PI);
            }
        });
    }

    public MathFunction create(String functionName) {

        MathFunctionCreator creator = creators.get(functionName);

        if (creator == null) {

            throw new IllegalArgumentException("Function not supported '%s'".formatted(functionName));
        }

        return creator.create();
    }

    public boolean isFunction(String functionName) {

        return creators.containsKey(functionName);
    }

    @FunctionalInterface
    interface MathFunctionCreator {

        MathFunction create();
    }
}
