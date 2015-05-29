package io.ask.bootstrap.ivy

import groovy.grape.Grape

class Resolver {
    final DependencyConfiguration ivy

    public Resolver(DependencyConfiguration ivy) {
        this.ivy = ivy
    }

    void resolveDependencies() {
        if (ivy.mavenRepository) {
            Grape.addResolver(name: 'custom', root: ivy.mavenRepository, m2Compatible: true)
        }
        ivy.dependencies.each { Grape.grab(it as Map) }
    }
}
