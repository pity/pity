package io.ask.bootstrap.injection
import com.google.inject.AbstractModule
import com.google.inject.multibindings.Multibinder
import io.ask.api.PropertyValueProvider
import io.ask.api.RootCollectorExecutor
import io.ask.api.WorkingDirectoryProvider
import io.ask.api.reporting.CollectionResults
import io.ask.api.reporting.ResultCollector
import io.ask.api.reporting.ResultCollectorImpl
import io.ask.bootstrap.RootExecutor
import io.ask.bootstrap.environment.MainEnvironmentCollectorExecutor
import io.ask.bootstrap.execution.MainCommandExecutor
import io.ask.bootstrap.provider.CliArgumentProviderImpl
import io.ask.bootstrap.environment.BootstrapEnvironmentCollector
import io.ask.bootstrap.environment.BootstrapEnvironmentCollectorImpl
import io.ask.bootstrap.execution.CommandExecutorRunner
import io.ask.bootstrap.execution.CommandExecutorRunnerImpl
import io.ask.bootstrap.preprocess.PreProcessorExecutor
import io.ask.bootstrap.preprocess.PreProcessorExecutorImpl
import io.ask.bootstrap.provider.InternalCliArgumentProvider
import io.ask.bootstrap.provider.WorkingDirectoryProviderImpl

class BootstrapInjector extends AbstractModule {
    private final File workingDirectory
    private final PropertyValueProvider propertyValueProvider
    private final InternalCliArgumentProvider cliArgumentProvider

    BootstrapInjector(PropertyValueProvider propertyValueProvider, InternalCliArgumentProvider cliArgumentProvider){
        this.workingDirectory = cliArgumentProvider.getTargetDirectory()
        this.propertyValueProvider = propertyValueProvider
        this.cliArgumentProvider = cliArgumentProvider
    }

    @Override
    protected void configure() {
        bind(BootstrapEnvironmentCollector.class).to(BootstrapEnvironmentCollectorImpl.class)
        bind(PreProcessorExecutor.class).to(PreProcessorExecutorImpl.class)
        bind(CommandExecutorRunner.class).to(CommandExecutorRunnerImpl.class)

        bind(WorkingDirectoryProvider.class).toInstance(new WorkingDirectoryProviderImpl(workingDirectory))

        bind(PropertyValueProvider.class).toInstance(propertyValueProvider)
        bind(InternalCliArgumentProvider.class).toInstance(cliArgumentProvider)

        def reportCollector = new ResultCollectorImpl()
        bind(ResultCollector.class).toInstance(reportCollector)
        bind(CollectionResults.class).toInstance(reportCollector)

        Multibinder<RootCollectorExecutor> envBinder = Multibinder.newSetBinder(binder(), RootCollectorExecutor.class);
        envBinder.addBinding().to(MainEnvironmentCollectorExecutor.class)
        envBinder.addBinding().to(MainCommandExecutor.class)
    }
}
