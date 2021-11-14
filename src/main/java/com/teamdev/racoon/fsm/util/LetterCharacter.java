package com.teamdev.racoon.fsm.util;

import com.teamdev.racoon.fsm.InputChain;
import com.teamdev.racoon.fsm.OutputChain;
import com.teamdev.racoon.fsm.StateAcceptor;

public final class LetterCharacter implements StateAcceptor {

    public static StateAcceptor acceptor() {

        return new LetterCharacter();
    }

    private LetterCharacter() {
    }

    @Override
    public boolean accept(InputChain inputChain, OutputChain outputChain) {

        return inputChain.hasMoreChars() && Character.isLetter(inputChain.currentChar());
    }
}
