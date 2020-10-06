package com.github.marcoral.versioning;

import java.util.Comparator;
import java.util.Objects;

//Thread-safe
public class Version implements Comparable<Version> {
    private final int releaseVersion;
    private final int majorVersion;
    private final int minorVersion;

    public Version(String version) {
        String[] versionSegments = version.split(Utils.VERSION_DELIMITER);
        if(versionSegments.length != 3)
            throw new IllegalArgumentException("Attempted to create VersionPattern object from " + version + " but only versions of format \"" + Utils.ALLOWED_FORMAT + "\" are supported.");
        this.releaseVersion = Utils.parseVersionFieldOrThrow(versionSegments[0], "releaseVersion");
        this.majorVersion = Utils.parseVersionFieldOrThrow(versionSegments[1], "majorVersion");
        this.minorVersion = Utils.parseVersionFieldOrThrow(versionSegments[2], "minorVersion");
    }

    public Version(int releaseVersion, int majorVersion, int minorVersion) {
        Utils.requireNonNegative(releaseVersion, "releaseVersion");
        Utils.requireNonNegative(majorVersion, "majorVersion");
        Utils.requireNonNegative(minorVersion, "minorVersion");
        this.releaseVersion = releaseVersion;
        this.majorVersion = majorVersion;
        this.minorVersion = minorVersion;
    }


    @Override
    public int compareTo(Version o) {
        return COMPARATOR.compare(this, o);
    }

    private static final Comparator<Version> COMPARATOR = Comparator.<Version>
            comparingInt(s -> s.releaseVersion)
            .thenComparing(s -> s.majorVersion)
            .thenComparing(s -> s.minorVersion);

    public int getReleaseVersion() {
        return releaseVersion;
    }

    public int getMajorVersion() {
        return majorVersion;
    }

    public int getMinorVersion() {
        return minorVersion;
    }

    @Override
    public String toString() {
        return "Version: " + toRawVersionString();
    }

    public String toRawVersionString() {
        return String.format("%d.%d.%d", releaseVersion, majorVersion, minorVersion);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Version version = (Version) o;
        return releaseVersion == version.releaseVersion &&
                majorVersion == version.majorVersion &&
                minorVersion == version.minorVersion;
    }

    @Override
    public int hashCode() {
        return Objects.hash(releaseVersion, majorVersion, minorVersion);
    }
}
