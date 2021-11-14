package com.teamdev.racoon.fsm.number;

import com.teamdev.racoon.fsm.FiniteStateMachine;
import com.teamdev.racoon.fsm.StateAcceptor;
import com.teamdev.racoon.runtime.Command;
import com.teamdev.racoon.runtime.value.DoubleValue;

import java.util.List;

public class NumberMachine extends FiniteStateMachine {

    public static StateAcceptor acceptor(List<Command> commands) {

        return (inputChain, outputChain) -> {

            boolean accepted = new NumberMachine().accept(inputChain, outputChain);

            if (accepted) {

                double numberValue = Double.parseDouble(outputChain.value());
                outputChain.clear();

                commands.add(runtime -> runtime.topStack().push(new DoubleValue(numberValue)));
            }
            return accepted;
        };
    }

    private NumberMachine() {

        super(new ParseNumberGraph(), true);
    }
}
