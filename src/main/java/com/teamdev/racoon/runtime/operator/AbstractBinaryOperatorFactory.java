package com.teamdev.racoon.runtime.operator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class AbstractBinaryOperatorFactory implements BinaryOperatorFactory {

    private final Map<String, OperatorCreator> creators = new HashMap<>();

    final void registerOperator(String operatorSign, OperatorCreator creator) {

        creators.put(operatorSign, creator);
    }

    @Override
    public final PrioritizedBinaryOperator create(String operatorSign) {

        OperatorCreator creator = creators.get(operatorSign);

        if (creator == null) {

            throw new IllegalArgumentException("Binary operator not found '%s'".formatted(operatorSign));
        }

        return creator.create();
    }

    @Override
    public final Set<String> operatorSigns() {

        return Collections.unmodifiableSet(creators.keySet());
    }

    @FunctionalInterface
    interface OperatorCreator {

        PrioritizedBinaryOperator create();
    }
}
