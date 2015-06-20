package io.pity.wrapper.ivy

import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

class DependencyResolverTest extends Specification {

    @Rule TemporaryFolder temporaryFolder = new TemporaryFolder()

    def setup() {
        temporaryFolder.create()
    }

    def 'can download dependencies from maven central'() {
        setup:
        def folder = new File(temporaryFolder.newFolder(), ".ask/cache")
        def configuration = new DependencyConfiguration(null, folder, [new Dependency("org.mockito:mockito-core:1.10.19")])

        when:
        def resolver = new DependencyResolver(configuration).resolveDependencies()

        then:
        resolver.size() == 3
    }

}
