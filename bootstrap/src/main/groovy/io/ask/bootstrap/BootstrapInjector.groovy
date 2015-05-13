package io.ask.bootstrap
import com.google.inject.AbstractModule
import io.ask.api.WorkingDirectoryProvider
import io.ask.bootstrap.environment.BootstrapEnvironmentCollector
import io.ask.bootstrap.environment.BootstrapEnvironmentCollectorImpl
import io.ask.bootstrap.provider.WorkingDirectoryProviderImpl

class BootstrapInjector extends AbstractModule {
    private final File workingDirectory

    BootstrapInjector(File workingDirectory){
        this.workingDirectory = workingDirectory
    }

    @Override
    protected void configure() {
        bind(BootstrapEnvironmentCollector.class).to(BootstrapEnvironmentCollectorImpl.class)
        bind(WorkingDirectoryProvider.class).toInstance(new WorkingDirectoryProviderImpl(workingDirectory))
    }
}
