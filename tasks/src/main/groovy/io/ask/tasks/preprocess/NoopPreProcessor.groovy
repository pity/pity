package io.ask.tasks.preprocess


import io.ask.api.preprocess.CommandOptions
import io.ask.api.preprocess.CommandPreProcessor

class NoopPreProcessor implements CommandPreProcessor{

    @Override
    int commandPrecedence() {
        return Integer.MIN_VALUE
    }

    @Override
    CommandOptions processCommand(CommandOptions command) {
        return null
    }
}
