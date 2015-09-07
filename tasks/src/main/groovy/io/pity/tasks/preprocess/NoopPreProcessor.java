package io.pity.tasks.preprocess;

import io.pity.api.preprocess.CommandOptions;
import io.pity.api.preprocess.CommandPreProcessor;

public class NoopPreProcessor implements CommandPreProcessor {

    @Override
    public int commandPrecedence() {
        return Integer.MIN_VALUE;
    }

    @Override
    public CommandOptions processCommand(CommandOptions command) {
        return null;
    }
}
