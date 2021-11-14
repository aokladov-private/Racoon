package com.teamdev.racoon.fsm.calculator;

import com.teamdev.racoon.fsm.CannotAcceptInputChainException;
import com.teamdev.racoon.fsm.FiniteStateMachine;
import com.teamdev.racoon.fsm.InputChain;
import com.teamdev.racoon.fsm.OutputChain;
import com.teamdev.racoon.fsm.StateAcceptor;
import com.teamdev.racoon.runtime.Command;

import java.util.List;

public final class CalculatorMachine extends FiniteStateMachine {

    public static StateAcceptor acceptor(List<Command> commands) {

        return (inputChain, outputChain) -> new CalculatorMachine(commands).accept(inputChain, outputChain);
    }

    private CalculatorMachine(List<Command> commands) {

        super(new CalculatorGraph(commands));
    }
}
