package amsi.dei.estg.ipleiria.aerocontrol.data.db.models;

import java.util.ArrayList;

public class SupportTicket {
    private int id;
    private String title;
    private String state;

    private ArrayList<LostItem> items;
    private ArrayList<TicketMessage> messages;

    public SupportTicket(int id,String title,String state){
        this.setId(id);
        this.setTitle(title);
        this.setState(state);
        messages = new ArrayList<>();
        items = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public ArrayList<TicketMessage> getMessages(){
        return this.messages;
    }

    public void addMessage(TicketMessage message){
        this.messages.add(message);
    }

    public ArrayList<LostItem> getItems(){
        return this.items;
    }

    public void addItem(LostItem item){
        this.items.add(item);
    }

    public void setMessages(ArrayList<TicketMessage> messages){
        this.messages = messages;
    }
}
