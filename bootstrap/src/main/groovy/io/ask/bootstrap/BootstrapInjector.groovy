package io.ask.bootstrap
import com.google.inject.AbstractModule
import io.ask.api.PropertyValueProvider
import io.ask.api.WorkingDirectoryProvider
import io.ask.bootstrap.environment.BootstrapEnvironmentCollector
import io.ask.bootstrap.environment.BootstrapEnvironmentCollectorImpl
import io.ask.bootstrap.execution.CommandExecutorRunner
import io.ask.bootstrap.execution.CommandExecutorRunnerImpl
import io.ask.bootstrap.preprocess.PreProcessorExecutor
import io.ask.bootstrap.preprocess.PreProcessorExecutorImpl
import io.ask.bootstrap.provider.WorkingDirectoryProviderImpl

class BootstrapInjector extends AbstractModule {
    private final File workingDirectory
    private final PropertyValueProvider propertyValueProvider

    BootstrapInjector(File workingDirectory, PropertyValueProvider propertyValueProvider){
        this.workingDirectory = workingDirectory
        this.propertyValueProvider = propertyValueProvider
    }

    @Override
    protected void configure() {
        bind(BootstrapEnvironmentCollector.class).to(BootstrapEnvironmentCollectorImpl.class)
        bind(PreProcessorExecutor.class).to(PreProcessorExecutorImpl.class)
        bind(CommandExecutorRunner.class).to(CommandExecutorRunnerImpl.class)

        bind(WorkingDirectoryProvider.class).toInstance(new WorkingDirectoryProviderImpl(workingDirectory))

        bind(PropertyValueProvider.class).toInstance(propertyValueProvider)

    }
}
