package io.pity.tasks.collector

import spock.lang.Specification

class EnvironmentVariableCollectorTest extends Specification {

    def 'reports all env variables that do not start with _'(){
        when:
        def collector = new EnvironmentVariableCollector(['env1': 'foo', '_env2': 'bar', 'env3': 'buzz'])
        def environmentData = collector.collectEnvironmentData()

        then:
        environmentData.getCollectorName() == EnvironmentVariableCollector.class.getSimpleName()
        environmentData.getEnvironmentResults().keySet() == [ 'env1', 'env3' ] as Set
        environmentData.getEnvironmentResults()['env1'] == 'foo'
        environmentData.getEnvironmentResults()['_env2'] == null
        environmentData.getEnvironmentResults()['env3'] == 'buzz'
    }

}
