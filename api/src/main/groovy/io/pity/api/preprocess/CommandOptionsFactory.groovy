package io.pity.api.preprocess

import io.pity.api.preprocess.internal.CommandOptionsImpl
import org.apache.commons.lang3.StringUtils

/**
 * Creates {@link CommandOptions} from strings.
 */
class CommandOptionsFactory {

    static CommandOptions create(List<String> options){
        def filteredOptions = options.findAll { StringUtils.isNotBlank(it) }
        if(filteredOptions.isEmpty()){
            return null;
        }
        return new CommandOptionsImpl(filteredOptions)
    }

    static CommandOptions create(String[] options){
        return create(Arrays.asList(options))
    }
}
