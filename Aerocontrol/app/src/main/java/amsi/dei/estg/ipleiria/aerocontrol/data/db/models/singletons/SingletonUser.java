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
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.LostItem;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.Passenger;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.SupportTicket;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.TicketMessage;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.User;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.helpers.UserDBHelper;
import amsi.dei.estg.ipleiria.aerocontrol.data.network.ApiEndPoint;
import amsi.dei.estg.ipleiria.aerocontrol.data.prefs.UserPreferences;
import amsi.dei.estg.ipleiria.aerocontrol.listeners.LoginListener;
import amsi.dei.estg.ipleiria.aerocontrol.listeners.SupportTicketListener;
import amsi.dei.estg.ipleiria.aerocontrol.listeners.SupportTicketsListener;
import amsi.dei.estg.ipleiria.aerocontrol.listeners.TicketListener;
import amsi.dei.estg.ipleiria.aerocontrol.listeners.TicketsListener;
import amsi.dei.estg.ipleiria.aerocontrol.listeners.UpdateUserListener;
import amsi.dei.estg.ipleiria.aerocontrol.utils.LoginParser;
import amsi.dei.estg.ipleiria.aerocontrol.utils.NetworkUtils;
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
    private SupportTicketsListener supportTicketsListener;
    private SupportTicketListener supportTicketListener;

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
                    params.put("firstName", userToUpdate.getFirstName());
                    params.put("lastName", userToUpdate.getLastName());
                    params.put("gender", userToUpdate.getGender());
                    params.put("country", userToUpdate.getCountry());
                    params.put("city", userToUpdate.getCity());
                    params.put("email", userToUpdate.getEmail());
                    params.put("phone", userToUpdate.getPhone());
                    params.put("phoneCountryCode", userToUpdate.getPhoneCountryCode());
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
     *
     * @param ticket Bilhete de voo a adicionar.
     */
    public void addTicket(FlightTicket ticket) {
        this.tickets.add(ticket);
    }

    /**
     *
     * @param ticket Bilhete de voo a apagar.
     */
    public void deleteTicket(FlightTicket ticket) {
        this.tickets.remove(ticket);
    }

    /**
     * Vai buscar os dados dos support ticket à API
     * @param context context da atividade ou fragment
     */
    public void getSupportTicketsAPI(final Context context){
        // Caso não haja internet
        if (!NetworkUtils.isConnectedInternet(context)){
            Toast.makeText(context, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
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
     * Cria todos os support tickets numa base de dados local para que possam ser visualizados offline
     * @param supportTickets lista dos support ticket a criar na BD
     */
    private void addSupportTicketsDB(ArrayList<SupportTicket> supportTickets) {
        for (SupportTicket supportTicket: supportTickets) {
            userDB.createSupportTicket(supportTicket);
        }
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

    public void setSupportTicketsListener(SupportTicketsListener supportTicketsListener) {
        this.supportTicketsListener = supportTicketsListener;
    }

    public void setSupportTicketListener(SupportTicketListener supportTicketListener) {
        this.supportTicketListener = supportTicketListener;
    }
}
