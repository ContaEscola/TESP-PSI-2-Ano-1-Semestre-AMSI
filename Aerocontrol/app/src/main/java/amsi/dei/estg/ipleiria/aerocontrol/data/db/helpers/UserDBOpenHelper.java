package amsi.dei.estg.ipleiria.aerocontrol.data.db.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.FlightTicket;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.Passenger;

public class UserDBOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "aerocontrol_user";
    private static final int DB_VERSION = 1;

    public static final String TBL_FLIGHT_TICKET = "flight_ticket";
    public static final String TBL_PASSENGER = "passenger";



    // Campos da tabela flight ticket
    public static final String COL_FLIGHT_TICKET_ID = "id";
    public static final String COL_FLIGHT_TICKET_PAYMENT_METHOD = "payment_method";
    public static final String COL_FLIGHT_TICKET_STATE = "flight_state";
    public static final String COL_FLIGHT_TICKET_ORIGIN = "flight_origin";
    public static final String COL_FLIGHT_TICKET_ARRIVAL = "flight_arrival";
    public static final String COL_FLIGHT_TICKET_DEPARTURE_TIME = "flight_departure_time";
    public static final String COL_FLIGHT_TICKET_ARRIVAL_TIME = "flight_arrival_time";
    public static final String COL_FLIGHT_TICKET_TERMINAL = "flight_terminal";
    public static final String COL_FLIGHT_TICKET_ORIGINAL_PRICE = "flight_original_price";
    public static final String COL_FLIGHT_TICKET_PAID_PRICE = "paid_price";
    public static final String COL_FLIGHT_TICKET_FLIGHT_DATE = "flight_date";
    public static final String COL_FLIGHT_TICKET_PURCHASE_DATE = "purchase_date";
    public static final String COL_FLIGHT_TICKET_DISTANCE = "distance";
    public static final String COL_FLIGHT_TICKET_CHECK_IN = "check_in";

    // Campos da tabela passenger
    public static final String COL_PASSENGER_ID = "id";
    public static final String COL_PASSENGER_NAME = "name";
    public static final String COL_PASSENGER_GENDER = "gender";
    public static final String COL_PASSENGER_SEAT = "seat";
    public static final String COL_PASSENGER_EXTRA_BAGGAGE = "extra_baggage";
    public static final String COL_PASSENGER_FLIGHT_TICKET_ID = "flight_ticket_id";


    // Sql para criar a tabela flight ticket
    private static final String CREATE_TBL_FLIGHT_TICKET =
            "CREATE TABLE " + TBL_FLIGHT_TICKET + "( " +
                    COL_FLIGHT_TICKET_ID + " INTEGER PRIMARY KEY NOT NULL, " +
                    COL_FLIGHT_TICKET_PAYMENT_METHOD + " TEXT NOT NULL," +
                    COL_FLIGHT_TICKET_STATE + " TEXT NOT NULL," +
                    COL_FLIGHT_TICKET_ORIGIN + " TEXT NOT NULL, " +
                    COL_FLIGHT_TICKET_ARRIVAL + " TEXT NOT NULL," +
                    COL_FLIGHT_TICKET_DEPARTURE_TIME + " TEXT NOT NULL," +
                    COL_FLIGHT_TICKET_ARRIVAL_TIME + " TEXT NOT NULL," +
                    COL_FLIGHT_TICKET_TERMINAL + " TEXT NOT NULL," +
                    COL_FLIGHT_TICKET_ORIGINAL_PRICE + " DOUBLE NOT NULL," +
                    COL_FLIGHT_TICKET_PAID_PRICE + " DOUBLE NOT NULL," +
                    COL_FLIGHT_TICKET_FLIGHT_DATE + " TEXT NOT NULL," +
                    COL_FLIGHT_TICKET_PURCHASE_DATE + " DATETIME NOT NULL," +
                    COL_FLIGHT_TICKET_DISTANCE + " FLOAT NOT NULL," +
                    COL_FLIGHT_TICKET_CHECK_IN + " BOOLEAN NOT NULL " +
                    ");";

    // Sql para criar a tabela passenger
    private static final String CREATE_TBL_PASSENGER =
            "CREATE TABLE " + TBL_PASSENGER +  "( " +
                    COL_PASSENGER_ID + " INTEGER PRIMARY KEY NOT NULL, " +
                    COL_PASSENGER_NAME + " TEXT NOT NULL," +
                    COL_PASSENGER_GENDER + " TEXT NOT NULL," +
                    COL_PASSENGER_SEAT + " VARCHAR(3) NOT NULL, " +
                    COL_PASSENGER_EXTRA_BAGGAGE + " BOOLEAN NOT NULL," +
                    COL_PASSENGER_FLIGHT_TICKET_ID + " INTEGER NOT NULL," +
                    "FOREIGN KEY (" + COL_PASSENGER_FLIGHT_TICKET_ID + ") REFERENCES " + TBL_FLIGHT_TICKET + "(" + COL_FLIGHT_TICKET_ID + ")" +
                    ");";




    private final SQLiteDatabase database;

    public UserDBOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.database = this.getWritableDatabase();
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TBL_FLIGHT_TICKET);
        db.execSQL(CREATE_TBL_PASSENGER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TBL_PASSENGER);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_FLIGHT_TICKET);
        this.onCreate(db);
    }
}
