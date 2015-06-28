package io.pity.bootstrap
import com.google.inject.Injector
import com.google.inject.Key
import com.google.inject.TypeLiteral
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import io.pity.api.PropertyValueProvider
import io.pity.api.cli.CliOptionConfigurer
import io.pity.api.reporting.CollectionResults
import io.pity.bootstrap.injection.InjectorCreators
import io.pity.bootstrap.provider.cli.CliArgumentProviderImpl
import io.pity.bootstrap.publish.PublishManager

@Slf4j
@CompileStatic
class AskBootstrapMain {

    static final TypeLiteral cliOptionConfigurerSet = new TypeLiteral<Set<CliOptionConfigurer>>() { }

    public static void main(String[] args) {
        def instanceInjector = new InjectorCreators()
        def bootstrapInjector = instanceInjector.findBootstrapInjectors()

        def cliProviders = (Set<CliOptionConfigurer>) bootstrapInjector.getInstance(Key.get(cliOptionConfigurerSet))
        def cliArgumentProvider = new CliArgumentProviderImpl(args, cliProviders)

        if (cliArgumentProvider.isHelp()) {
            println cliArgumentProvider.usage()
            return
        }

        Injector injector = instanceInjector.findTaskInjectors(cliArgumentProvider)
        log.info("Loading version {}", injector.getInstance(PropertyValueProvider).getProperty('ask.version'))

        def publishManager = new PublishManager(cliArgumentProvider, instanceInjector.getPropertyFinder(), injector)
        if(!publishManager.shouldExecutionContinue()) {
            return;
        }

        injector.getInstance(RootExecutor).executeAll()

        publishManager.publishReport(injector.getInstance(CollectionResults))
    }
}
