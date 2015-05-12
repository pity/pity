package io.ask.bootstrap
import com.google.inject.AbstractModule
import io.ask.bootstrap.environment.BootstrapEnvironmentCollector
import io.ask.bootstrap.environment.BootstrapEnvironmentCollectorImpl

class BootstrapInjector extends AbstractModule {
    @Override
    protected void configure() {
        bind(BootstrapEnvironmentCollector.class).to(BootstrapEnvironmentCollectorImpl.class)
    }
}
