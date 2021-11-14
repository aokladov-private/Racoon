package com.teamdev.racoon.fsm.expression;

import com.teamdev.racoon.fsm.AbstractGraph;
import com.teamdev.racoon.fsm.util.ResetReadingPosition;
import com.teamdev.racoon.runtime.Command;

import java.util.List;

import static com.teamdev.racoon.fsm.expression.ParseExpressionGraph.State.BOOLEAN_EXPRESSION;
import static com.teamdev.racoon.fsm.expression.ParseExpressionGraph.State.DOUBLE_EXPRESSION;

class ParseExpressionGraph extends AbstractGraph {

    enum State {

        BOOLEAN_EXPRESSION,
        DOUBLE_EXPRESSION
    }

    ParseExpressionGraph(List<Command> commands) {

        registerState(BOOLEAN_EXPRESSION, ResetReadingPosition.acceptor(
                BooleanExpressionMachine.acceptor(commands)));

        registerState(DOUBLE_EXPRESSION, ResetReadingPosition.acceptor(
                DoubleExpressionMachine.acceptor(commands)));

        setStartStates(BOOLEAN_EXPRESSION, DOUBLE_EXPRESSION);

        setFinishStates(BOOLEAN_EXPRESSION, DOUBLE_EXPRESSION);
    }
}
