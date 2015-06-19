package io.ask.bootstrap


import com.google.inject.Injector
import io.ask.api.RootCollectorExecutor
import io.ask.bootstrap.provider.InternalCliArgumentProvider

public abstract class AbstractMainExecutor implements RootCollectorExecutor {
    InternalCliArgumentProvider cliArgumentProvider;
    Injector injector;

    public AbstractMainExecutor(InternalCliArgumentProvider cliArgumentProvider, Injector injector) {
        this.cliArgumentProvider = cliArgumentProvider
        this.injector = injector
    }
}
