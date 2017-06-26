package com.j0rsa.caricyno.application;

import java.util.function.Function;
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

    private static Function<String, Boolean> linkIsImage = link -> {
        final String trimmedLink = link.trim();
        return trimmedLink.endsWith("jpg") ||
                trimmedLink.endsWith("jpeg") ||
                trimmedLink.endsWith("png") ||
                trimmedLink.endsWith("gif") ||
                trimmedLink.endsWith("bmp");
    };
    private static Function<String, Boolean> linkIsYoutubeLink = link -> {
        final String trimmedLink = link.trim();
        return trimmedLink.startsWith("https://www.youtube.com/watch?v=");
    };

    public static String wrapLinks(String htmlString) {
        Matcher matcher = pattern.matcher(htmlString);
        StringBuffer sb = new StringBuffer(htmlString.length());
        while (matcher.find()) {
            String text = matcher.group(1);
            matcher.appendReplacement(sb, wrapLink(Matcher.quoteReplacement(text)));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    private static String wrapLink(final String text) {
        final String lowerCasedText = text.toLowerCase().trim();
        if (linkIsImage.apply(lowerCasedText)) {
            return String.format("<img src='%s' alt=''>", text);
        }
        if (linkIsYoutubeLink.apply(lowerCasedText)) {
            return wrapYoutubeLink(text);
        }
        return String.format("<a href='%1$s'>%1$s</a>", text);

    }

    private static String wrapYoutubeLink(String text) {
        String trimmedText = text.trim();
        String videoId = trimmedText.substring("https://www.youtube.com/watch?v=".length());
        return String.format(
                "<iframe width=\"560\" height=\"315\" " +
                        "src=\"https://www.youtube.com/embed/%s\" " +
                        "frameborder=\"0\" " +
                        "allowfullscreen>" +
                        "</iframe>", videoId);
    }
}
