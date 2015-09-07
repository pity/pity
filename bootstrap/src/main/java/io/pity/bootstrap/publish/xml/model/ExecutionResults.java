package io.pity.bootstrap.publish.xml.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import io.pity.api.execution.CommandExecutionResult;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.util.List;
import java.util.stream.Collectors;

public class ExecutionResults {

    @JacksonXmlProperty(isAttribute = true, localName = "name")
    String name;

    String stdOut;

    String stdErr;

    @JacksonXmlProperty(localName = "command-executed")
    String commandExecuted;

    @JacksonXmlProperty(localName = "result-dir")
    String resultDir;

    @JacksonXmlProperty(localName = "exception-thrown")
    String exceptionThrown;

    @JacksonXmlElementWrapper(localName = "entry")
    @JacksonXmlProperty(localName = "other-data")
    List<MapData> mapData;


    public ExecutionResults(CommandExecutionResult commandExecutionResult) {
        this.name = commandExecutionResult.getCommandExecutorClass();
        this.stdErr = commandExecutionResult.getStdError();
        this.stdOut = commandExecutionResult.getStdOut();
        this.commandExecuted = commandExecutionResult.getCommandExecuted().toString();
        this.resultDir = commandExecutionResult.getResultDir().getAbsolutePath();
        this.exceptionThrown = ExceptionUtils.getStackTrace(commandExecutionResult.getExceptionThrown());
        this.mapData = commandExecutionResult.getOtherResults().entrySet().stream()
            .map(MapData::new)
            .collect(Collectors.toList());
    }
}
