package io.ask.bootstrap.environment

import com.google.inject.Inject
import io.ask.api.environment.EnvironmentCollector
import io.ask.api.reporting.ResultCollector
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class BootstrapEnvironmentCollectorImpl implements BootstrapEnvironmentCollector {

    private static final Logger logger = LoggerFactory.getLogger(BootstrapEnvironmentCollectorImpl.class)
    Set<EnvironmentCollector> environmentCollectors
    private final ResultCollector resultCollector

    @Inject
    BootstrapEnvironmentCollectorImpl(Set<EnvironmentCollector> environmentCollectors, ResultCollector resultCollector) {
        this.resultCollector = resultCollector
        this.environmentCollectors = environmentCollectors
    }

    @Override
    void collectEnvironmentData() {
        environmentCollectors.findAll { it }
        environmentCollectors.collect {
            logger.debug("Getting results from {}", it.getClass().getSimpleName())
            resultCollector.collect(it.collectEnvironmentData())
        }
    }
}
