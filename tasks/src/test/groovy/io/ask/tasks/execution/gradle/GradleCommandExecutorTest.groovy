package io.ask.tasks.execution.gradle


import io.ask.api.WorkingDirectoryProvider
import io.ask.api.preprocess.CommandOptionsFactory
import spock.lang.Specification

class GradleCommandExecutorTest extends Specification {

    WorkingDirectoryProvider pwd = [ 'getWorkingDirectory': { -> return new File('/Users/ethall/workspace/personal/gradle-semantic-versioning') } ] as WorkingDirectoryProvider

    def 'oh god why'() {
        when:
        def executor = new GradleCommandExecutor(pwd)

        then:
        executor.execute(CommandOptionsFactory.create('gradle build'.split(' ')))

    }
}
