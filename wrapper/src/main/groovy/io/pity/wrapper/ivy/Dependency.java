package io.pity.wrapper.ivy;

public class Dependency {

    private String group;
    private String name;
    private String version;

    public Dependency(String coordinate) {
        String[] split = coordinate.split(":");
        group = split[0];
        name = split[1];
        version = split[2];
    }

    public Dependency(String group, String name, String version) {
        this.group = group;
        this.name = name;
        this.version = version;
    }

    @Override
    public String toString() {
        return group + ":" + name + ":" + version;
    }

    public String getGroup() {
        return group;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }
}
