package io.ask.tasks.injector
import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.google.inject.multibindings.Multibinder
import io.ask.api.EnvironmentCollector
import io.ask.tasks.collector.*
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
    }

    @Provides
    ExternalProcessCreator provideExternalProcessCreator() {
        return new ExternalProcessCreator();
    }
}
