package io.ask.api.execution;

import io.ask.api.preprocess.CommandOptions;
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
