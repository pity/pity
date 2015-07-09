package io.pity.bootstrap.injection.injectors;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import io.pity.api.PropertyValueProvider;

import java.io.IOException;
import java.util.List;

public class TaskInjector {
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
}
