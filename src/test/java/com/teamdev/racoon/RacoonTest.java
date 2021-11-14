package com.teamdev.racoon;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

class RacoonTest extends AbstractRacoonTest {

    static Stream<Arguments> positiveCases() {

        return Stream.of(

                Arguments.of("a = 5; print(a);", "5.0" + System.lineSeparator()),

                Arguments.of("a = 5; print(a * a);", "25.0" + System.lineSeparator()),
                Arguments.of("a = 1; b = 2; print(a + b);", "3.0" + System.lineSeparator()),
                Arguments.of("a = 8; b = a * 2; print(b);", "16.0" + System.lineSeparator()),

                Arguments.of("a = 3; b = 7; print(a, b);",
                        "3.0" + System.lineSeparator() +
                                "7.0" + System.lineSeparator()),

                Arguments.of("print = 3; print(print);", "3.0" + System.lineSeparator()),

                Arguments.of("print(5); print = 4; print(print);",
                        "5.0" + System.lineSeparator() +
                                "4.0" + System.lineSeparator()),


                Arguments.of("min = 3; print(min(min, min(min, min, min)));", "3.0" + System.lineSeparator()),

                Arguments.of("a = 3 > 2; print(a);", Boolean.TRUE + System.lineSeparator()),

                Arguments.of("a = 3 >= 3; print(a);", Boolean.TRUE + System.lineSeparator()),

                Arguments.of("a = 3 < 2; print(a);", Boolean.FALSE + System.lineSeparator()),

                Arguments.of("a = 3 <= 2; print(a);", Boolean.FALSE + System.lineSeparator()),

                Arguments.of("a = 3 == 2; print(a);", Boolean.FALSE + System.lineSeparator()),

                Arguments.of("a = 3 != 2; print(a);", Boolean.TRUE + System.lineSeparator()),

                Arguments.of("a = 1 < 2; b = 5; print(a, 7, b);", Boolean.TRUE + System.lineSeparator() +
                        "7.0" + System.lineSeparator() +
                        "5.0" + System.lineSeparator())

        );
    }

    static Stream<Arguments> negativeCases() {

        return Stream.of(

                Arguments.of("a", 0),
                Arguments.of("a = ;", 4),
                Arguments.of("a = 5 b = 7;", 6),
                Arguments.of("a = b;", 6),
                Arguments.of("print();", 7),
                Arguments.of("a = 1 < 2; b = a + 1;", 21)
        );
    }
}
