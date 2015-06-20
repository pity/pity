package io.pity.wrapper.ivy

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

    Dependency(String group, String name, String version) {
        this.group = group
        this.name = name
        this.version = version
    }

    @Override
    String toString() {
        return group + ':' + name + ':' + version
    }
}
