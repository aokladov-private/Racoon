package com.teamdev.racoon.runtime;

@FunctionalInterface
public interface Command {

    void execute(RuntimeEnvironment runtime);
}
