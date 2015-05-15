package io.ask.api.preprocess.internal


import spock.lang.Specification
import sun.reflect.generics.reflectiveObjects.NotImplementedException

class CommandOptionsImplTest extends Specification {

    def 'create option with multiple arguments'() {
        when:
        def options = new CommandOptionsImpl('ls --help -w -a -s wwww'.split(' ') as List<String>)

        then:
        options.getCommand() == 'ls'
        options.getArguments() == '--help -w -a -s wwww'.split(' ') as List
    }

    def 'create option with no arguments'() {
        when:
        def options = new CommandOptionsImpl('ls'.split(' ') as List<String>)

        then:
        options.getCommand() == 'ls'
        options.getArguments().isEmpty()
    }

    def 'when empty should throw'() {
        when:
        new CommandOptionsImpl([])

        then:
        thrown(IndexOutOfBoundsException)
    }
}
