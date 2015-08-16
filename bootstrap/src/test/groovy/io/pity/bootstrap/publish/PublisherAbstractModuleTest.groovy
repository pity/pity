package io.pity.bootstrap.publish
import io.pity.api.PropertyValueProvider
import io.pity.api.StopExecutionException
import io.pity.api.reporting.CollectionResults
import io.pity.api.reporting.ReportPublisher
import io.pity.bootstrap.injection.injectors.TaskInjector
import io.pity.bootstrap.provider.cli.InternalCliArgumentProvider
import io.pity.bootstrap.publish.html.HtmlReportPublisher
import spock.lang.Specification

class PublisherAbstractModuleTest extends Specification {
    def 'when nothing is setup, will default to HtmlReportPublisher'() {
        setup:
        def propertyValueProvider = Mock(PropertyValueProvider)
        def cliArgumentProvider = Mock(InternalCliArgumentProvider)

        when:
        def injector = new TaskInjector([new PublisherAbstractModule(propertyValueProvider, cliArgumentProvider)])
        def publisher = injector.getReportPublisher()

        then:
        publisher.class == HtmlReportPublisher
        1 * cliArgumentProvider.isOverridePublisher() >> false
    }

    def 'if default.publisher is, will use property'() {
        setup:
        def propertyValueProvider = Mock(PropertyValueProvider)
        def cliArgumentProvider = Mock(InternalCliArgumentProvider)

        when:
        def injector = new TaskInjector([new PublisherAbstractModule(propertyValueProvider, cliArgumentProvider)])
        def publisher = injector.getReportPublisher()

        then:
        publisher.class == TestPublisher
        1 * propertyValueProvider.getProperty('default.publisher') >> TestPublisher.name
        1 * cliArgumentProvider.isOverridePublisher() >> false
    }

    def 'if cli publisher is set it, will use param'() {
        setup:
        def propertyValueProvider = Mock(PropertyValueProvider)
        def cliArgumentProvider = Mock(InternalCliArgumentProvider)

        when:
        def injector = new TaskInjector([new PublisherAbstractModule(propertyValueProvider, cliArgumentProvider)])
        def publisher = injector.getReportPublisher()

        then:
        publisher.class == TestPublisher
        1 * cliArgumentProvider.isOverridePublisher() >> true
        1 * cliArgumentProvider.getOverriddenPublisher() >> TestPublisher.name
    }

    def 'when class picked is not publisher, will fall back to default'() {
        setup:
        def propertyValueProvider = Mock(PropertyValueProvider)
        def cliArgumentProvider = Mock(InternalCliArgumentProvider)

        when:
        def injector = new TaskInjector([new PublisherAbstractModule(propertyValueProvider, cliArgumentProvider)])
        def publisher = injector.getReportPublisher()

        then:
        publisher.class == TestPublisher.class
        1 * cliArgumentProvider.isOverridePublisher() >> true
        1 * cliArgumentProvider.getOverriddenPublisher() >> TestPublisher.name
    }

    private static class TestPublisher implements ReportPublisher {
        @Override
        void publishReport(CollectionResults collectedResults) {

        }

        @Override
        void validateRequirements() throws StopExecutionException {

        }
    }
}
