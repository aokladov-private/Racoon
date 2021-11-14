package com.teamdev.racoon.fsm;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.Set;

/**
 * Implementation of the concept of a
 * <a href=https://en.wikipedia.org/wiki/Finite-state_machine>Finite State Machine</a>.
 *
 * <p> Uses a {@link Graph} as a data model.
 *
 * <p> Implements and serves as a {@link StateAcceptor} so
 * it may be used as a state acceptor inside the other machines.
 */
public class FiniteStateMachine implements StateAcceptor {

    private static final Logger LOG = LoggerFactory.getLogger(FiniteStateMachine.class);

    private final Graph graph;

    private final boolean isLexemeMachine;

    protected FiniteStateMachine(Graph graph) {

        this(graph, false);
    }

    protected FiniteStateMachine(Graph graph, boolean isLexemeMachine) {

        this.graph = Preconditions.checkNotNull(graph);

        this.isLexemeMachine = isLexemeMachine;
    }

    @Override
    public final boolean accept(InputChain inputChain, OutputChain outputChain)
            throws CannotAcceptInputChainException {

        if (LOG.isInfoEnabled()) {

            LOG.info("[STARTED] {} for '{}'", getClass().getSimpleName(), inputChain);
        }

        Set<?> startStates = graph.getStartStates();

        Optional<Object> currentState = makeTransition(startStates, inputChain, outputChain);

        if (currentState.isEmpty()) {

            if (LOG.isInfoEnabled()) {

                LOG.info("[EXIT] {}", getClass().getSimpleName());
            }

            // cannot accept input so just return false
            return false;
        }

        while (true) {

            Set<?> possibleTransitions = graph.getTransitions(currentState.get());

            Optional<Object> newState = makeTransition(possibleTransitions, inputChain, outputChain);

            if (newState.isEmpty()) {

                if (graph.isFinishState(currentState.get())) {

                    if (LOG.isInfoEnabled()) {

                        LOG.info("[FINISH] {} at {}", getClass().getSimpleName(), currentState.get());
                    }

                    // we're at FINISH state of a graph, so we can just stop moving!
                    return true;
                }

                if (graph.isOptionalState(currentState.get())) {

                    if (LOG.isInfoEnabled()) {

                        LOG.info("[ROLLBACK] {} <- {}", getClass().getSimpleName(), currentState.get());
                    }

                    // just not accept input
                    return false;
                }

                if (LOG.isInfoEnabled()) {

                    LOG.info("[DEADLOCK] {} at {}", getClass().getSimpleName(), currentState.get());
                }

                throw new CannotAcceptInputChainException("Machine '%s' is in deadlock at the state '%s'".
                        formatted(getClass().getSimpleName(), currentState.get()));
            }

            currentState = newState;
        }
    }

    private Optional<Object> makeTransition(Iterable<?> possibleTransitions,
                                            InputChain inputChain, OutputChain outputChain)
            throws CannotAcceptInputChainException {

        skipWhiteSpaces(inputChain);

        for (Object state : possibleTransitions) {

            StateAcceptor acceptor = graph.getAcceptor(state);

            if (acceptor.accept(inputChain, outputChain)) {

                if (LOG.isInfoEnabled()) {

                    LOG.info("[ACCEPT] {} -> {}", getClass().getSimpleName(), state);
                }

                graph.onTransition(state, inputChain, outputChain);

                return Optional.of(state);

            } else {

                if (LOG.isDebugEnabled()) {

                    LOG.debug("[REJECT] {} !-> {}", getClass().getSimpleName(), state);
                }
            }
        }

        return Optional.empty();
    }

    private void skipWhiteSpaces(InputChain inputChain) {

        if (!isLexemeMachine) {

            while (inputChain.hasMoreChars() && Character.isWhitespace(inputChain.currentChar())) {

                inputChain.incrementReadingPosition();
            }
        }
    }
}
