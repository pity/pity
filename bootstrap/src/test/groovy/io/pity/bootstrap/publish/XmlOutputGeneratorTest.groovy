package io.pity.bootstrap.publish
import io.pity.api.environment.EnvironmentDataBuilder
import io.pity.api.execution.CommandExecutionResultBuilder
import io.pity.api.reporting.internal.ResultCollectorImpl
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

class XmlOutputGeneratorTest extends Specification {
    ResultCollectorImpl resultCollector
    @Rule TemporaryFolder temporaryFolder = new TemporaryFolder()

    def setup() {
        resultCollector = new ResultCollectorImpl()
        temporaryFolder.create()
    }

    def 'able to write xml'(){
        when:
        def xml = new XmlOutputGenerator(resultCollector).createXmlString()
        def parsed = new XmlSlurper().parseText(xml)

        then:
        parsed.results.environment.size() == 0
    }

    def 'when env data is provided it should be written'(){
        setup:
        def builder = EnvironmentDataBuilder.Builder('foo')
        builder.addData('field1', 'data1')
        builder.addData('field2', 'data2')
        builder.addData('field3', 'data3')
        resultCollector.collect(builder.build())

        when:
        def xml = new XmlOutputGenerator(resultCollector).createXmlString()
        def parsed = new XmlSlurper().parseText(xml)

        then:
        parsed.environment.collector.entry[0].text() == 'data1'
        parsed.environment.collector.children().size() == 3
    }

    def 'when process data is provided it should be written'(){
        setup:
        def builder = new CommandExecutionResultBuilder('executor', temporaryFolder.newFolder())
        builder.addResult('field1', 'result1')
        builder.addResult('field2', 'result2')
        builder.addResult('field3', 'result3')
        resultCollector.collect(builder.build())

        when:
        def xml = new XmlOutputGenerator(resultCollector).createXmlString()
        def parsed = new XmlSlurper().parseText(xml)

        then:
        parsed.execution.'execution-result'.@name == 'executor'
        parsed.execution.'execution-result'.'other-data'.children().size() == 3
    }
}
