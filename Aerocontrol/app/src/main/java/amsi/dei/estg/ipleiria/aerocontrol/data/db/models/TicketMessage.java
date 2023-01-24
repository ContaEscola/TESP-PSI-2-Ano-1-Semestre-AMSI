package amsi.dei.estg.ipleiria.aerocontrol.data.db.models;

public class TicketMessage {
    private int id;
    private String message;
    private String sender;

    public TicketMessage(int id, String message, String sender){
        this.setId(id);
        this.setMessage(message);
        this.setSender(sender);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
