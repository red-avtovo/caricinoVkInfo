package com.j0rsa.caricyno.application;

public class Utils {
    public static final String IMAGE_TEMPLATE = "<img src='$1' alt=''>";

    public static boolean isNotNull(Object o) {
        return !isNull(o);
    }

    public static boolean isNull(Object o) {
        return o == null;
    }

    public static String wrapLinks(String text) {
        return text
                .replaceAll("([http|ftp|https]+://[^<> ]+)", "<a href='$1'>$1</a>");
    }
}
