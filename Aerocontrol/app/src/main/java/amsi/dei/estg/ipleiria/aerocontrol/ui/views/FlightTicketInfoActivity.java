package amsi.dei.estg.ipleiria.aerocontrol.ui.views;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import amsi.dei.estg.ipleiria.aerocontrol.R;
import amsi.dei.estg.ipleiria.aerocontrol.ui.adapters.RecyclerViewFlightTicketPassengersAdapter;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.FlightTicket;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.singletons.SingletonUser;
import amsi.dei.estg.ipleiria.aerocontrol.listeners.FlightTicketListener;
import amsi.dei.estg.ipleiria.aerocontrol.utils.DateDisplayFormatUtils;

public class FlightTicketInfoActivity extends AppCompatActivity implements FlightTicketListener {

    public static final String TICKET_ID = "ticket_id";

    private FlightTicket ticket;

    private TextView tvDate, tvState, tvDeparture, tvArrival, tvDepartureTime, tvArrivalTime,
            tvDistance, tvTerminal, tvOriginalPrice, tvPaidPrice, tvPurchaseDate;
    private Button btCheckIn, btCancel;

    private RecyclerView recyclerView;
    private RecyclerViewFlightTicketPassengersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_info);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.TicketInfo_Toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SingletonUser.getInstance(this).setFlightTicketListener(this);

        initialize();
        getFlightTicketIdFromIntent();
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

        recyclerView = findViewById(R.id.TicketInfo_Rv_Passengers);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        btCheckIn = findViewById(R.id.TicketInfo_Bt_CheckIn);
        btCancel = findViewById(R.id.TicketInfo_Bt_Cancel);
    }

    private void getFlightTicketIdFromIntent() {
        int idTicket = getIntent().getIntExtra(TICKET_ID,-1);

        if (idTicket != -1){
            ticket = SingletonUser.getInstance(this).getFlightTicketById(idTicket);
            btCheckIn.setOnClickListener(v -> SingletonUser.getInstance(this).updateFlightTicketAPI(this,ticket));
            btCancel.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(FlightTicketInfoActivity.this);
                builder.setTitle(R.string.cancel_ticket);
                builder.setMessage("Se deseja realmente apagar o seu bilhete por favor confirme abaixo.");
                builder.setPositiveButton(R.string.confirm, (dialog, which) -> {
                    SingletonUser.getInstance(this).deleteFlightTicketAPI(this, ticket);
                });
                builder.setNegativeButton(R.string.cancel,(dialog,which) -> {});
                builder.show();
            });
            setFlightTicketInView();
        } else Toast.makeText(this, R.string.error_on_ticket, Toast.LENGTH_SHORT).show();
    }

    private void setFlightTicketInView() {
        tvDate.setText(ticket.getFlightDate());
        tvState.setText(ticket.getFlightState());
        tvDeparture.setText(ticket.getFlightOrigin());
        tvArrival.setText(ticket.getFlightArrival());
        tvDepartureTime.setText(ticket.getFlightDepartureTime());
        tvArrivalTime.setText(ticket.getFlightArrivalTime());
        tvDistance.setText(getString(R.string.km,ticket.getDistance()));
        tvTerminal.setText(ticket.getTerminal());
        if (ticket.getOriginalPrice() == ticket.getPaidPrice()) tvOriginalPrice.setText("");
        else{
            tvOriginalPrice.setText(getString(R.string.euro_symbol,ticket.getOriginalPrice()));
            tvOriginalPrice.setPaintFlags(tvOriginalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        tvPaidPrice.setText(getString(R.string.euro_symbol,ticket.getPaidPrice()));
        tvPurchaseDate.setText(DateDisplayFormatUtils.formatDateTimeToString(ticket.getPurchaseDate()));

        if (ticket.getPassengers().size() > 0){
            adapter = new RecyclerViewFlightTicketPassengersAdapter(this, ticket.getPassengers());
            recyclerView.setAdapter(adapter);
        }
        if (ticket.isCheckIn()){
            hideActionBtns();
        }
    }

    private void hideActionBtns() {
        btCheckIn.setVisibility(View.GONE);
        btCancel.setVisibility(View.GONE);
    }

    @Override
    public void onRefreshTicket() {
        hideActionBtns();
    }

    @Override
    public void onDeleteTicket() {
        hideActionBtns();
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}