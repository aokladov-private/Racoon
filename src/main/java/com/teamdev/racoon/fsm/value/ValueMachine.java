package com.teamdev.racoon.fsm.value;

import com.teamdev.racoon.fsm.FiniteStateMachine;
import com.teamdev.racoon.fsm.StateAcceptor;
import com.teamdev.racoon.runtime.Command;
import com.teamdev.racoon.runtime.value.ValueHolder;

import java.util.ArrayList;
import java.util.List;

public final class ValueMachine extends FiniteStateMachine {

    public static StateAcceptor acceptor(List<Command> commands) {

        return (inputChain, outputChain) -> {

            ArrayList<Command> calculateValueCommandList = new ArrayList<>();

            boolean accepted = new ValueMachine(calculateValueCommandList).accept(inputChain, outputChain);

            if (accepted) {

                commands.add((runtime) -> {

                    runtime.newStack();

                    calculateValueCommandList.forEach(command -> command.execute(runtime));

                    ValueHolder<?> result = runtime.closeTopStack();

                    runtime.topStack().push(result);

                });
            }

            return accepted;
        };
    }


    private ValueMachine(List<Command> commands) {

        super(new ParseDoubleValueGraph(commands));
    }
}
