package com.teamdev.racoon.fsm.statement;

import com.teamdev.racoon.fsm.FiniteStateMachine;
import com.teamdev.racoon.fsm.StateAcceptor;
import com.teamdev.racoon.runtime.Command;

import java.util.ArrayList;
import java.util.List;

final class StatementMachine extends FiniteStateMachine {

    public static StateAcceptor acceptor(List<Command> commands) {

        return (inputChain, outputChain) -> {

            List<Command> executeStatementCommands = new ArrayList<>();

            boolean accepted = new StatementMachine(executeStatementCommands).accept(inputChain, outputChain);

            if (accepted) {

                commands.addAll(executeStatementCommands);
            }

            return accepted;
        };
    }

    private StatementMachine(List<Command> commands) {

        super(new ParseStatementGraph(commands));
    }
}
