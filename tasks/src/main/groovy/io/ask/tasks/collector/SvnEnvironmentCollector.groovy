package io.ask.tasks.collector
import com.google.inject.Inject
import io.ask.api.WorkingDirectoryProvider
import io.ask.tasks.util.ExternalProcessReporter

class SvnEnvironmentCollector extends ProcessBasedEnvironmentCollector {

    public static final String WORKING_COPY = 'is not a working copy'

    @Inject
    def SvnEnvironmentCollector(WorkingDirectoryProvider workingDirectoryProvider) {
        super(workingDirectoryProvider)
    }

    @Override
    boolean shouldCollect() {
        def result = new ExternalProcessReporter('svn status --depth=empty', workingDirectory).getResult()
        if(result.inputStream.text.contains(WORKING_COPY) || result.errorStream.text.contains(WORKING_COPY)){
            return false;
        }

        return result.exitCode == 0
    }

    @Override
    void collect() {
        collectCommandResults('status', 'svn status --depth=empty')
        collectCommandResults('diff,', 'svn diff')
    }
}
