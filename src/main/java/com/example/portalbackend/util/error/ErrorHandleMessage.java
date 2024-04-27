package com.example.portalbackend.util.error;

public class ErrorHandleMessage {

    public static String constraintKeyToMessage(String key){
        if (key.contains("name")){
            return key.replaceAll("name", "nombre");
        }
        if (key.contains("description")){
            return key.replaceAll("description", "descripción");
        }
        if (key.contains("email")){
            return key.replaceAll("email", "correo electrónico");
        }
        if (key.contains("dni")){
            return key.replaceAll("dni", "D.N.I.");
        }
        if (key.contains("phone")){
            return key.replaceAll("phone", "teléfono");
        }
        if (key.contains("severity")){
            return key.replaceAll("severity", "color");
        }
        return key;
    }
}
