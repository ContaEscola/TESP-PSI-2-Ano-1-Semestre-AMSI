package amsi.dei.estg.ipleiria.aerocontrol.models;

public class TicketMessage {
    private int id;
    private String message;
    private String photo;
    private String sender;

    public TicketMessage(int id, String message, String photo, String sender){
        this.setId(id);
        this.setMessage(message);
        this.setPhoto(photo);
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
