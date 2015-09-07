package io.pity.bootstrap.environment;

import com.google.inject.Inject;
import io.pity.api.RootCollectorExecutor;
import io.pity.bootstrap.provider.cli.InternalCliArgumentProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainEnvironmentCollectorExecutor implements RootCollectorExecutor {

    private static final Logger logger = LoggerFactory.getLogger(BootstrapEnvironmentCollectorImpl.class);
    private final InternalCliArgumentProvider cliArgumentProvider;
    private final BootstrapEnvironmentCollector bootstrapEnvironmentCollector;

    @Inject
    public MainEnvironmentCollectorExecutor(InternalCliArgumentProvider cliArgumentProvider,
                                            BootstrapEnvironmentCollector bootstrapEnvironmentCollector) {
        this.bootstrapEnvironmentCollector = bootstrapEnvironmentCollector;
        this.cliArgumentProvider = cliArgumentProvider;
    }

    public void execute() {
        if (cliArgumentProvider.isEnvironmentCollectionEnabled()) {
            logger.debug("Collecting Environmental Data");
            bootstrapEnvironmentCollector.collectEnvironmentData();
        } else {
            logger.debug("NOT Collecting Environmental Data");
        }

    }
}
