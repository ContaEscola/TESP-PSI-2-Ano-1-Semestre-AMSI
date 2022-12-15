package amsi.dei.estg.ipleiria.aerocontrol.data.network;

public class ApiEndPoint {
    private final static String IP = "http://192.168.56.1:80";
    private final static String API_ENDPOINT = IP + "/projetofinal/aerocontrol/backend/web/api/";
    private final static String API_FOLDER_ENDPOINT = IP + "/projetofinal/aerocontrol/uploads/";

    //Entities
    public final static String RESTAURANTS = API_ENDPOINT + "restaurants";
    public final static String RESTAURANTS_IMAGE_FOLDER = API_FOLDER_ENDPOINT + "logos/restaurants/";
    public final static String RESTAURANTS_ITEMS_IMAGE_FOLDER = RESTAURANTS_IMAGE_FOLDER + "items/";

}
