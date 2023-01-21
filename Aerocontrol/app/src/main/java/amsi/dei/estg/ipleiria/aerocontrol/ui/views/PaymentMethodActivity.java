package amsi.dei.estg.ipleiria.aerocontrol.ui.views;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import amsi.dei.estg.ipleiria.aerocontrol.R;

public class PaymentMethodActivity extends AppCompatActivity {

    public static final String FLIGHT_GO_ID = "flight_go_id";
    public static final String FLIGHT_BACK_ID = "flight_back_id";
    public static final String NUM_PASSENGERS = "num_passengers";
    public static final String TWO_WAY_TRIP = "two_way_trip";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.PaymentMethod_Toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}