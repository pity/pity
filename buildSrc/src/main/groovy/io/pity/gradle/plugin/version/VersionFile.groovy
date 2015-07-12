package io.pity.gradle.plugin.version

import groovy.transform.CompileStatic

@CompileStatic
class VersionFile {

    public static final String VERSION = 'version'

    public static Version getVersion(File propertyFile) {
        def properties = new Properties()
        properties.load(propertyFile.newReader())
        return new Version(properties.getProperty(VERSION))
    }

    public static void writeVersionToFile(File propertyFile, Version version) {
        def properties = new Properties()
        properties.load(propertyFile.newReader())
        properties.put(VERSION, version.toString())
        properties.store(propertyFile.newWriter(), 'version container')
    }
}
