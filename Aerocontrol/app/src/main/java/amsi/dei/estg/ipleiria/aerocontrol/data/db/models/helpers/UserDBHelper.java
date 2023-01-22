package amsi.dei.estg.ipleiria.aerocontrol.data.db.models.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.FlightTicket;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.Passenger;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.SupportTicket;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.TicketMessage;

public class UserDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "aerocontrol_user";
    private static final String TABLE_NAME_TICKETS = "flight_tickets";
    private static final String TABLE_NAME_PASSENGERS = "passengers";
    private static final String TABLE_NAME_SUPPORT_TICKETS = "support_tickets";
    private static final String TABLE_NAME_SUPPORT_TICKET_MESSAGES = "ticket_messages";

    private static final int DB_VERSION=1;

    // Campos da tabela flight_tickets
    public static final String TICKETS_ID = "id";
    public static final String TICKETS_PAYMENT_METHOD = "payment_method";
    public static final String TICKETS_STATE = "state";
    public static final String TICKETS_ORIGIN = "origin";
    public static final String TICKETS_ARRIVAL = "arrival";
    public static final String TICKETS_DEPARTURE_TIME = "departure_time";
    public static final String TICKETS_ARRIVAL_TIME = "arrival_time";
    public static final String TICKETS_TERMINAL = "terminal";
    public static final String TICKETS_ORIGINAL_PRICE = "original_price";
    public static final String TICKETS_PAID_PRICE = "paid_price";
    public static final String TICKETS_FLIGHT_DATE = "flight_date";
    public static final String TICKETS_PURCHASE_DATE = "purchase_date";
    public static final String TICKETS_DISTANCE = "distance";
    public static final String TICKETS_CHECK_IN = "check_in";

    // Campos da tabela passengers
    public static final String PASSENGERS_ID = "id";
    public static final String PASSENGERS_NAME = "name";
    public static final String PASSENGERS_GENDER = "gender";
    public static final String PASSENGERS_SEAT = "seat";
    public static final String PASSENGERS_EXTRA_BAGGAGE = "extra_baggage";
    public static final String PASSENGERS_TICKET_ID = "flight_ticket_id";

    //Campos da tabela support tickets
    public static final String SUPPORT_TICKET_ID = "id";
    public static final String SUPPORT_TICKET_TITLE = "title";
    public static final String SUPPORT_TICKET_STATE = "state";

    //Campos da tabela support ticket messages
    public static final String TICKET_MESSAGES_ID = "id";
    public static final String TICKET_MESSAGES_MESSAGE = "message";
    public static final String TICKET_MESSAGES_SENDER = "sender";
    public static final String TICKET_MESSAGES_TICKET_ID = "support_ticket_id";

    private final SQLiteDatabase database;

    public UserDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.database = this.getWritableDatabase();
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTicketsTable = "CREATE TABLE " + TABLE_NAME_TICKETS +
                "( " + TICKETS_ID + " INTEGER PRIMARY KEY NOT NULL, " +
                TICKETS_PAYMENT_METHOD + " TEXT NOT NULL," +
                TICKETS_STATE + " TEXT NOT NULL," +
                TICKETS_ORIGIN + " TEXT NOT NULL, " +
                TICKETS_ARRIVAL + " TEXT NOT NULL," +
                TICKETS_DEPARTURE_TIME + " TEXT NOT NULL," +
                TICKETS_ARRIVAL_TIME + " TEXT NOT NULL," +
                TICKETS_TERMINAL + " TEXT NOT NULL," +
                TICKETS_ORIGINAL_PRICE + " DOUBLE NOT NULL," +
                TICKETS_PAID_PRICE + " DOUBLE NOT NULL," +
                TICKETS_FLIGHT_DATE + " TEXT NOT NULL," +
                TICKETS_PURCHASE_DATE + " TEXT NOT NULL," +
                TICKETS_DISTANCE + " FLOAT NOT NULL," +
                TICKETS_CHECK_IN + " BOOLEAN NOT NULL " + ");";

        String createPassengersTable = "CREATE TABLE " + TABLE_NAME_PASSENGERS +
                "( " + PASSENGERS_ID + " INTEGER PRIMARY KEY NOT NULL, " +
                PASSENGERS_NAME + " TEXT NOT NULL," +
                PASSENGERS_GENDER + " TEXT NOT NULL," +
                PASSENGERS_SEAT + " TEXT NOT NULL, " +
                PASSENGERS_EXTRA_BAGGAGE + " BOOLEAN NOT NULL," +
                PASSENGERS_TICKET_ID + " INTEGER NOT NULL," +
                "FOREIGN KEY (" + PASSENGERS_TICKET_ID + ") REFERENCES " + TABLE_NAME_TICKETS + "(" + TICKETS_ID + ")" + ");";

        String createSupportTicketTable = "CREATE TABLE " + TABLE_NAME_SUPPORT_TICKETS +
                "( " + SUPPORT_TICKET_ID + " INTEGER PRIMARY KEY NOT NULL, " +
                SUPPORT_TICKET_TITLE + " TEXT NOT NULL," +
                SUPPORT_TICKET_STATE + " TEXT NOT NULL) ;";

        String createTicketMessagesTable = "CREATE TABLE " + TABLE_NAME_SUPPORT_TICKET_MESSAGES +
                "( " + TICKET_MESSAGES_ID + " INTEGER PRIMARY KEY NOT NULL, " +
                TICKET_MESSAGES_MESSAGE + " TEXT NOT NULL," +
                TICKET_MESSAGES_SENDER + " TEXT NOT NULL," +
                TICKET_MESSAGES_TICKET_ID + " INTEGER NOT NULL," +
                "FOREIGN KEY (" + TICKET_MESSAGES_TICKET_ID + ") REFERENCES " + TABLE_NAME_SUPPORT_TICKETS + "(" + SUPPORT_TICKET_ID + ")" + ");";

        db.execSQL(createTicketsTable);
        db.execSQL(createPassengersTable);
        db.execSQL(createSupportTicketTable);
        db.execSQL(createTicketMessagesTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_PASSENGERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_TICKETS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_SUPPORT_TICKETS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_SUPPORT_TICKET_MESSAGES);
        this.onCreate(db);
    }

    /**
     * Cria um bilhete na BD local
     * @param ticket Bilhete a criar
     */
    public void createTicket (FlightTicket ticket){
        ContentValues values = new ContentValues();
        values.put(TICKETS_ID, ticket.getId());
        values.put(TICKETS_PAYMENT_METHOD, ticket.getPaymentMethod());
        values.put(TICKETS_STATE, ticket.getFlightState());
        values.put(TICKETS_ORIGIN,ticket.getFlightOrigin());
        values.put(TICKETS_ARRIVAL, ticket.getFlightArrival());
        values.put(TICKETS_DEPARTURE_TIME,ticket.getFlightDepartureTime());
        values.put(TICKETS_ARRIVAL_TIME, ticket.getFlightArrivalTime());
        values.put(TICKETS_TERMINAL,ticket.getTerminal());
        values.put(TICKETS_ORIGINAL_PRICE,ticket.getOriginalPrice());
        values.put(TICKETS_PAID_PRICE, ticket.getPricePaid());
        values.put(TICKETS_FLIGHT_DATE,ticket.getFlightDate());
        values.put(TICKETS_PURCHASE_DATE, ticket.getPurchaseDate());
        values.put(TICKETS_DISTANCE,ticket.getDistance());
        values.put(TICKETS_CHECK_IN, ticket.isCheckIn());
        this.database.insert(TABLE_NAME_TICKETS, null, values);
    }

    /**
     * Lê os bilhetes da BD
     * @return Devolve todos os bilhetes que estão na BD local
     */
    public ArrayList<FlightTicket> readTickets(){
        ArrayList<FlightTicket> tickets = new ArrayList<>();
        Cursor cursor = this.database.rawQuery("SELECT * FROM " + TABLE_NAME_TICKETS, null);
        if(cursor.moveToFirst()){
            do{
                tickets.add(new FlightTicket(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getDouble(8),
                        cursor.getDouble(9),
                        cursor.getString(10),
                        cursor.getString(11),
                        cursor.getFloat(12),
                        cursor.getInt(13) == 1));
            }while(cursor.moveToNext());
        }
        cursor.close();
        return tickets;
    }

    /**
     * Atualiza o bilhete na BD
     * @param ticket Bilhete a ser autalizado
     * @return Devolve o número de rows atualizadas.
     */
    public boolean updateTicket(FlightTicket ticket){
        ContentValues values = new ContentValues();
        values.put(TICKETS_ID, ticket.getId());
        values.put(TICKETS_PAYMENT_METHOD, ticket.getPaymentMethod());
        values.put(TICKETS_STATE, ticket.getFlightState());
        values.put(TICKETS_ORIGIN,ticket.getFlightOrigin());
        values.put(TICKETS_ARRIVAL, ticket.getFlightArrival());
        values.put(TICKETS_DEPARTURE_TIME,ticket.getFlightDepartureTime());
        values.put(TICKETS_ARRIVAL_TIME, ticket.getFlightArrivalTime());
        values.put(TICKETS_TERMINAL,ticket.getTerminal());
        values.put(TICKETS_ORIGINAL_PRICE,ticket.getOriginalPrice());
        values.put(TICKETS_PAID_PRICE, ticket.getPricePaid());
        values.put(TICKETS_FLIGHT_DATE,ticket.getFlightDate());
        values.put(TICKETS_PURCHASE_DATE, ticket.getPurchaseDate());
        values.put(TICKETS_DISTANCE,ticket.getDistance());
        values.put(TICKETS_CHECK_IN, ticket.isCheckIn());
        return this.database.update(TABLE_NAME_TICKETS, values, "id = ?", new String[]{"" + ticket.getId()}) > 0;
    }

    /**
     * Apaga um bilhete da BD
     * @param idTicket id do bilhete a ser eliminado
     */
    public void deleteTicket(int idTicket){
        deletePassengers(idTicket);
        this.database.delete(TABLE_NAME_TICKETS, "id = ?", new String[]{"" + idTicket});
    }

    /**
     * Dá truncate à tabela dos bilhetes
     */
    public void truncateTableTickets(){
        truncateTablePassengers();
        this.database.delete(TABLE_NAME_TICKETS,null,null);
    }

    /**
     * Cria um passageiro na BD local
     * @param passenger Passageiro a criar
     */
    public void createPassenger (Passenger passenger, int ticket_id) {
        ContentValues values = new ContentValues();
        values.put(PASSENGERS_ID, passenger.getId());
        values.put(PASSENGERS_NAME, passenger.getName());
        values.put(PASSENGERS_GENDER, passenger.getGender());
        values.put(PASSENGERS_SEAT, passenger.getSeat());
        values.put(PASSENGERS_EXTRA_BAGGAGE, passenger.haveExtraBaggage());
        values.put(PASSENGERS_TICKET_ID, ticket_id);
        this.database.insert(TABLE_NAME_PASSENGERS, null, values);
    }

    /**
     * Lê os passageiros da BD
     * @return Devolve todos os passageiros que estão na BD local
     */
    public ArrayList<Passenger> readPassengers(int ticket_id){
        ArrayList<Passenger> passengers = new ArrayList<>();
        Cursor cursor = this.database.rawQuery("SELECT * FROM " + TABLE_NAME_PASSENGERS +
                " WHERE " + PASSENGERS_TICKET_ID + " == " +  ticket_id +";", null);
        if(cursor.moveToFirst()){
            do{
                passengers.add(new Passenger(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getInt(4) == 1));
            }while(cursor.moveToNext());
        }
        cursor.close();
        return passengers;
    }

    /**
     * Apaga os passageiros da BD
     * @param idTicket id do bilhete associado aos passageiros
     */
    public void deletePassengers(int idTicket){
        this.database.delete(TABLE_NAME_PASSENGERS, "+" + PASSENGERS_TICKET_ID + " = ?", new String[]{"" + idTicket});
    }

    /**
     * Dá truncate à tabela dos passageiros
     */
    public void truncateTablePassengers(){
        this.database.delete(TABLE_NAME_PASSENGERS,null,null);
    }

    /**
     * Cria um support ticket na BD local
     * @param supportTicket support ticket a criar
     */
    public void createSupportTicket (SupportTicket supportTicket){
        ContentValues values = new ContentValues();
        values.put(SUPPORT_TICKET_ID, supportTicket.getId());
        values.put(SUPPORT_TICKET_TITLE, supportTicket.getTitle());
        values.put(SUPPORT_TICKET_STATE, supportTicket.getState());
        this.database.insert(TABLE_NAME_SUPPORT_TICKETS, null, values);
    }

    /**
     * Lê os support ticket da BD
     * @return Devolve todos os support ticket que estão na BD local
     */
    public ArrayList<SupportTicket> readSupportTickets(){
        ArrayList<SupportTicket> supportTickets = new ArrayList<>();
        Cursor cursor = this.database.rawQuery("SELECT * FROM " + TABLE_NAME_SUPPORT_TICKETS, null);
        if(cursor.moveToFirst()){
            do{
                supportTickets.add(new SupportTicket(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2)));
            }while(cursor.moveToNext());
        }
        cursor.close();
        return supportTickets;
    }

    /**
     * Atualiza o suporte ticket na BD
     * @param supportTicket support ticket a ser autalizado
     * @return Devolve o número de rows atualizadas.
     */
    public boolean updateSupportTicket(SupportTicket supportTicket){
        ContentValues values = new ContentValues();
        values.put(SUPPORT_TICKET_ID, supportTicket.getId());
        values.put(SUPPORT_TICKET_TITLE, supportTicket.getTitle());
        values.put(SUPPORT_TICKET_STATE, supportTicket.getState());
        return this.database.update(TABLE_NAME_SUPPORT_TICKETS, values, "id = ?", new String[]{"" + supportTicket.getId()}) > 0;
    }

    /**
     * Dá truncate à tabela dos support ticket
     */
    public void truncateTableSupportTickets(){
        this.truncateTableMessages();
        this.database.delete(TABLE_NAME_SUPPORT_TICKETS,null,null);
    }

    /**
     * Cria uma mensagem na BD local
     * @param message Passageiro a criar
     */
    public void createMessage (TicketMessage message, int support_ticket_id) {
        ContentValues values = new ContentValues();
        values.put(TICKET_MESSAGES_MESSAGE, message.getMessage());
        values.put(TICKET_MESSAGES_SENDER, message.getSender());
        values.put(TICKET_MESSAGES_TICKET_ID, support_ticket_id);
        this.database.insert(TABLE_NAME_SUPPORT_TICKET_MESSAGES, null, values);
    }

    /**
     * Lê os passageiros da BD
     * @return Devolve todos os passageiros que estão na BD local
     */
    public ArrayList<TicketMessage> readMessages(int support_ticket_id){
        ArrayList<TicketMessage> messages = new ArrayList<>();
        Cursor cursor = this.database.rawQuery("SELECT * FROM " + TABLE_NAME_SUPPORT_TICKET_MESSAGES +
                " WHERE " + TICKET_MESSAGES_TICKET_ID + " == " +  support_ticket_id +";", null);
        if(cursor.moveToFirst()){
            do{
                messages.add(new TicketMessage(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2))
                );
            }while(cursor.moveToNext());
        }
        cursor.close();
        return messages;
    }

    /**
    *Dá truncate à tabela dos mensagens
    */
    public void truncateTableMessages(){
        this.database.delete(TABLE_NAME_SUPPORT_TICKET_MESSAGES,null,null);
    }
}
