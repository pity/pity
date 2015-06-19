package io.ask.tasks.collector


import com.google.inject.Inject
import groovy.transform.CompileStatic
import io.ask.api.CliArgumentProvider
import io.ask.api.WorkingDirectoryProvider
import io.ask.api.environment.EnvironmentCollector

@CompileStatic
class CommandLineParameterCollector extends EnvironmentCollector {

    CliArgumentProvider cliArgumentProvider;

    @Inject
    CommandLineParameterCollector(WorkingDirectoryProvider workingDirectoryProvider, CliArgumentProvider cliArgumentProvider) {
        super(workingDirectoryProvider)
        this.cliArgumentProvider = cliArgumentProvider;
    }

    @Override
    boolean shouldCollect() {
        return true;
    }

    @Override
    void collect() {
        environmentDataBuilder.addData('ticketId', cliArgumentProvider.getTicketId())
    }
}
