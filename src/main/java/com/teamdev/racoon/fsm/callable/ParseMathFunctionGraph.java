package com.teamdev.racoon.fsm.callable;


import com.teamdev.racoon.runtime.Command;
import com.teamdev.racoon.runtime.function.ArgumentsValidator;
import com.teamdev.racoon.runtime.function.MathFunctionFactory;
import com.teamdev.racoon.runtime.function.MathFunction;
import com.teamdev.racoon.runtime.value.ValueHolder;

import java.util.List;

final class ParseMathFunctionGraph extends ParseCallableGraph {

    private final MathFunctionFactory factory = new MathFunctionFactory();

    ParseMathFunctionGraph(List<Command> commands) {
        super(commands);
    }

    @Override
    protected boolean isNameAcceptable(String callableName) {

        return factory.isFunction(callableName);
    }

    @Override
    protected Command createCommand(String callableName, List<List<Command>> calculateArgumentsCommands) {

        return runtime -> {

            List<ValueHolder<?>> arguments = calculateArguments(runtime, calculateArgumentsCommands);

            MathFunction function = factory.create(callableName);

            runtime.topStack().push(function.apply(arguments));
        };
    }

    @Override
    protected ArgumentsValidator getValidator(String callableName) {

        return factory.create(callableName).getValidator();
    }

}
