package amsi.dei.estg.ipleiria.aerocontrol.data.network;

public class ApiEndPoint {
    private final static String IP = "http://192.168.1.68:80";
    private final static String FOLDER_NAME = "/TESP-PSI-2-Ano-1-Semestre-PLSI-SIS";
    private final static String ENDPOINT = IP + FOLDER_NAME;
    private final static String API_ENDPOINT = ENDPOINT + "/aerocontrol/backend/web/api/";
    private final static String API_FOLDER_ENDPOINT = ENDPOINT + "/aerocontrol/uploads/";

    //Entities
    public final static String RESTAURANTS = API_ENDPOINT + "restaurants";
    public final static String RESTAURANTS_IMAGE_FOLDER = API_FOLDER_ENDPOINT + "logos/restaurants/";
    public final static String RESTAURANTS_ITEMS_IMAGE_FOLDER = RESTAURANTS_IMAGE_FOLDER + "items/";

    public final static String STORES = API_ENDPOINT + "stores";
    public final static String STORES_IMAGE_FOLDER = API_FOLDER_ENDPOINT + "logos/stores/";

    public final static String LOGIN = API_ENDPOINT + "auth/login";
}
