package amsi.dei.estg.ipleiria.aerocontrol.data.db.models;

public class Airport {
    private int id;
    private String country;
    private String city;
    private String name;
    private String website;

    public Airport (int id, String country, String city, String name, String website){
        this.setId(id);
        this.setCountry(country);
        this.setCity(city);
        this.setName(name);
        this.setWebsite(website);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Override
    public String toString() {
        return name;

    }
}
