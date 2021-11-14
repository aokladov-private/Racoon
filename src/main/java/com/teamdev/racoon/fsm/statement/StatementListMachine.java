package com.teamdev.racoon.fsm.statement;

import com.teamdev.racoon.fsm.FiniteStateMachine;
import com.teamdev.racoon.fsm.StateAcceptor;
import com.teamdev.racoon.runtime.Command;

import java.util.List;

public final class StatementListMachine extends FiniteStateMachine {

    public static StateAcceptor acceptor(List<Command> commands) {

        return (inputChain, outputChain) -> new StatementListMachine(commands).accept(inputChain, outputChain);
    }

    private StatementListMachine(List<Command> commands) {

        super(new StatementListGraph(commands));
    }
}
