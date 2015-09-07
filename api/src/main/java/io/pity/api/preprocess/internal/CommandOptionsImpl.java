package io.pity.api.preprocess.internal;

import io.pity.api.preprocess.CommandOptions;

import java.util.Collections;
import java.util.List;

public class CommandOptionsImpl implements CommandOptions {

    private final String command;
    private final List<String> arguments;

    public CommandOptionsImpl(List<String> args) {
        command = args.remove(0);
        arguments = Collections.unmodifiableList(args);
    }

    @Override
    public String getCommand() {
        return command;
    }

    @Override
    public List<String> getArguments() {
        return arguments;
    }

    @Override
    public String toString() {
        return "CommandOptionsImpl{" + "command='" + command + "\'" + ", arguments=" + arguments + "}";
    }
}
