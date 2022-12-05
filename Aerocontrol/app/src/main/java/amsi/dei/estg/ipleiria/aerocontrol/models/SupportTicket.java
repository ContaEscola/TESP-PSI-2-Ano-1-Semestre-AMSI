package amsi.dei.estg.ipleiria.aerocontrol.models;

public class SupportTicket {
    private int id;
    private String title;
    private String state;

    // ArrayList TicketItem
    // ArrayList TicketMessages

    public SupportTicket(int id,String title,String state){
        this.setId(id);
        this.setTitle(title);
        this.setState(state);
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
}
