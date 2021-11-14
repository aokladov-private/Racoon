package com.teamdev.racoon.fsm.callable;

import com.teamdev.racoon.fsm.CannotAcceptInputChainException;
import com.teamdev.racoon.fsm.FiniteStateMachine;
import com.teamdev.racoon.fsm.InputChain;
import com.teamdev.racoon.fsm.OutputChain;
import com.teamdev.racoon.fsm.StateAcceptor;
import com.teamdev.racoon.runtime.Command;

import java.util.ArrayList;
import java.util.List;

public final class SystemProcedureMachine extends FiniteStateMachine {

    public static StateAcceptor acceptor(List<Command> commands) {

        return (inputChain, outputChain) -> {

            List<Command> executeProcedureCommands = new ArrayList<>();

            boolean accepted = new SystemProcedureMachine(executeProcedureCommands).accept(inputChain, outputChain);

            if (accepted) {

                commands.addAll(executeProcedureCommands);
            }

            return accepted;
        };
    }

    private SystemProcedureMachine(List<Command> commands) {

        super(new ParseSystemProcedureGraph(commands));
    }
}
