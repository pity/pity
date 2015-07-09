package io.pity.bootstrap

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import io.pity.api.reporting.CollectionResults
import io.pity.bootstrap.injection.InjectorCreators
import io.pity.bootstrap.injection.injectors.TaskInjector
import io.pity.bootstrap.provider.cli.CliArgumentProviderImpl
import io.pity.bootstrap.publish.PublishManager

@Slf4j
@CompileStatic
class AskBootstrapMain {

    public static void main(String[] args) {
        def instanceInjector = new InjectorCreators()
        def initializationInjector = instanceInjector.createInitializationInjector()

        def cliArgumentProvider = new CliArgumentProviderImpl(args, initializationInjector.findCliOptions())
        TaskInjector taskInjector = instanceInjector.findTaskInjectors(cliArgumentProvider)

        if (cliArgumentProvider.isHelp()) {
            println taskInjector.getInstance(HelpOutputGenerator).getHelpOutput(cliArgumentProvider)
            return
        }

        log.info("Loading version {}", taskInjector.getPropertyValueProvider().getProperty('ask.version'))

        def publishManager = new PublishManager(cliArgumentProvider, instanceInjector.getPropertyFinder(), taskInjector)
        if(!publishManager.shouldExecutionContinue()) {
            return;
        }

        taskInjector.getInstance(RootExecutor).executeAll()

        publishManager.publishReport(taskInjector.getInstance(CollectionResults))
    }
}
