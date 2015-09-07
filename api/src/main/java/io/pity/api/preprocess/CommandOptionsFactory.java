package io.pity.api.preprocess;

import io.pity.api.preprocess.internal.CommandOptionsImpl;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Creates {@link CommandOptions} from strings.
 */
public class CommandOptionsFactory {
    public static CommandOptions create(List<String> options) {
        List<String> filteredOptions = options.stream().filter(StringUtils::isNotBlank).collect(Collectors.toList());
        if (filteredOptions.isEmpty()) {
            return null;
        }

        return new CommandOptionsImpl(filteredOptions);
    }

    public static CommandOptions create(String[] options) {
        return create(Arrays.asList(options));
    }

}
