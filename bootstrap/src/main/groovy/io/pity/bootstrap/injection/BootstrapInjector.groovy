package io.pity.bootstrap.injection
import com.google.inject.AbstractModule
import com.google.inject.multibindings.Multibinder
import io.pity.api.cli.CliOptionConfigurer
import io.pity.bootstrap.provider.cli.DefaultCliOptionConfigurer

class BootstrapInjector extends AbstractModule {

    @Override
    protected void configure() {
        Multibinder<CliOptionConfigurer> envBinder = Multibinder.newSetBinder(binder(), CliOptionConfigurer.class);
        envBinder.addBinding().to(DefaultCliOptionConfigurer.class)
    }
}
