package io.ask.api.execution;

import com.google.inject.Inject;
import io.ask.api.WorkingDirectoryProvider;


abstract public class AbstractCommandExecutor implements CommandExecutor {

    final WorkingDirectoryProvider workingDirectoryProvider;

    @Inject
    AbstractCommandExecutor(WorkingDirectoryProvider workingDirectoryProvider){
        this.workingDirectoryProvider = workingDirectoryProvider;
    }

}
