package io.pity.api.preprocess


import spock.lang.Specification

class CommandOptionsFactoryTest extends Specification {

    def 'when empty should return null'() {
        expect:
        CommandOptionsFactory.create([]) == null
    }

    def 'any string that is null or blank should be skipped'() {
        when:
        def options = CommandOptionsFactory.create(['  ', 'ls', null, 'foo'])

        then:
        options.getCommand() == 'ls'
        options.getArguments() == [ 'foo' ]

    }
}
