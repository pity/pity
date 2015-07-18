package io.pity.bootstrap.injection.injectors
import com.google.inject.AbstractModule
import io.pity.api.PropertyValueProvider
import io.pity.api.StopExecutionException
import io.pity.api.reporting.CollectionResults
import io.pity.api.reporting.ReportPublisher
import io.pity.bootstrap.provider.cli.InternalCliArgumentProvider
import io.pity.bootstrap.publish.XmlReportPublisher
import spock.lang.Specification

class TaskInjectorTest extends Specification {

    def 'when nothing is setup, will default to XmlReportPublisher'() {
        setup:
        def propertyValueProvider = Mock(PropertyValueProvider)
        def cliArgumentProvider = Mock(InternalCliArgumentProvider)
        def injector = new TaskInjector([new TestDoubleAbstractModule(propertyValueProvider, cliArgumentProvider)])

        when:
        def publisher = injector.getReportPublisher()

        then:
        publisher.class == XmlReportPublisher
        1 * cliArgumentProvider.isOverridePublisher() >> false
    }

    def 'if default.publisher is, will use property'() {
        setup:
        def propertyValueProvider = Mock(PropertyValueProvider)
        def cliArgumentProvider = Mock(InternalCliArgumentProvider)
        def injector = new TaskInjector([new TestDoubleAbstractModule(propertyValueProvider, cliArgumentProvider)])

        when:
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
        def injector = new TaskInjector([new TestDoubleAbstractModule(propertyValueProvider, cliArgumentProvider)])

        when:
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
        def injector = new TaskInjector([new TestDoubleAbstractModule(propertyValueProvider, cliArgumentProvider)])

        when:
        def publisher = injector.getReportPublisher()

        then:
        publisher.class == XmlReportPublisher
        1 * cliArgumentProvider.isOverridePublisher() >> true
        1 * cliArgumentProvider.getOverriddenPublisher() >> TestDoubleAbstractModule.name
    }

    private static class TestPublisher implements ReportPublisher {
        @Override
        void publishReport(CollectionResults collectedResults) {

        }

        @Override
        void validateRequirements() throws StopExecutionException {

        }
    }

    private static class TestDoubleAbstractModule extends AbstractModule {
        PropertyValueProvider propertyValueProvider
        InternalCliArgumentProvider cliArgumentProvider

        TestDoubleAbstractModule(PropertyValueProvider propertyValueProvider, InternalCliArgumentProvider cliArgumentProvider) {
            this.propertyValueProvider = propertyValueProvider
            this.cliArgumentProvider = cliArgumentProvider
        }

        @Override
        protected void configure() {
            bind(InternalCliArgumentProvider.class).toInstance(cliArgumentProvider);
            bind(PropertyValueProvider.class).toInstance(propertyValueProvider);
        }
    }
}
