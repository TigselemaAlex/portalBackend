package com.example.portalbackend.util.user;

import java.util.Random;

public class UserUtil {

    public static String generatePassword() {
        //TODO: Implement password generation
        return "123456";
    }

    public static String generateRecoveryPassword(){
        int leftLimit = 48;
        int rightLimit = 122;
        int targetStringLength = 6;
        Random random = new Random();
        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
