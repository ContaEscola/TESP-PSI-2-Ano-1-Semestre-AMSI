package amsi.dei.estg.ipleiria.aerocontrol.utils;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.HashMap;

import amsi.dei.estg.ipleiria.aerocontrol.R;

public class UserValidations {

    public static final String usernameError = Resources.getSystem().getString(R.string.username_error);
    public static final String passwordError = Resources.getSystem().getString(R.string.password_error);
    public static final String firstNameError = Resources.getSystem().getString(R.string.first_name_error);
    public static final String lastNameError = Resources.getSystem().getString(R.string.last_name_error);
    public static final String genderError = Resources.getSystem().getString(R.string.gender_error);
    public static final String countryError = Resources.getSystem().getString(R.string.country_error);
    public static final String cityError = Resources.getSystem().getString(R.string.city_error);
    public static final String emailError = Resources.getSystem().getString(R.string.email_error);
    public static final String phoneError = Resources.getSystem().getString(R.string.phone_error);
    public static final String phoneCodeError = Resources.getSystem().getString(R.string.phone_code_error);
    public static final String birthdateError = Resources.getSystem().getString(R.string.birthdate_error);

    public static boolean validateUsername(String username){
        if (!Validations.validateRequired(username)) return false;
        if (!Validations.validateMaxLength(30, username)) return false;
        if (!Validations.validateMinLength(2, username)) return false;
        return true;
    }

    public static boolean validatePassword(String password){
        if (!Validations.validateRequired(password)) return false;
        if (!Validations.validateMaxLength(255, password)) return false;
        if (!Validations.validateMinLength(8, password)) return false;
        return true;
    }

    public static boolean validateFirstName(String firstName){
        if (!Validations.validateRequired(firstName)) return false;
        if (!Validations.validateMaxLength(50, firstName)) return false;
        return true;
    }

    public static boolean validateLastName(String lastName){
        if (!Validations.validateRequired(lastName)) return false;
        if (!Validations.validateMaxLength(50, lastName)) return false;
        return true;
    }

    public static boolean validateGender(String gender){
        if (!Validations.validateRequired(gender)) return false;
        if (gender.equals("Masculino")) return true;
        if (gender.equals("Feminino")) return true;
        if (gender.equals("Outro")) return true;
        return false;
    }

    public static boolean validateCountry(String country){
        if (!Validations.validateRequired(country)) return false;
        if (!Validations.validateMinLength(4, country)) return false;
        if (!Validations.validateMaxLength(50, country)) return false;
        return true;
    }

    public static boolean validateCity(String city){
        if (!Validations.validateRequired(city)) return false;
        if (!Validations.validateMinLength(1, city)) return false;
        if (!Validations.validateMaxLength(75, city)) return false;
        return true;
    }

    public static boolean validateEmail(String email){
        if (!Validations.validateRequired(email)) return false;
        if (!Validations.validateEmail(email)) return false;
        return true;
    }

    public static boolean validatePhone(String phone){
        return Validations.validatePhoneNumber(phone);
    }

    public static boolean validatePhoneCode(String phoneCode){
        return Validations.validatePhoneCountryCode(phoneCode);
    }

    public static boolean validateBirthdate(String birthdate){
        return Validations.validateDateFormat(birthdate);
    }

    public static HashMap<String,ArrayList<String>> validateUser(){
        /*HashMap<String, ArrayList<String>> hashMap = new HashMap<>();
        ArrayList<String> contacts = new ArrayList<>();
        ArrayList<String> personalData = new ArrayList<>();
        ArrayList<String> accessData = new ArrayList<>();
        contacts.add("Phone");
        hashMap.put("Contactos",contacts);

        UsernameValid.validateusername(binding.username.gettext);
        usernamevalid.validUser(usertoupdate);*/
        return null;
    }
}
