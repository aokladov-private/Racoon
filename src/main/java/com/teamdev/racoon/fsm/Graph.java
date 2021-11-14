package com.teamdev.racoon.fsm;

import java.util.Set;

/**
 * A connected directed graph that serves as a model for a {@link FiniteStateMachine}.
 *
 * <p> The graph is described by a set of start states, a set of finish states,
 * allowed transitions between states, and a set of {@link StateAcceptor}s that check
 * the possibility of a transition to a particular state.
 *
 * <p> Also, there is a way to define a {@link OnTransitionListener} that will be notified
 * after some specific state is reached.
 */
public interface Graph {

    Set<?> getStartStates();

    boolean isOptionalState(Object state);

    boolean isFinishState(Object state);

    Set<?> getTransitions(Object state);

    StateAcceptor getAcceptor(Object state);

    default void onTransition(Object state, InputChain inputChain, OutputChain outputChain)
            throws CannotAcceptInputChainException {
    }
}
