package io.ask.bootstrap.ivy

import groovy.transform.TupleConstructor

@TupleConstructor
class DependencyConfiguration {
    String mavenRepository
    List<Dependency> dependencies

    boolean shouldResolve() {
        return mavenRepository && dependencies
    }
}
