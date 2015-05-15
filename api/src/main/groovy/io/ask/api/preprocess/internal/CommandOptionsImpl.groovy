package io.ask.api.preprocess.internal


import io.ask.api.preprocess.CommandOptions

class CommandOptionsImpl implements CommandOptions {

    final private String command
    final private List<String> arguments

    CommandOptionsImpl(List<String> args){
        command = args.remove(0)
        arguments = Collections.unmodifiableList(args)
    }

    @Override
    String getCommand() {
        return command;
    }

    @Override
    List<String> getArguments() {
        return arguments
    }

    @Override
    public String toString() {
        return "CommandOptionsImpl{" + "command='" + command + '\'' + ", arguments=" + arguments + '}';
    }
}
