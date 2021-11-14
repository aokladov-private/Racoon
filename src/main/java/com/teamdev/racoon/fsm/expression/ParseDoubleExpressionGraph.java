package com.teamdev.racoon.fsm.expression;

import com.teamdev.racoon.fsm.AbstractGraph;
import com.teamdev.racoon.fsm.value.ValueMachine;
import com.teamdev.racoon.runtime.Command;

import java.util.List;

import static com.teamdev.racoon.fsm.expression.ParseDoubleExpressionGraph.State.DOUBLE_BINARY_OPERATOR;
import static com.teamdev.racoon.fsm.expression.ParseDoubleExpressionGraph.State.DOUBLE_VALUE;

final class ParseDoubleExpressionGraph extends AbstractGraph {

    enum State {

        DOUBLE_VALUE,
        DOUBLE_BINARY_OPERATOR
    }

    ParseDoubleExpressionGraph(List<Command> commands) {

        registerState(DOUBLE_VALUE, ValueMachine.acceptor(commands), DOUBLE_BINARY_OPERATOR);

        registerState(DOUBLE_BINARY_OPERATOR, BinaryOperatorAcceptor.doubleAcceptor(commands), DOUBLE_VALUE);

        setStartStates(DOUBLE_VALUE);

        setFinishStates(DOUBLE_VALUE);
    }

}
