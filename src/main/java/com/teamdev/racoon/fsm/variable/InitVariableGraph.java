package com.teamdev.racoon.fsm.variable;

import com.teamdev.racoon.fsm.AbstractGraph;
import com.teamdev.racoon.fsm.expression.ExpressionMachine;
import com.teamdev.racoon.fsm.name.NameMachine;
import com.teamdev.racoon.fsm.util.SpecificCharacter;
import com.teamdev.racoon.runtime.Command;
import com.teamdev.racoon.runtime.value.ValueHolder;

import java.util.ArrayList;
import java.util.List;

import static com.teamdev.racoon.fsm.variable.InitVariableGraph.State.ASSIGN;
import static com.teamdev.racoon.fsm.variable.InitVariableGraph.State.EXPRESSION;
import static com.teamdev.racoon.fsm.variable.InitVariableGraph.State.NAME;

final class InitVariableGraph extends AbstractGraph {

    enum State {

        NAME,
        ASSIGN,
        EXPRESSION
    }

    InitVariableGraph(List<Command> commands) {

        InitVariableContext context = new InitVariableContext();

        registerState(NAME, NameMachine.acceptor(), ASSIGN);

        registerState(ASSIGN, SpecificCharacter.acceptor('=').andIncrementReadingPosition(), EXPRESSION);

        registerState(EXPRESSION, ExpressionMachine.acceptor(context.calculateVariableValueCommands));


        setStartStates(NAME);

        setOptionalStates(NAME);

        setFinishStates(EXPRESSION);


        addTransitionListener((inputChain, outputChain) -> {

            context.variableName = outputChain.value();

            outputChain.clear();

        }, NAME);


        addTransitionListener((inputChain, outputChain) -> {

            commands.add(runtime -> {

                runtime.newStack();

                context.calculateVariableValueCommands.forEach(command -> command.execute(runtime));

                ValueHolder<?> variableValue = runtime.closeTopStack();

                runtime.setVariableValue(context.variableName, variableValue);
            });

        }, EXPRESSION);
    }


    private static class InitVariableContext {

        private String variableName;

        private final List<Command> calculateVariableValueCommands = new ArrayList<>();
    }
}
