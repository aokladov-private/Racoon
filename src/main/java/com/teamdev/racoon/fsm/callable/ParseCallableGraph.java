package com.teamdev.racoon.fsm.callable;


import com.teamdev.racoon.fsm.AbstractGraph;
import com.teamdev.racoon.fsm.CannotAcceptInputChainException;
import com.teamdev.racoon.fsm.StateAcceptor;
import com.teamdev.racoon.fsm.expression.DoubleExpressionMachine;
import com.teamdev.racoon.fsm.name.NameMachine;
import com.teamdev.racoon.fsm.util.SpecificCharacter;
import com.teamdev.racoon.runtime.Command;
import com.teamdev.racoon.runtime.RuntimeEnvironment;
import com.teamdev.racoon.runtime.function.ArgumentsValidator;
import com.teamdev.racoon.runtime.value.ValueHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.teamdev.racoon.fsm.callable.ParseCallableGraph.State.ARGUMENT;
import static com.teamdev.racoon.fsm.callable.ParseCallableGraph.State.ARGUMENT_SEPARATOR;
import static com.teamdev.racoon.fsm.callable.ParseCallableGraph.State.CLOSING_BRACKET;
import static com.teamdev.racoon.fsm.callable.ParseCallableGraph.State.NAME;
import static com.teamdev.racoon.fsm.callable.ParseCallableGraph.State.OPENING_BRACKET;

abstract class ParseCallableGraph extends AbstractGraph {

    enum State {

        NAME,
        OPENING_BRACKET,
        ARGUMENT,
        ARGUMENT_SEPARATOR,
        CLOSING_BRACKET
    }

    ParseCallableGraph(List<Command> commands) {

        ParseCallableContext context = new ParseCallableContext();

        registerState(NAME, ((StateAcceptor) (inputChain, outputChain) ->
                NameMachine.acceptor().accept(inputChain, outputChain) &&
                        isNameAcceptable(outputChain.value())).andThen((inputChain, outputChain) -> {

            context.callableName = outputChain.value();
            outputChain.clear();

        }), OPENING_BRACKET);

        registerState(OPENING_BRACKET, SpecificCharacter.acceptor('(').andIncrementReadingPosition(),
                ARGUMENT, CLOSING_BRACKET);

        registerState(ARGUMENT, (inputChain, outputChain) -> {

            List<Command> calculateArgumentCommands = new ArrayList<>();

            boolean accepted = DoubleExpressionMachine.acceptor(calculateArgumentCommands).accept(inputChain, outputChain);

            if (accepted) {

                context.calculateArgumentsCommands.add(calculateArgumentCommands);
            }

            return accepted;

        }, CLOSING_BRACKET, ARGUMENT_SEPARATOR);

        registerState(ARGUMENT_SEPARATOR, SpecificCharacter.acceptor(',').andIncrementReadingPosition(), ARGUMENT);

        registerState(CLOSING_BRACKET, SpecificCharacter.acceptor(')').andIncrementReadingPosition());


        setStartStates(NAME);

        setOptionalStates(NAME);

        setFinishStates(CLOSING_BRACKET);


        addTransitionListener((inputChain, outputChain) -> {

            if (!getValidator(context.callableName).validate(context.calculateArgumentsCommands.size())) {

                throw new CannotAcceptInputChainException("Wrong number of arguments in function '%s'".
                        formatted(context.callableName));
            }

            commands.add(createCommand(context.callableName, context.calculateArgumentsCommands));

        }, CLOSING_BRACKET);

    }

    protected abstract boolean isNameAcceptable(String callableName);

    protected abstract Command createCommand(String callableName, List<List<Command>> calculateArgumentsCommands);

    protected abstract ArgumentsValidator getValidator(String callableName);


    static List<ValueHolder<?>> calculateArguments(RuntimeEnvironment runtime, List<List<Command>> commands) {

        return commands.stream().map((Function<List<Command>, ValueHolder<?>>) argumentCommands -> {

            runtime.newStack();

            argumentCommands.forEach(command -> command.execute(runtime));

            return runtime.closeTopStack();

        }).collect(Collectors.toList());
    }

    private static class ParseCallableContext {

        private String callableName;

        private final List<List<Command>> calculateArgumentsCommands = new ArrayList<>(8);

    }
}
