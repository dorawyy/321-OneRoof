package ca.oneroof.oneroof;

public class Utils {
    public static String formatDollars(int cents) {
        return String.format("%d.%02d", cents / 100, cents % 100);
    }
}
