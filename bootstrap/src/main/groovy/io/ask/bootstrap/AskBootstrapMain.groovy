package io.ask.bootstrap
import com.google.inject.AbstractModule
import com.google.inject.Guice
import com.google.inject.Injector
import groovy.json.JsonBuilder
import groovy.util.logging.Slf4j
import io.ask.api.preprocess.CommandOptions
import io.ask.bootstrap.environment.BootstrapEnvironmentCollector
import io.ask.bootstrap.execution.CommandExecutorRunner
import io.ask.bootstrap.ivy.DependencyResolver
import io.ask.bootstrap.preprocess.PreProcessorExecutor
import io.ask.bootstrap.provider.PropertyValueProviderImpl
import org.codehaus.groovy.tools.RootLoader

@Slf4j
class AskBootstrapMain {

    public static void main(String[] args) {
        def cliArgumentProvider = new CliArgumentProvider(args)

        if(cliArgumentProvider.isHelp()){
            println cliArgumentProvider.usage()
            return
        }

        def dependencyResolver = new DependencyResolver(
            cliArgumentProvider.ivyConfiguration,
            this.getClassLoader().rootLoader as RootLoader)
        dependencyResolver.resolveDependencies()

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
        def injectorFinder = new PropertyFinder()
        def propertyValueProviderImpl = new PropertyValueProviderImpl(injectorFinder.findAskProperties())

        def allInjectors = [] as List<AbstractModule>
        allInjectors.add(new BootstrapInjector(cliArgumentProvider.getTargetDirectory(), propertyValueProviderImpl))
        allInjectors.addAll(injectorFinder.findInjectors())

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
