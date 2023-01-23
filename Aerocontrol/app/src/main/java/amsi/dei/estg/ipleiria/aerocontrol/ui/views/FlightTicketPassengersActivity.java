package amsi.dei.estg.ipleiria.aerocontrol.ui.views;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import amsi.dei.estg.ipleiria.aerocontrol.R;

public class FlightTicketPassengersActivity extends AppCompatActivity {

    public static final String FLIGHT_GO_ID = "flight_go_id";
    public static final String FLIGHT_BACK_ID = "flight_back_id";
    public static final String NUM_PASSENGERS = "num_passengers";
    public static final String TWO_WAY_TRIP = "two_way_trip";

    public static final String FLIGHT_GO_ID = "flight_go_id";
    public static final String FLIGHT_BACK_ID = "flight_back_id";
    public static final String NUM_PASSENGERS = "num_passengers";
    public static final String TWO_WAY_TRIP = "two_way_trip";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_ticket_passengers);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.FlightTicketPassengers_Toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int flightGoId = getIntent().getIntExtra(FLIGHT_GO_ID,-1);
        int flightBackId = getIntent().getIntExtra(FLIGHT_BACK_ID,-1);
        int num_passengers = getIntent().getIntExtra(NUM_PASSENGERS,-1);
        boolean two_way_trip = getIntent().getBooleanExtra(TWO_WAY_TRIP,false);
    }
}