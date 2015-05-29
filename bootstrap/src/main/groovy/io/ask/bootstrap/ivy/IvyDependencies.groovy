package io.ask.bootstrap.ivy

import groovy.transform.TupleConstructor

@TupleConstructor
class IvyDependencies {
    String configurationLocation
    List<String> dependencies

    boolean shouldResolve() {
        return configurationLocation && dependencies
    }
}
