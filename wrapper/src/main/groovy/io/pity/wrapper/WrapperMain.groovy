package io.pity.wrapper

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.Logger
import io.pity.wrapper.ivy.DependencyConfiguration
import io.pity.wrapper.ivy.DependencyResolver
import io.pity.wrapper.ivy.IvyConfiguration
import io.pity.wrapper.ivy.IvyLogger
import org.apache.ivy.util.Message
import org.slf4j.LoggerFactory

public class WrapperMain {

    public static void main(String[] args) {

        if (args.contains('--debug')) {
            ((Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME)).setLevel(Level.DEBUG);
        } else if (args.contains('--silent')) {
            ((Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME)).setLevel(Level.ERROR);
        } else if (args.contains('--trace')) {
            ((Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME)).setLevel(Level.TRACE);
        }

        Message.defaultLogger = new IvyLogger(args);

        def ivyConfiguration = new IvyConfiguration()
        ivyConfiguration.update()
        def configuration = new DependencyConfiguration(ivyConfiguration.ivySettingUrl, null, ivyConfiguration.dependencies)

        def urls = new DependencyResolver(configuration).resolveDependencies().toArray(new URL[0])
        def loader = new URLClassLoader(urls, WrapperMain.getClassLoader())

        def mainClass = Class.forName('io.pity.bootstrap.AskBootstrapMain', false, loader)
        mainClass.'main'(args)
    }
}
