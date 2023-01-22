package amsi.dei.estg.ipleiria.aerocontrol.ui.views.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.aerocontrol.R;
import amsi.dei.estg.ipleiria.aerocontrol.adapters.FlightTicketAdapter;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.FlightTicket;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.singletons.SingletonUser;
import amsi.dei.estg.ipleiria.aerocontrol.databinding.ActivityTicketsBinding;
import amsi.dei.estg.ipleiria.aerocontrol.listeners.TicketsListener;

public class TicketsActivity extends AppCompatActivity implements TicketsListener {

    private ActivityTicketsBinding binding;

    private FlightTicketAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTicketsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.TicketsToolbar.getRoot());
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.TicketsRvMyTickets.setLayoutManager(new LinearLayoutManager(this));

        SingletonUser.getInstance(this).setTicketsListener(this);
        SingletonUser.getInstance(this).getTicketsAPI(this);
    }

    public void showTicketDetails(int id) {
        FlightTicket ticket = SingletonUser.getInstance(this).getTicketById(id);
        Intent intent = new Intent(this, TicketInfoActivity.class);
        intent.putExtra(TicketInfoActivity.TICKET_ID, (int) ticket.getId());
        startActivityForResult(intent,1);
    }

    @Override
    public void onRefreshList(ArrayList<FlightTicket> tickets) {
        adapter = new FlightTicketAdapter(this, SingletonUser.getInstance(this).getTickets());
        binding.TicketsRvMyTickets.setAdapter(adapter);
        binding.TicketsRvMyTickets.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1)
            if (resultCode == Activity.RESULT_OK) {
                onRefreshList(SingletonUser.getInstance(this).getTickets());
            }
    }
}