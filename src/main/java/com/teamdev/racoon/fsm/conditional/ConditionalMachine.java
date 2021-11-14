package com.teamdev.racoon.fsm.conditional;

import com.teamdev.racoon.fsm.FiniteStateMachine;
import com.teamdev.racoon.fsm.StateAcceptor;
import com.teamdev.racoon.runtime.Command;

import java.util.List;

public class ConditionalMachine extends FiniteStateMachine {
  public static StateAcceptor acceptor(List<Command> commands) {
    return (inputChain, outputChain) -> new ConditionalMachine(commands).accept(inputChain, outputChain);
  }

  private ConditionalMachine(List<Command> commands) {
    super(new ParseConditionalGraph(commands));
  }
}
