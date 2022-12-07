package amsi.dei.estg.ipleiria.aerocontrol.models;

import java.util.ArrayList;

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

    public static synchronized SingletonUser getInstance(){
        if (instance == null) instance = new SingletonUser();
        return instance;
    }

    /*
        User Data
     */

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void editUser(User user){
        if(!this.user.getUsername().equals(user.getUsername())) this.user.setUsername(user.getUsername());
        // Password encriptada, logo impossivel fazer comparação
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

    /*
        FlightTickets Data
     */

    public ArrayList<FlightTicket> getTickets() {
        return tickets;
    }

    public FlightTicket getTicketById(int id){
        for(FlightTicket ticket : tickets) {
            if(ticket.getId() == id) {
                return ticket;
            }
        }
        return null;
    }

    public void addTickets(FlightTicket ticket) {
        this.tickets.add(ticket);
    }

    public void deleteTickets(FlightTicket ticket) {
        this.tickets.remove(ticket);
    }

    /*
        FlightTickets -> Passengers Data
     */

    public ArrayList<Passenger> getPassengers(int id){
        for(FlightTicket ticket : tickets) {
            if(ticket.getId() == id) {
                return ticket.getPassengers();
            }
        }
        return null;
    }

    public void addPassengers(int id, Passenger passenger){
        for(FlightTicket ticket : tickets) {
            if(ticket.getId() == id) {
                ticket.addPassenger(passenger);
            }
        }
    }

    /*
        SupportTickets Data
     */

    public ArrayList<SupportTicket> getSupportTickets() {
        return supportTickets;
    }

    public SupportTicket getSupportTicketById(int id){
        for(SupportTicket supportTicket : supportTickets) {
            if(supportTicket.getId() == id) {
                return supportTicket;
            }
        }
        return null;
    }

    public void addSupportTicket(SupportTicket supportTicket) {
        this.supportTickets.add(supportTicket);
    }

    /*
        SupportTickets -> LostItems Data
     */

    public ArrayList<LostItem> getLostItems(int id){
        for(SupportTicket supportTicket : supportTickets) {
            if(supportTicket.getId() == id) {
                return supportTicket.getItems();
            }
        }
        return null;
    }

    public void addLostItem(int id, LostItem item){
        for(SupportTicket supportTicket : supportTickets) {
            if(supportTicket.getId() == id) {
                supportTicket.addItem(item);
            }
        }
    }

    /*
        SupportTickets -> Messages Data
     */

    public ArrayList<TicketMessage> getMessages(int id){
        for(SupportTicket supportTicket : supportTickets) {
            if(supportTicket.getId() == id) {
                return supportTicket.getMessages();
            }
        }
        return null;
    }

    public void addMessage(int id, TicketMessage message){
        for(SupportTicket supportTicket : supportTickets) {
            if(supportTicket.getId() == id) {
                supportTicket.addMessage(message);
            }
        }
    }

}
