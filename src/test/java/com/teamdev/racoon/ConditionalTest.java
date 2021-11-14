package com.teamdev.racoon;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class ConditionalTest extends AbstractRacoonTest {

  static Stream<Arguments> positiveCases() {

    return Stream.of(

        Arguments.of("i = 0; j = 5; if(j < 10) {" +

                "i = i + 1;" +
                "print(i); " +

                "};",

            "1.0" + System.lineSeparator()
        ),
        Arguments.of("i = 0; j = 5; if(j > 10) {" +

                "i = i + 1;" +
                "print(i); " +

                "} else {" +
                "i = i + 2;" +
                "print(i); " +
              "};",

            "2.0" + System.lineSeparator()
        )
    );
  }

  static Stream<Arguments> negativeCases() {

    return Stream.of(

        Arguments.of("if(){};", 3)
    );
  }
}
