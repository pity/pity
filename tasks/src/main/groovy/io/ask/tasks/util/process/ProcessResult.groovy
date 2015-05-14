package io.ask.tasks.util.process

import org.apache.commons.io.IOUtils

class ProcessResult {
    public InputStreamReader inputStream;
    public InputStreamReader errorStream;
    public int exitCode;

    ProcessResult(Process process) {
        inputStream = new InputStreamReader(process.getInputStream())
        errorStream = new InputStreamReader(process.getErrorStream())
        exitCode = process.exitValue()
    }

    ProcessResult(String inputString, String errorString, int exitCode) {
        this.inputStream = new InputStreamReader(IOUtils.toInputStream(inputString))
        this.errorStream = new InputStreamReader(IOUtils.toInputStream(errorString))
        this.exitCode = exitCode
    }
}
