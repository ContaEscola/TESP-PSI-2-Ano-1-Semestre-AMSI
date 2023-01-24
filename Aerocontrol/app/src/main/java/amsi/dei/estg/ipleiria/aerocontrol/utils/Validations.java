package amsi.dei.estg.ipleiria.aerocontrol.utils;

import android.util.Patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validations {

    /**
     * Valida o máximo de caracteres que a String pode ter.
     * @param max Máximo caracteres permitidos
     * @param string string a validar
     * @return return true se for válido, false se não for válido.
     */
    public static boolean validateMaxLength(int max, String string){
        string = string.trim();
        return string.length() <= max;
    }

    /**
     * Valida o mínimo de caracteres que a String pode ter.
     * @param min Mínimo de caracateres permitidos
     * @param string string a validar
     * @return return true se for válido, false se não for válido.
     */
    public static boolean validateMinLength(int min, String string){
        string = string.trim();
        return string.length() >= min;
    }

    /**
     * Valida se o email tem um formato correto.
     * @param email email a validar
     * @return return true se for válido, false se não for válido.
     */
    public static boolean validateEmail(String email){
        email = email.trim();
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * Valida se a string possui caractares para além de espaços vazios.
     * @param string string a validar
     * @return return true se for válido, false se não for válido.
     */
    public static boolean validateRequired(String string){
        string = string.trim();
        return string.length() != 0;
    }

    /**
     * Valida se a string é um código de telefone de um país como por exemplo "+351".
     * @param string string a validar
     * @return return true se for válido, false se não for válido.
     */
    public static boolean validatePhoneCountryCode(String string){
        string = string.trim();
        Pattern pattern = Pattern.compile("\\+[\\d]{1,4}$");
        return pattern.matcher(string).matches();
    }

    /**
     * Valida se a string é um número de telefone.
     * @param string string a validar
     * @return return true se for válido, false se não for válido.
     */
    public static boolean validatePhoneNumber(String string){
        string = string.trim();
        Pattern pattern = Pattern.compile("[\\d]{4,15}$");
        return pattern.matcher(string).matches();
    }

    /**
     * Valida se a formato da data está correto.
     * @param string string a validar
     * @return return true se for válido, false se não for válido.
     */
    public static boolean validateDateFormat(String string){
        string = string.trim();
        Pattern pattern = Pattern.compile("[\\d]{2}/[\\d]{2}/[\\d]{4}$");
        return pattern.matcher(string).matches();
    }
}
