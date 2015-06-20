package io.pity.wrapper.ivy

import groovy.transform.TupleConstructor

@TupleConstructor
class DependencyConfiguration {
    URL configurationFile
    File cacheDir
    List<Dependency> dependencies = []

    boolean shouldResolve() {
        return null != dependencies && !dependencies.isEmpty()
    }
}
