package amsi.dei.estg.ipleiria.aerocontrol.data.db.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.FlightTicket;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.Passenger;
import amsi.dei.estg.ipleiria.aerocontrol.data.network.ApiConfig;
import amsi.dei.estg.ipleiria.aerocontrol.utils.DateDisplayFormatUtils;

public class UserDBManager {

    private static UserDBManager instance = null;
    private static UserDBOpenHelper dbOpenHelper = null;

    private Context context;
    private final SQLiteDatabase database;

    private UserDBManager(Context context) {
        this.context = context;
        if (this.dbOpenHelper == null)
            this.dbOpenHelper = new UserDBOpenHelper(context);
        this.database = dbOpenHelper.getWritableDatabase();
    }

    public static synchronized UserDBManager getInstance(Context context) {
        if (instance == null)
            instance = new UserDBManager(context);

        return instance;
    }

    private ContentValues convertFlightTicketToContentValues(FlightTicket flightTicket) {
        ContentValues values = new ContentValues();

        values.put(UserDBOpenHelper.COL_FLIGHT_TICKET_ID, flightTicket.getId());
        values.put(UserDBOpenHelper.COL_FLIGHT_TICKET_PAYMENT_METHOD, flightTicket.getPaymentMethod());
        values.put(UserDBOpenHelper.COL_FLIGHT_TICKET_STATE, flightTicket.getFlightState());
        values.put(UserDBOpenHelper.COL_FLIGHT_TICKET_ORIGIN,flightTicket.getFlightOrigin());
        values.put(UserDBOpenHelper.COL_FLIGHT_TICKET_ARRIVAL, flightTicket.getFlightArrival());
        values.put(UserDBOpenHelper.COL_FLIGHT_TICKET_DEPARTURE_TIME,flightTicket.getFlightDepartureTime());
        values.put(UserDBOpenHelper.COL_FLIGHT_TICKET_ARRIVAL_TIME, flightTicket.getFlightArrivalTime());
        values.put(UserDBOpenHelper.COL_FLIGHT_TICKET_TERMINAL,flightTicket.getTerminal());
        values.put(UserDBOpenHelper.COL_FLIGHT_TICKET_ORIGINAL_PRICE,flightTicket.getOriginalPrice());
        values.put(UserDBOpenHelper.COL_FLIGHT_TICKET_PAID_PRICE, flightTicket.getPaidPrice());
        values.put(UserDBOpenHelper.COL_FLIGHT_TICKET_FLIGHT_DATE,flightTicket.getFlightDate());
        values.put(UserDBOpenHelper.COL_FLIGHT_TICKET_PURCHASE_DATE, DateDisplayFormatUtils.formatDateTimeToString(flightTicket.getPurchaseDate()));
        values.put(UserDBOpenHelper.COL_FLIGHT_TICKET_DISTANCE,flightTicket.getDistance());
        values.put(UserDBOpenHelper.COL_FLIGHT_TICKET_CHECK_IN, flightTicket.isCheckIn());

        return values;
    }

    /**
     * Cria um bilhete de voo na BD local
     * @param ticket Bilhete de voo a criar
     */
    public void createFlightTicket(FlightTicket ticket){
        ContentValues values = convertFlightTicketToContentValues(ticket);
        this.database.insert(UserDBOpenHelper.TBL_FLIGHT_TICKET, null, values);
    }

    /**
     * Lê os bilhetes de voo da BD
     * @return Devolve todos os bilhetes de voo que estão na BD local
     */
    public ArrayList<FlightTicket> readFlightTickets(){
        ArrayList<FlightTicket> tickets = new ArrayList<>();
        Cursor cursor = this.database.rawQuery("SELECT * FROM " + UserDBOpenHelper.TBL_FLIGHT_TICKET, null);
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
                        DateDisplayFormatUtils.formatStringToDateTime(cursor.getString(11), null), // Para o User ter o birthdate com o formate consistente
                        cursor.getFloat(12),
                        cursor.getInt(13) == 1) // Verifica se o check in é true ou false
                );
            }while(cursor.moveToNext());
        }
        cursor.close();
        return tickets;
    }

    /**
     * Atualiza o bilhete de voo na BD
     * @param ticket Bilhete de voo a ser autalizado
     * @return boolean Devolve true se atualizou alguma row
     */
    public boolean updateFlightTicket(FlightTicket ticket){
        ContentValues values = convertFlightTicketToContentValues(ticket);

        return this.database.update(UserDBOpenHelper.TBL_FLIGHT_TICKET, values, "id = ?", new String[]{"" + ticket.getId()}) > 0;
    }

    /**
     * Apaga um bilhete de voo da BD
     * @param idTicket id do bilhete de voo a ser eliminado
     */
    public void deleteFlightTicket(int idTicket){
        deletePassengers(idTicket);
        this.database.delete(UserDBOpenHelper.TBL_FLIGHT_TICKET, "id = ?", new String[]{"" + idTicket});
    }

    /**
     * Dá truncate à tabela dos bilhetes de voo
     */
    public void truncateTableFlightTickets(){
        truncateTablePassengers();
        this.database.delete(UserDBOpenHelper.TBL_FLIGHT_TICKET,null,null);
    }

    private ContentValues convertPassengerToContentValues(Passenger passenger, int flightTicketId) {
        ContentValues values = new ContentValues();

        values.put(UserDBOpenHelper.COL_PASSENGER_ID, passenger.getId());
        values.put(UserDBOpenHelper.COL_PASSENGER_NAME, passenger.getName());
        values.put(UserDBOpenHelper.COL_PASSENGER_GENDER, passenger.getGender());
        values.put(UserDBOpenHelper.COL_PASSENGER_SEAT, passenger.getSeat());
        values.put(UserDBOpenHelper.COL_PASSENGER_EXTRA_BAGGAGE, passenger.haveExtraBaggage());
        values.put(UserDBOpenHelper.COL_PASSENGER_FLIGHT_TICKET_ID, flightTicketId);

        return values;
    }

    /**
     * Cria um passageiro na BD local
     * @param passenger Passageiro a criar
     * @param flightTicketId Id do bilhete de voo
     */
    public void createPassenger (Passenger passenger, int flightTicketId) {
        ContentValues values = convertPassengerToContentValues(passenger, flightTicketId);

        this.database.insert(UserDBOpenHelper.TBL_PASSENGER, null, values);
    }

    /**
     * Lê os passageiros da BD Local
     * @return Devolve todos os passageiros que estão na BD local
     */
    public ArrayList<Passenger> readPassengers(int ticket_id){
        ArrayList<Passenger> passengers = new ArrayList<>();
        Cursor cursor = this.database.rawQuery("SELECT * " +
                        "FROM " + UserDBOpenHelper.TBL_PASSENGER + " " +
                        "WHERE " + UserDBOpenHelper.COL_PASSENGER_FLIGHT_TICKET_ID + " == " +  ticket_id +";", null);
        if(cursor.moveToFirst()){
            do{
                passengers.add(new Passenger(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getInt(4) == 1) // Verifica se o extra baggage é true ou false
                );
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
        this.database.delete(UserDBOpenHelper.TBL_PASSENGER, "+" + UserDBOpenHelper.COL_PASSENGER_FLIGHT_TICKET_ID + " = ?", new String[]{"" + idTicket});
    }

    /**
     * Dá truncate à tabela dos passageiros
     */
    public void truncateTablePassengers(){
        this.database.delete(UserDBOpenHelper.TBL_PASSENGER,null,null);
    }
}
