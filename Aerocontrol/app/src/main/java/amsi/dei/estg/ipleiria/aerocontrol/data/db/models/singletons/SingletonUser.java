package amsi.dei.estg.ipleiria.aerocontrol.data.db.models.singletons;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import amsi.dei.estg.ipleiria.aerocontrol.R;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.helpers.UserDBManager;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.FlightTicket;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.LostItem;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.Passenger;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.SupportTicket;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.TicketMessage;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.User;
import amsi.dei.estg.ipleiria.aerocontrol.data.network.ApiBodyHelper;
import amsi.dei.estg.ipleiria.aerocontrol.data.network.ApiEndPoint;
import amsi.dei.estg.ipleiria.aerocontrol.data.network.models.LoginRequest;
import amsi.dei.estg.ipleiria.aerocontrol.data.prefs.UserPreferences;
import amsi.dei.estg.ipleiria.aerocontrol.listeners.FlightTicketListener;
import amsi.dei.estg.ipleiria.aerocontrol.listeners.FlightTicketsListener;
import amsi.dei.estg.ipleiria.aerocontrol.listeners.LoginListener;
import amsi.dei.estg.ipleiria.aerocontrol.listeners.ResetPasswordListener;
import amsi.dei.estg.ipleiria.aerocontrol.listeners.SignupListener;
import amsi.dei.estg.ipleiria.aerocontrol.listeners.SupportTicketListener;
import amsi.dei.estg.ipleiria.aerocontrol.listeners.SupportTicketMessageListener;
import amsi.dei.estg.ipleiria.aerocontrol.listeners.SupportTicketsListener;
import amsi.dei.estg.ipleiria.aerocontrol.listeners.UpdateUserListener;
import amsi.dei.estg.ipleiria.aerocontrol.utils.NetworkUtils;
import amsi.dei.estg.ipleiria.aerocontrol.utils.ResetPasswordJsonParser;
import amsi.dei.estg.ipleiria.aerocontrol.utils.UserJsonParser;

public class SingletonUser {

    private static SingletonUser instance = null;

    private User user;
    private User userToUpdate;
    private boolean loggedIn = false;

    private ArrayList<FlightTicket> flightTickets;
    private ArrayList<SupportTicket> supportTickets;

    private Context context;

    private RequestQueue volleyQueue;

    private LoginListener loginListener;
    private SignupListener signupListener;
    private ResetPasswordListener resetPasswordListener;
	private UpdateUserListener updateUserListener;
	
    private FlightTicketsListener flightTicketsListener;
    private FlightTicketListener flightTicketListener;

    private SupportTicketsListener supportTicketsListener;
    private SupportTicketListener supportTicketListener;
    private SupportTicketMessageListener supportTicketMessageListener;

    public void setLoginListener(LoginListener loginListener) {
        this.loginListener = loginListener;
    }

    public void setFlightTicketsListener(FlightTicketsListener flightTicketsListener) {
        this.flightTicketsListener = flightTicketsListener;
    }

    public void setFlightTicketListener(FlightTicketListener flightTicketListener) {
        this.flightTicketListener = flightTicketListener;
    }
    
	public void setUpdateUserListener(UpdateUserListener updateUserListener) {
        this.updateUserListener = updateUserListener;
    }

    public void setSignupListener(SignupListener signupListener) {
        this.signupListener = signupListener;
    }

	public void setResetPasswordListener(ResetPasswordListener resetPasswordListener){
        this.resetPasswordListener = resetPasswordListener;
    }
	public void setSupportTicketsListener(SupportTicketsListener supportTicketsListener) {
        this.supportTicketsListener = supportTicketsListener;
    }

    public void setSupportTicketListener(SupportTicketListener supportTicketListener) {
        this.supportTicketListener = supportTicketListener;
    }

    public void setSupportTicketMessageListener(SupportTicketMessageListener supportTicketMessageListener) {
        this.supportTicketMessageListener = supportTicketMessageListener;
    }

    private SingletonUser(Context context){
        this.user = null;
        this.userToUpdate = null;
        this.flightTickets = new ArrayList<>();
        this.supportTickets = new ArrayList<>();
        this.context = context;

        //https://developer.android.com/training/volley/requestqueue?hl=pt-br
        volleyQueue = Volley.newRequestQueue(context.getApplicationContext());

        getLoggedInOnStart(context);
    }

    public static synchronized SingletonUser getInstance(Context context){
        if (instance == null) instance = new SingletonUser(context);
        return instance;
    }

    /**
     * Vai buscar os dados do login à API
     * @param context context da atividade ou fragment
     */
    public void getLoginAPI(final String username, final String password, final Context context){

        // Caso não haja internet
        if (!NetworkUtils.isConnectedInternet(context)){
            Toast.makeText(context, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
            return;
        }


        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiEndPoint.ENDPOINT_LOGIN,
                response -> {
                    try {
                        User user = User.parseJsonToUser(response);
                        this.user = user; // Set do user autenticado na Singleton
                        setLoggedIn(true); // Dá Set à variável do LoggedIn para true

                        if (loginListener != null)
                            loginListener.onValidateLogin(user, context);

                    } catch (JsonProcessingException e) {
                        Toast.makeText(context, R.string.common_error, Toast.LENGTH_SHORT).show();
                    }

                }, error -> {
                    //https://stackoverflow.com/questions/24700582/handle-volley-error
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        Toast.makeText(context, R.string.common_error, Toast.LENGTH_SHORT).show();
                    } else if (error instanceof AuthFailureError) {
                        Toast.makeText(context, R.string.invalid_credentials, Toast.LENGTH_SHORT).show();
                    } else if (error instanceof ServerError) {
                        Toast.makeText(context, R.string.common_error, Toast.LENGTH_SHORT).show();
                    }


        }){
            @Override
            public Map<String, String> getHeaders(){
                String authorizationToken = LoginRequest.getHttpBasicAuthToken(username, password);

                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", authorizationToken);
                return params;
            }
        };

        volleyQueue.add(stringRequest);
    }

    /**
     * Faz o Registo através da API
     * @param user Utilizador a registar
     * @param context Context da atividade ou fragmento
     */
    public void signupAPI(User user, final Context context){
        // Caso não haja internet
        if (!NetworkUtils.isConnectedInternet(context)){
            Toast.makeText(context, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
            return;

        }

        try {

            String jsonUser = User.convertUserToJson(user);
            Map<String, Object> jsonParams = ApiBodyHelper.convertJsonStringToMap(jsonUser);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApiEndPoint.ENDPOINT_SIGNUP, new JSONObject(jsonParams),
                    response -> {
                        // Aqui já tá com successo
                        if (signupListener != null)
                            signupListener.onSignup();

                    }, error -> {

                        String body = null;
                        Map<String, Object> bodyParams = null;
                        String errorReturned = "";

                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            signupListener.onErrorSignup(errorReturned);
                            return;
                        }

                        if(error.networkResponse.data != null) {
                            try {
                                // Aqui tem a resposta em String
                                body = new String(error.networkResponse.data,"UTF-8");
                                // Aqui converte a resposta para um Map
                                bodyParams = ApiBodyHelper.convertJsonStringToMap(body);

                                if(bodyParams.containsKey("message")) {
                                    // Aqui converte a resposta["message"] para um Map
                                    Map<String, Object> bodyMessage = ApiBodyHelper.convertJsonStringToMap((String) bodyParams.get("message"));

                                    // Aqui faz um for em todos os items da resposta["message"]
                                    for (Object value : bodyMessage.values()) {
                                        // Busca o ArrayList de erros para cada atributo da resposta["message"]
                                        ArrayList<String> errorsInAttribute = (ArrayList<String>) value;
                                        for(String errorInAttribute : errorsInAttribute) {
                                            // Adiciona cada erro do atributo ao String erro
                                            errorReturned += errorInAttribute + "\n";
                                        }
                                    }

                                }

                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            } catch (JsonProcessingException e) {
                                e.printStackTrace();
                            }
                        }

                        signupListener.onErrorSignup(errorReturned);
            });

            volleyQueue.add(jsonObjectRequest);

        } catch (JsonProcessingException e) {
            Toast.makeText(context, R.string.common_error, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Envia um email para resetar a password através da API
     * @param email Email do utilizador
     * @param context Context da atividade ou fragmento
     */
    public void resetPasswordAPI(final String email, final Context context){
        // Caso não haja internet
        if (!NetworkUtils.isConnectedInternet(context)){
            Toast.makeText(context, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiEndPoint.ENDPOINT_RESET_PASSWORD,
                response -> {
                    String message = ResetPasswordJsonParser.parserJsonResetPassword(response);
                    if (resetPasswordListener != null && message != null) {
                        resetPasswordListener.onEmailSent(message);
                    } else Toast.makeText(context, R.string.common_error, Toast.LENGTH_SHORT).show();
                },
                error -> {
                    Toast.makeText(context, R.string.common_error, Toast.LENGTH_SHORT).show();
                }){
            @Override
            public Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                return params;
            }
        };

        volleyQueue.add(stringRequest);
    }

    /**
     * Verifica se o utilizador está autenticado através do username no SharedPreferences.
     */
    private void getLoggedInOnStart(Context context) {
        if(!UserPreferences.getInstance(context).getUsername().equals("")) {
            this.setLoggedIn(true);
            this.setUser(UserPreferences.getInstance(context).getUser());
        }
        else this.setLoggedIn(false);
    }

    public void setLoggedIn (boolean loggedIn){
        this.loggedIn = loggedIn;
    }

    public boolean isLoggedIn(){
        return this.loggedIn;
    }

    /**
     *
     * @param user Utilizador que passa a estar logado.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     *
     * @return Devolve o Utilizador.
     */
    public User getUser() {
        return user;
    }

    /**
     * Envia o utilizador para a API para que possa ser atualizado
     * @param context Contexto da Atividade ou Fragment
     */
    public void updateUserAPI(final Context context, final String confirm_password){
        if (!NetworkUtils.isConnectedInternet(context)){
            Toast.makeText(context, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
            return;
        }

        if (this.user != null && userToUpdate != null){
            String endPoint = ApiEndPoint.ENDPOINT_UPDATE_ACCOUNT + "/" + this.user.getId() + "?access-token=" + this.user.getToken();

            try {
                String jsonUser = User.convertUserToJson(userToUpdate);

                String fullJson = ApiBodyHelper.addAttributeToJsonBody(jsonUser, "confirm_password", confirm_password);
                Map<String, Object> jsonParams = ApiBodyHelper.convertJsonStringToMap(fullJson);

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, endPoint, new JSONObject(jsonParams),
                        response -> {
                            setUser(userToUpdate);
                            UserPreferences.getInstance(context).setUser(userToUpdate);

                            if(updateUserListener != null)
                                updateUserListener.onUpdateUser(context.getString(R.string.save_data_success));

                        },
                        error -> {
                            String body = null;
                            Map<String, Object> bodyParams = null;
                            String errorReturned = "";

                            if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                updateUserListener.onErrorUpdate(errorReturned);
                                return;
                            }

                            if (error.networkResponse.data != null) {
                                try {
                                    // Aqui tem a resposta em String
                                    body = new String(error.networkResponse.data, "UTF-8");
                                    // Aqui converte a resposta para um Map
                                    bodyParams = ApiBodyHelper.convertJsonStringToMap(body);

                                    if (bodyParams.containsKey("message")) {
                                        if (((String) bodyParams.get("message")).contains("Esta password não corresponde à password atual.") ){
                                            errorReturned = (String) bodyParams.get("message");
                                        } else {
                                            Map<String, Object> bodyMessage = ApiBodyHelper.convertJsonStringToMap((String) bodyParams.get("message"));
                                            // Aqui faz um for em todos os items da resposta["message"]
                                            for (Object value : bodyMessage.values()) {
                                                // Busca o ArrayList de erros para cada atributo da resposta["message"]
                                                ArrayList<String> errorsInAttribute = (ArrayList<String>) value;
                                                for (String errorInAttribute : errorsInAttribute) {
                                                    // Adiciona cada erro do atributo ao String erro
                                                    errorReturned += errorInAttribute + "\n";
                                                }
                                            }
                                        }
                                    }

                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                } catch (JsonProcessingException e) {
                                    e.printStackTrace();
                                }
                            }

                            updateUserListener.onErrorUpdate(errorReturned);
                        }
                );
                volleyQueue.add(jsonObjectRequest);
            }
            catch (JsonProcessingException e) {
                Toast.makeText(context, R.string.save_data_failed, Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     *
     * @param user Objeto do tipo utilizador que será atualizado.
     */
    public void setUserToUpdate(final User user) {
        // Atribuição feita atributo a atributo, porque caso seja feita this.userToUpdate = user
        // o this.user (Objeto recebido nos parâmetros) fica ligado como pointer ao this.userToUpdate
        this.userToUpdate = new User();
        this.userToUpdate.setId(user.getId());
        this.userToUpdate.setUsername(user.getUsername());
        this.userToUpdate.setToken(user.getToken());
        this.userToUpdate.setPassword(null);
        this.userToUpdate.setFirstName(user.getFirstName());
        this.userToUpdate.setLastName(user.getLastName());
        this.userToUpdate.setGender(user.getGender());
        this.userToUpdate.setCountry(user.getCountry());
        this.userToUpdate.setCity(user.getCity());
        this.userToUpdate.setEmail(user.getEmail());
        this.userToUpdate.setPhone(user.getPhone());
        this.userToUpdate.setPhoneCountryCode(user.getPhoneCountryCode());
        this.userToUpdate.setBirthdate(user.getBirthdate());
    }

    /**
     *
     * @return Devolve o Utilizador.
     */
    public User getUserToUpdate() {
        return userToUpdate;
    }

    /**
     *
     * @return Devolve a lista de todos os bilhetes de voo.
     */
    public ArrayList<FlightTicket> getFlightTickets() {
        return flightTickets;
    }

    /**
     * Vai buscar os dados dos bilhetes de voo à API
     * @param context context da atividade ou fragment
     */
    public void getFlightTicketsAPI(final Context context){
        // Caso não haja internet
        if (!NetworkUtils.isConnectedInternet(context)){
            Toast.makeText(context, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
            readFlightTicketsDB();
            flightTicketsListener.onRefreshList(flightTickets);
            return;
        }

        if (this.user != null){
            String endPoint = ApiEndPoint.ENDPOINT_MY_FLIGHT_TICKETS + "?access-token=" + this.user.getToken();

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, endPoint, null,
                    response -> {
                        try {
                            FlightTicket[] flightTicketsArray = FlightTicket.parseJsonToFlightTickets(response.toString());
                            flightTickets.clear();
                            Collections.addAll(flightTickets, flightTicketsArray);

                            if (flightTicketsListener != null && flightTickets.size()>0){
                                UserDBManager.getInstance(context).truncateTableFlightTickets();
                                addFlightTicketsDB(flightTickets);
                                flightTicketsListener.onRefreshList(flightTickets);
                            }
                        } catch (JsonProcessingException e) {
                            Toast.makeText(context, R.string.error_tickets, Toast.LENGTH_SHORT).show();
                        }

                    }, error -> Toast.makeText(context, R.string.error_tickets, Toast.LENGTH_SHORT).show());

            volleyQueue.add(jsonArrayRequest);
        }
    }

    /**
     * Envia um ticket para API de forma a ser efetuado o Check-in.
     * @param context context da atividade ou fragment
     * @param ticket ticket a enviar para a API para ser atualizado
     */
    public void updateFlightTicketAPI(final Context context, FlightTicket ticket){
        if (!NetworkUtils.isConnectedInternet(context)){
            Toast.makeText(context, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
            return;
        }

        if (this.user != null){
            String endPoint = ApiEndPoint.ENDPOINT_FLIGHT_TICKETS + "/" + ticket.getId() + "?access-token=" + this.user.getToken();

            StringRequest stringRequest = new StringRequest(Request.Method.PUT, endPoint,
                    response -> {
                        Toast.makeText(context, R.string.check_in_done, Toast.LENGTH_SHORT).show();

                        ticket.setCheckIn(true);
                        updateFlightTicketDB(ticket);

                        flightTicketListener.onRefreshTicket();
                    }, error -> Toast.makeText(context, R.string.error_tickets, Toast.LENGTH_SHORT).show()
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("checkin","1");
                    return params;
                }
            };

            volleyQueue.add(stringRequest);
        }
    }

    /**
     * Elimina um ticket da BD
     * @param context context da atividade ou fragment
     * @param ticket ticket eliminar
     */
    public void deleteFlightTicketAPI(final Context context, FlightTicket ticket){
        if (!NetworkUtils.isConnectedInternet(context)){
            Toast.makeText(context, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
            return;
        }

        if (this.user != null){
            String endPoint = ApiEndPoint.ENDPOINT_FLIGHT_TICKETS + "/" + ticket.getId() + "?access-token=" + this.user.getToken();

            StringRequest stringRequest = new StringRequest(Request.Method.DELETE, endPoint,
                    response -> {
                        Toast.makeText(context, R.string.ticket_deleted, Toast.LENGTH_SHORT).show();
                        deleteFlightTicketDB(ticket.getId());
                        this.flightTickets.remove(ticket);
                        flightTicketListener.onDeleteTicket();
                    }, error -> {
                Toast.makeText(context, "Erro ao eliminar.", Toast.LENGTH_SHORT).show();
            });
            volleyQueue.add(stringRequest);
        }
    }

    /**
     * Cria todos os bilhetes numa base de dados local para que possam ser visualizados offline
     * @param tickets lista dos bilhetes a criar na BD
     */
    private void addFlightTicketsDB(ArrayList<FlightTicket> tickets) {
        for (FlightTicket ticket: tickets) {
            UserDBManager.getInstance(context).createFlightTicket(ticket);
            int flightTicketId = ticket.getId();
            for (Passenger passenger : ticket.getPassengers()){
                UserDBManager.getInstance(context).createPassenger(passenger, flightTicketId);
            }
        }
    }

    /**
     * Vai buscar à BD local todos os bilhetes de voo.
     */
    private void readFlightTicketsDB() {
        flightTickets = UserDBManager.getInstance(context).readFlightTickets();
        for (FlightTicket ticket: flightTickets){
            ticket.setPassengers(UserDBManager.getInstance(context).readPassengers(ticket.getId()));
        }
    }

    /**
     * Atualiza um bilhete de voo que já esteja na BD local
     * @param ticket bilhete de voo a atualizar na BD
     */
    private void updateFlightTicketDB(FlightTicket ticket) {
        UserDBManager.getInstance(context).updateFlightTicket(ticket);
    }

    /**
     *
     * @param id id do bilhete de voo a eliminar
     */
    private void deleteFlightTicketDB(int id){
        UserDBManager.getInstance(context).deleteFlightTicket(id);
    }

    /**
     *
     * @param id Id do bilhete de voo.
     * @return Devolve o bilhete de voo.
     */
    public FlightTicket getFlightTicketById(int id){
        for(FlightTicket ticket : flightTickets) {
            if(ticket.getId() == id) {
                return ticket;
            }
        }
        return null;
    }

    /**
     *
     * @param ticket Bilhete de voo a adicionar.
     */
    public void addTicket(FlightTicket ticket) {
        this.flightTickets.add(ticket);
    }

    /**
     *
     * @param ticket Bilhete de voo a apagar.
     */
    public void deleteFlightTicket(FlightTicket ticket) {
        this.flightTickets.remove(ticket);
    }

    /**
     *
     * @param id Id do bilhete de voo.
     * @return Devolve a lista de passageiros do bilhete de voo.
     */
    public ArrayList<Passenger> getPassengers(int id){
        FlightTicket ticket = getFlightTicketById(id);
        if(ticket != null)
            return ticket.getPassengers();

        return null;
    }

    /**
     *
     * @param id Id do bilhete de voo.
     * @param passenger Passageiro a adicionar ao bilhete de voo.
     */
    public void addPassenger(int id, Passenger passenger){
        FlightTicket ticket = getFlightTicketById(id);
        if(ticket != null)
            ticket.addPassenger(passenger);
	}

    /**
     * Vai buscar os dados dos support ticket à API
     * @param context context da atividade ou fragment
     */
    public void getSupportTicketsAPI(final Context context){
        // Caso não haja internet
        if (!NetworkUtils.isConnectedInternet(context)){
            Toast.makeText(context, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
            readSupportTicketsDB();
            supportTicketsListener.onRefreshList(supportTickets);
            return;
        }

        if (this.user != null){
            String endPoint = ApiEndPoint.ENDPOINT_MY_SUPPORT_TICKETS + "?access-token=" + this.user.getToken();

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, endPoint, null,
                    response -> {
                        supportTickets = UserJsonParser.parserJsonSupportTickets(response);
                        if (supportTicketsListener != null && supportTickets.size()>0){
                            UserDBManager.getInstance(context).truncateTableSupportTickets();
                            addSupportTicketsDB(supportTickets);
                            supportTicketsListener.onRefreshList(supportTickets);
                        }
                    }, error -> Toast.makeText(context, R.string.error_support_tickets, Toast.LENGTH_SHORT).show());

            volleyQueue.add(jsonArrayRequest);
        }
    }

    /**
     * Vai criar mensagem no support ticket à API
     * @param context context da atividade ou fragment
     */
    public void setMessageSupportTicketAPI(final Context context, String message, Integer support_ticket_id){
        if (!NetworkUtils.isConnectedInternet(context)){
            Toast.makeText(context, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
            return;
        }

        if (this.user != null){
            String endPoint = ApiEndPoint.ENDPOINT_SUPPORT_TICKET_MESSAGES + "?access-token=" + this.user.getToken();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, endPoint,
                    response -> {
                        if(supportTicketMessageListener != null){
                            supportTicketMessageListener.onSetSupportTicketMessage(context.getString(R.string.create_data_success));
                        }
                    }, error -> Toast.makeText(context, R.string.save_data_failed, Toast.LENGTH_SHORT).show()
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("message", message);
                    params.put("sender_id", user.getId()+"");
                    params.put("support_ticket_id", support_ticket_id+"");
                    return params;
                }
            };
            volleyQueue.add(stringRequest);
        }
    }

    /**
     * Envia um support ticket para API de forma a alterar o estado.
     * @param context context da atividade ou fragment
     * @param supportTicket a enviar para a API para ser atualizado
     */
    public void updateSupportTicketAPI(final Context context, SupportTicket supportTicket){
        if (!NetworkUtils.isConnectedInternet(context)){
            Toast.makeText(context, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
            return;
        }

        if (this.user != null){
            String endPoint = ApiEndPoint.ENDPOINT_SUPPORT_TICKETS + "/" + supportTicket.getId() + "?access-token=" + this.user.getToken();

            StringRequest stringRequest = new StringRequest(Request.Method.PUT, endPoint,
                    response -> {
                        Toast.makeText(context, R.string.support_ticket_done, Toast.LENGTH_SHORT).show();
                        supportTicket.setState("Concluido");
                        updateSupportTicketDB(supportTicket);
                        supportTicketListener.onRefreshSupportTicket();
                    }, error -> Toast.makeText(context, R.string.error_support_tickets, Toast.LENGTH_SHORT).show()
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("state","Concluido");
                    return params;
                }
            };

            volleyQueue.add(stringRequest);
        }
    }

    /**
     * Cria todos os support tickets numa base de dados local para que possam ser visualizados offline
     * @param supportTickets lista dos support ticket a criar na BD
     */
    private void addSupportTicketsDB(ArrayList<SupportTicket> supportTickets) {
        for (SupportTicket supportTicket: supportTickets) {
            UserDBManager.getInstance(context).createSupportTicket(supportTicket);
            for (TicketMessage message : supportTicket.getMessages()){
                UserDBManager.getInstance(context).createMessage(message,supportTicket.getId());
            }
        }
    }

    /**
     * Atualiza um support ticket na BD
     */
    private void readSupportTicketsDB() {
        supportTickets = UserDBManager.getInstance(context).readSupportTickets();
        for (SupportTicket supportTicket: supportTickets){
            supportTicket.setMessages(UserDBManager.getInstance(context).readMessages(supportTicket.getId()));
        }
    }

    /**
     * Atualiza um support ticket que já esteja na BD local
     * @param supportTicket bilhete a atualizar na BD
     */
    private void updateSupportTicketDB(SupportTicket supportTicket) {
        UserDBManager.getInstance(context).updateSupportTicket(supportTicket);
    }

    /**
     *
     * @return Devolve a lista dos tickets de suporte.
     */
    public ArrayList<SupportTicket> getSupportTickets() {
        return supportTickets;
    }

    /**
     *
     * @param id Id do ticket de suporte.
     * @return Devolve o ticket de suporte.
     */
    public SupportTicket getSupportTicketById(int id){
        for(SupportTicket supportTicket : supportTickets) {
            if(supportTicket.getId() == id) {
                return supportTicket;
            }
        }
        return null;
    }

    /**
     *
     * @param supportTicket Ticket de suporte a adicionar.
     */
    public void addSupportTicket(SupportTicket supportTicket) {
        this.supportTickets.add(supportTicket);
    }

    /**
     *
     * @param id Id do ticket de suporte.
     * @return Devolve os itens dos perdidos e achados associados ao ticket de suporte.
     */
    public ArrayList<LostItem> getLostItems(int id){
        SupportTicket supportTicket = getSupportTicketById(id);
        if(supportTicket != null)
            return supportTicket.getItems();

        return null;
    }

    /**
     *
     * @param id Id do ticket de suporte.
     * @param item Item a adicionar à lista de perdidos e achados associados ao ticket de suporte.
     */
    public void addLostItem(int id, LostItem item){
        SupportTicket supportTicket = getSupportTicketById(id);
        if(supportTicket != null)
            supportTicket.addItem(item);
    }

    /**
     *
     * @param id Id do ticket de suporte.
     * @return Devolve a lista das mensagens associadas ao ticket de suporte.
     */
    public ArrayList<TicketMessage> getMessages(int id){
        SupportTicket supportTicket = getSupportTicketById(id);
        if(supportTicket != null)
            return supportTicket.getMessages();

        return null;
    }

    /**
     *
     * @param id Id do ticket de suporte.
     * @param message Adiciona a mensagem à lista de mensagens do ticket de suporte.
     */
    public void addMessage(int id, TicketMessage message){
        SupportTicket supportTicket = getSupportTicketById(id);
        if(supportTicket != null)
            supportTicket.addMessage(message);
    }


}
