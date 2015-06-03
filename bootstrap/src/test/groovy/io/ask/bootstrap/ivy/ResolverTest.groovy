package io.ask.bootstrap.ivy
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

class ResolverTest extends Specification {

    @Rule TemporaryFolder temporaryFolder = new TemporaryFolder()

    def setup() {
        temporaryFolder.create()
    }

    def download() {
        setup:
        def folder = new File(temporaryFolder.newFolder(), ".ask/cache")
        def configuration = new DependencyConfiguration(null, folder, [new Dependency("org.mockito:mockito-core:1.10.19")])

        when:
        def resolver = new Resolver(configuration)
        resolver.resolveDependencies()

        then:
        folder.listFiles().each { println it }
    }
}
