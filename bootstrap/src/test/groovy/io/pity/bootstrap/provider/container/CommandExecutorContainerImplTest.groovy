package io.pity.bootstrap.provider.container

import io.pity.api.execution.CommandExecutionResult
import io.pity.api.execution.CommandExecutor
import io.pity.api.preprocess.CommandOptions
import io.pity.bootstrap.injection.PropertyFinder
import io.pity.bootstrap.provider.cli.InternalCliArgumentProvider
import spock.lang.Specification

class CommandExecutorContainerImplTest extends Specification {

    def 'can filter command executors'() {
        setup:
        def executors = [new TestDouble1(), new TestDouble2()] as Set<CommandExecutor>
        def propertyFinder = ['findProperties': { it -> [TestDouble1.name] }] as PropertyFinder
        def cli = [ 'getExcludedCollectors': { -> [] }] as InternalCliArgumentProvider

        when:
        def impl = new CommandExecutorContainerImpl(cli, propertyFinder, executors)

        then:
        def commandExecutors = impl.getAvailable()
        commandExecutors.size() == 1
        commandExecutors.first().getClass() == TestDouble2
        def filtered = impl.getFiltered()
        filtered.size() == 1
        filtered.first().getClass() == TestDouble1
    }

    private static class TestDouble1 implements CommandExecutor {
        @Override
        int commandPrecedence() {
            return 0
        }

        @Override
        boolean shouldStopExecutionAfter() {
            return false
        }

        @Override
        boolean willDoWork(CommandOptions commandOptions) {
            return false
        }

        @Override
        CommandExecutionResult execute(CommandOptions commandOptions) {
            return null
        }
    }

    private static class TestDouble2 extends TestDouble1 {}

}
