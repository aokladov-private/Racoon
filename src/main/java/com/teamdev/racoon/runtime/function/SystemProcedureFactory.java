package com.teamdev.racoon.runtime.function;

import com.teamdev.racoon.runtime.RuntimeEnvironment;
import com.teamdev.racoon.runtime.value.ValueHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class SystemProcedureFactory {

    private final Map<String, ProcedureCreator> creators = new HashMap<>();

    public SystemProcedureFactory() {

        creators.put("print", () -> new SystemProcedure() {

            @Override
            public void execute(RuntimeEnvironment runtime, List<ValueHolder<?>> arguments) {

                arguments.forEach(argument -> runtime.printStream().println(argument));
            }

            @Override
            public ArgumentsValidator getValidator() {
                return ArgumentsValidator.oneAndMore();
            }
        });
    }

    public SystemProcedure create(String procedureName) {

        ProcedureCreator creator = creators.get(procedureName);

        if (creator == null) {

            throw new IllegalArgumentException("System procedure not supported '%s'".formatted(procedureName));
        }

        return creator.create();
    }

    public boolean isProcedure(String procedureName) {

        return creators.containsKey(procedureName);
    }

    @FunctionalInterface
    interface ProcedureCreator {

        SystemProcedure create();
    }
}
