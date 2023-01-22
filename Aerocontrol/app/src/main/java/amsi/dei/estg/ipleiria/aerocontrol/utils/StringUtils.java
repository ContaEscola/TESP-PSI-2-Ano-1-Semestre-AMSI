package amsi.dei.estg.ipleiria.aerocontrol.utils;

public class StringUtils {
    public static String convertWhiteSpaceToUnderscores(String stringToBeConverted) {
        return stringToBeConverted.replaceAll(" ", "_");
    }
}
