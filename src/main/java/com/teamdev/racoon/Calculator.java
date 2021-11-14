package com.teamdev.racoon;

import com.google.common.base.Preconditions;
import com.teamdev.racoon.fsm.CannotAcceptInputChainException;
import com.teamdev.racoon.fsm.InputChain;
import com.teamdev.racoon.fsm.OutputChain;
import com.teamdev.racoon.fsm.StateAcceptor;
import com.teamdev.racoon.fsm.calculator.CalculatorMachine;
import com.teamdev.racoon.runtime.Command;
import com.teamdev.racoon.runtime.RuntimeEnvironment;
import com.teamdev.racoon.runtime.value.DoubleValueReader;
import com.teamdev.racoon.runtime.value.ValueHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * A service that evaluates math expressions.
 *
 * <p> Math expression may contain numbers, binary operators, brackets, and functions.
 * Numbers are positive or negative, integers or doubles.
 *
 *
 * <p> Implementation is based on the concept of
 * <a href=https://en.wikipedia.org/wiki/Finite-state_machine>Finite State Machine</a>.
 *
 * <p> Typical usage scenarios:
 * {@code
 * double result = new Calculator().evaluate("2+3");
 * }
 */
final class Calculator {

    private static final Logger LOG = LoggerFactory.getLogger(Calculator.class);

    /**
     * Evaluates math expression given.
     *
     * @param mathExpression math expression to evaluate
     * @return result of evaluation
     * @throws InvalidMathExpressionException if math expression contains syntax error
     */
    double evaluate(String mathExpression) throws InvalidMathExpressionException {

        Preconditions.checkNotNull(mathExpression);

        if (LOG.isInfoEnabled()) {

            LOG.info("Evaluating expression '{}'", mathExpression);
        }

        InputChain inputChain = new InputChain(mathExpression);
        OutputChain outputChain = new OutputChain();

        List<Command> commands = new ArrayList<>(16);

        StateAcceptor finiteStateMachine = CalculatorMachine.acceptor(commands);

        try {

            boolean accepted = finiteStateMachine.accept(inputChain, outputChain);

            if (!accepted) {

                raiseInvalidExpressionError("Math expression is not correct.", inputChain);
            }

        } catch (CannotAcceptInputChainException e) {

            raiseInvalidExpressionError(e.getMessage(), inputChain);
        }

        RuntimeEnvironment runtimeEnvironment = new RuntimeEnvironment();

        commands.forEach(command -> command.execute(runtimeEnvironment));

        ValueHolder<?> result = runtimeEnvironment.closeTopStack();

        if (LOG.isInfoEnabled()) {

            LOG.info("Result of evaluation '{}'", result);
        }

        return DoubleValueReader.readValue(result);
    }

    private static void raiseInvalidExpressionError(String message, InputChain inputChain)
            throws InvalidMathExpressionException {

        throw new InvalidMathExpressionException(message, inputChain.readingPosition());
    }
}
