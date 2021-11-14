package com.teamdev.racoon.fsm.expression;

import com.google.common.base.Preconditions;
import com.teamdev.racoon.fsm.CannotAcceptInputChainException;
import com.teamdev.racoon.fsm.InputChain;
import com.teamdev.racoon.fsm.OutputChain;
import com.teamdev.racoon.fsm.StateAcceptor;
import com.teamdev.racoon.fsm.util.TextAcceptor;
import com.teamdev.racoon.runtime.Command;
import com.teamdev.racoon.runtime.operator.BinaryOperatorFactory;
import com.teamdev.racoon.runtime.operator.BooleanBinaryOperatorFactory;
import com.teamdev.racoon.runtime.operator.DoubleBinaryOperatorFactory;
import com.teamdev.racoon.runtime.operator.PrioritizedBinaryOperator;

import java.util.ArrayList;
import java.util.List;

class BinaryOperatorAcceptor implements StateAcceptor {

    static StateAcceptor booleanAcceptor(List<Command> commands) {

        return new BinaryOperatorAcceptor(commands, new BooleanBinaryOperatorFactory());
    }

    static StateAcceptor doubleAcceptor(List<Command> commands) {

        return new BinaryOperatorAcceptor(commands, new DoubleBinaryOperatorFactory());
    }

    private final List<Command> commands;
    private final BinaryOperatorFactory factory;

    private BinaryOperatorAcceptor(List<Command> commands, BinaryOperatorFactory factory) {

        this.commands = Preconditions.checkNotNull(commands);
        this.factory = Preconditions.checkNotNull(factory);
    }

    @Override
    public boolean accept(InputChain inputChain, OutputChain outputChain) throws CannotAcceptInputChainException {


        List<String> operatorSigns = new ArrayList<>(factory.operatorSigns());

        // Sort the list so that the longer items come first.
        // This will allow parsing of 2 symbols operators in first order, e.g. '<=' before '<'
        operatorSigns.sort((o1, o2) -> Integer.compare(o2.length(), o1.length()));

        boolean accepted = TextAcceptor.oneOfAcceptor(operatorSigns).accept(inputChain, outputChain);

        if (accepted) {

            String operatorSign = outputChain.value();
            outputChain.clear();

            PrioritizedBinaryOperator binaryOperator = factory.create(operatorSign);

            commands.add((runtime) -> runtime.topStack().push(binaryOperator));
        }

        return accepted;
    }
}
