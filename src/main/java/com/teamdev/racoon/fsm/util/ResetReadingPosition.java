package com.teamdev.racoon.fsm.util;

import com.google.common.base.Preconditions;
import com.teamdev.racoon.fsm.CannotAcceptInputChainException;
import com.teamdev.racoon.fsm.InputChain;
import com.teamdev.racoon.fsm.OutputChain;
import com.teamdev.racoon.fsm.StateAcceptor;

public class ResetReadingPosition implements StateAcceptor {

    public static StateAcceptor acceptor(StateAcceptor origin) {

        return new ResetReadingPosition(origin);
    }

    private final StateAcceptor origin;

    private ResetReadingPosition(StateAcceptor origin) {

        this.origin = Preconditions.checkNotNull(origin);
    }

    @Override
    public boolean accept(InputChain inputChain, OutputChain outputChain)
            throws CannotAcceptInputChainException {

        int readingPosition = inputChain.readingPosition();

        boolean accepted = origin.accept(inputChain, outputChain);

        if (!accepted) {

            inputChain.setReadingPosition(readingPosition);

            outputChain.clear();
        }

        return accepted;
    }
}
