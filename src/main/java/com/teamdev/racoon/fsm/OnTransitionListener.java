package com.teamdev.racoon.fsm;

@FunctionalInterface
public interface OnTransitionListener {

    void onTransition(InputChain inputChain, OutputChain outputChain)
            throws CannotAcceptInputChainException;
}
