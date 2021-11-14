package com.teamdev.racoon;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

class NumbersEvaluationTest extends AbstractCalculatorTest {

    static Stream<Arguments> positiveCases() {

        return Stream.of(

                Arguments.of("0", 0),
                Arguments.of("1", 1),
                Arguments.of("   1", 1),
                Arguments.of("1   ", 1),
                Arguments.of("   1    ", 1),
                Arguments.of("123", 123),
                Arguments.of("0.123", 0.123),
                Arguments.of("12.345", 12.345),
                Arguments.of("-1", -1),
                Arguments.of("-123", -123),
                Arguments.of("-12345.67", -12345.67)
        );
    }

    static Stream<Arguments> negativeCases() {

        return Stream.of(

                Arguments.of("1a", 1),
                Arguments.of("1 2", 2),
                Arguments.of("1.", 2),
                Arguments.of("-a", 1),
                Arguments.of("-.", 1),
                Arguments.of("1.a", 2),
                Arguments.of("1.2a", 3),
                Arguments.of("-12a", 3),
                Arguments.of("-1.2a", 4)
        );
    }
}
