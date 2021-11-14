package com.teamdev.racoon.fsm.util;

import com.google.common.base.Preconditions;
import com.teamdev.racoon.fsm.CannotAcceptInputChainException;
import com.teamdev.racoon.fsm.InputChain;
import com.teamdev.racoon.fsm.OutputChain;
import com.teamdev.racoon.fsm.StateAcceptor;

import java.util.List;

public class TextAcceptor implements StateAcceptor {

    public static StateAcceptor oneOfAcceptor(List<String> texts) {

        return (inputChain, outputChain) -> {

            for (String text: texts) {

                if (acceptor(text).accept(inputChain, outputChain)) {

                    return true;
                }
            }

            return false;
        };
    }

    public static StateAcceptor acceptor(String text) {

        return ResetReadingPosition.acceptor(new TextAcceptor(text));
    }

    private final String text;

    private TextAcceptor(String text) {

        this.text = Preconditions.checkNotNull(text);
    }

    @Override
    public boolean accept(InputChain inputChain, OutputChain outputChain) throws CannotAcceptInputChainException {

        for (char aChar : text.toCharArray()) {

            if (inputChain.hasMoreChars() && inputChain.currentChar() == aChar) {

                outputChain.append(inputChain.currentChar());
                inputChain.incrementReadingPosition();

            } else {

                return false;
            }
        }

        return true;
    }
}
