package io.ask.bootstrap
import com.google.inject.AbstractModule
import com.google.inject.Guice
import com.google.inject.Injector
import groovy.json.JsonBuilder
import groovy.util.logging.Slf4j
import io.ask.api.preprocess.CommandOptions
import io.ask.bootstrap.environment.BootstrapEnvironmentCollector
import io.ask.bootstrap.execution.CommandExecutorRunner
import io.ask.bootstrap.ivy.IvyResolver
import io.ask.bootstrap.preprocess.PreProcessorExecutor

@Slf4j
class AskBootstrapMain {

    public static void main(String[] args) {
        def cliArgumentProvider = new CliArgumentProvider(args)

        if(cliArgumentProvider.isHelp()){
            println cliArgumentProvider.usage()
            return
        }

        new IvyResolver(cliArgumentProvider.ivyConfiguration).resolveDependencies()

        Injector injector = getInjector(cliArgumentProvider)

        if(cliArgumentProvider.isEnvironmentCollectionEnabled()) {
            log.debug("Collecting Environmental Data")
            collectEnvironmentData(injector)
        } else {
            log.debug("NOT Environmental Data")
        }

        if(cliArgumentProvider.isCommandExecution()) {
            def commandOptions = cliArgumentProvider.getExecutionCommandOptions()

            log.info("Executing command: {}", commandOptions)
            collectExecutionData(injector, commandOptions)
        }

    }

    private static Injector getInjector(CliArgumentProvider cliArgumentProvider) {
        def allInjectors = [new BootstrapInjector(cliArgumentProvider.getTargetDirectory())] as List<AbstractModule>
        allInjectors.addAll(new InjectorFinder().findInjectors())

        Guice.createInjector()
        def injector = Guice.createInjector(allInjectors)
        injector
    }

    private static void collectEnvironmentData(Injector injector) {
        def results = injector.getInstance(BootstrapEnvironmentCollector).collectEnvironmentData()

        def processedResults = results.collectEntries { [it.collectorName, it.environmentResults] }

        new File('generated-data.json').text = new JsonBuilder(processedResults).toPrettyString()
    }

    private static void collectExecutionData(Injector injector, CommandOptions executionCommandOptions) {
        def commandOptions = injector
            .getInstance(PreProcessorExecutor)
            .processCommandOptions(executionCommandOptions)

        def executionResult = injector.getInstance(CommandExecutorRunner).execute(commandOptions)
        println executionResult;
    }
}
