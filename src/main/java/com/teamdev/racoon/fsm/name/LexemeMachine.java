package com.teamdev.racoon.fsm.name;

import com.teamdev.racoon.fsm.FiniteStateMachine;
import com.teamdev.racoon.fsm.StateAcceptor;
import com.teamdev.racoon.fsm.util.ResetReadingPosition;

public class LexemeMachine extends FiniteStateMachine {

    public static StateAcceptor acceptor(String lexeme) {

        return ResetReadingPosition.acceptor((inputChain, outputChain) ->
                new LexemeMachine().accept(inputChain, outputChain) &&
                        outputChain.value().equals(lexeme)).andClearOutput();
    }

    private LexemeMachine() {

        super(new ParseNameGraph(), true);
    }
}
