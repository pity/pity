package io.ask.tasks.collector
import com.google.inject.Inject
import com.google.inject.Provider
import io.ask.api.WorkingDirectoryProvider
import io.ask.tasks.util.process.ExternalProcessCreator
import io.ask.tasks.util.process.ExternalProcessReporter

class SvnEnvironmentCollector extends ProcessBasedEnvironmentCollector {

    public static final String WORKING_COPY = 'is not a working copy'

    @Inject
    SvnEnvironmentCollector(WorkingDirectoryProvider workingDirectoryProvider,
                            Provider<ExternalProcessCreator> externalProcessCreatorProvider) {
        super(workingDirectoryProvider, externalProcessCreatorProvider)
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
