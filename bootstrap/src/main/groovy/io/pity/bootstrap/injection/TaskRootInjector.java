package io.pity.bootstrap.injection;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import io.pity.api.PropertyValueProvider;
import io.pity.api.RootCollectorExecutor;
import io.pity.api.WorkingDirectoryProvider;
import io.pity.api.cli.CliArgumentProvider;
import io.pity.api.reporting.CollectionResults;
import io.pity.api.reporting.ResultCollector;
import io.pity.bootstrap.ResultCollectorImpl;
import io.pity.bootstrap.environment.BootstrapEnvironmentCollector;
import io.pity.bootstrap.environment.BootstrapEnvironmentCollectorImpl;
import io.pity.bootstrap.environment.MainEnvironmentCollectorExecutor;
import io.pity.bootstrap.execution.CommandExecutorRunner;
import io.pity.bootstrap.execution.CommandExecutorRunnerImpl;
import io.pity.bootstrap.execution.MainCommandExecutor;
import io.pity.bootstrap.preprocess.PreProcessorExecutor;
import io.pity.bootstrap.preprocess.PreProcessorExecutorImpl;
import io.pity.bootstrap.provider.WorkingDirectoryProviderImpl;
import io.pity.bootstrap.provider.cli.InternalCliArgumentProvider;
import io.pity.bootstrap.provider.container.CommandExecutorContainer;
import io.pity.bootstrap.provider.container.CommandExecutorContainerImpl;
import io.pity.bootstrap.provider.container.EnvironmentCollectorContainer;
import io.pity.bootstrap.provider.container.EnvironmentCollectorContainerImpl;

import java.io.File;

/**
 * Used to inject the task work.
 */
public class TaskRootInjector extends AbstractModule {

    private final File workingDirectory;
    private final PropertyValueProvider propertyValueProvider;
    private final InternalCliArgumentProvider cliArgumentProvider;

    public TaskRootInjector(PropertyValueProvider propertyValueProvider, InternalCliArgumentProvider cliArgumentProvider) {
        this.workingDirectory = cliArgumentProvider.getTargetDirectory();
        this.propertyValueProvider = propertyValueProvider;
        this.cliArgumentProvider = cliArgumentProvider;
    }

    @Override
    protected void configure() {
        bind(BootstrapEnvironmentCollector.class).to(BootstrapEnvironmentCollectorImpl.class);
        bind(PreProcessorExecutor.class).to(PreProcessorExecutorImpl.class);
        bind(CommandExecutorRunner.class).to(CommandExecutorRunnerImpl.class);
        bind(CommandExecutorContainer.class).to(CommandExecutorContainerImpl.class);
        bind(EnvironmentCollectorContainer.class).to(EnvironmentCollectorContainerImpl.class);

        bind(WorkingDirectoryProvider.class).toInstance(new WorkingDirectoryProviderImpl(workingDirectory));

        bind(PropertyValueProvider.class).toInstance(propertyValueProvider);
        bind(InternalCliArgumentProvider.class).toInstance(cliArgumentProvider);
        bind(CliArgumentProvider.class).toInstance(cliArgumentProvider);

        ResultCollectorImpl reportCollector = new ResultCollectorImpl();
        bind(ResultCollector.class).toInstance(reportCollector);
        bind(CollectionResults.class).toInstance(reportCollector);

        Multibinder<RootCollectorExecutor> envBinder = Multibinder.newSetBinder(binder(), RootCollectorExecutor.class);
        envBinder.addBinding().to(MainEnvironmentCollectorExecutor.class);
        envBinder.addBinding().to(MainCommandExecutor.class);
    }
}
