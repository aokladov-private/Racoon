package com.teamdev.racoon.fsm.program;


import com.teamdev.racoon.fsm.AbstractGraph;
import com.teamdev.racoon.fsm.statement.StatementListMachine;
import com.teamdev.racoon.runtime.Command;

import java.util.List;

import static com.teamdev.racoon.fsm.program.ProgramGraph.State.END_OF_PROGRAM;
import static com.teamdev.racoon.fsm.program.ProgramGraph.State.LIST_OF_STATEMENTS;


final class ProgramGraph extends AbstractGraph {

    enum State {

        LIST_OF_STATEMENTS,
        END_OF_PROGRAM
    }

    ProgramGraph(List<Command> commands) {

        registerState(LIST_OF_STATEMENTS, StatementListMachine.acceptor(commands), END_OF_PROGRAM);

        registerState(END_OF_PROGRAM, (inputChain, outputChain) -> !inputChain.hasMoreChars());

        setStartStates(LIST_OF_STATEMENTS);

        setFinishStates(END_OF_PROGRAM);
    }

}
