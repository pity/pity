package io.pity.tasks.collector
import com.google.inject.Inject
import groovy.transform.CompileStatic
import io.pity.api.cli.CliArgumentProvider
import io.pity.api.environment.EnvironmentCollector

@CompileStatic
class CommandLineParameterCollector extends EnvironmentCollector {

    CliArgumentProvider cliArgumentProvider;

    @Inject
    CommandLineParameterCollector(CliArgumentProvider cliArgumentProvider) {
        super(CommandLineParameterCollector.class)
        this.cliArgumentProvider = cliArgumentProvider;
    }

    @Override
    boolean shouldCollect() {
        return true;
    }

    @Override
    void collect() {
        environmentDataBuilder.addData('configured-options', "${cliArgumentProvider.getRawArgs()}")
    }
}
