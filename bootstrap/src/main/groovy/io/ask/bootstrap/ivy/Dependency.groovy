package io.ask.bootstrap.ivy

class Dependency {
    String group
    String name
    String version

    Dependency(String coordinate) {
        def split = coordinate.split(':')
        group = split[0]
        name = split[1]
        version = split[2]
    }
}
