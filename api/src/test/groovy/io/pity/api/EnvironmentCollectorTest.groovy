package io.pity.api

import io.pity.api.environment.EnvironmentCollector
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification
import sun.reflect.generics.reflectiveObjects.NotImplementedException

class EnvironmentCollectorTest extends Specification {

    @Rule TemporaryFolder temporaryFolder = new TemporaryFolder()

    def setup() {
        temporaryFolder.create()
    }

    def 'when should collect is false it will never run'() {
        setup:
        def collector = Spy(TestEvnCollector)

        when:
        collector.collectEnvironmentData()

        then:
        1 * collector.shouldCollect() >> false
        0 * collector.collect()
    }

    def 'when should collect is true it will run'() {
        setup:
        def collector = Spy(TestEvnCollector)

        when:
        collector.collectEnvironmentData()

        then:
        1 * collector.shouldCollect() >> true
        1 * collector.collect()
    }

    class TestEvnCollector extends EnvironmentCollector {

        TestEvnCollector() {
            super(TestEvnCollector.class)
        }

        @Override
        boolean shouldCollect() {
            throw new NotImplementedException()
        }

        @Override
        void collect() {

        }
    }
}
