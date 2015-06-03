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

    def 'dependency management'() {
        when:
        def argumentProvider = new CliArgumentProvider('--ivy-configuration foo/bar/fizz --include foo:bar:1,bar:bizz:5'.split(' '))

        then:
        argumentProvider.getIvyConfiguration() != null
        argumentProvider.getIvyConfiguration().configurationFile != null
        argumentProvider.getIvyConfiguration().configurationFile.getPath() == 'foo/bar/fizz'
        argumentProvider.getIvyConfiguration().dependencies as Set == [ 'foo:bar:1', 'bar:bizz:5'] as Set

        when:
        argumentProvider = new CliArgumentProvider('--ivy-configuration foo/bar/fizz'.split(' '))

        then:
        argumentProvider.getIvyConfiguration() != null
        !argumentProvider.getIvyConfiguration().shouldResolve()
    }
}
