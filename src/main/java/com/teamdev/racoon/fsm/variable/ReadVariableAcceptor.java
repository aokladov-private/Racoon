package com.teamdev.racoon.fsm.variable;

import com.teamdev.racoon.fsm.CannotAcceptInputChainException;
import com.teamdev.racoon.fsm.InputChain;
import com.teamdev.racoon.fsm.OutputChain;
import com.teamdev.racoon.fsm.StateAcceptor;
import com.teamdev.racoon.fsm.name.NameMachine;
import com.teamdev.racoon.runtime.Command;
import com.teamdev.racoon.runtime.value.ValueHolder;

import java.util.List;

public class ReadVariableAcceptor implements StateAcceptor {

    public static StateAcceptor acceptor(List<Command> commands) {

        return (inputChain, outputChain) -> {

            boolean accepted = new ReadVariableAcceptor().accept(inputChain, outputChain);

            if (accepted) {

                String variableName = outputChain.value();

                outputChain.clear();

                commands.add(runtime -> {

                    if (!runtime.isVariableInitialized(variableName)) {

                        throw new IllegalStateException("Variable is not initialized '%s'".
                                formatted(variableName));
                    }

                    ValueHolder<?> variableValue = runtime.getVariableValue(variableName);

                    runtime.topStack().push(variableValue);
                });
            }

            return accepted;
        };
    }


    private ReadVariableAcceptor() {
    }

    @Override
    public boolean accept(InputChain inputChain, OutputChain outputChain) throws CannotAcceptInputChainException {
        return NameMachine.acceptor().accept(inputChain, outputChain);
    }
}
