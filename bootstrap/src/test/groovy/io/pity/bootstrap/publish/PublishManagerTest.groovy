package io.pity.bootstrap.publish

import io.pity.api.StopExecutionException
import io.pity.api.reporting.ReportPublisher
import spock.lang.Specification

class PublishManagerTest extends Specification {

    def 'when StopExecutionException is thrown, should continue is false'() {
        setup:
        def reportPublisher = Mock(ReportPublisher)
        def manager = new PublishManager(reportPublisher)

        when:
        def executionContinue = manager.shouldExecutionContinue()

        then:
        1 * reportPublisher.validateRequirements() >> { throw new StopExecutionException('')}
        !executionContinue
    }

    def 'when nothing is wrong, will continue'() {
        setup:
        def reportPublisher = Mock(ReportPublisher)
        def manager = new PublishManager(reportPublisher)

        when:
        def executionContinue = manager.shouldExecutionContinue()

        then:
        1 * reportPublisher.validateRequirements()
        executionContinue
    }

    def 'publishReport delegates'() {
        setup:
        def reportPublisher = Mock(ReportPublisher)
        def manager = new PublishManager(reportPublisher)

        when:
        manager.publishReport(null)

        then:
        1 * reportPublisher.publishReport(null)
    }
}
