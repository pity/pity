package io.pity.tasks.preprocess


import io.pity.api.preprocess.CommandOptions
import io.pity.api.preprocess.CommandPreProcessor

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
