package io.ask.bootstrap.environment

import com.google.inject.Inject
import io.ask.api.EnvironmentCollector
import io.ask.api.EnvironmentData
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class BootstrapEnvironmentCollectorImpl implements BootstrapEnvironmentCollector {

    private static final Logger logger = LoggerFactory.getLogger(BootstrapEnvironmentCollectorImpl.class)
    Set<EnvironmentCollector> environmentCollectors

    @Inject
    BootstrapEnvironmentCollectorImpl(Set<EnvironmentCollector> environmentCollectors) {
        this.environmentCollectors = environmentCollectors
    }

    @Override
    Set<EnvironmentData> collectEnvironmentData() {
        environmentCollectors.findAll { it }
        return environmentCollectors.collect {
            logger.debug("Getting results from {}", it.getClass().getSimpleName())
            return it.collectEnvironmentData()
        }
    }
}
