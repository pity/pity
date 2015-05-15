package io.ask.bootstrap


import spock.lang.Specification

class CliArgumentProviderTest extends Specification {

    def 'testing help argument'() {
        when:
        def argumentProvider = new CliArgumentProvider(['--help'] as String[])

        then:
        argumentProvider.isHelp()

        when:
        argumentProvider = new CliArgumentProvider(['-h'] as String[])

        then:
        argumentProvider.isHelp()
    }

    def 'testing disable-env-collection argument'() {
        when:
        def argumentProvider = new CliArgumentProvider(['--disable-env-collection'] as String[])

        then:
        !argumentProvider.isEnvironmentCollectionEnabled()

        when:
        argumentProvider = new CliArgumentProvider([''] as String[])

        then:
        argumentProvider.isEnvironmentCollectionEnabled()
    }

    def 'testing execute argument and options'() {
        when:
        new CliArgumentProvider(['--execute'] as String[])

        then:
        thrown(CliArgumentProvider.ArgumentParseError)

        when:
        def argumentProvider = new CliArgumentProvider('--execute ls'.split(' '))

        then:
        argumentProvider.isCommandExecution()
        argumentProvider.getExecutionCommandOptions()

    }
}
