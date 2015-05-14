package io.ask.tasks.collector
import com.google.inject.Provider
import io.ask.api.WorkingDirectoryProvider
import io.ask.tasks.util.process.ExternalProcessCreator
import io.ask.tasks.util.process.ExternalProcessReporter
import io.ask.tasks.util.process.ExternalProcessReporterImpl
import io.ask.tasks.util.process.ProcessResult
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

class SvnEnvironmentCollectorTest extends Specification {

    @Rule TemporaryFolder temporaryFolder = new TemporaryFolder()

    WorkingDirectoryProvider workingDirectoryProvider;
    Provider<ExternalProcessCreator> processCreatorProvider;
    ExternalProcessCreator creator = Mock(ExternalProcessCreator)
    ExternalProcessReporterImpl reporter = Mock(ExternalProcessReporterImpl)
    SvnEnvironmentCollector svnCollector

    def setup() {
        temporaryFolder.create()
        workingDirectoryProvider = ['getWorkingDirectory' : { -> temporaryFolder.root }] as WorkingDirectoryProvider
        processCreatorProvider = [ 'get': { -> creator }] as Provider<ExternalProcessCreator>
        svnCollector = new SvnEnvironmentCollector(workingDirectoryProvider, processCreatorProvider)
    }

    def 'when directory is not svn, should skip when result is in stdout'() {
        when:
        creator.createProcess('svn status --depth=empty', _) >>
                createProcessReporter('svn: E155007: \'/Volumes/Workspace/ask/bootstrap\' is not a working copy', '', 0)

        then:
        !svnCollector.shouldCollect()
    }

    def 'when directory is not svn, should skip when result is in stderr'(){
        when:
        creator.createProcess('svn status --depth=empty', _) >>
                createProcessReporter('', 'svn: E155007: \'/Volumes/Workspace/ask/bootstrap\' is not a working copy', 0)

        then:
        !svnCollector.shouldCollect()
    }

    def 'adds status and diff'() {
        when:
        creator.createProcess('svn status --depth=empty', _) >>>
                [ createProcessReporter('', '', 0), createProcessReporter('status', '', 0)]
        creator.createProcess('svn diff', _) >> createProcessReporter('diff', '', 0)

        then:
        def environmentData = svnCollector.collectEnvironmentData()
        environmentData.getCollectorName() == SvnEnvironmentCollector.class.getSimpleName()
        environmentData.getEnvironmentResults().keySet() == [ 'diff', 'status' ] as Set

        environmentData.getEnvironmentResults()['diff'] == 'diff'
        environmentData.getEnvironmentResults()['status'] == 'status'
    }

    ExternalProcessReporter createProcessReporter(final String stdOut, final String stdErr, final int exit) {
        def reporter = [ getResult: { -> new ProcessResult(stdOut, stdErr, exit) }]
        return reporter as ExternalProcessReporter
    }
}
