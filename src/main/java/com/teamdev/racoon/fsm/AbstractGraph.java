package com.teamdev.racoon.fsm;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class AbstractGraph implements Graph {

    private final Map<Object, Set<?>> transitions = new HashMap<>();

    private final Map<Object, StateAcceptor> acceptors = new HashMap<>();

    private final Set<Object> startStates = new TreeSet<>();

    private final Collection<Object> finishStates = new TreeSet<>();

    private final Collection<Object> optionalStates = new TreeSet<>();

    private final Multimap<Object, OnTransitionListener> listeners = ArrayListMultimap.create();

    @Override
    public final Set<?> getStartStates() {

        return Collections.unmodifiableSet(startStates);
    }

    @Override
    public final boolean isFinishState(Object state) {

        return finishStates.contains(state);
    }

    @Override
    public boolean isOptionalState(Object state) {

        return optionalStates.contains(state);
    }

    @Override
    public final Set<?> getTransitions(Object state) {

        return Collections.unmodifiableSet(transitions.get(state));
    }

    @Override
    public final StateAcceptor getAcceptor(Object state) {

        return acceptors.get(state);
    }

    @Override
    public final void onTransition(Object state, InputChain inputChain, OutputChain outputChain)
            throws CannotAcceptInputChainException {

        for (OnTransitionListener listener : listeners.get(state)) {

            listener.onTransition(inputChain, outputChain);
        }
    }

    protected final void setStartStates(Object... startStates) {

        this.startStates.addAll(Arrays.asList(startStates));
    }

    protected final void setFinishStates(Object... finishStates) {

        this.finishStates.addAll(Arrays.asList(finishStates));
    }

    protected final void setOptionalStates(Object... finishStates) {

        this.optionalStates.addAll(Arrays.asList(finishStates));
    }

    protected final void registerState(Object state, StateAcceptor acceptor, Object... transitions) {

        acceptors.put(state, acceptor);

        this.transitions.put(state, new TreeSet<>(Arrays.asList(transitions)));
    }

    protected final void addTransitionListener(OnTransitionListener listener, Object... states) {

        for (Object state : states) {

            listeners.put(state, listener);
        }
    }
}
