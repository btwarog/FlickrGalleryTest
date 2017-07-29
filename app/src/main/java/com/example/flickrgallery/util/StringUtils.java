package com.example.flickrgallery.util;

import java.text.DecimalFormat;
import java.util.List;

public class StringUtils {

    public static DecimalFormat sDecimalFormat = new DecimalFormat("#.##");

    /**
     * Format distance to string ready to display on view
     *
     * @param distance - distance in meters
     * @return - generated string
     */
    public static String prepareDistanceString(double distance) {
        String distanceString = "";
        if (distance < 0) {
            throw new IllegalArgumentException("Distance cant be negative");
        }
        if (distance >= 10000) {
            distanceString = (int) (distance / 1000) + " km";
        } else if (distance > 1000 && distance < 10000) {
            distanceString = sDecimalFormat.format(distance / 1000) + " km";
        } else {
            distanceString = (int) distance + " m";
        }
        return distanceString;
    }

    public static <T> String buildListWithSeparator(List<T> items) {
        return buildListWithSeparator(items, (StringMaker<T>) null);
    }

    public static <T> String buildListWithSeparator(List<T> items, StringMaker<T> stringMaker) {
        return buildListWithSeparator(items, ", ", stringMaker);
    }

    public static <T> String buildListWithSeparator(List<T> items, String separator) {
        return buildListWithSeparator(items, separator, null);
    }

    public static <T> String buildListWithSeparator(List<T> items, String separator, StringMaker<T> stringMaker) {
        if (stringMaker == null) {
            stringMaker = new StringMaker<T>() {
                @Override
                public String makeString(T item) {
                    return item.toString();
                }
            };
        }

        StringBuilder idsBuilder = new StringBuilder();
        boolean isFirst = true;
        for (T item : items) {
            if (!isFirst) {
                idsBuilder.append(separator);
            }
            isFirst = false;
            String s = stringMaker.makeString(item);
            idsBuilder.append(s);
        }

        return idsBuilder.toString();
    }

    public static CharSequence trimTrailingWhitespace(CharSequence source) {
        if (source == null)
            return "";

        int i = source.length();

        while (--i >= 0 && Character.isWhitespace(source.charAt(i))) {
        }

        return source.subSequence(0, i + 1);
    }

    public interface StringMaker<T> {
        String makeString(T item);
    }
}