package com.teamdev.racoon.fsm.name;

import com.teamdev.racoon.fsm.FiniteStateMachine;
import com.teamdev.racoon.fsm.StateAcceptor;

public final class NameMachine extends FiniteStateMachine {

    public static StateAcceptor acceptor() {

        return new NameMachine();
    }

    private NameMachine() {

        super(new ParseNameGraph(), true);
    }
}
