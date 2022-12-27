package amsi.dei.estg.ipleiria.aerocontrol.ui.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.aerocontrol.R;
import amsi.dei.estg.ipleiria.aerocontrol.adapters.FlightTicketAdapter;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.FlightTicket;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.singletons.SingletonUser;
import amsi.dei.estg.ipleiria.aerocontrol.listeners.TicketsListener;

public class TicketsActivity extends AppCompatActivity implements TicketsListener {

    private RecyclerView recyclerView;
    private FlightTicketAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.Tickets_Toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.Tickets_Rv_MyTickets);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
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