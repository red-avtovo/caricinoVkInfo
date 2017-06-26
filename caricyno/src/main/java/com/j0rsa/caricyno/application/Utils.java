package com.j0rsa.caricyno.application;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static final String IMAGE_TEMPLATE = "<img src='$1' alt=''>";
    private static final Pattern pattern = Pattern.compile("([http|ftp|https]+://[^<> ]+)");

    public static boolean isNotNull(Object o) {
        return !isNull(o);
    }

    public static boolean isNull(Object o) {
        return o == null;
    }

    public static String wrapLinks(String htmlString) {
        Matcher matcher = pattern.matcher(htmlString);
        StringBuffer sb = new StringBuffer(htmlString.length());
        while (matcher.find()) {
            String text = matcher.group(1);
            matcher.appendReplacement(sb,
                    String.format("<a href='%1$s'>%1$s</a>",
                            Matcher.quoteReplacement(text))
            );
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}
