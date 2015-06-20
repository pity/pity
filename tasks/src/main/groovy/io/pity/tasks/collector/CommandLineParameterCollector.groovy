package io.pity.tasks.collector


import com.google.inject.Inject
import groovy.transform.CompileStatic
import io.pity.api.CliArgumentProvider
import io.pity.api.WorkingDirectoryProvider
import io.pity.api.environment.EnvironmentCollector

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
