package io.pity.api.preprocess;

public interface CommandPreProcessor {

    int commandPrecedence();

    CommandOptions processCommand(CommandOptions command);
}
