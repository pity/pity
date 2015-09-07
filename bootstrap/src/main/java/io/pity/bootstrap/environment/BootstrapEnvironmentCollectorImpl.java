package io.pity.bootstrap.environment;

import com.google.inject.Inject;
import groovy.transform.CompileStatic;
import io.pity.api.reporting.ResultCollector;
import io.pity.bootstrap.provider.container.EnvironmentCollectorContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CompileStatic
public class BootstrapEnvironmentCollectorImpl implements BootstrapEnvironmentCollector {

    private static final Logger logger = LoggerFactory.getLogger(BootstrapEnvironmentCollectorImpl.class);
    private final EnvironmentCollectorContainer environmentCollectorContainer;
    private final ResultCollector resultCollector;

    @Inject
    public BootstrapEnvironmentCollectorImpl(EnvironmentCollectorContainer environmentCollectorContainer,
                                             ResultCollector resultCollector) {
        this.resultCollector = resultCollector;
        this.environmentCollectorContainer = environmentCollectorContainer;
    }

    @Override
    public void collectEnvironmentData() {
        environmentCollectorContainer.getAvailable().forEach(it -> {
            logger.debug("Getting results from {}", it.getClass().getSimpleName());
            resultCollector.collect(it.collectEnvironmentData());
        });
    }
}
