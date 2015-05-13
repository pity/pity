package io.ask.tasks.injector

import com.google.inject.AbstractModule
import com.google.inject.multibindings.Multibinder
import io.ask.api.EnvironmentCollector
import io.ask.tasks.collector.*

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
}
