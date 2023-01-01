package amsi.dei.estg.ipleiria.aerocontrol.utils;

import java.util.ArrayList;
import java.util.HashMap;

import amsi.dei.estg.ipleiria.aerocontrol.R;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.User;

public class UserValidations {

    public static final String usernameError = App.getRes().getString(R.string.username_error);
    public static final String passwordError = App.getRes().getString(R.string.password_error);
    public static final String firstNameError = App.getRes().getString(R.string.first_name_error);
    public static final String lastNameError = App.getRes().getString(R.string.last_name_error);
    public static final String genderError = App.getRes().getString(R.string.gender_error);
    public static final String countryError = App.getRes().getString(R.string.country_error);
    public static final String cityError = App.getRes().getString(R.string.city_error);
    public static final String emailError = App.getRes().getString(R.string.email_error);
    public static final String phoneError = App.getRes().getString(R.string.phone_error);
    public static final String phoneCodeError = App.getRes().getString(R.string.phone_code_error);
    public static final String birthdateError = App.getRes().getString(R.string.birthdate_error);

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

    public static HashMap<String,ArrayList<String>> validateUser(User user){
        HashMap<String, ArrayList<String>> errors = new HashMap<>();
        ArrayList<String> contacts = new ArrayList<>();
        ArrayList<String> personalData = new ArrayList<>();
        ArrayList<String> accessData = new ArrayList<>();
        // Valida os contactos
        if (!validateEmail(user.getEmail())) contacts.add("Email");
        if (!validatePhoneCode(user.getPhoneCountryCode())) contacts.add("Código de telefone do país");
        if (!validatePhone(user.getPhone())) contacts.add("Telefone");
        // Valida os dados de acesso
        if (!validateUsername(user.getUsername())) accessData.add("Username");
        if (user.getPassword() != null) {
            if (user.getPassword().length() > 0 || !validatePassword(user.getPassword()))
                accessData.add("Password");
        }
        // Valida os dados pessoais
        if (!validateFirstName(user.getFirstName())) personalData.add("Primeiro nome");
        if (!validateLastName(user.getLastName())) personalData.add("Último nome");
        if (!validateGender(user.getGender())) personalData.add("Género");
        if (!validateBirthdate(user.getBirthdate())) personalData.add("Data de Nascimento");
        if (!validateCountry(user.getCountry())) personalData.add("País");
        if (!validateCity(user.getCity())) personalData.add("Cidade");

        if (contacts.size() > 0) errors.put("Contacts", contacts);
        if (personalData.size() > 0) errors.put("PersonalData", personalData);
        if (accessData.size() > 0) errors.put("AccessData", accessData);

        return errors.size() > 0 ? errors : null;
    }
}
