package io.pity.bootstrap.injection.injectors;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import io.pity.api.PropertyValueProvider;
import io.pity.api.reporting.ReportPublisher;
import io.pity.bootstrap.publish.html.HtmlReportPublisher;

import java.io.IOException;
import java.util.List;

/**
 * Used using the 'task' phase of execution.
 */
public class TaskInjector {

    public static final Class<HtmlReportPublisher> DEFAULT_PUBLISHER = HtmlReportPublisher.class;

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
        return getInjector().getInstance(clazz);
    }

    public ReportPublisher getReportPublisher() {
        return getInstance(ReportPublisher.class);
    }
}
