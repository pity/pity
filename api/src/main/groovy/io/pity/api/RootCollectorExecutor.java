package io.pity.api;

/**
 * This will allow plugins to create 'root' collectors. This is useful if you want to collect different data than is
 * normally consumed.
 */
public interface RootCollectorExecutor {

    /**
     * Called to collect data.
     */
    void execute();
}
