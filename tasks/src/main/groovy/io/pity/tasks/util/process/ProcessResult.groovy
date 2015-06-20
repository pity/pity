package io.pity.tasks.util.process

import org.apache.commons.io.IOUtils

class ProcessResult {
    public InputStreamReader inputStream;
    public InputStreamReader errorStream;
    public int exitCode;

    ProcessResult(Process process, InputStream inputStream, InputStream errorStream) {
        this.inputStream = new InputStreamReader(inputStream)
        this.errorStream = new InputStreamReader(errorStream)
        this.exitCode = process.exitValue()
    }

    ProcessResult(String inputString, String errorString, int exitCode) {
        this.inputStream = new InputStreamReader(IOUtils.toInputStream(inputString))
        this.errorStream = new InputStreamReader(IOUtils.toInputStream(errorString))
        this.exitCode = exitCode
    }
}
