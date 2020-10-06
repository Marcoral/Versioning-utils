# Versioning utils

This tiny library allows you to perform basic operations on Version objects, consisting of three parts, separated by a dot (release, major and minor respectively), for example: 1.12.2. You can check if a given version belongs to a certain range or pattern (e.g. 1.12.x)


## Examples


    public static void main(String[] args) {
        System.out.println(new Version("1.12.4"));  //Prints "Version: 1.12.4"
        System.out.println(new Version("1.12.4").compareTo(new Version("1.12.6")));  //Prints negative number
        System.out.println(new Version("1.13.1").compareTo(new Version("1.12.6")));  //Prints positive number

        System.out.println(new VersionPattern("1.12.x")); //Prints "VersionPattern: 1.12.x"
        System.out.println(new VersionPattern("1.12.x").test(new Version("1.12.4"))); //Prints "true"
        System.out.println(new VersionPattern("1.12.x").test(new Version("1.12.6"))); //Prints "true"
        System.out.println(new VersionPattern("1.12.x").test(new Version("1.13.1"))); //Prints "false"

        System.out.println(new VersionRange("1.0.0", "1.13.0").test(new Version("1.12.4")));  //Prints "true"
        System.out.println(new VersionRange("1.0.0", "1.13.0").test(new Version("1.12.15")));  //Prints "true"
        System.out.println(new VersionRange("1.0.0", "1.13.0").test(new Version("1.13.2")));  //Prints "false"

        System.out.println(VersionRange.minVersion("1.12.5").test(new Version("1.10.2")));  //Prints "false"
        System.out.println(VersionRange.maxVersion("1.12.5").test(new Version("1.10.2")));  //Prints "true"
        System.out.println(VersionRange.minVersion("1.12.5").test(new Version("1.13.2")));  //Prints "true"
        System.out.println(VersionRange.maxVersion("1.12.5").test(new Version("1.13.2")));  //Prints "false"

        matcherMatches(new VersionPattern("1.x.x"), new Version("1.0.0"));
        matcherMatches(new VersionRange("0.8.0", "2.0.0"), new Version("1.0.0"));
    }

    //This method was created to show you, that both VersionPattern and VersionRange implements Predicate<Version>
    private static boolean matcherMatches(Predicate<Version> matcher, Version version) {
        return matcher.test(version);
    }
