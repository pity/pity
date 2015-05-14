package io.ask.bootstrap
import com.google.inject.AbstractModule
import io.ask.api.WorkingDirectoryProvider
import io.ask.bootstrap.environment.BootstrapEnvironmentCollector
import io.ask.bootstrap.environment.BootstrapEnvironmentCollectorImpl
import io.ask.bootstrap.execution.CommandExecutionCollector
import io.ask.bootstrap.execution.CommandExecutionCollectorImpl
import io.ask.bootstrap.execution.CommandPreProcessorTransformer
import io.ask.bootstrap.execution.CommandPreProcessorTransformerImpl
import io.ask.bootstrap.provider.WorkingDirectoryProviderImpl

class BootstrapInjector extends AbstractModule {
    private final File workingDirectory

    BootstrapInjector(File workingDirectory){
        this.workingDirectory = workingDirectory
    }

    @Override
    protected void configure() {
        bind(BootstrapEnvironmentCollector.class).to(BootstrapEnvironmentCollectorImpl.class)
        bind(CommandPreProcessorTransformer.class).to(CommandPreProcessorTransformerImpl.class)
        bind(CommandExecutionCollector.class).to(CommandExecutionCollectorImpl.class)

        bind(WorkingDirectoryProvider.class).toInstance(new WorkingDirectoryProviderImpl(workingDirectory))

    }
}
