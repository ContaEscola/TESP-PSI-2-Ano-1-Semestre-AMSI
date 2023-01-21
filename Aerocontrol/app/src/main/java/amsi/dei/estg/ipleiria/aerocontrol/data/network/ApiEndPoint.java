package amsi.dei.estg.ipleiria.aerocontrol.data.network;

public class ApiEndPoint {
    private final static String IP = "http://192.168.1.66:80";
    private final static String FOLDER_NAME = "/projetofinal";
    private final static String ENDPOINT = IP + FOLDER_NAME;
    private final static String API_ENDPOINT = ENDPOINT + "/aerocontrol/backend/web/api/";
    private final static String API_FOLDER_ENDPOINT = ENDPOINT + "/aerocontrol/common/uploads/";

    public final static String LOGIN = API_ENDPOINT + "auth/login";
    public final static String SIGNUP = API_ENDPOINT + "auth/signup";
    public final static String RESETPASSWORD = API_ENDPOINT + "user/reset-password";
    public final static String UPDATE_ACCOUNT = API_ENDPOINT + "user/";

    //Entities
    public final static String RESTAURANTS = API_ENDPOINT + "restaurants";
    public final static String RESTAURANTS_IMAGE_FOLDER = API_FOLDER_ENDPOINT + "restaurants/";
    public final static String RESTAURANTS_ITEMS_IMAGE_FOLDER = RESTAURANTS_IMAGE_FOLDER + "items/";

    public final static String STORES = API_ENDPOINT + "stores";
    public final static String STORES_IMAGE_FOLDER = API_FOLDER_ENDPOINT + "stores/";

    public final static String TICKETS = API_ENDPOINT + "flight-tickets/";
    public final static String MY_TICKETS = TICKETS + "my-tickets";

    public final static String SUPPORT_TICKETS = API_ENDPOINT + "support-tickets";
    public final static String MY_SUPPORT_TICKETS = SUPPORT_TICKETS + "/my-support-tickets";
    public final static String LOST_ITEM_IMAGE_FOLDER = API_FOLDER_ENDPOINT + "lost-items/";
    public final static String SUPPORT_TICKET_MESSAGE = API_ENDPOINT + "ticket-messages";
    
    public final static String AIRPORTS = API_ENDPOINT + "airports";

    public final static String FLIGHT_SEARCH = API_ENDPOINT + "flight/search";

    public static final String PAYMENT_METHODS = API_ENDPOINT + "payment-methods";
}
