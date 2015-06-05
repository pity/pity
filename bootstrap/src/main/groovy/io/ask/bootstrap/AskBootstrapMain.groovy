package io.ask.bootstrap
import com.google.inject.AbstractModule
import com.google.inject.Guice
import com.google.inject.Injector
import groovy.util.logging.Slf4j
import io.ask.api.PropertyValueProvider
import io.ask.api.environment.EnvironmentData
import io.ask.api.execution.CommandExecutionResults
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

        def envData = null
        def executionData = null;

        if (cliArgumentProvider.isEnvironmentCollectionEnabled()) {
            log.debug("Collecting Environmental Data")
            envData = collectEnvironmentData(injector)
        } else {
            log.debug("NOT Collecting Environmental Data")
        }

        if (cliArgumentProvider.isCommandExecution()) {
            def commandOptions = cliArgumentProvider.getExecutionCommandOptions()

            log.info("Executing command: {}", commandOptions)
            executionData = collectExecutionData(injector, commandOptions)
        }

        new OutputGenerator(envData, executionData).writeToFile(new File('generated-data.xml'))
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

    private static Set<EnvironmentData> collectEnvironmentData(Injector injector) {
        return injector.getInstance(BootstrapEnvironmentCollector).collectEnvironmentData()
    }


    private static List<CommandExecutionResults> collectExecutionData(Injector injector,
                                             CommandOptions executionCommandOptions) {
        def commandOptions = injector
            .getInstance(PreProcessorExecutor)
            .processCommandOptions(executionCommandOptions)

        return injector
            .getInstance(CommandExecutorRunner)
            .execute(commandOptions)
    }
}
