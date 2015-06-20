package io.pity.api.execution;

import io.pity.api.preprocess.CommandOptions;
import java.io.File;
import java.util.Map;


public interface CommandExecutionResult {

    CommandOptions getCommandExecuted();

    String getStdError();

    String getStdOut();

    File getResultDir();

    Exception getExceptionThrown();

    String getCommandExecutorClass();

    Map<String, String> getOtherResults();

}
