package io.pity.tasks.collector
import com.google.inject.Provider
import io.pity.api.WorkingDirectoryProvider
import io.pity.tasks.util.process.ExternalProcessCreator
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

class JavaProcessEnvironmentCollectorTest extends Specification {

    @Rule TemporaryFolder temporaryFolder = new TemporaryFolder()
    ExternalProcessCreator creator = new ExternalProcessCreator()

    def 'has at least one java process showing up'() {
        setup:
        temporaryFolder.create()
        def java = new JavaProcessEnvironmentCollector(
            [ 'getWorkingDirectory' : { -> temporaryFolder.root }] as WorkingDirectoryProvider,
            [ 'get': { -> creator }] as Provider<ExternalProcessCreator>)

        when:
        def data = java.collectEnvironmentData()

        then:
        data.collectorName == JavaProcessEnvironmentCollector.getSimpleName()
        data.environmentResults['processes'].split('\n').size() != 0
    }

}
