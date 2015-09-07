package io.pity.api;

/**
 * This will gracefully stop the collection before any data is collected.
 */
public class StopExecutionException extends Exception {
    /**
     * Providing a reason for the exception is helpful for the user, thus required.
     * @param message why the execution was stopped
     */
    public StopExecutionException(String message) {
        super(message);
    }
}
