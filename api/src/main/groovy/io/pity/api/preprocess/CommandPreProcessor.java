package io.pity.api.preprocess;

/**
 * {@link CommandPreProcessor} allow for the execution command to be 'mangled'. This is useful when you have a wrapper
 * that is a wrapper around other programs.
 *
 * You could, for example, take a gradle process and convert it into a ./gradlew command
 */
public interface CommandPreProcessor {

    /**
     * The order that the {@CommandPreProcessor} will be applied.
     * @return an integer, where order of execution is {@link Integer#MAX_VALUE}, {@link Integer#MAX_VALUE} - 1, ...
     */
    int commandPrecedence();

    /**
     * Given a {@link CommandOptions} convert it into a new {@link CommandOptions}
     *
     * @param command previous options
     * @return a new {@link CommandOptions}, the original value, or null. When null is returned,
     *  the previous value will be sued.
     */
    CommandOptions processCommand(CommandOptions command);
}
