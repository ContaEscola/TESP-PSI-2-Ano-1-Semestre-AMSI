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
import amsi.dei.estg.ipleiria.aerocontrol.ui.adapters.RecyclerViewFlightTicketsAdapter;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.FlightTicket;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.singletons.SingletonUser;
import amsi.dei.estg.ipleiria.aerocontrol.listeners.FlightTicketsListener;

public class FlightTicketsActivity extends AppCompatActivity implements FlightTicketsListener {

    private static final int REQUEST_FLIGHT_TICKET_INFO = 1;

    private RecyclerView recyclerView;
    private RecyclerViewFlightTicketsAdapter adapter;

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
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        SingletonUser.getInstance(this).setFlightTicketsListener(this);
        SingletonUser.getInstance(this).getFlightTicketsAPI(this);
    }

    public void showTicketDetails(int id) {
        FlightTicket ticket = SingletonUser.getInstance(this).getFlightTicketById(id);
        Intent intent = new Intent(this, FlightTicketInfoActivity.class);
        intent.putExtra(FlightTicketInfoActivity.TICKET_ID, ticket.getId());
        startActivityForResult(intent,REQUEST_FLIGHT_TICKET_INFO);
    }

    @Override
    public void onRefreshList(ArrayList<FlightTicket> tickets) {
        adapter = new RecyclerViewFlightTicketsAdapter(this, tickets);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_FLIGHT_TICKET_INFO) {
            if (resultCode == Activity.RESULT_OK) {
                onRefreshList(SingletonUser.getInstance(this).getFlightTickets());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}