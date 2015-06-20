package io.pity.bootstrap.ivy

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

    @Override
    String toString() {
        return group + ':' + name + ':' + version
    }
}
