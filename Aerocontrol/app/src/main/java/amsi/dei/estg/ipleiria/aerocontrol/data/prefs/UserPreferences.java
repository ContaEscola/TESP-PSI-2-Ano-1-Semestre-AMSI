package amsi.dei.estg.ipleiria.aerocontrol.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import java.sql.Date;

import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.User;
import amsi.dei.estg.ipleiria.aerocontrol.utils.DateDisplayFormatUtils;

public class UserPreferences {
    private static UserPreferences instance = null;

    private static final String PREF_USER = "user";

    private static final String PREF_KEY_ID = "id";
    private static final String PREF_KEY_TOKEN = "token";
    private static final String PREF_KEY_USERNAME = "username";
    private static final String PREF_KEY_FIRST_NAME = "first_name";
    private static final String PREF_KEY_LAST_NAME = "last_name";
    private static final String PREF_KEY_GENDER = "gender";
    private static final String PREF_KEY_COUNTRY = "country";
    private static final String PREF_KEY_CITY = "city";
    private static final String PREF_KEY_EMAIL = "email";
    private static final String PREF_KEY_PHONE = "phone";
    private static final String PREF_KEY_PHONE_COUNTRY_CODE = "phone_country_code";
    private static final String PREF_KEY_BIRTHDATE = "birthdate";

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    private UserPreferences(Context context){
        this.sp = context.getSharedPreferences(PREF_USER,Context.MODE_PRIVATE);
        this.editor = sp.edit();
    }

    public static synchronized UserPreferences getInstance(Context context){
        if(instance == null)
            instance = new UserPreferences(context);
        return instance;
    }

    public void setId(int id){
        editor.putInt(PREF_KEY_ID, id).apply();
    }

    public int getId(){
        return sp.getInt(PREF_KEY_ID, -1);
    }

    public void setToken(String token){
        editor.putString(PREF_KEY_TOKEN,token).apply();
    }

    public String getToken(){
        return sp.getString(PREF_KEY_TOKEN,"");
    }

    public void setUsername(String username){
        editor.putString(PREF_KEY_USERNAME,username).apply();
    }

    public String getUsername(){
        return sp.getString(PREF_KEY_USERNAME,"");
    }

    public void setFirstName(String firstName){
        editor.putString(PREF_KEY_FIRST_NAME,firstName).apply();
    }

    public String getFirstName(){
        return sp.getString(PREF_KEY_FIRST_NAME,"");
    }

    public void setLastName(String lastName){
        editor.putString(PREF_KEY_LAST_NAME,lastName).apply();
    }

    public String getLastName(){
        return sp.getString(PREF_KEY_LAST_NAME,"");
    }

    public void setGender(String gender){
        editor.putString(PREF_KEY_GENDER,gender).apply();
    }

    public String getGender(){
        return sp.getString(PREF_KEY_GENDER,"");
    }

    public void setCountry(String country){
        editor.putString(PREF_KEY_COUNTRY,country).apply();
    }

    public String getCountry(){
        return sp.getString(PREF_KEY_COUNTRY,"");
    }

    public void setCity(String city){
        editor.putString(PREF_KEY_CITY,city).apply();
    }

    public String getCity(){
        return sp.getString(PREF_KEY_CITY,"");
    }

    public void setEmail(String email){
        editor.putString(PREF_KEY_EMAIL,email).apply();
    }

    public String getEmail(){
        return sp.getString(PREF_KEY_EMAIL,"");
    }

    public void setPhone(String phone){
        editor.putString(PREF_KEY_PHONE,phone).apply();
    }

    public String getPhone(){
        return sp.getString(PREF_KEY_PHONE,"");
    }

    public void setPhoneCountryCode(String phoneCountryCode){
        editor.putString(PREF_KEY_PHONE_COUNTRY_CODE,phoneCountryCode).apply();
    }

    public String getPhoneCountryCode(){
        return sp.getString(PREF_KEY_PHONE_COUNTRY_CODE,"");
    }

    public void setBirthdate(String birthdate){
        editor.putString(PREF_KEY_BIRTHDATE,birthdate).apply();
    }

    public String getBirthdate(){
        return sp.getString(PREF_KEY_BIRTHDATE,"");
    }

    public void clearUser(){
        sp.edit().remove(PREF_KEY_ID).apply();
        sp.edit().remove(PREF_KEY_TOKEN).apply();
        sp.edit().remove(PREF_KEY_USERNAME).apply();
        sp.edit().remove(PREF_KEY_FIRST_NAME).apply();
        sp.edit().remove(PREF_KEY_LAST_NAME).apply();
        sp.edit().remove(PREF_KEY_GENDER).apply();
        sp.edit().remove(PREF_KEY_COUNTRY).apply();
        sp.edit().remove(PREF_KEY_CITY).apply();
        sp.edit().remove(PREF_KEY_EMAIL).apply();
        sp.edit().remove(PREF_KEY_PHONE).apply();
        sp.edit().remove(PREF_KEY_PHONE_COUNTRY_CODE).apply();
        sp.edit().remove(PREF_KEY_BIRTHDATE).apply();
    }

    public void setUser(User user){
        this.setId(user.getId());
        this.setToken(user.getToken());
        this.setUsername(user.getUsername());
        this.setFirstName(user.getFirstName());
        this.setLastName(user.getLastName());
        this.setGender(user.getGender());
        this.setCountry(user.getCountry());
        this.setCity(user.getCity());
        this.setEmail(user.getEmail());
        this.setPhone(user.getPhone());
        this.setPhoneCountryCode(user.getPhoneCountryCode());
        this.setBirthdate(user.getBirthdate().toString());
    }

    public User getUser(){
        return new User(
                this.getId(),
                this.getUsername(),
                this.getToken(),
                null,
                this.getFirstName(),
                this.getLastName(),
                this.getGender(),
                this.getCountry(),
                this.getCity(),
                this.getEmail(),
                this.getPhone(),
                this.getPhoneCountryCode(),
                DateDisplayFormatUtils.formatStringToDate(this.getBirthdate())
        );
    }
}
