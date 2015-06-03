package io.ask.tasks.execution.gradle
import io.ask.api.WorkingDirectoryProvider
import io.ask.api.preprocess.CommandOptionsFactory
import spock.lang.Specification

class GradleCommandExecutorTest extends Specification {


    WorkingDirectoryProvider pwd = [
        'getWorkingDirectory': { -> return new File('build.gradle').getAbsoluteFile().getParentFile() }
    ] as WorkingDirectoryProvider

    def 'run tasks on local project'() {
        when:
        println pwd.workingDirectory.getAbsolutePath()
        def executor = new GradleCommandExecutor(pwd)

        then:
        executor.execute(CommandOptionsFactory.create('gradle tasks'.split(' ')))

    }
}
