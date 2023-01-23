package amsi.dei.estg.ipleiria.aerocontrol.ui.views;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import amsi.dei.estg.ipleiria.aerocontrol.R;
import amsi.dei.estg.ipleiria.aerocontrol.adapters.TicketInfoPassengersAdapter;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.FlightTicket;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.singletons.SingletonUser;
import amsi.dei.estg.ipleiria.aerocontrol.databinding.ActivityTicketInfoBinding;
import amsi.dei.estg.ipleiria.aerocontrol.listeners.TicketListener;

public class TicketInfoActivity extends AppCompatActivity implements TicketListener {

    public static final String TICKET_ID = "ticket_id";

    private FlightTicket ticket;

    private ActivityTicketInfoBinding binding;

    private TicketInfoPassengersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTicketInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.TicketInfoToolbar.getRoot());
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SingletonUser.getInstance(this).setTicketListener(this);

        getTicketId();
    }

    private void getTicketId() {
        int idTicket = getIntent().getIntExtra(TICKET_ID,-1);

        if (idTicket != -1){
            ticket = SingletonUser.getInstance(this).getTicketById(idTicket);
            binding.TicketInfoBtCheckIn.setOnClickListener(v -> SingletonUser.getInstance(this).updateTicketAPI(this,ticket));
            binding.TicketInfoBtCancel.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(TicketInfoActivity.this);
                builder.setTitle(R.string.cancel_ticket);
                builder.setMessage("Se deseja realmente apagar o seu bilhete por favor confirme abaixo.");
                builder.setPositiveButton(R.string.confirm, (dialog, which) -> {
                    SingletonUser.getInstance(this).deleteTicketAPI(this, ticket);
                });
                builder.setNegativeButton(R.string.cancel,(dialog,which) -> {});
                builder.show();
            });
            ticketDetails();
        } else Toast.makeText(this, R.string.error_on_ticket, Toast.LENGTH_SHORT).show();
    }

    private void ticketDetails() {
        binding.TicketInfoTvDate.setText(ticket.getFlightDate());
        binding.TicketInfoTvState.setText(ticket.getFlightState());
        binding.TicketInfoTvDepartureCity.setText(ticket.getFlightOrigin());
        binding.TicketInfoTvArrivalCity.setText(ticket.getFlightArrival());
        binding.TicketInfoTvDepartureTime.setText(ticket.getFlightDepartureTime());
        binding.TicketInfoTvArrivalTime.setText(ticket.getFlightArrivalTime());
        binding.TicketInfoTvDistance.setText(getString(R.string.km,ticket.getDistance()));
        binding.TicketInfoTvTerminal.setText(ticket.getTerminal());
        if (ticket.getOriginalPrice() == ticket.getPricePaid()) binding.TicketInfoTvPrice.setText("");
        else{
            binding.TicketInfoTvPrice.setText(getString(R.string.euro_symbol,ticket.getOriginalPrice()));
            binding.TicketInfoTvPrice.setPaintFlags(binding.TicketInfoTvPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        binding.TicketInfoTvPriceDiscount.setText(getString(R.string.euro_symbol,ticket.getPricePaid()));
        binding.TicketInfoTvPurchaseDate.setText(ticket.getPurchaseDate());

        if (ticket.getPassengers().size() > 0){
            binding.TicketInfoRvPassengers.setLayoutManager(new LinearLayoutManager(this));
            adapter = new TicketInfoPassengersAdapter(this, ticket.getPassengers());
            binding.TicketInfoRvPassengers.setAdapter(adapter);
            binding.TicketInfoRvPassengers.setItemAnimator(new DefaultItemAnimator());
        }
        if (ticket.isCheckIn()){
            binding.TicketInfoBtCheckIn.setVisibility(View.GONE);
            binding.TicketInfoBtCancel.setVisibility(View.GONE);
        }
    }

    @Override
    public void onRefreshTicket() {
        binding.TicketInfoBtCheckIn.setVisibility(View.GONE);
        binding.TicketInfoBtCancel.setVisibility(View.GONE);
    }

    @Override
    public void onDeleteTicket() {
        binding.TicketInfoBtCheckIn.setVisibility(View.GONE);
        binding.TicketInfoBtCancel.setVisibility(View.GONE);
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}