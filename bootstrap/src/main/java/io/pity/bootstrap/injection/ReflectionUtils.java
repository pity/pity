package io.pity.bootstrap.injection;

import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.util.Set;

public class ReflectionUtils {

    public Reflections createPityPluginScanner() {
        ConfigurationBuilder configuration = ConfigurationBuilder.build()
            .addScanners(new ResourcesScanner())
            .setUrls(ClasspathHelper.forPackage("META-INF/pity-plugins"));
        return new Reflections(configuration);
    }

    public <T> Set<Class<? extends T>> searchForClass(Class<T> clazz) {
        ConfigurationBuilder configuration = ConfigurationBuilder.build();
        Reflections reflections = new Reflections(configuration);
        return reflections.getSubTypesOf(clazz);
    }
}
