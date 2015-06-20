package io.pity.tasks.injector
import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.google.inject.multibindings.Multibinder
import io.pity.api.environment.EnvironmentCollector
import io.pity.api.execution.CommandExecutor
import io.pity.api.preprocess.CommandPreProcessor
import io.pity.tasks.collector.*
import io.pity.tasks.execution.NoopCommandExecutor
import io.pity.tasks.execution.gradle.GradleCommandExecutor
import io.pity.tasks.preprocess.NoopPreProcessor
import io.pity.tasks.util.process.ExternalProcessCreator

class BuiltinTaskInjector extends AbstractModule {
    @Override
    protected void configure() {
        Multibinder<EnvironmentCollector> envBinder = Multibinder.newSetBinder(binder(), EnvironmentCollector.class);
        envBinder.addBinding().to(WorkingDirectoryEnvironmentCollector.class)
        envBinder.addBinding().to(GitEnvironmentCollector.class)
        envBinder.addBinding().to(SvnEnvironmentCollector.class)
        envBinder.addBinding().to(EnvironmentVariableCollector.class)
        envBinder.addBinding().to(NodeEnvironmentCollector.class)
        envBinder.addBinding().to(JavaProcessEnvironmentCollector.class)
        envBinder.addBinding().to(CommandLineParameterCollector.class)

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
