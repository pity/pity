package io.pity.wrapper;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import io.pity.wrapper.ivy.DependencyConfiguration;
import io.pity.wrapper.ivy.DependencyResolver;
import io.pity.wrapper.ivy.IvyConfiguration;
import io.pity.wrapper.ivy.IvyLogger;
import org.apache.ivy.util.Message;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.ParseException;

public class WrapperMain {
    public static final org.slf4j.Logger logger = LoggerFactory.getLogger(WrapperMain.class);

    public static void main(String[] args) throws IOException, URISyntaxException, ClassNotFoundException,
        ParseException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        if (contains(args, "--debug")) {
            ((Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME)).setLevel(Level.DEBUG);
        } else if (contains(args, "--silent")) {
            ((Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME)).setLevel(Level.ERROR);
        } else if (contains(args, "--trace")) {
            ((Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME)).setLevel(Level.TRACE);
        }


        Message.setDefaultLogger(new IvyLogger(args));

        long startIvyTime = System.currentTimeMillis();
        URLClassLoader loader = createNewClassLoader();
        long endIvyTime = System.currentTimeMillis();
        logger.trace("Ivy Resolve took {}ms", endIvyTime - startIvyTime);

        long startTime = System.currentTimeMillis();
        executeMain(args, loader);
        long endTime = System.currentTimeMillis();
        logger.trace("Total execution time: {}ms", endTime - startTime);
    }

    private static void executeMain(Object args, URLClassLoader loader) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Class<?> mainClass = Class.forName("io.pity.bootstrap.AskBootstrapMain", false, loader);
        Method method = mainClass.getDeclaredMethod("main", String[].class);
        method.invoke(null, args);
    }

    private static URLClassLoader createNewClassLoader() throws URISyntaxException, IOException, ParseException {
        IvyConfiguration ivyConfiguration = new IvyConfiguration();
        ivyConfiguration.update();
        DependencyConfiguration configuration = new DependencyConfiguration(ivyConfiguration, null);

        URL[] urls = new DependencyResolver(configuration).resolveDependencies().toArray(new URL[0]);
        return new URLClassLoader(urls, WrapperMain.class.getClassLoader());
    }

    public static boolean contains(String[] args, String name) {
        for (String arg : args) {
            if (name.equals(arg)) {
                return true;
            }
        }
        return false;
    }

}
