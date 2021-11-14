package com.teamdev.racoon.fsm.statement;


import com.teamdev.racoon.fsm.AbstractGraph;
import com.teamdev.racoon.fsm.util.SpecificCharacter;
import com.teamdev.racoon.runtime.Command;

import java.util.List;

import static com.teamdev.racoon.fsm.statement.StatementListGraph.State.STATEMENT;
import static com.teamdev.racoon.fsm.statement.StatementListGraph.State.STATEMENT_SEPARATOR;


final class StatementListGraph extends AbstractGraph {

    enum State {

        STATEMENT,
        STATEMENT_SEPARATOR
    }

    StatementListGraph(List<Command> commands) {

        registerState(STATEMENT, StatementMachine.acceptor(commands), STATEMENT_SEPARATOR);

        registerState(STATEMENT_SEPARATOR, SpecificCharacter.acceptor(';').andIncrementReadingPosition(), STATEMENT);

        setStartStates(STATEMENT);

        setFinishStates(STATEMENT_SEPARATOR);

    }

}
