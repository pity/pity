package io.pity.bootstrap.preprocess
import groovy.transform.TupleConstructor
import io.pity.api.preprocess.CommandOptions
import io.pity.api.preprocess.CommandPreProcessor
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

    def 'when multiple pre-processors return results, the latest should be returned'() {
        setup:
        def commandOptions = (-1..1).collect { [:] as CommandOptions }
        def commands = (-1..1).collect { new SampleCommandPreProcessor(it, { arg1, arg2 -> commandOptions[it + 1]}) }
        def executor = new PreProcessorExecutorImpl(commands as Set)

        expect:
        executor.processCommandOptions([:] as CommandOptions) == commandOptions.last()
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
