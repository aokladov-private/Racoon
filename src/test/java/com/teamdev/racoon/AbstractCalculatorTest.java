package com.teamdev.racoon;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

abstract class AbstractCalculatorTest {

    private final Calculator calculator = new Calculator();

    @ParameterizedTest
    @MethodSource("positiveCases")
    void verifyPositiveScenarios(String expression, double expectedResult)
            throws InvalidMathExpressionException {

        double calculatedResult = calculator.evaluate(expression);

        Assertions.assertEquals(expectedResult, calculatedResult);
    }

    @ParameterizedTest
    @MethodSource("negativeCases")
    void verifyNegativeScenarios(String expression, int expectedErrorPosition) {

        try {

            calculator.evaluate(expression);

            Assertions.fail("Expected exception was not thrown: " +
                    InvalidMathExpressionException.class.getName());

        } catch (InvalidMathExpressionException e) {

            Assertions.assertEquals(expectedErrorPosition, e.getErrorPosition());
        }
    }
}
