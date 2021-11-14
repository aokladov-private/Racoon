package com.teamdev.racoon;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

class FunctionsEvaluationTest extends AbstractCalculatorTest {

    static Stream<Arguments> positiveCases() {

        return Stream.of(

                Arguments.of("min(2, 1, 5, 7)", 1),
                Arguments.of("max(2, 1, 5, 7)", 7),
                Arguments.of("max(2, 1, 5, min(7, 8, 9))", 7),
                Arguments.of("pi() * pi()", Math.PI * Math.PI),
                Arguments.of("log2(8)", 3)
        );
    }

    static Stream<Arguments> negativeCases() {

        return Stream.of(

                Arguments.of("a()", 1),
                Arguments.of("min()", 5),
                Arguments.of("max(1)", 6),
                Arguments.of("pi(1)", 5),
                Arguments.of("log2(4, 4)", 10),
                Arguments.of("log2()", 6)
        );
    }
}
