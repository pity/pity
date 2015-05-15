package io.ask.api.preprocess

import io.ask.api.preprocess.internal.CommandOptionsImpl
import org.apache.commons.lang3.StringUtils

class CommandOptionsFactory {


    static CommandOptions create(List<String> options){
        def filteredOptions = options.findAll { StringUtils.isNotBlank(it) }
        if(filteredOptions.isEmpty()){
            return null;
        }
        return new CommandOptionsImpl(filteredOptions)
    }
}
