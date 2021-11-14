package com.teamdev.racoon.fsm.conditional;

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
import java.util.Locale;

import static com.teamdev.racoon.fsm.conditional.ParseConditionalGraph.State.IF;
import static com.teamdev.racoon.fsm.conditional.ParseConditionalGraph.State.OPENING_BRACKET;
import static com.teamdev.racoon.fsm.conditional.ParseConditionalGraph.State.BOOLEAN_EXPRESSION;
import static com.teamdev.racoon.fsm.conditional.ParseConditionalGraph.State.CLOSING_BRACKET;
import static com.teamdev.racoon.fsm.conditional.ParseConditionalGraph.State.OPENING_CURLY_BRACE;
import static com.teamdev.racoon.fsm.conditional.ParseConditionalGraph.State.STATEMENT_LIST;
import static com.teamdev.racoon.fsm.conditional.ParseConditionalGraph.State.CLOSING_CURLY_BRACE;
import static com.teamdev.racoon.fsm.conditional.ParseConditionalGraph.State.ELSE;
import static com.teamdev.racoon.fsm.conditional.ParseConditionalGraph.State.NEGATIVE_OPENING_CURLY_BRACE;
import static com.teamdev.racoon.fsm.conditional.ParseConditionalGraph.State.NEGATIVE_STATEMENT_LIST;
import static com.teamdev.racoon.fsm.conditional.ParseConditionalGraph.State.NEGATIVE_CLOSING_CURLY_BRACE;

public class ParseConditionalGraph extends AbstractGraph {
  enum State {

    IF,

    OPENING_BRACKET,
    BOOLEAN_EXPRESSION,
    CLOSING_BRACKET,

    OPENING_CURLY_BRACE,
    STATEMENT_LIST,
    CLOSING_CURLY_BRACE,


    ELSE,
    NEGATIVE_OPENING_CURLY_BRACE,
    NEGATIVE_STATEMENT_LIST,
    NEGATIVE_CLOSING_CURLY_BRACE,
  }

  ParseConditionalGraph(List<Command> commands) {

    List<Command> evaluateConditionCommands = new ArrayList<>();
    List<Command> executePositiveBodyCommands = new ArrayList<>();
    List<Command> executeNegativeBodyCommands = new ArrayList<>();

    registerState(IF, LexemeMachine.acceptor(IF.name().toLowerCase()), OPENING_BRACKET);

    registerState(OPENING_BRACKET, SpecificCharacter.acceptor('(').andIncrementReadingPosition(),
        BOOLEAN_EXPRESSION);
    registerState(BOOLEAN_EXPRESSION, BooleanExpressionMachine.acceptor(evaluateConditionCommands),
        CLOSING_BRACKET);
    registerState(CLOSING_BRACKET, SpecificCharacter.acceptor(')').andIncrementReadingPosition(),
        OPENING_CURLY_BRACE);

    registerState(OPENING_CURLY_BRACE, SpecificCharacter.acceptor('{').andIncrementReadingPosition(),
        STATEMENT_LIST);
    registerState(STATEMENT_LIST, StatementListMachine.acceptor(executePositiveBodyCommands), CLOSING_CURLY_BRACE);
    registerState(CLOSING_CURLY_BRACE, SpecificCharacter.acceptor('}').andIncrementReadingPosition());

    registerState(CLOSING_CURLY_BRACE, SpecificCharacter.acceptor('}').andIncrementReadingPosition(), ELSE);
    registerState(ELSE, LexemeMachine.acceptor(ELSE.name().toLowerCase()), NEGATIVE_OPENING_CURLY_BRACE);
    registerState(NEGATIVE_OPENING_CURLY_BRACE, SpecificCharacter.acceptor('{').andIncrementReadingPosition(),
        NEGATIVE_STATEMENT_LIST);
    registerState(NEGATIVE_STATEMENT_LIST, StatementListMachine.acceptor(executeNegativeBodyCommands), NEGATIVE_CLOSING_CURLY_BRACE);
    registerState(NEGATIVE_CLOSING_CURLY_BRACE, SpecificCharacter.acceptor('}').andIncrementReadingPosition());

    setStartStates(IF);
    setFinishStates(CLOSING_CURLY_BRACE);
    setFinishStates(NEGATIVE_CLOSING_CURLY_BRACE);


    addTransitionListener((inputChain, outputChain) -> commands.add(runtime -> {
      if (evaluateCondition(runtime, evaluateConditionCommands)) {
        executePositiveBodyCommands.forEach(command -> command.execute(runtime));
      }
    }), CLOSING_CURLY_BRACE);

    addTransitionListener((inputChain, outputChain) -> commands.add(runtime -> {
      if (!evaluateCondition(runtime, evaluateConditionCommands)) {
        executeNegativeBodyCommands.forEach(command -> command.execute(runtime));
      }
    }), NEGATIVE_CLOSING_CURLY_BRACE);
  }

  private static boolean evaluateCondition(RuntimeEnvironment runtime, List<Command> conditionCommands) {

    runtime.newStack();
    conditionCommands.forEach(command -> command.execute(runtime));

    return BooleanValueReader.readValue(runtime.closeTopStack());
  }
}
