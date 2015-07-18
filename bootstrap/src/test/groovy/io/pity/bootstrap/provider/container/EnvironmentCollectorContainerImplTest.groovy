package io.pity.bootstrap.provider.container
import io.pity.api.environment.EnvironmentCollector
import io.pity.bootstrap.injection.PropertyFinder
import spock.lang.Specification

class EnvironmentCollectorContainerImplTest extends Specification {
    def 'can filter environment collector'() {
        setup:
        def collectors = [new TestDouble1(), new TestDouble2()] as Set<EnvironmentCollector>
        def propertyFinder = ['findProperties': { it -> [TestDouble1.name] }] as PropertyFinder

        when:
        def impl = new EnvironmentCollectorContainerImpl(collectors, propertyFinder)

        then:
        def commandExecutors = impl.getAvailable()
        commandExecutors.size() == 1
        commandExecutors.first().getClass() == TestDouble2

        def filtered = impl.getFiltered()
        filtered.size() == 1
        filtered.first().getClass() == TestDouble1
    }

    private static class TestDouble1 extends EnvironmentCollector {
        TestDouble1() {
            super(null)
        }

        @Override
        boolean shouldCollect() {
            return false
        }

        @Override
        void collect() {

        }
    }

    private static class TestDouble2 extends TestDouble1 {}
}
