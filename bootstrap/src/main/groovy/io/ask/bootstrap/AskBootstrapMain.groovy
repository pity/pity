package io.ask.bootstrap

import com.google.inject.AbstractModule
import com.google.inject.Guice
import com.google.inject.Injector
import groovy.json.JsonBuilder
import groovy.util.logging.Slf4j
import io.ask.api.PropertyValueProvider
import io.ask.api.preprocess.CommandOptions
import io.ask.bootstrap.environment.BootstrapEnvironmentCollector
import io.ask.bootstrap.execution.CommandExecutorRunner
import io.ask.bootstrap.ivy.DependencyResolver
import io.ask.bootstrap.preprocess.PreProcessorExecutor
import org.codehaus.groovy.tools.RootLoader

@Slf4j
class AskBootstrapMain {

    public static void main(String[] args) {
        def cliArgumentProvider = new CliArgumentProvider(args)

        if (cliArgumentProvider.isHelp()) {
            println cliArgumentProvider.usage()
            return
        }

        PropertyFinder injectorFinder = new PropertyFinder()

        def dependencyResolver = new DependencyResolver(
            injectorFinder,
            cliArgumentProvider.ivyConfiguration,
            this.getClassLoader().rootLoader as RootLoader)

        dependencyResolver.resolveDependencies()

        Injector injector = getInjector(cliArgumentProvider, injectorFinder)
        log.info("Loading version {}", injector.getInstance(PropertyValueProvider).getProperty('ask.version'))

        Map<String, Map> collectedData = [:]

        if (cliArgumentProvider.isEnvironmentCollectionEnabled()) {
            log.debug("Collecting Environmental Data")
            collectedData['env'] = collectEnvironmentData(injector)
        } else {
            log.debug("NOT Collecting Environmental Data")
        }

        if (cliArgumentProvider.isCommandExecution()) {
            def commandOptions = cliArgumentProvider.getExecutionCommandOptions()

            log.info("Executing command: {}", commandOptions)
            collectedData['process'] = collectExecutionData(injector, commandOptions)
        }

        new File('generated-data.json').text = new JsonBuilder(collectedData).toPrettyString()
    }

    private static Injector getInjector(CliArgumentProvider cliArgumentProvider,
                                        PropertyFinder injectorFinder) {
        def allInjectors = [] as List<AbstractModule>
        allInjectors.add(new BootstrapInjector(cliArgumentProvider.getTargetDirectory(), injectorFinder.createPropertyValueProvider()))
        allInjectors.addAll(injectorFinder.findInjectors())

        Guice.createInjector()
        def injector = Guice.createInjector(allInjectors)
        return injector
    }

    private static Map<String, Object> collectEnvironmentData(Injector injector) {
        def results = injector.getInstance(BootstrapEnvironmentCollector).collectEnvironmentData()

        return results.collectEntries { [it.collectorName, it.environmentResults] }
    }

    private static Map<String, Object> collectExecutionData(Injector injector, CommandOptions executionCommandOptions) {
        def commandOptions = injector
            .getInstance(PreProcessorExecutor)
            .processCommandOptions(executionCommandOptions)

        return injector
            .getInstance(CommandExecutorRunner)
            .execute(commandOptions)
            .collectEntries { [it.commandExecutorClass, it] }

    }
}
