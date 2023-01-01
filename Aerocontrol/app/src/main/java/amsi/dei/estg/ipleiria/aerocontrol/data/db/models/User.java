package amsi.dei.estg.ipleiria.aerocontrol.data.db.models;

public class User {

    public static final String[] GENDERS = {
            "Masculino",
            "Feminino",
            "Outro",
    };

    private int id;
    private String username;
    private String token;
    private String password;
    private String firstName;
    private String lastName;
    private String gender;
    private String country;
    private String city;
    private String email;
    private String phone;
    private String phoneCountryCode;
    private String birthdate;

    // ArrayList de FlightTickets?
    // ArrayList de SupportTickets?

    public User (int id, String username, String token, String password, String firstName, String lastName, String gender,
                 String country, String city, String email, String phone, String phoneCountryCode, String birthdate){
        this.setId(id);
        this.setUsername(username);
        this.setToken(token);
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

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

    public String getPhoneCountryCode() {
        return phoneCountryCode;
    }

    public void setPhoneCountryCode(String phoneCountryCode) {
        this.phoneCountryCode = phoneCountryCode;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }
}
