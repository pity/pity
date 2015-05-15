package io.ask.bootstrap.preprocess

import groovy.transform.TupleConstructor
import io.ask.api.preprocess.CommandOptions
import io.ask.api.preprocess.CommandPreProcessor
import spock.lang.Specification

class PreProcessorExecutorImplTest extends Specification {

    def 'order of pre-processors is in descending order'() {
        setup:
        def commands = (-1..1).collect { new SampleCommandPreProcessor(it, { -> }) }
        def executor = new PreProcessorExecutorImpl(commands as Set)

        expect:
        executor.orderPreProcessors().collect { it.commandPrecedence()} ==  [ 1, 0, -1]
    }

    def 'when command pre-processor returns null, we should use the existing value'() {
        setup:
        def processorExecutor = new PreProcessorExecutorImpl([] as Set)
        def preProcessor = new SampleCommandPreProcessor(0, { arg1, arg2 -> return null })
        def commandOptions = [ : ] as CommandOptions

        expect:
        commandOptions == processorExecutor.runPreProcessor(preProcessor, commandOptions)
    }

    def 'when command pre-processor returns non null, we should use the result'() {
        setup:
        def processorExecutor = new PreProcessorExecutorImpl([] as Set)
        def origonalOption = [ : ] as CommandOptions
        def newOption = [ : ] as CommandOptions

        def preProcessor = new SampleCommandPreProcessor(0, { arg1, arg2 -> return newOption })

        expect:
        newOption == processorExecutor.runPreProcessor(preProcessor, origonalOption)
    }

    @TupleConstructor
    static class SampleCommandPreProcessor implements CommandPreProcessor {

        final int precedence
        final Closure process

        @Override
        int commandPrecedence() {
            return precedence
        }

        @Override
        CommandOptions processCommand(CommandOptions command) {
            return process.call(this, command) as CommandOptions
        }
    }

}
