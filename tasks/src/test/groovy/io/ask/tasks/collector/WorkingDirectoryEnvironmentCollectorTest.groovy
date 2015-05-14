package io.ask.tasks.collector

import io.ask.api.environment.EnvironmentData
import io.ask.api.WorkingDirectoryProvider
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

class WorkingDirectoryEnvironmentCollectorTest extends Specification {

    @Rule TemporaryFolder temporaryFolder = new TemporaryFolder()
    WorkingDirectoryProvider workingDirectoryProvider;


    def setup() {
        temporaryFolder.create()
        workingDirectoryProvider = [ 'getWorkingDirectory': { -> temporaryFolder.getRoot() } ] as WorkingDirectoryProvider

    }

    def 'gets the working directory'() {
        when:
        EnvironmentData data = new WorkingDirectoryEnvironmentCollector(workingDirectoryProvider).collectEnvironmentData()

        then:
        data.collectorName == WorkingDirectoryEnvironmentCollector.class.getSimpleName()
        data.environmentResults.keySet() == [ WorkingDirectoryEnvironmentCollector.WORKING_DIRECTORY ] as Set
        data.environmentResults[WorkingDirectoryEnvironmentCollector.WORKING_DIRECTORY] == temporaryFolder.root.absolutePath

    }
}
