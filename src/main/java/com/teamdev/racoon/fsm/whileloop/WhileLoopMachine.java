package com.teamdev.racoon.fsm.whileloop;

import com.teamdev.racoon.fsm.FiniteStateMachine;
import com.teamdev.racoon.fsm.StateAcceptor;
import com.teamdev.racoon.runtime.Command;

import java.util.List;

public class WhileLoopMachine extends FiniteStateMachine {

    public static StateAcceptor acceptor(List<Command> commands) {

        return (inputChain, outputChain) -> new WhileLoopMachine(commands).accept(inputChain, outputChain);
    }

    private WhileLoopMachine(List<Command> commands) {

        super(new ParseWhileLoopGraph(commands));
    }
}
