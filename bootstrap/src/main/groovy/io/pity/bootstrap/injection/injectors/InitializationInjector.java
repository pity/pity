package io.pity.bootstrap.injection.injectors;

import com.google.inject.*;
import io.pity.api.cli.CliOptionConfigurer;

import java.util.List;
import java.util.Set;

public class InitializationInjector {

    static private final TypeLiteral cliOptionConfigurerSet = new TypeLiteral<Set<CliOptionConfigurer>>() { };

    public final Injector injector;

    public InitializationInjector(List<AbstractModule> abstractModule) {
        injector = Guice.createInjector(abstractModule);
    }

    @SuppressWarnings("unchecked cast")
    public Set<CliOptionConfigurer> findCliOptions() {
        return (Set<CliOptionConfigurer>) injector.getInstance(Key.get(cliOptionConfigurerSet));
    }

}
