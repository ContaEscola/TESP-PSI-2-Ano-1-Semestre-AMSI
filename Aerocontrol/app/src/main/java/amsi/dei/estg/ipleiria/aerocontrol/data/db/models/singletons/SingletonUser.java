package amsi.dei.estg.ipleiria.aerocontrol.data.db.models.singletons;

import android.content.Context;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.FlightTicket;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.LostItem;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.Passenger;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.SupportTicket;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.TicketMessage;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.User;

public class SingletonUser {
    private static SingletonUser instance = null;

    private User user;
    private ArrayList<FlightTicket> tickets;
    private ArrayList<SupportTicket> supportTickets;

    private SingletonUser(){
        user = null;
        tickets = new ArrayList<>();
        supportTickets = new ArrayList<>();
    }

    public static synchronized SingletonUser getInstance(Context context){
        if (instance == null) instance = new SingletonUser();
        return instance;
    }

    /**
     *
     * @return Devolve o Utilizador.
     */
    public User getUser() {
        return user;
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
     * @param user Utilizador atualizado.
     */
    public void editUser(User user){
        if(!this.user.getUsername().equals(user.getUsername())) this.user.setUsername(user.getUsername());
        // TODO Password encriptada, logo impossivel fazer comparação
        //if(!this.user.getPassword().equals(user.getPassword())) this.user.setPassword(user.getPassword());
        this.user.setPassword(user.getPassword());
        if(!this.user.getFirstName().equals(user.getFirstName())) this.user.setFirstName(user.getFirstName());
        if(!this.user.getLastName().equals(user.getLastName())) this.user.setLastName(user.getLastName());
        if(!this.user.getGender().equals(user.getGender())) this.user.setGender(user.getGender());
        if(!this.user.getCountry().equals(user.getCountry())) this.user.setCountry(user.getCountry());
        if(!this.user.getCity().equals(user.getCity())) this.user.setCity(user.getCity());
        if(!this.user.getEmail().equals(user.getEmail())) this.user.setEmail(user.getEmail());
        if(!this.user.getPhone().equals(user.getPhone())) this.user.setPhone(user.getPhone());
        if(!this.user.getPhone().equals(user.getPhone())) this.user.setPhoneCountryCode(user.getPhoneCountryCode());
        if(!this.user.getBirthdate().equals(user.getBirthdate())) this.user.setBirthdate(user.getBirthdate());
    }

    /**
     *
     * @return Devolve a lista de todos os bilhetes de voo.
     */
    public ArrayList<FlightTicket> getTickets() {
        return tickets;
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
     *
     * @param id Id do bilhete de voo.
     * @return Devolve a lista de passageiros do bilhete de voo.
     */
    public ArrayList<Passenger> getPassengers(int id){
        FlightTicket ticket = getTicketById(id);
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
        FlightTicket ticket = getTicketById(id);
        if(ticket != null)
            ticket.addPassenger(passenger);
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
