package com.teamdev.racoon.fsm.program;

import com.teamdev.racoon.fsm.FiniteStateMachine;
import com.teamdev.racoon.fsm.StateAcceptor;
import com.teamdev.racoon.runtime.Command;

import java.util.List;

public final class ProgramMachine extends FiniteStateMachine {

    public static StateAcceptor acceptor(List<Command> commands) {

        return (inputChain, outputChain) -> new ProgramMachine(commands).accept(inputChain, outputChain);
    }

    private ProgramMachine(List<Command> commands) {

        super(new ProgramGraph(commands));
    }
}
