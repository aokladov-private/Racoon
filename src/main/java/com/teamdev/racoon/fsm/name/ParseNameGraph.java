package com.teamdev.racoon.fsm.name;


import com.teamdev.racoon.fsm.AbstractGraph;
import com.teamdev.racoon.fsm.util.DigitCharacter;
import com.teamdev.racoon.fsm.util.LetterCharacter;

import static com.teamdev.racoon.fsm.name.ParseNameGraph.State.DIGIT;
import static com.teamdev.racoon.fsm.name.ParseNameGraph.State.LETTER;

final class ParseNameGraph extends AbstractGraph {


    enum State {

        LETTER,
        DIGIT
    }

    ParseNameGraph() {

        registerState(LETTER, LetterCharacter.acceptor().andAppendToOutput().andIncrementReadingPosition(),
                LETTER, DIGIT);

        registerState(DIGIT, DigitCharacter.acceptor().andAppendToOutput().andIncrementReadingPosition(),
                DIGIT, LETTER);


        setStartStates(LETTER);

        setFinishStates(LETTER, DIGIT);
    }

}
