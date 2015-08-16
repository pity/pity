package io.pity.bootstrap.injection.injectors;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import io.pity.api.PropertyValueProvider;
import io.pity.api.reporting.ReportPublisher;
import io.pity.bootstrap.provider.cli.InternalCliArgumentProvider;
import io.pity.bootstrap.publish.xml.XmlReportPublisher;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * Used using the 'task' phase of execution.
 */
public class TaskInjector {

    private static final Logger log = LoggerFactory.getLogger(TaskInjector.class);

    private final Injector injector;

    public TaskInjector(List<AbstractModule> abstractModules) throws IOException {
        injector = Guice.createInjector(abstractModules);
    }

    public PropertyValueProvider getPropertyValueProvider() {
        return injector.getInstance(PropertyValueProvider.class);
    }

    public Injector getInjector() {
        return injector;
    }

    public <T> T getInstance(Class<T> clazz) {
        return injector.getInstance(clazz);
    }

    public ReportPublisher getReportPublisher() throws ClassNotFoundException, IOException {
        InternalCliArgumentProvider cliArgumentProvider = getInstance(InternalCliArgumentProvider.class);
        String publisherName = getPropertyValueProvider().getProperty("default.publisher");
        if (StringUtils.isEmpty(publisherName)) {
            publisherName = XmlReportPublisher.class.getName();
            log.trace("Could not find publisher, default to {}", publisherName);
        }

        if (cliArgumentProvider.isOverridePublisher()) {
            publisherName = cliArgumentProvider.getOverriddenPublisher();
            log.info("Overriding publisher to {}", publisherName);
        }

        Class publisher = Class.forName(publisherName);
        if (!ReportPublisher.class.isAssignableFrom(publisher)) {
            log.error("Unable to publish results using {}, failing back to XML", publisher);
            publisher = XmlReportPublisher.class;
        }

        log.debug("Publisher class: {}", publisher.getName());
        return (ReportPublisher) getInjector().getInstance(publisher);
    }
}
