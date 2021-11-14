package com.teamdev.racoon.fsm.callable;

import com.teamdev.racoon.fsm.FiniteStateMachine;
import com.teamdev.racoon.fsm.StateAcceptor;
import com.teamdev.racoon.runtime.Command;
import com.teamdev.racoon.runtime.value.ValueHolder;

import java.util.ArrayList;
import java.util.List;

public final class MathFunctionMachine extends FiniteStateMachine {

    public static StateAcceptor acceptor(List<Command> commands) {

        return (inputChain, outputChain) -> {

            ArrayList<Command> calculateFunctionValue = new ArrayList<>();

            boolean accepted = new MathFunctionMachine(calculateFunctionValue).accept(inputChain, outputChain);

            if (accepted) {

                commands.add((runtime) -> {

                    runtime.newStack();

                    calculateFunctionValue.forEach(command -> command.execute(runtime));

                    ValueHolder<?> result = runtime.closeTopStack();

                    runtime.topStack().push(result);
                });
            }

            return accepted;
        };
    }

    private MathFunctionMachine(List<Command> commands) {

        super(new ParseMathFunctionGraph(commands));
    }
}
