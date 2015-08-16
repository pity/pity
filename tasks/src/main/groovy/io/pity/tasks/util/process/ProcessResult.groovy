package io.pity.tasks.util.process

import org.apache.commons.io.IOUtils

class ProcessResult {
    public InputStreamReader standardOut;
    public InputStreamReader errorStream;
    public int exitCode;

    ProcessResult(Process process, InputStream standardOut, InputStream errorStream) {
        this.standardOut = new InputStreamReader(standardOut)
        this.errorStream = new InputStreamReader(errorStream)
        this.exitCode = process.exitValue()
    }

    ProcessResult(String standardOutString, String errorString, int exitCode) {
        this.standardOut = new InputStreamReader(IOUtils.toInputStream(standardOutString))
        this.errorStream = new InputStreamReader(IOUtils.toInputStream(errorString))
        this.exitCode = exitCode
    }
}
