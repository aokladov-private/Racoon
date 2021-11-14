package com.teamdev.racoon;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

class BracketsEvaluationTest extends AbstractCalculatorTest {

    static Stream<Arguments> positiveCases() {

        return Stream.of(

                Arguments.of("(0)", 0),
                Arguments.of("(((123)))", 123),
                Arguments.of("(10-1)/3^2", 1),
                Arguments.of("2*(2+2)", 8),
                Arguments.of("2*(2+2*3)", 16),
                Arguments.of("2*((2+2)*3)", 24)
        );
    }

    static Stream<Arguments> negativeCases() {

        return Stream.of(

                Arguments.of("1)", 1),
                Arguments.of("(1", 2),
                Arguments.of("()", 1),
                Arguments.of("(1))", 3),
                Arguments.of("((1)", 4)
        );
    }
}
