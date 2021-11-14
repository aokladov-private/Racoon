package com.teamdev.racoon.fsm.brackets;


import com.teamdev.racoon.fsm.AbstractGraph;
import com.teamdev.racoon.fsm.expression.ExpressionMachine;
import com.teamdev.racoon.fsm.util.SpecificCharacter;
import com.teamdev.racoon.runtime.Command;

import java.util.List;

import static com.teamdev.racoon.fsm.brackets.ParseBracketsGraph.State.CLOSING_BRACKET;
import static com.teamdev.racoon.fsm.brackets.ParseBracketsGraph.State.EXPRESSION;
import static com.teamdev.racoon.fsm.brackets.ParseBracketsGraph.State.OPENING_BRACKET;

final class ParseBracketsGraph extends AbstractGraph {

    enum State {

        OPENING_BRACKET,
        EXPRESSION,
        CLOSING_BRACKET
    }

    ParseBracketsGraph(List<Command> commands) {

        registerState(OPENING_BRACKET, SpecificCharacter.acceptor('(').andIncrementReadingPosition(), EXPRESSION);

        registerState(EXPRESSION, ExpressionMachine.acceptor(commands), CLOSING_BRACKET);

        registerState(CLOSING_BRACKET, SpecificCharacter.acceptor(')').andIncrementReadingPosition());

        setStartStates(OPENING_BRACKET);

        setFinishStates(CLOSING_BRACKET);

    }

}
