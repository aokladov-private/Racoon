package com.teamdev.racoon.fsm.expression;

import com.teamdev.racoon.fsm.FiniteStateMachine;
import com.teamdev.racoon.fsm.StateAcceptor;
import com.teamdev.racoon.runtime.Command;
import com.teamdev.racoon.runtime.value.ValueHolder;

import java.util.ArrayList;
import java.util.List;

public final class DoubleExpressionMachine extends FiniteStateMachine {

    public static StateAcceptor acceptor(List<Command> commands) {

        return (inputChain, outputChain) -> {

            List<Command> expressionCommandList = new ArrayList<>();

            boolean accepted = new DoubleExpressionMachine(expressionCommandList).accept(inputChain, outputChain);

            if (accepted) {

                commands.add((runtime) -> {

                    runtime.newStack();

                    expressionCommandList.forEach(command -> command.execute(runtime));

                    ValueHolder<?> result = runtime.closeTopStack();

                    runtime.topStack().push(result);
                });

            }

            return accepted;
        };
    }

    private DoubleExpressionMachine(List<Command> commands) {

        super(new ParseDoubleExpressionGraph(commands));
    }
}
