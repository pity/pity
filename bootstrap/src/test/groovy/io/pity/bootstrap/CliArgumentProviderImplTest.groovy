package io.pity.bootstrap


import io.pity.bootstrap.provider.CliArgumentProviderImpl
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

    def 'dependency management'() {
        when:
        def argumentProvider = new CliArgumentProviderImpl('--ivy-configuration foo/bar/fizz --include foo:bar:1,bar:bizz:5'.split(' '))

        then:
        argumentProvider.getIvyConfiguration() != null
        argumentProvider.getIvyConfiguration().configurationFile != null
        argumentProvider.getIvyConfiguration().configurationFile.getPath() == 'foo/bar/fizz'
        argumentProvider.getIvyConfiguration().dependencies*.toString() as Set == [ 'foo:bar:1', 'bar:bizz:5'] as Set

        when:
        argumentProvider = new CliArgumentProviderImpl('--ivy-configuration foo/bar/fizz'.split(' '))

        then:
        argumentProvider.getIvyConfiguration() != null
        !argumentProvider.getIvyConfiguration().shouldResolve()
    }

    def 'when argument is not present, can it be retrieved'() {
        when:
        new CliArgumentProviderImpl('--bar biz'.split(' '))

        then:
        noExceptionThrown()
    }
}
