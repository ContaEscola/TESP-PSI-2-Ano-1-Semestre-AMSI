package amsi.dei.estg.ipleiria.aerocontrol.data.db.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import amsi.dei.estg.ipleiria.aerocontrol.data.network.ApiEndPoint;
import amsi.dei.estg.ipleiria.aerocontrol.utils.StringUtils;

@JsonPropertyOrder({
    "id",
    "name",
    "description",
    "phone",
    "open_time",
    "close_time",
    "logo",
    "website"
})
public class Store {

    private int id;
    private String name;
    private String description;
    private String phone;
    private String logo;
    private String website;

    @JsonProperty("open_time")
    private String openTime;

    @JsonProperty("close_time")
    private String closeTime;

    public Store(int id, String name, String description, String phone, String logo, String website, String openTime, String closeTime){
        this.setId(id);
        this.setName(name);
        this.setDescription(description);
        this.setPhone(phone);
        this.setLogo(logo);
        this.setWebsite(website);
        this.setOpenTime(openTime);
        this.setCloseTime(closeTime);
    }

    public Store() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @JsonProperty("open_time")
    public String getOpenTime() {
        return openTime;
    }

    @JsonProperty("open_time")
    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    @JsonProperty("close_time")
    public String getCloseTime() {
        return closeTime;
    }

    @JsonProperty("close_time")
    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public String getUploadAPIPath() {
        return ApiEndPoint.ENDPOINT_STORES_UPLOADS + "/" + StringUtils.convertWhiteSpaceToUnderscores(getName());
    }

    /**
     * Devolve o caminho do logo onde está guardado na Api
     * @return
     */
    public String getLogoAPIPath() {
        return getUploadAPIPath() + "/" +  getLogo();
    }


    //    Converter array de json para array de objetos
    //    https://stackoverflow.com/questions/6349421/how-to-use-jackson-to-deserialise-an-array-of-objects
    public static Store[] parseJsonToStores(String jsonString) throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(jsonString, Store[].class);

    }
}
