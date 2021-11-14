package com.teamdev.racoon.fsm.calculator;


import com.teamdev.racoon.fsm.AbstractGraph;
import com.teamdev.racoon.fsm.expression.DoubleExpressionMachine;
import com.teamdev.racoon.runtime.Command;

import java.util.List;

import static com.teamdev.racoon.fsm.calculator.CalculatorGraph.State.END_OF_EXPRESSION;
import static com.teamdev.racoon.fsm.calculator.CalculatorGraph.State.EXPRESSION;

final class CalculatorGraph extends AbstractGraph {

    enum State {

        EXPRESSION,
        END_OF_EXPRESSION
    }

    CalculatorGraph(List<Command> commands) {

        registerState(EXPRESSION, DoubleExpressionMachine.acceptor(commands), END_OF_EXPRESSION);

        registerState(END_OF_EXPRESSION, (inputChain, outputChain) -> !inputChain.hasMoreChars());

        setStartStates(EXPRESSION);

        setFinishStates(END_OF_EXPRESSION);
    }

}
