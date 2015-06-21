package io.pity.bootstrap


import io.pity.bootstrap.provider.cli.CliArgumentProviderImpl
import spock.lang.Specification

class CliArgumentProviderImplTest extends Specification {

    def 'testing help argument'() {
        when:
        def argumentProvider = new CliArgumentProviderImpl(['--help'] as String[])

        then:
        argumentProvider.isHelp()

        when:
        argumentProvider = new CliArgumentProviderImpl(['-h'] as String[])

        then:
        argumentProvider.isHelp()
    }

    def 'testing disable-env-collection argument'() {
        when:
        def argumentProvider = new CliArgumentProviderImpl(['--disable-env-collection'] as String[])

        then:
        !argumentProvider.isEnvironmentCollectionEnabled()

        when:
        argumentProvider = new CliArgumentProviderImpl([''] as String[])

        then:
        argumentProvider.isEnvironmentCollectionEnabled()
    }

    def 'testing execute argument and options'() {
        when:
        new CliArgumentProviderImpl(['--execute'] as String[])

        then:
        thrown(CliArgumentProviderImpl.ArgumentParseError)

        when:
        def argumentProvider = new CliArgumentProviderImpl('--execute ls'.split(' '))

        then:
        argumentProvider.isCommandExecution()
        argumentProvider.getExecutionCommandOptions()
    }

    def 'when argument is not present, can it be retrieved'() {
        when:
        new CliArgumentProviderImpl('--bar biz'.split(' '))

        then:
        noExceptionThrown()
    }
}
