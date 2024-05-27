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


    private static final String[] UNIDADES = {
            "", "uno", "dos", "tres", "cuatro", "cinco", "seis", "siete", "ocho", "nueve", "diez",
            "once", "doce", "trece", "catorce", "quince", "dieciséis", "diecisiete", "dieciocho", "diecinueve", "veinte"
    };

    private static final String[] DECENAS = {
            "", "", "veinti", "treinta", "cuarenta", "cincuenta", "sesenta", "setenta", "ochenta", "noventa"
    };

    private static final String[] CENTENAS = {
            "", "ciento", "doscientos", "trescientos", "cuatrocientos", "quinientos",
            "seiscientos", "setecientos", "ochocientos", "novecientos"
    };

    public static String convert(int number) {
        if (number == 0) {
            return "cero";
        }
        if (number == 100) {
            return "cien";
        }

        String words = "";
        if (number >= 1000) {
            int thousands = number / 1000;
            if (thousands == 1) {
                words += "mil";
            } else {
                words += convert(thousands) + " mil";
            }
            number %= 1000;
        }

        if (number >= 100) {
            int hundreds = number / 100;
            words += " " + CENTENAS[hundreds];
            number %= 100;
        }

        if (number >= 30) {
            int tens = number / 10;
            words += " " + DECENAS[tens];
            number %= 10;
        }

        if (number > 0 && number <= 20) {
            words += " " + UNIDADES[number];
        } else if (number > 20) {
            words += " y " + UNIDADES[number];
        }

        return words.trim().toUpperCase();
    }
}
