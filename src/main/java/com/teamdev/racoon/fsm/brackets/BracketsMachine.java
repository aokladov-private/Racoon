package com.teamdev.racoon.fsm.brackets;

import com.teamdev.racoon.fsm.FiniteStateMachine;
import com.teamdev.racoon.fsm.StateAcceptor;
import com.teamdev.racoon.runtime.Command;
import com.teamdev.racoon.runtime.value.ValueHolder;

import java.util.ArrayList;
import java.util.List;

public final class BracketsMachine extends FiniteStateMachine {

    public static StateAcceptor acceptor(List<Command> commands) {

        return (inputChain, outputChain) -> {

            ArrayList<Command> calculateBracketsValue = new ArrayList<>();

            boolean accepted = new BracketsMachine(calculateBracketsValue).accept(inputChain, outputChain);

            if (accepted) {

                commands.add((runtime) -> {

                    runtime.newStack();

                    calculateBracketsValue.forEach(command -> command.execute(runtime));

                    ValueHolder<?> result = runtime.closeTopStack();

                    runtime.topStack().push(result);
                });
            }

            return accepted;
        };
    }

    private BracketsMachine(List<Command> commands) {

        super(new ParseBracketsGraph(commands));
    }
}
