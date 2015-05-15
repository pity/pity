package io.ask.api.preprocess;

import javax.annotation.Nonnull;

public interface CommandPreProcessor {

    int commandPrecedence();

    @Nonnull
    CommandOptions processCommand(CommandOptions command);
}
