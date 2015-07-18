package io.pity.bootstrap.environment
import com.google.inject.Inject
import groovy.transform.CompileStatic
import io.pity.api.reporting.ResultCollector
import io.pity.bootstrap.provider.container.EnvironmentCollectorContainer
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@CompileStatic
class BootstrapEnvironmentCollectorImpl implements BootstrapEnvironmentCollector {

    private static final Logger logger = LoggerFactory.getLogger(BootstrapEnvironmentCollectorImpl.class)
    EnvironmentCollectorContainer environmentCollectorContainer
    private final ResultCollector resultCollector

    @Inject
    BootstrapEnvironmentCollectorImpl(EnvironmentCollectorContainer environmentCollectorContainer,
                                      ResultCollector resultCollector) {
        this.resultCollector = resultCollector
        this.environmentCollectorContainer = environmentCollectorContainer
    }

    @Override
    void collectEnvironmentData() {
        environmentCollectorContainer.getAvailable().collect {
            logger.debug("Getting results from {}", it.getClass().getSimpleName())
            resultCollector.collect(it.collectEnvironmentData())
        }
    }
}
