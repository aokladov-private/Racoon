package com.teamdev.racoon.runtime.operator;

import java.util.Set;

public interface BinaryOperatorFactory {

    PrioritizedBinaryOperator create(String operatorSign);

    Set<String> operatorSigns();
}
