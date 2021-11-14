package com.teamdev.racoon.fsm.whileloop;

import com.teamdev.racoon.fsm.AbstractGraph;
import com.teamdev.racoon.fsm.expression.BooleanExpressionMachine;
import com.teamdev.racoon.fsm.name.LexemeMachine;
import com.teamdev.racoon.fsm.statement.StatementListMachine;
import com.teamdev.racoon.fsm.util.SpecificCharacter;
import com.teamdev.racoon.runtime.Command;
import com.teamdev.racoon.runtime.RuntimeEnvironment;
import com.teamdev.racoon.runtime.value.BooleanValueReader;

import java.util.ArrayList;
import java.util.List;

import static com.teamdev.racoon.fsm.whileloop.ParseWhileLoopGraph.State.BOOLEAN_EXPRESSION;
import static com.teamdev.racoon.fsm.whileloop.ParseWhileLoopGraph.State.CLOSING_BRACKET;
import static com.teamdev.racoon.fsm.whileloop.ParseWhileLoopGraph.State.CLOSING_CURLY_BRACE;
import static com.teamdev.racoon.fsm.whileloop.ParseWhileLoopGraph.State.OPENING_BRACKET;
import static com.teamdev.racoon.fsm.whileloop.ParseWhileLoopGraph.State.OPENING_CURLY_BRACE;
import static com.teamdev.racoon.fsm.whileloop.ParseWhileLoopGraph.State.STATEMENT_LIST;
import static com.teamdev.racoon.fsm.whileloop.ParseWhileLoopGraph.State.WHILE;

class ParseWhileLoopGraph extends AbstractGraph {

    enum State {

        WHILE,

        OPENING_BRACKET,
        BOOLEAN_EXPRESSION,
        CLOSING_BRACKET,

        OPENING_CURLY_BRACE,
        STATEMENT_LIST,
        CLOSING_CURLY_BRACE
    }

    ParseWhileLoopGraph(List<Command> commands) {

        List<Command> evaluateConditionCommands = new ArrayList<>();
        List<Command> executeBodyCommands = new ArrayList<>();

        registerState(WHILE, LexemeMachine.acceptor(WHILE.name().toLowerCase()), OPENING_BRACKET);

        registerState(OPENING_BRACKET, SpecificCharacter.acceptor('(').andIncrementReadingPosition(),
                BOOLEAN_EXPRESSION);

        registerState(BOOLEAN_EXPRESSION, BooleanExpressionMachine.acceptor(evaluateConditionCommands),
                CLOSING_BRACKET);

        registerState(CLOSING_BRACKET, SpecificCharacter.acceptor(')').andIncrementReadingPosition(),
                OPENING_CURLY_BRACE);


        registerState(OPENING_CURLY_BRACE, SpecificCharacter.acceptor('{').andIncrementReadingPosition(),
                STATEMENT_LIST);

        registerState(STATEMENT_LIST, StatementListMachine.acceptor(executeBodyCommands), CLOSING_CURLY_BRACE);

        registerState(CLOSING_CURLY_BRACE, SpecificCharacter.acceptor('}').andIncrementReadingPosition());


        setStartStates(WHILE);

        setFinishStates(CLOSING_CURLY_BRACE);


        addTransitionListener((inputChain, outputChain) -> commands.add(runtime -> {

            while (evaluateCondition(runtime, evaluateConditionCommands)) {

                executeBodyCommands.forEach(command -> command.execute(runtime));
            }

        }), CLOSING_CURLY_BRACE);
    }

    private static boolean evaluateCondition(RuntimeEnvironment runtime, List<Command> conditionCommands) {

        runtime.newStack();
        conditionCommands.forEach(command -> command.execute(runtime));

        return BooleanValueReader.readValue(runtime.closeTopStack());
    }
}
