package com.teamdev.racoon;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

class BinaryOperatorsEvaluationTest extends AbstractCalculatorTest {

    static Stream<Arguments> positiveCases() {

        return Stream.of(

                Arguments.of("5-2", 3),
                Arguments.of("3*7*10", 210),
                Arguments.of("8/4", 2),
                Arguments.of("2^8", 256),
                Arguments.of("2+2*2", 6),
                Arguments.of("5-3-2", 0),
                Arguments.of("512/2^8", 2),
                Arguments.of("10-9/3^2", 9)
        );
    }

    static Stream<Arguments> negativeCases() {

        return Stream.of(

                Arguments.of("+", 0)
        );
    }
}
