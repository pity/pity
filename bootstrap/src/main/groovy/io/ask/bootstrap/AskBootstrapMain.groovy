package io.ask.bootstrap
import com.google.inject.AbstractModule
import com.google.inject.Guice
import com.google.inject.Injector
import groovy.json.JsonBuilder
import groovy.util.logging.Slf4j
import io.ask.api.preprocess.CommandOptionsFactory
import io.ask.bootstrap.environment.BootstrapEnvironmentCollector
import io.ask.bootstrap.execution.CommandExecutorRunner
import io.ask.bootstrap.preprocess.PreProcessorExecutor

@Slf4j
class AskBootstrapMain {

    public static void main(String[] args) {

        Injector injector = getInjector()

        collectEnvironmentData(injector)

        collectExecutionData(injector, args)

    }

    private static Injector getInjector() {
        def allInjectors = [new BootstrapInjector(new File('').getAbsoluteFile())] as List<AbstractModule>
        allInjectors.addAll(new InjectorFinder().findInjectors())

        def injector = Guice.createInjector(allInjectors)
        injector
    }

    private static void collectEnvironmentData(Injector injector) {
        def results = injector.getInstance(BootstrapEnvironmentCollector).collectEnvironmentData()

        def processedResults = results.collectEntries { [it.collectorName, it.environmentResults] }

        new File('generated-data.json').text = new JsonBuilder(processedResults).toPrettyString()
    }

    private static void collectExecutionData(Injector injector, String[] args) {
        def commandOptions = injector
            .getInstance(PreProcessorExecutor)
            .processCommandOptions(CommandOptionsFactory.create(args))

        def executionResult = injector.getInstance(CommandExecutorRunner).execute(commandOptions)
        println executionResult;
    }
}
