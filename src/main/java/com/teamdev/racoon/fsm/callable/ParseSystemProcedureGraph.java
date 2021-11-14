package com.teamdev.racoon.fsm.callable;

import com.teamdev.racoon.runtime.Command;
import com.teamdev.racoon.runtime.function.ArgumentsValidator;
import com.teamdev.racoon.runtime.function.SystemProcedure;
import com.teamdev.racoon.runtime.function.SystemProcedureFactory;
import com.teamdev.racoon.runtime.value.ValueHolder;

import java.util.List;

final class ParseSystemProcedureGraph extends ParseCallableGraph {

    private final SystemProcedureFactory factory = new SystemProcedureFactory();

    ParseSystemProcedureGraph(List<Command> commands) {
        super(commands);
    }

    @Override
    protected boolean isNameAcceptable(String name) {

        return factory.isProcedure(name);
    }

    @Override
    protected Command createCommand(String callableName, List<List<Command>> calculateArgumentsCommands) {

        return runtime -> {

            List<ValueHolder<?>> arguments = calculateArguments(runtime, calculateArgumentsCommands);

            SystemProcedure systemProcedure = factory.create(callableName);

            systemProcedure.execute(runtime, arguments);
        };
    }

    @Override
    protected ArgumentsValidator getValidator(String callableName) {

        return factory.create(callableName).getValidator();
    }

}
