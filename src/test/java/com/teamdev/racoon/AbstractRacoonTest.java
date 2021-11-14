package com.teamdev.racoon;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

abstract class AbstractRacoonTest {

    private final Racoon interpreter = new Racoon();

    @ParameterizedTest
    @MethodSource("positiveCases")
    void verifyPositiveScenarios(String program, String expectedOutput)
            throws RacoonExecutionException {

        String output = interpreter.execute(program);

        Assertions.assertEquals(expectedOutput, output);
    }

    @ParameterizedTest
    @MethodSource("negativeCases")
    void verifyNegativeScenarios(String program, int expectedErrorPosition) {

        try {

            interpreter.execute(program);

            Assertions.fail("Expected exception was not thrown: " +
                    RacoonExecutionException.class.getName());

        } catch (RacoonExecutionException e) {

            Assertions.assertEquals(expectedErrorPosition, e.getErrorPosition());
        }
    }
}
