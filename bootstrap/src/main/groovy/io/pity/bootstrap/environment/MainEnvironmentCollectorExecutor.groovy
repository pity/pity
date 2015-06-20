package io.pity.bootstrap.environment


import com.google.inject.Inject
import groovy.util.logging.Slf4j
import io.pity.api.RootCollectorExecutor
import io.pity.bootstrap.provider.InternalCliArgumentProvider

@Slf4j
public class MainEnvironmentCollectorExecutor implements RootCollectorExecutor {

    private final InternalCliArgumentProvider cliArgumentProvider
    private final BootstrapEnvironmentCollector bootstrapEnvironmentCollector

    @Inject
    MainEnvironmentCollectorExecutor(InternalCliArgumentProvider cliArgumentProvider, BootstrapEnvironmentCollector bootstrapEnvironmentCollector) {
        this.bootstrapEnvironmentCollector = bootstrapEnvironmentCollector
        this.cliArgumentProvider = cliArgumentProvider
    }

    public void execute() {
        if (cliArgumentProvider.isEnvironmentCollectionEnabled()) {
            log.debug("Collecting Environmental Data")
            bootstrapEnvironmentCollector.collectEnvironmentData()
        } else {
            log.debug("NOT Collecting Environmental Data")
        }
    }
}
