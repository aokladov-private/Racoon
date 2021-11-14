package com.teamdev.racoon.runtime.function;

import com.teamdev.racoon.runtime.RuntimeEnvironment;
import com.teamdev.racoon.runtime.value.ValueHolder;

import java.util.List;

@FunctionalInterface
public interface SystemProcedure {

    void execute(RuntimeEnvironment runtime, List<ValueHolder<?>> arguments);

    default ArgumentsValidator getValidator() {

        return arguments -> true;
    }
}
