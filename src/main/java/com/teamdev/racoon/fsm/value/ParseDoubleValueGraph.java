package com.teamdev.racoon.fsm.value;


import com.teamdev.racoon.fsm.AbstractGraph;
import com.teamdev.racoon.fsm.brackets.BracketsMachine;
import com.teamdev.racoon.fsm.callable.MathFunctionMachine;
import com.teamdev.racoon.fsm.number.NumberMachine;
import com.teamdev.racoon.fsm.util.ResetReadingPosition;
import com.teamdev.racoon.fsm.variable.ReadVariableAcceptor;
import com.teamdev.racoon.runtime.Command;

import java.util.List;

import static com.teamdev.racoon.fsm.value.ParseDoubleValueGraph.State.BRACKETS;
import static com.teamdev.racoon.fsm.value.ParseDoubleValueGraph.State.FUNCTION;
import static com.teamdev.racoon.fsm.value.ParseDoubleValueGraph.State.NUMBER;
import static com.teamdev.racoon.fsm.value.ParseDoubleValueGraph.State.READ_VARIABLE;

final class ParseDoubleValueGraph extends AbstractGraph {

    enum State {

        NUMBER,
        BRACKETS,
        FUNCTION,
        READ_VARIABLE
    }

    ParseDoubleValueGraph(List<Command> commands) {


        registerState(NUMBER, NumberMachine.acceptor(commands));

        registerState(BRACKETS, BracketsMachine.acceptor(commands));

        registerState(FUNCTION, ResetReadingPosition.acceptor(MathFunctionMachine.acceptor(commands)));

        registerState(READ_VARIABLE, ResetReadingPosition.acceptor(ReadVariableAcceptor.acceptor(commands)));


        setStartStates(NUMBER, BRACKETS, FUNCTION, READ_VARIABLE);

        setFinishStates(NUMBER, BRACKETS, FUNCTION, READ_VARIABLE);
    }

}
