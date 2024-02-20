package com.example.portalbackend.util.number;

public class NumberUtils {
    public static boolean isNumeric(String str) {
        if (str == null)
            return false;
        return str.matches("-?\\d+(\\.\\d+)?");
    }
    public static String formatToThreeDigits(String strNum) {
        int num = Integer.parseInt(strNum);
        return String.format("%03d", num);
    }
}
