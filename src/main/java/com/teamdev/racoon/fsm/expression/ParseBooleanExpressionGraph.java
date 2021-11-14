package com.teamdev.racoon.fsm.expression;

import com.teamdev.racoon.fsm.AbstractGraph;
import com.teamdev.racoon.runtime.Command;
import com.teamdev.racoon.runtime.value.ValueHolder;

import java.util.ArrayList;
import java.util.List;

import static com.teamdev.racoon.fsm.expression.ParseBooleanExpressionGraph.State.BOOLEAN_BINARY_OPERATOR;
import static com.teamdev.racoon.fsm.expression.ParseBooleanExpressionGraph.State.LEFT_EXPRESSION;
import static com.teamdev.racoon.fsm.expression.ParseBooleanExpressionGraph.State.RIGHT_EXPRESSION;

final class ParseBooleanExpressionGraph extends AbstractGraph {

    enum State {

        LEFT_EXPRESSION,
        BOOLEAN_BINARY_OPERATOR,
        RIGHT_EXPRESSION
    }

    ParseBooleanExpressionGraph(List<Command> commands) {

        List<Command> leftExpressionCommands = new ArrayList<>();
        List<Command> rightExpressionCommands = new ArrayList<>();
        List<Command> pushOperatorCommands = new ArrayList<>();

        registerState(LEFT_EXPRESSION, DoubleExpressionMachine.acceptor(leftExpressionCommands),
                BOOLEAN_BINARY_OPERATOR);

        registerState(BOOLEAN_BINARY_OPERATOR, BinaryOperatorAcceptor.booleanAcceptor(pushOperatorCommands),
                RIGHT_EXPRESSION);

        registerState(RIGHT_EXPRESSION, DoubleExpressionMachine.acceptor(rightExpressionCommands));


        setStartStates(LEFT_EXPRESSION);
        setOptionalStates(LEFT_EXPRESSION);

        setFinishStates(RIGHT_EXPRESSION);


        addTransitionListener((inputChain, outputChain) -> commands.add(runtime -> {

            // calculate left expression value
            runtime.newStack();
            leftExpressionCommands.forEach(command -> command.execute(runtime));
            ValueHolder<?> leftOperand = runtime.closeTopStack();

            // calculate right expression value
            runtime.newStack();
            rightExpressionCommands.forEach(command -> command.execute(runtime));
            ValueHolder<?> rightOperand = runtime.closeTopStack();

            // execute boolean binary operator
            runtime.newStack();
            runtime.topStack().push(leftOperand);
            runtime.topStack().push(rightOperand);
            pushOperatorCommands.forEach(command -> command.execute(runtime));
            ValueHolder<?> result = runtime.closeTopStack();

            // push result to the stack
            runtime.topStack().push(result);

        }), RIGHT_EXPRESSION);
    }

}
