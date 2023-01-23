package amsi.dei.estg.ipleiria.aerocontrol.data.network;

public class ApiEndPoint {
    private final static String IP = "http://192.168.8.100:80";

    private final static String API_ROOT_FOLDER = IP + "/TESP-PSI-2-Ano-1-Semestre-PLSI-SIS/aerocontrol";
    private final static String ENDPOINT_API = API_ROOT_FOLDER + "/backend/web/api";
    private final static String ENDPOINT_API_UPLOADS = API_ROOT_FOLDER + "/common/uploads";

	public final static String ENDPOINT_LOGIN = ENDPOINT_API + "/auth/login";
    public final static String ENDPOINT_SIGNUP = ENDPOINT_API + "/auth/signup";
	public final static String ENDPOINT_UPDATE_ACCOUNT = ENDPOINT_API + "/user";
	public final static String ENDPOINT_RESET_PASSWORD = ENDPOINT_API + "/user/reset-password";

    //Entities
    public final static String ENDPOINT_RESTAURANTS = ENDPOINT_API + "/restaurants";
    public final static String ENDPOINT_RESTAURANTS_UPLOADS = ENDPOINT_API_UPLOADS + "/restaurants";
    public final static String RESTAURANT_ITEMS_FOLDER = "/menu";

    public final static String ENDPOINT_STORES = ENDPOINT_API + "/stores";
    public final static String ENDPOINT_STORES_UPLOADS = ENDPOINT_API_UPLOADS + "/stores";

    public final static String ENDPOINT_FLIGHT_TICKETS = ENDPOINT_API + "/flight-tickets";
    public final static String ENDPOINT_MY_FLIGHT_TICKETS = ENDPOINT_FLIGHT_TICKETS + "/my-tickets";
    
	public final static String ENDPOINT_AIRPORTS = ENDPOINT_API + "/airports";

    public final static String ENDPOINT_FLIGHT_SEARCH = ENDPOINT_API + "/flight/search";
    
    
    public final static String ENDPOINT_SUPPORT_TICKETS = ENDPOINT_API + "/support-tickets";
    public final static String ENDPOINT_MY_SUPPORT_TICKETS = ENDPOINT_SUPPORT_TICKETS + "/my-support-tickets";
    public final static String ENDPOINT_SUPPORT_TICKET_MESSAGES = ENDPOINT_API + "/ticket-messages";
    
     public final static String LOST_ITEMS_FOLDER = ENDPOINT_API_UPLOADS + "/lost-items";
}
