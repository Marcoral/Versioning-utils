package com.github.marcoral.versioning;

import java.util.function.Predicate;

//Thread-safe
public class VersionPattern implements Predicate<Version> {
    private final Integer releaseVersion;
    private final Integer majorVersion;
    private final Integer minorVersion;

    public VersionPattern(String version) {
        String[] versionSegments = version.split(Utils.VERSION_DELIMITER);
        if(versionSegments.length != 3)
            throw new IllegalArgumentException("Attempted to create VersionPattern object from " + version + " but only versions of format \"x.x.x\" are supported.");
        this.releaseVersion = tryParseVersionInt(versionSegments[0], "releaseVersion");
        this.majorVersion = tryParseVersionInt(versionSegments[1], "majorVersion");
        this.minorVersion = tryParseVersionInt(versionSegments[2], "minorVersion");
    }

    @Override
    public boolean test(Version version) {
        return
                fieldMatches(releaseVersion, version.getReleaseVersion()) &&
                fieldMatches(majorVersion, version.getMajorVersion()) &&
                fieldMatches(minorVersion, version.getMinorVersion());
    }

    private boolean fieldMatches(Integer patternFieldValue, int versionFieldValue) {
        return patternFieldValue == null || patternFieldValue == versionFieldValue;
    }

    private Integer tryParseVersionInt(String value, String fieldName) {
        if(value.equals("x"))
            return null;
        return Utils.parseVersionFieldOrThrow(value, fieldName);
    }

    public VersionPattern(Integer releaseVersion, Integer majorVersion, Integer minorVersion) {
        Utils.requireNonNegative(releaseVersion, "releaseVersion");
        Utils.requireNonNegative(majorVersion, "majorVersion");
        Utils.requireNonNegative(minorVersion, "minorVersion");
        this.releaseVersion = releaseVersion;
        this.majorVersion = majorVersion;
        this.minorVersion = minorVersion;
    }

    public Integer getReleaseVersion() {
        return releaseVersion;
    }

    public Integer getMajorVersion() {
        return majorVersion;
    }

    public Integer getMinorVersion() {
        return minorVersion;
    }

    @Override
    public String toString() {
        return String.format("VersionPattern: %s.%s.%s",
                versionStringOrX(releaseVersion),
                versionStringOrX(majorVersion),
                versionStringOrX(minorVersion));
    }

    private String versionStringOrX(Integer value) {
        return value == null? "x" : String.valueOf(value);
    }
}
