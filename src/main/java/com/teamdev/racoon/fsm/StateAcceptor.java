package com.teamdev.racoon.fsm;

import java.util.function.BiConsumer;

/**
 * An abstract base that checks the possibility of a transition to a particular state
 * based on {@link InputChain} provided.
 *
 * <p> For mode information follow the
 * <a href=https://en.wikipedia.org/wiki/Finite-state_machine#Acceptors>paper on wikipedia</a>.
 */
@FunctionalInterface
public interface StateAcceptor {

    boolean accept(InputChain inputChain, OutputChain outputChain) throws CannotAcceptInputChainException;

    default StateAcceptor andThen(BiConsumer<InputChain, OutputChain> additionalAction) {

        return (inputChain, outputChain) -> {

            boolean accepted = accept(inputChain, outputChain);

            if (accepted) {

                additionalAction.accept(inputChain, outputChain);
            }

            return accepted;
        };
    }

    default StateAcceptor andAppendToOutput() {

        return andThen((inputChain, outputChain) -> outputChain.append(inputChain.currentChar()));
    }

    default StateAcceptor andClearOutput() {

        return andThen((inputChain, outputChain) -> outputChain.clear());
    }

    default StateAcceptor andIncrementReadingPosition() {

        return andThen((inputChain, outputChain) -> inputChain.incrementReadingPosition());
    }
}

