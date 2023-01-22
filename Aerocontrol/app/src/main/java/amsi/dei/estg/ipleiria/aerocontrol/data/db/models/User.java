package amsi.dei.estg.ipleiria.aerocontrol.data.db.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;

public class User {

    private int id;
    private String username;

    @JsonProperty("token")
    private String authKey;

    private String password;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    private String gender;
    private String country;
    private String city;
    private String email;
    private String phone;

    @JsonProperty("phone_country_code")
    private String phoneCountryCode;

    private Date birthdate;

    // ArrayList de FlightTickets?
    // ArrayList de SupportTickets?

    public User (int id, String username, String authKey, String password, String firstName, String lastName, String gender,
                 String country, String city, String email, String phone, String phoneCountryCode, Date birthdate){
        this.setId(id);
        this.setUsername(username);
        this.setAuthKey(authKey);
        this.setPassword(password);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setGender(gender);
        this.setCountry(country);
        this.setCity(city);
        this.setEmail(email);
        this.setPhone(phone);
        this.setPhoneCountryCode(phoneCountryCode);
        this.setBirthdate(birthdate);
    }

    public User() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonProperty("token")
    public String getAuthKey() {
        return authKey;
    }

    @JsonProperty("token")
    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JsonProperty("first_name")
    public String getFirstName() {
        return firstName;
    }

    @JsonProperty("first_name")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @JsonProperty("last_name")
    public String getLastName() {
        return lastName;
    }

    @JsonProperty("last_name")
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @JsonProperty("phone_country_code")
    public String getPhoneCountryCode() {
        return phoneCountryCode;
    }

    @JsonProperty("phone_country_code")
    public void setPhoneCountryCode(String phoneCountryCode) {
        this.phoneCountryCode = phoneCountryCode;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public static User parseJsonToUser(String jsonString) throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonString, User.class);
    }
}
