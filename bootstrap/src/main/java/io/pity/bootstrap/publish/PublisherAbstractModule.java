package io.pity.bootstrap.publish;

import com.google.inject.AbstractModule;
import io.pity.api.PropertyValueProvider;
import io.pity.api.reporting.ReportPublisher;
import io.pity.bootstrap.injection.injectors.TaskInjector;
import io.pity.bootstrap.provider.cli.InternalCliArgumentProvider;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class PublisherAbstractModule extends AbstractModule {
    private static final Logger log = LoggerFactory.getLogger(PublisherAbstractModule.class);

    private final PropertyValueProvider propertyValueProvider;
    private final InternalCliArgumentProvider cliArgumentProvider;

    public PublisherAbstractModule(PropertyValueProvider propertyValueProvider, InternalCliArgumentProvider cliArgumentProvider) {
        this.propertyValueProvider = propertyValueProvider;
        this.cliArgumentProvider = cliArgumentProvider;
    }

    @Override
    protected void configure() {
        bind(ReportPublisher.class).to(safeFindReporter());
    }

    private Class<? extends ReportPublisher> safeFindReporter() {
        try {
            return findReporter();
        } catch (ClassNotFoundException e) {
            log.error("Unable to setup reporter, using default", e);
        } catch (IOException e) {
            log.error("Unable to setup reporter, using default", e);
            e.printStackTrace();
        } return TaskInjector.DEFAULT_PUBLISHER;
    }

    private Class findReporter() throws ClassNotFoundException, IOException {
        String publisherName = propertyValueProvider.getProperty("default.publisher");
        if (StringUtils.isEmpty(publisherName)) {
            publisherName = TaskInjector.DEFAULT_PUBLISHER.getName();
            log.trace("Could not find publisher, default to {}", publisherName);
        }

        if (cliArgumentProvider.isOverridePublisher()) {
            publisherName = cliArgumentProvider.getOverriddenPublisher();
            log.info("Overriding publisher to {}", publisherName);
        }

        Class publisher = Class.forName(publisherName);
        if (!ReportPublisher.class.isAssignableFrom(publisher)) {
            log.error("Unable to publish results using {}, failing back to HTML", publisher);
            publisher = TaskInjector.DEFAULT_PUBLISHER;
        }

        return publisher;
    }
}
