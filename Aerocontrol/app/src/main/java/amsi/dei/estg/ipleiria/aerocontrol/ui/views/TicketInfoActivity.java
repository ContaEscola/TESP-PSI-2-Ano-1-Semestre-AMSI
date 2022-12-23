package amsi.dei.estg.ipleiria.aerocontrol.ui.views;

import android.graphics.Paint;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import amsi.dei.estg.ipleiria.aerocontrol.R;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.FlightTicket;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.singletons.SingletonUser;

public class TicketInfoActivity extends AppCompatActivity {

    public static final String TICKET_ID = "ticket_id";

    private FlightTicket ticket;

    private TextView tvDate, tvState, tvDeparture, tvArrival, tvDepartureTime, tvArrivalTime,
            tvDistance, tvTerminal, tvOriginalPrice, tvPaidPrice, tvPurchaseDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_info);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.TicketInfo_Toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initialize();
        getTicketId();
    }

    private void initialize() {
        tvDate = findViewById(R.id.TicketInfo_Tv_Date);
        tvState = findViewById(R.id.TicketInfo_Tv_State);
        tvDeparture = findViewById(R.id.TicketInfo_Tv_DepartureCity);
        tvArrival = findViewById(R.id.TicketInfo_Tv_ArrivalCity);
        tvDepartureTime = findViewById(R.id.TicketInfo_Tv_DepartureTime);
        tvArrivalTime = findViewById(R.id.TicketInfo_Tv_ArrivalTime);
        tvDistance = findViewById(R.id.TicketInfo_Tv_Distance);
        tvTerminal = findViewById(R.id.TicketInfo_Tv_Terminal);
        tvOriginalPrice = findViewById(R.id.TicketInfo_Tv_Price);
        tvPaidPrice = findViewById(R.id.TicketInfo_Tv_PriceDiscount);
        tvPurchaseDate = findViewById(R.id.TicketInfo_Tv_PurchaseDate);
    }

    private void getTicketId() {
        int idTicket = getIntent().getIntExtra(TICKET_ID,-1);

        if (idTicket != -1){
            ticket = SingletonUser.getInstance(this).getTicketById(idTicket);
            ticketDetails();
        } else Toast.makeText(this, R.string.error_on_ticket, Toast.LENGTH_SHORT).show();
    }

    private void ticketDetails() {
        tvDate.setText(ticket.getFlightDate());
        tvState.setText(ticket.getFlightState());
        tvDeparture.setText(ticket.getFlightOrigin());
        tvArrival.setText(ticket.getFlightArrival());
        tvDepartureTime.setText(ticket.getFlightDepartureTime());
        tvArrivalTime.setText(ticket.getFlightArrivalTime());
        tvDistance.setText(getString(R.string.km,ticket.getDistance()));
        tvTerminal.setText(ticket.getTerminal());
        if (ticket.getOriginalPrice() == ticket.getPricePaid()) tvOriginalPrice.setText("");
        else{
            tvOriginalPrice.setText(getString(R.string.euro_symbol,ticket.getOriginalPrice()));
            tvOriginalPrice.setPaintFlags(tvOriginalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        tvPaidPrice.setText(getString(R.string.euro_symbol,ticket.getPricePaid()));
        tvPurchaseDate.setText(ticket.getPurchaseDate());

    }
}