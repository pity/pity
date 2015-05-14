package io.ask.tasks.collector
import com.google.inject.Inject
import com.google.inject.Provider
import io.ask.api.WorkingDirectoryProvider
import io.ask.tasks.util.process.ExternalProcessCreator

class SvnEnvironmentCollector extends ProcessBasedEnvironmentCollector {

    public static final String WORKING_COPY = 'is not a working copy'

    @Inject
    SvnEnvironmentCollector(WorkingDirectoryProvider workingDirectoryProvider,
                            Provider<ExternalProcessCreator> externalProcessCreatorProvider) {
        super(workingDirectoryProvider, externalProcessCreatorProvider)
    }

    @Override
    boolean shouldCollect() {
        def result = externalProcessCreatorProvider.get().createProcess('svn status --depth=empty', workingDirectory).getResult()

        def inputStream = result.inputStream.text
        def errorStream = result.errorStream.text
        if(inputStream.contains(WORKING_COPY) || errorStream.contains(WORKING_COPY)){
            return false;
        }

        return result.exitCode == 0
    }

    @Override
    void collect() {
        collectCommandResults('status', 'svn status --depth=empty')
        collectCommandResults('diff', 'svn diff')
    }
}
