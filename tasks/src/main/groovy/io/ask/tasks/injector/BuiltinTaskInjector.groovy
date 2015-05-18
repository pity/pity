package io.ask.tasks.injector
import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.google.inject.multibindings.Multibinder
import io.ask.api.environment.EnvironmentCollector
import io.ask.api.execution.CommandExecutor
import io.ask.api.preprocess.CommandPreProcessor
import io.ask.tasks.collector.*
import io.ask.tasks.execution.NoopCommandExecutor
import io.ask.tasks.execution.gradle.GradleCommandExecutor
import io.ask.tasks.preprocess.NoopPreProcessor
import io.ask.tasks.util.process.ExternalProcessCreator

class BuiltinTaskInjector extends AbstractModule {
    @Override
    protected void configure() {
        Multibinder<EnvironmentCollector> envBinder = Multibinder.newSetBinder(binder(), EnvironmentCollector.class);
        envBinder.addBinding().to(WorkingDirectoryEnvironmentCollector.class)
        envBinder.addBinding().to(GitEnvironmentCollector.class)
        envBinder.addBinding().to(SvnEnvironmentCollector.class)
        envBinder.addBinding().to(EnvironmentVariableCollector.class)
        envBinder.addBinding().to(NodeEnvironmentCollector.class)

        Multibinder<CommandPreProcessor> preprocessorBinder = Multibinder.newSetBinder(binder(), CommandPreProcessor.class);
        preprocessorBinder.addBinding().to(NoopPreProcessor.class)

        Multibinder<CommandExecutor> commandBinder = Multibinder.newSetBinder(binder(), CommandExecutor.class);
        commandBinder.addBinding().to(NoopCommandExecutor.class)
        commandBinder.addBinding().to(GradleCommandExecutor.class)
    }

    @Provides
    ExternalProcessCreator provideExternalProcessCreator() {
        return new ExternalProcessCreator();
    }
}
