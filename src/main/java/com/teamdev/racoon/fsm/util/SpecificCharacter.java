package com.teamdev.racoon.fsm.util;

import com.teamdev.racoon.fsm.InputChain;
import com.teamdev.racoon.fsm.OutputChain;
import com.teamdev.racoon.fsm.StateAcceptor;

public final class SpecificCharacter implements StateAcceptor {

    public static SpecificCharacter acceptor(char valueToAccept) {

        return new SpecificCharacter(valueToAccept);
    }

    private final char valueToAccept;

    private SpecificCharacter(char valueToAccept) {

        this.valueToAccept = valueToAccept;
    }

    @Override
    public boolean accept(InputChain inputChain, OutputChain outputChain) {

        return inputChain.hasMoreChars() && inputChain.currentChar() == valueToAccept;
    }

}
