package io.pity.bootstrap
import io.pity.bootstrap.provider.cli.CliArgumentProviderImpl
import io.pity.bootstrap.provider.cli.DefaultCliOptionConfigurer
import spock.lang.Shared
import spock.lang.Specification

class CliArgumentProviderImplTest extends Specification {

    @Shared
    DefaultCliOptionConfigurer defaultCliOptionConfigurer = new DefaultCliOptionConfigurer()

    def 'testing help argument'() {
        when:
        def argumentProvider = createCliArgumentProvider(['--help'] as String[])

        then:
        argumentProvider.isHelp()

        when:
        argumentProvider = createCliArgumentProvider(['-h'] as String[])

        then:
        argumentProvider.isHelp()
    }

    def 'testing disable-env-collection argument'() {
        when:
        def argumentProvider = createCliArgumentProvider(['--disable-env-collection'] as String[])

        then:
        !argumentProvider.isEnvironmentCollectionEnabled()
        argumentProvider.getRawOption('disable-env-collection') == true

        when:
        argumentProvider = createCliArgumentProvider([''] as String[])

        then:
        argumentProvider.isEnvironmentCollectionEnabled()
    }

    def 'testing execute argument and options'() {
        when:
        createCliArgumentProvider(['--execute'] as String[])

        then:
        thrown(CliArgumentProviderImpl.ArgumentParseError)

        when:
        def argumentProvider = createCliArgumentProvider('--execute ls'.split(' '))

        then:
        argumentProvider.isCommandExecution()
        argumentProvider.getExecutionCommandOptions()
        argumentProvider.getRawOption('execute') == 'ls'
    }

    def 'when argument is not present, can it be retrieved'() {
        when:
        createCliArgumentProvider('--bar biz'.split(' '))

        then:
        noExceptionThrown()
    }

    private CliArgumentProviderImpl createCliArgumentProvider(String[] strings) {
        new CliArgumentProviderImpl(strings, [defaultCliOptionConfigurer] as Set)
    }
}
