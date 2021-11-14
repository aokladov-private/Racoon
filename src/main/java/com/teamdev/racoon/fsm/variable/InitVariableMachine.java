package com.teamdev.racoon.fsm.variable;

import com.teamdev.racoon.fsm.FiniteStateMachine;
import com.teamdev.racoon.fsm.StateAcceptor;
import com.teamdev.racoon.runtime.Command;

import java.util.List;

public final class InitVariableMachine extends FiniteStateMachine {

    public static StateAcceptor acceptor(List<Command> commands) {

        return (inputChain, outputChain) -> new InitVariableMachine(commands).accept(inputChain, outputChain);

    }

    private InitVariableMachine(List<Command> commands) {

        super(new InitVariableGraph(commands));
    }
}
