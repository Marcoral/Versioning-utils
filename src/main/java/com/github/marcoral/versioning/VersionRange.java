package com.github.marcoral.versioning;

import java.util.function.Predicate;

//Thread-safe
public class VersionRange implements Predicate<Version> {
    private final Version minVersion, maxVersion;

    public static VersionRange minVersion(String minVersion) {
        return new VersionRange(minVersion, null);
    }

    public static VersionRange minVersion(Version minVersion) {
        return new VersionRange(minVersion, null);
    }

    public static VersionRange maxVersion(String maxVersion) {
        return new VersionRange(null, maxVersion);
    }

    public static VersionRange maxVersion(Version maxVersion) {
        return new VersionRange(null, maxVersion);
    }

    public VersionRange(String minVersion, String maxVersion) {
        this(
                minVersion == null? null : new Version(minVersion),
                maxVersion == null? null : new Version(maxVersion)
        );
    }

    public VersionRange(Version minVersion, Version maxVersion) {
        validateAtLeastOneIsNotNull(minVersion, maxVersion);
        validateMinNotGreaterThanMax(minVersion, maxVersion);
        this.minVersion = minVersion;
        this.maxVersion = maxVersion;
    }

    private void validateAtLeastOneIsNotNull(Version minVersion, Version maxVersion) {
        if(minVersion == null && maxVersion == null)
            throw new IllegalArgumentException("You must specify at least either minVersion or maxVersion!");
    }

    private void validateMinNotGreaterThanMax(Version minVersion, Version maxVersion) {
        if(minVersion == null || maxVersion == null)
            return;
        if(minVersion.compareTo(maxVersion) > 0)
            throw new IllegalArgumentException(
                    String.format("minVersion (%s) is greater than maxVersion (%s)!",
                            minVersion, maxVersion));
    }

    @Override
    public boolean test(Version version) {
        if(minVersion != null && minVersion.compareTo(version) > 0)
            return false;
        if(maxVersion != null && maxVersion.compareTo(version) < 0)
            return false;
        return true;
    }

    @Override
    public String toString() {
        if(minVersion == null)
            return "VersionRange: Max version: " + maxVersion;
        if(maxVersion == null)
            return "VersionRange: Min version: " + minVersion;
        return String.format("VersionRange: %s - %s", minVersion, maxVersion);
    }
}
