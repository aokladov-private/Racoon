package com.teamdev.racoon.fsm.number;

import com.teamdev.racoon.fsm.AbstractGraph;
import com.teamdev.racoon.fsm.util.DigitCharacter;
import com.teamdev.racoon.fsm.util.SpecificCharacter;

import static com.teamdev.racoon.fsm.number.ParseNumberGraph.State.DIGIT;
import static com.teamdev.racoon.fsm.number.ParseNumberGraph.State.DOT;
import static com.teamdev.racoon.fsm.number.ParseNumberGraph.State.FRACTIONAL_DIGIT;
import static com.teamdev.racoon.fsm.number.ParseNumberGraph.State.SIGN;

final class ParseNumberGraph extends AbstractGraph {


    enum State {

        SIGN,
        DIGIT,
        DOT,
        FRACTIONAL_DIGIT
    }

    ParseNumberGraph() {

        registerState(SIGN, SpecificCharacter.acceptor('-').andAppendToOutput().andIncrementReadingPosition(),
                DIGIT);

        registerState(DIGIT, DigitCharacter.acceptor().andAppendToOutput().andIncrementReadingPosition(),
                DIGIT, DOT);

        registerState(DOT, SpecificCharacter.acceptor('.').andAppendToOutput().andIncrementReadingPosition(),
                FRACTIONAL_DIGIT);

        registerState(FRACTIONAL_DIGIT, DigitCharacter.acceptor().andAppendToOutput().andIncrementReadingPosition(),
                FRACTIONAL_DIGIT);

        setStartStates(SIGN, DIGIT);

        setFinishStates(DIGIT, FRACTIONAL_DIGIT);
    }

}
