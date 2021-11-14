package com.teamdev.racoon;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

class WhileLoopTest extends AbstractRacoonTest {

    static Stream<Arguments> positiveCases() {

        return Stream.of(

                Arguments.of("i = 0; iterationCount = 5; while(i < iterationCount) {" +

                                "i = i + 1;" +
                                "print(i); " +

                                "};",

                        "1.0" + System.lineSeparator() +
                                "2.0" + System.lineSeparator() +
                                "3.0" + System.lineSeparator() +
                                "4.0" + System.lineSeparator() +
                                "5.0" + System.lineSeparator()
                )
        );
    }

    static Stream<Arguments> negativeCases() {

        return Stream.of(

                Arguments.of("while(){};", 6)
        );
    }
}
