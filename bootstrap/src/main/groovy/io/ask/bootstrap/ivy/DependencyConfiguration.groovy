package io.ask.bootstrap.ivy

import groovy.transform.TupleConstructor

@TupleConstructor
class DependencyConfiguration {
    File configurationFile
    File cacheDir
    List<Dependency> dependencies

    boolean shouldResolve() {
        return null != dependencies && !dependencies.isEmpty()
    }
}
