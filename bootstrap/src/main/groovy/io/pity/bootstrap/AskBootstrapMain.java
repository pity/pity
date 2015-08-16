package io.pity.bootstrap;

import io.pity.api.reporting.CollectionResults;
import io.pity.bootstrap.injection.InjectorCreators;
import io.pity.bootstrap.injection.injectors.InitializationInjector;
import io.pity.bootstrap.injection.injectors.TaskInjector;
import io.pity.bootstrap.provider.cli.CliArgumentProviderImpl;
import io.pity.bootstrap.publish.PublishManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class AskBootstrapMain {
    public static final Logger log = LoggerFactory.getLogger(AskBootstrapMain.class);

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        InjectorCreators instanceInjector = new InjectorCreators();
        InitializationInjector initializationInjector = instanceInjector.createInitializationInjector();

        CliArgumentProviderImpl cliArgumentProvider = new CliArgumentProviderImpl(args, initializationInjector.findCliOptions());
        TaskInjector taskInjector = instanceInjector.findTaskInjectors(cliArgumentProvider);

        if (cliArgumentProvider.isHelp()) {
            System.out.print(taskInjector.getInstance(HelpOutputGenerator.class).getHelpOutput(cliArgumentProvider));
            return;
        }

        log.info("Loading version {}", taskInjector.getPropertyValueProvider().getProperty("pity.version"));

        PublishManager publishManager = new PublishManager(taskInjector.getReportPublisher());
        if (!publishManager.shouldExecutionContinue()) {
            return;
        }

        taskInjector.getInstance(RootExecutor.class).executeAll();
        publishManager.publishReport(taskInjector.getInstance(CollectionResults.class));
    }

}
