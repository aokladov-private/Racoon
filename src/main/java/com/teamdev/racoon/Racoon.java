package com.teamdev.racoon;

import com.google.common.base.Preconditions;
import com.teamdev.racoon.fsm.CannotAcceptInputChainException;
import com.teamdev.racoon.fsm.InputChain;
import com.teamdev.racoon.fsm.OutputChain;
import com.teamdev.racoon.fsm.StateAcceptor;
import com.teamdev.racoon.fsm.program.ProgramMachine;
import com.teamdev.racoon.runtime.Command;
import com.teamdev.racoon.runtime.RuntimeEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * An interpreter for a program written on the Racoon programming language.
 *
 * <p> Program consists of list of statements. Statements are: initialize variable,
 * call system procedure like print(), some loop statements, and condition statements.
 *
 * <p> Implementation is based on a concept of
 * <a href=https://en.wikipedia.org/wiki/Finite-state_machine>Finite State Machine</a>.
 */
final class Racoon {

    private static final Logger LOG = LoggerFactory.getLogger(Racoon.class);

    /**
     * Executes the program given.
     *
     * @param program program to execute
     * @return output of the program
     * @throws RacoonExecutionException if program contains syntax error
     */
    String execute(String program) throws RacoonExecutionException {

        Preconditions.checkNotNull(program);

        if (LOG.isInfoEnabled()) {

            LOG.info("Executing program '{}'", program);
        }

        InputChain inputChain = new InputChain(program);
        OutputChain outputChain = new OutputChain();

        List<Command> commands = new ArrayList<>(32);

        StateAcceptor finiteStateMachine = ProgramMachine.acceptor(commands);

        try {

            boolean accepted = finiteStateMachine.accept(inputChain, outputChain);

            if (!accepted) {

                raiseRacoonExecutionException("Program is not correct.", inputChain);
            }

        } catch (CannotAcceptInputChainException e) {

            LOG.error("Error while compiling program.", e);

            raiseRacoonExecutionException(e.getMessage(), inputChain);

        }


        RuntimeEnvironment runtimeEnvironment = new RuntimeEnvironment();

        try {

            commands.forEach(command -> command.execute(runtimeEnvironment));

        } catch (RuntimeException e) {

            LOG.error("Error while executing program.", e);

            raiseRacoonExecutionException(e.getMessage(), inputChain);
        }

        String output = runtimeEnvironment.programOutput();

        if (LOG.isInfoEnabled()) {

            LOG.info("Program executed successfully.");
        }

        if (LOG.isDebugEnabled()) {

            LOG.debug("Program output:" + System.lineSeparator() + output);
        }

        return output;
    }

    private static void raiseRacoonExecutionException(String message, InputChain inputChain)
            throws RacoonExecutionException {

        throw new RacoonExecutionException(message, inputChain.readingPosition());
    }
}
