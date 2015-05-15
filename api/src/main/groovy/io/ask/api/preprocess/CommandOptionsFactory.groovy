package io.ask.api.preprocess

import io.ask.api.preprocess.internal.CommandOptionsImpl

class CommandOptionsFactory {


    static CommandOptions create(String[] options){
        return new CommandOptionsImpl()
    }
}
