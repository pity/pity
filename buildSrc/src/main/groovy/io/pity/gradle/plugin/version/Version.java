package io.pity.gradle.plugin.version;

import org.gradle.api.GradleException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Version {
    public static final Pattern PATTERN = Pattern.compile("(\\d+)\\.(\\d+)\\.(\\d+)");

    private final Integer major;
    private final Integer minor;
    private final Integer patch;
    private final boolean snapshot;

    public Version(final String versionString) {
        Matcher matcher = PATTERN.matcher(versionString);

        if (!matcher.matches()) {
            throw new GradleException("Version must follow the `1.2.3` format, found " + versionString);
        }

        major = Integer.parseInt(matcher.group(1));
        minor = Integer.parseInt(matcher.group(2));
        patch = Integer.parseInt(matcher.group(3));
        snapshot = false;
    }

    public Version(Integer major, Integer minor, Integer patch, boolean snapshot) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
        this.snapshot = snapshot;
    }

    public Version(Integer major, Integer minor, Integer patch) {
        this(major, minor, patch, false);
    }

    public Version asSnapshot() {
        return new Version(major, minor, patch, true);
    }

    public Version withNextPatch() {
        return new Version(major, minor, patch + 1);
    }

    public Version withNextMajor() {
        return new Version(major + 1, 0, 0);
    }

    public Version withNextMinor() {
        return new Version(major, minor + 1, 0);
    }

    @Override
    public String toString() {
        String version = String.format("%d.%d.%d", major, minor, patch);
        if (snapshot) {
            version += "-SNAPSHOT";
        }

        return version;
    }

    public boolean isSnapshot() {
        return snapshot;
    }
}
