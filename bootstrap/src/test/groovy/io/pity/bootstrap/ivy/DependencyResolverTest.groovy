package io.pity.bootstrap.ivy

import io.pity.bootstrap.injection.PropertyFinder
import org.codehaus.groovy.tools.RootLoader
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
        def rootLoader =new RootLoader([] as URL[], this.getClass().getClassLoader())

        when:
        def resolver = new DependencyResolver(new PropertyFinder(), configuration, rootLoader)
        resolver.resolveDependencies()

        then:
        rootLoader.getURLs().size() == 3
    }

}