package amsi.dei.estg.ipleiria.aerocontrol.data.db.models.singletons;

import android.content.Context;
import android.util.Base64;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import amsi.dei.estg.ipleiria.aerocontrol.R;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.FlightTicket;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.Passenger;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.SupportTicket;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.TicketMessage;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.User;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.helpers.UserDBHelper;
import amsi.dei.estg.ipleiria.aerocontrol.data.network.ApiEndPoint;
import amsi.dei.estg.ipleiria.aerocontrol.data.prefs.UserPreferences;
import amsi.dei.estg.ipleiria.aerocontrol.listeners.LoginListener;
import amsi.dei.estg.ipleiria.aerocontrol.listeners.ResetPasswordListener;
import amsi.dei.estg.ipleiria.aerocontrol.listeners.SupportTicketMessageListener;
import amsi.dei.estg.ipleiria.aerocontrol.listeners.SignupListener;
import amsi.dei.estg.ipleiria.aerocontrol.listeners.SupportTicketListener;
import amsi.dei.estg.ipleiria.aerocontrol.listeners.SupportTicketsListener;
import amsi.dei.estg.ipleiria.aerocontrol.listeners.TicketListener;
import amsi.dei.estg.ipleiria.aerocontrol.listeners.TicketsListener;
import amsi.dei.estg.ipleiria.aerocontrol.listeners.UpdateUserListener;
import amsi.dei.estg.ipleiria.aerocontrol.utils.LoginParser;
import amsi.dei.estg.ipleiria.aerocontrol.utils.NetworkUtils;
import amsi.dei.estg.ipleiria.aerocontrol.utils.SignupJsonParser;
import amsi.dei.estg.ipleiria.aerocontrol.utils.UserJsonParser;

public class SingletonUser {

    private static SingletonUser instance = null;

    private static RequestQueue volleyQueue;

    private User user;
    private User userToUpdate;
    private ArrayList<FlightTicket> tickets;
    private ArrayList<SupportTicket> supportTickets;

    private static UserDBHelper userDB;

    private boolean loggedIn = false;

    private TicketsListener ticketsListener;
    private TicketListener ticketListener;
    private LoginListener loginListener;
    private UpdateUserListener updateUserListener;
    private SignupListener signupListener;
    private ResetPasswordListener resetPasswordListener;
    private SupportTicketsListener supportTicketsListener;
    private SupportTicketListener supportTicketListener;
    private SupportTicketMessageListener supportTicketMessageListener;

    private SingletonUser(Context context){
        user = null;
        userToUpdate = null;
        tickets = new ArrayList<>();
        supportTickets = new ArrayList<>();
        userDB = new UserDBHelper(context);
        getLoggedInOnStart(context);
    }

    public static synchronized SingletonUser getInstance(Context context){
        volleyQueue = Volley.newRequestQueue(context);

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

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiEndPoint.LOGIN,
                response -> {
                    User user = LoginParser.parserJsonLogin(response);
                    if (loginListener != null && user != null) {
                        loginListener.onValidateLogin(user, context);
                    } else Toast.makeText(context, R.string.invalid_credentials, Toast.LENGTH_SHORT).show();
                }, error -> Toast.makeText(context, "Erro", Toast.LENGTH_SHORT).show()){
            @Override
            public Map<String, String> getHeaders(){
                String userAndPass = username+":"+password;
                byte[] data = userAndPass.getBytes(StandardCharsets.UTF_8);
                String authorization = "Basic " + Base64.encodeToString(data,Base64.DEFAULT);

                Map<String, String> params = new HashMap<>();
                params.put("Authorization", authorization);
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

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiEndPoint.SIGNUP,
                response -> {
                    String message = SignupJsonParser.parserJsonSignup(response);
                    if (signupListener != null && message != null) {
                        signupListener.onSignup(message);
                    } else Toast.makeText(context, "Erro", Toast.LENGTH_SHORT).show();
                }, error -> Toast.makeText(context, "Erro", Toast.LENGTH_SHORT).show()){
            @Override
            public Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("username", user.getUsername());
                params.put("password_hash", user.getPassword());
                params.put("first_name", user.getFirstName());
                params.put("last_name", user.getLastName());
                params.put("gender", user.getGender());
                user.convertBirthdayToSave();
                params.put("birthdate", user.getBirthdate());
                user.convertBirthdayToDisplay();
                params.put("country", user.getCountry());
                params.put("city", user.getCity());
                params.put("email", user.getEmail());
                params.put("phone", user.getPhone());
                params.put("phone_country_code", user.getPhoneCountryCode());
                return params;
            }
        };

        volleyQueue.add(stringRequest);
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

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiEndPoint.RESETPASSWORD,
                response -> {
                    String message = SignupJsonParser.parserJsonSignup(response);
                    if (resetPasswordListener != null && message != null) {
                        resetPasswordListener.onEmailSent(message);
                    } else Toast.makeText(context, "Erro", Toast.LENGTH_SHORT).show();
                }, error -> Toast.makeText(context, "Erro", Toast.LENGTH_SHORT).show()){
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
        this.user = (User) user;
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
            String endPoint = ApiEndPoint.UPDATE_ACCOUNT + this.user.getId() + "?access-token=" + this.user.getToken();
            StringRequest stringRequest = new StringRequest(Request.Method.PUT, endPoint,
                    response -> {
                        if(updateUserListener != null){
                            updateUserListener.onUpdateUser(context.getString(R.string.save_data_success));
                            this.setUser(userToUpdate);
                        }
                        UserPreferences.getInstance(context).setUser(userToUpdate);
                    }, error -> Toast.makeText(context, R.string.save_data_failed, Toast.LENGTH_SHORT).show()
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("confirm_password", confirm_password);
                    params.put("username", userToUpdate.getUsername());
                    if (userToUpdate.getPassword() != null && userToUpdate.getPassword().length() > 0)
                        params.put("password_hash", userToUpdate.getPassword());
                    params.put("first_name", userToUpdate.getFirstName());
                    params.put("last_name", userToUpdate.getLastName());
                    params.put("gender", userToUpdate.getGender());
                    params.put("country", userToUpdate.getCountry());
                    params.put("city", userToUpdate.getCity());
                    params.put("email", userToUpdate.getEmail());
                    params.put("phone", userToUpdate.getPhone());
                    params.put("phone_country_code", userToUpdate.getPhoneCountryCode());
                    userToUpdate.convertBirthdayToSave();
                    params.put("birthdate", userToUpdate.getBirthdate());
                    userToUpdate.convertBirthdayToDisplay();
                    return params;
                }
            };
            volleyQueue.add(stringRequest);
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
    public ArrayList<FlightTicket> getTickets() {
        return tickets;
    }

    /**
     * Vai buscar os dados dos bilhetes à API
     * @param context context da atividade ou fragment
     */
    public void getTicketsAPI(final Context context){
        // Caso não haja internet
        if (!NetworkUtils.isConnectedInternet(context)){
            Toast.makeText(context, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
            readTicketsDB();
            ticketsListener.onRefreshList(tickets);
            return;
        }

        if (this.user != null){
            String endPoint = ApiEndPoint.MY_TICKETS + "?access-token=" + this.user.getToken();

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, endPoint, null,
                    response -> {
                        tickets = UserJsonParser.parserJsonTickets(response);
                        if (ticketsListener != null && tickets.size()>0){
                            userDB.truncateTableTickets();
                            addTicketsDB(tickets);
                            ticketsListener.onRefreshList(tickets);
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
    public void updateTicketAPI(final Context context, FlightTicket ticket){
        if (!NetworkUtils.isConnectedInternet(context)){
            Toast.makeText(context, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
            return;
        }

        if (this.user != null){
            String endPoint = ApiEndPoint.TICKETS + ticket.getId() + "?access-token=" + this.user.getToken();

            StringRequest stringRequest = new StringRequest(Request.Method.PUT, endPoint,
                    response -> {
                        Toast.makeText(context, R.string.check_in_done, Toast.LENGTH_SHORT).show();
                        ticket.setCheckIn(true);
                        updateTicketDB(ticket);
                        ticketListener.onRefreshTicket();
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
    public void deleteTicketAPI(final Context context, FlightTicket ticket){
        if (!NetworkUtils.isConnectedInternet(context)){
            Toast.makeText(context, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
            return;
        }

        if (this.user != null){
            String endPoint = ApiEndPoint.TICKETS + ticket.getId() + "?access-token=" + this.user.getToken();

            StringRequest stringRequest = new StringRequest(Request.Method.DELETE, endPoint,
                    response -> {
                        Toast.makeText(context, R.string.ticket_deleted, Toast.LENGTH_SHORT).show();
                        deleteTicketDB(ticket.getId());
                        this.tickets.remove(ticket);
                        ticketListener.onDeleteTicket();
                    }, error -> {
                Toast.makeText(context, "Erro ao eliminar", Toast.LENGTH_SHORT).show();
            });
            volleyQueue.add(stringRequest);
        }
    }

    /**
     * Cria todos os bilhetes numa base de dados local para que possam ser visualizados offline
     * @param tickets lista dos bilhetes a criar na BD
     */
    private void addTicketsDB(ArrayList<FlightTicket> tickets) {
        for (FlightTicket ticket: tickets) {
            userDB.createTicket(ticket);
            for (Passenger passenger : ticket.getPassengers()){
                userDB.createPassenger(passenger,ticket.getId());
            }
        }
    }

    /**
     * Atualiza um bilhete na BD
     */
    private void readTicketsDB() {
        tickets = userDB.readTickets();
        for (FlightTicket ticket: tickets){
            ticket.setPassengers(userDB.readPassengers(ticket.getId()));
        }
    }

    /**
     * Atualiza um bilhete que já esteja na BD local
     * @param ticket bilhete a atualizar na BD
     */
    private void updateTicketDB(FlightTicket ticket) {
        userDB.updateTicket(ticket);
    }

    /**
     *
     * @param id id do bilhete a eliminar
     */
    private void deleteTicketDB(int id){
        userDB.deleteTicket(id);
    }

    /**
     *
     * @param id Id do bilhete de voo.
     * @return Devolve o bilhete de voo.
     */
    public FlightTicket getTicketById(int id){
        for(FlightTicket ticket : tickets) {
            if(ticket.getId() == id) {
                return ticket;
            }
        }
        return null;
    }

    /**
     * Criar novo support ticket
     * @param context context da atividade ou fragment
     * @param title titulo a enviar para a API para ser guardado
     * @param message mensagem a enviar para a API para ser guardada
     */
    public void createSupportTicketAPI(final Context context, String title, String message){
        if (!NetworkUtils.isConnectedInternet(context)){
            Toast.makeText(context, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
            return;
        }

        if (this.user != null){
            String endPoint = ApiEndPoint.SUPPORT_TICKETS + "?access-token=" + this.user.getToken();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, endPoint,
                    response -> {
                        
                    }, error -> Toast.makeText(context, R.string.save_data_failed, Toast.LENGTH_SHORT).show()
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("title", title);
                    params.put("message", message);
                    return params;
                }
            };

            volleyQueue.add(stringRequest);
        }


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
            String endPoint = ApiEndPoint.MY_SUPPORT_TICKETS + "?access-token=" + this.user.getToken();

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, endPoint, null,
                    response -> {
                        supportTickets = UserJsonParser.parserJsonSupportTickets(response);
                        if (supportTicketsListener != null && supportTickets.size()>0){
                            userDB.truncateTableSupportTickets();
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
    public void createMessageSupportTicketAPI(final Context context, String message, SupportTicket supportTicket){
        if (!NetworkUtils.isConnectedInternet(context)){
            Toast.makeText(context, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
            return;
        }

        if (this.user != null){
            String endPoint = ApiEndPoint.SUPPORT_TICKET_MESSAGE + "?access-token=" + this.user.getToken();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, endPoint,
                    response -> {
                        TicketMessage ticketMessage = new TicketMessage(0, message, user.getUsername());
                        createSupportTicketMessageDB(ticketMessage, supportTicket.getId());
                        supportTicket.addMessage(ticketMessage);
                        if(supportTicketMessageListener != null){
                            supportTicketMessageListener.onSupportTicketMessage(context.getString(R.string.create_data_success));
                        }
                    }, error -> Toast.makeText(context, R.string.save_data_failed, Toast.LENGTH_SHORT).show()
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("message", message);
                    params.put("sender_id", user.getId()+"");
                    params.put("support_ticket_id", supportTicket.getId() +"");
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
            String endPoint = ApiEndPoint.SUPPORT_TICKETS + "/" + supportTicket.getId() + "?access-token=" + this.user.getToken();

            StringRequest stringRequest = new StringRequest(Request.Method.PUT, endPoint,
                    response -> {
                        Toast.makeText(context, R.string.support_ticket_done, Toast.LENGTH_SHORT).show();
                        supportTicket.setState("Concluido");
                        updateSupportTicketDB(supportTicket);
                        if (supportTicketListener != null)
                            supportTicketListener.onRefreshSupportTicket();
                    }, error -> Toast.makeText(context, R.string.error_closing_support_ticket, Toast.LENGTH_SHORT).show()
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
            userDB.createSupportTicket(supportTicket);
            for (TicketMessage message : supportTicket.getMessages()){
                userDB.createMessage(message,supportTicket.getId());
            }
        }
    }

    /**
     * Atualiza um support ticket na BD
     */
    private void readSupportTicketsDB() {
        supportTickets = userDB.readSupportTickets();
        for (SupportTicket supportTicket: supportTickets){
            supportTicket.setMessages(userDB.readMessages(supportTicket.getId()));
        }
    }

    /**
     * Atualiza um support ticket que já esteja na BD local
     * @param supportTicket bilhete a atualizar na BD
     */
    private void updateSupportTicketDB(SupportTicket supportTicket) {
        userDB.updateSupportTicket(supportTicket);
    }

    /**
     * Cria uma mensagem num ticket de suporte já existente na BD Local
     * @param message Mensagem a criar
     * @param supportTicket_id id do Support ticket
     */
    private void createSupportTicketMessageDB(TicketMessage message, int supportTicket_id) {
        userDB.createMessage(message, supportTicket_id);
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

    public void setLoginListener(LoginListener loginListener) {
        this.loginListener = loginListener;
    }

    public void setTicketsListener(TicketsListener ticketsListener) {
        this.ticketsListener = ticketsListener;
    }

    public void setTicketListener(TicketListener ticketListener) {
        this.ticketListener = ticketListener;
    }

    public void setUpdateUserListener(UpdateUserListener updateUserListener) {
        this.updateUserListener = updateUserListener;
    }

    public void setSignupListener(SignupListener signupListener){
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
}
