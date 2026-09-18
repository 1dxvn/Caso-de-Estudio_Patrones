package service;

import java.util.Locale;

public class Format {

    public static String money(double value) {
        long rounded = Math.round(value);
        return String.format(Locale.GERMANY, "%,d", rounded);
    }

    public static String signed(double value) {
        if (value >= 0) {
            return String.format(Locale.US, "+%.2f", value);
        }
        return String.format(Locale.US, "%.2f", value);
    }
}
