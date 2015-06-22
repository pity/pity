# Pity Plugins

Pity uses a plugin architecture for running tasks. Pity runs in two different phases that don't share contexts. The phases are 'bootstrap' and 'tasks'

## Bootstrap
Bootstrap phase happens first. Currently the only purpose of the bootstrap phase it to allow for commandline parameters to be added based on requirement of plugins.

To create a bootstrap plugin, you must add a property file at `META-INF/pity-plugins/*.properties` that defines the value `bootstrap-injector-class` is the fully qualified class reference. The class that is defined here must extend [AbstractModule](https://google.github.io/guice/api-docs/latest/javadoc/index.html?com/google/inject/AbstractModule.html)

An example of this can be found in the [bootstrap](https://github.com/pity/pity/tree/master/bootstrap) with the class [BootstrapInjector](https://github.com/pity/pity/blob/master/bootstrap/src/main/groovy/io/pity/bootstrap/injection/BootstrapInjector.groovy).

## Tasks
The task phase happens after the bootstrap phase. It also uses Guice (like the bootstrap phase) to create an 'injector' that
provides different ways to collect data.

To create a tasks plugin, you must add another field in a property file with the value `task-injector-class`. An example of this can be found with [BuiltinTaskInjector](https://github.com/pity/pity/blob/master/tasks/src/main/groovy/io/pity/tasks/injector/BuiltinTaskInjector.groovy).

