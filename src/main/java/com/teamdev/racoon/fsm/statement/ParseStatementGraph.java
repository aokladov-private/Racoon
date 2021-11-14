package com.teamdev.racoon.fsm.statement;


import com.teamdev.racoon.fsm.AbstractGraph;
import com.teamdev.racoon.fsm.callable.SystemProcedureMachine;
import com.teamdev.racoon.fsm.conditional.ConditionalMachine;
import com.teamdev.racoon.fsm.util.ResetReadingPosition;
import com.teamdev.racoon.fsm.variable.InitVariableMachine;
import com.teamdev.racoon.fsm.whileloop.WhileLoopMachine;
import com.teamdev.racoon.runtime.Command;

import java.util.List;

import static com.teamdev.racoon.fsm.statement.ParseStatementGraph.State.*;

final class ParseStatementGraph extends AbstractGraph {

    enum State {

        INITIALIZE_VARIABLE,
        SYSTEM_PROCEDURE,
        WHILE_LOOP,
        CONDITION
    }

    ParseStatementGraph(List<Command> commands) {

        registerState(INITIALIZE_VARIABLE,
                ResetReadingPosition.acceptor(InitVariableMachine.acceptor(commands)));

        registerState(SYSTEM_PROCEDURE,
                ResetReadingPosition.acceptor(SystemProcedureMachine.acceptor(commands)));

        registerState(WHILE_LOOP,
                ResetReadingPosition.acceptor(WhileLoopMachine.acceptor(commands)));

        registerState(CONDITION,
                ResetReadingPosition.acceptor(ConditionalMachine.acceptor(commands)));


        setStartStates(INITIALIZE_VARIABLE, SYSTEM_PROCEDURE, WHILE_LOOP, CONDITION);

        setFinishStates(INITIALIZE_VARIABLE, SYSTEM_PROCEDURE, WHILE_LOOP, CONDITION);
    }

}
